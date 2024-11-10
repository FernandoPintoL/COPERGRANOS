package NEGOCIO;

import DATA.DPersona;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author angela
 */
public class NPersona {

    private DPersona DATA_PERSONA;
    private final String TABLE = " PERSONA";
    private final String MESSAGE_TRYCATCH = "ERROR NEGOCIO: " + TABLE;

    public NPersona() {
        //Persona = new DPersona();
    }

    public Object[] guardar(String nombre, String apellido, String direccion, int telefono, String correo, int ci) throws SQLException, ParseException {
        // Validaciones antes de intentar guardar
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 2) {
            return new Object[]{false, "El nombre es requerido. || Mayor a 3 caracteres.", null};
        }
        if (apellido == null || apellido.trim().isEmpty() || apellido.length() <= 2) {
            return new Object[]{false, "El apellido es requerido. || Mayor a 3 caracteres.", null};
        }
        if (ci <= 0 || String.valueOf(ci).length() <= 2) {
            return new Object[]{false, "El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres", null};
        }
        try {
            // Intentar guardar los datos
            DATA_PERSONA = new DPersona(nombre, direccion, telefono, correo, ci);
            Object[] respuestaGuardado = DATA_PERSONA.guardar();
            DATA_PERSONA.desconectar(); // Llamada a desconectar después de guardar
            System.out.println("RETORNO DEL MODELO EN NEGOCIO PERSONA");
            System.out.println(Arrays.toString(respuestaGuardado));
            // Si el guardado fue exitoso
            boolean isSuccess = (boolean) respuestaGuardado[0];
            String mensajeGuardado = (String) respuestaGuardado[1];
            int id_persona = (int) respuestaGuardado[2];  // Supongamos que es el objeto registrado

            // Retornar el resultado de guardar
            return new Object[]{isSuccess, mensajeGuardado, id_persona};

        } catch (SQLException e) {
            // Imprimir detalles de la excepción SQLException
            System.out.println(MESSAGE_TRYCATCH + " GUARDAR");
            e.printStackTrace(); // Imprime toda la traza del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
            System.out.println("Estado SQL: " + e.getSQLState()); // Muestra el estado SQL
            System.out.println("Código de error: " + e.getErrorCode()); // Muestra el código de error SQL
            return new Object[]{false, "(GUARDAR) Error en la base de datos: " + e.getMessage(), null};

        } catch (ParseException e) {
            // Imprimir detalles de la excepción ParseException
            System.out.println("Error al parsear los datos:");
            e.printStackTrace(); // Imprime la traza completa del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
            return new Object[]{false, "(GUARDAR) Error al parsear los datos: " + e.getMessage(), null};
        }
    }

    public Object[] modificar(int id, String nombre, String apellido, String direccion, int telefono, String correo, int ci) throws SQLException, ParseException {
        try {
            // Intentar guardar los datos
            DATA_PERSONA = new DPersona(id, nombre, direccion, telefono, correo, ci);
            Object[] response = DATA_PERSONA.modificar();
            DATA_PERSONA.desconectar(); // Llamada a desconectar después de guardar
            return response;
        } catch (SQLException e) {
            // Imprimir detalles de la excepción SQLException
            System.out.println(MESSAGE_TRYCATCH + " MODIFICAR");
            e.printStackTrace(); // Imprime toda la traza del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
            System.out.println("Estado SQL: " + e.getSQLState()); // Muestra el estado SQL
            System.out.println("Código de error: " + e.getErrorCode()); // Muestra el código de error SQL
            return new Object[]{false, MESSAGE_TRYCATCH+" (MODIFICAR) Error en la base de datos: " + e.getMessage(), null};

        } catch (ParseException e) {
            // Imprimir detalles de la excepción ParseException
            System.out.println("Error al parsear los datos:");
            e.printStackTrace(); // Imprime la traza completa del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
            return new Object[]{false, MESSAGE_TRYCATCH+" (MODIFICAR) Error al parsear los datos: " + e.getMessage(), null};
        }
    }

    public boolean eliminar(int id) throws SQLException {
        // Intentar guardar los datos
        DATA_PERSONA = new DPersona(id);
        boolean response = DATA_PERSONA.eliminar();
        DATA_PERSONA.desconectar(); // Llamada a desconectar después de guardar
        return response;
    }

    public List<String[]> listar() throws SQLException {
        ArrayList<String[]> listado = new ArrayList<>();
        try {
            // Intentar guardar los datos
            DATA_PERSONA = new DPersona();
            listado = (ArrayList<String[]>) DATA_PERSONA.listar();
            DATA_PERSONA.desconectar();

        } catch (ClassCastException e) {
            // Manejar posibles errores de conversión de tipo
            System.out.println(MESSAGE_TRYCATCH + " LISTAR");
            System.out.println("Error al convertir el tipo de listado: " + e.getMessage());
            e.printStackTrace();

        }
        return listado;
    }

    public String[] ver(int id) throws SQLException {
        String[] data = null;
        try {
            DATA_PERSONA = new DPersona(id);
            // Intentar obtener los datos de la categoría
            data = DATA_PERSONA.ver();
            //dCategoria.desconectar();

        } catch (SQLException e) {
            // Manejar la excepción SQL
            System.out.println(MESSAGE_TRYCATCH + " VER");
            System.out.println("Error al obtener la categoría con ID: " + id);
            e.printStackTrace(); // Imprimir la traza completa del error
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Estado SQL: " + e.getSQLState());
            System.out.println("Código de error: " + e.getErrorCode());

        } finally {
            // Asegurarse de cerrar la conexión, incluso si ocurre un error
            DATA_PERSONA.desconectar();
        }
        return data;
    }
}
