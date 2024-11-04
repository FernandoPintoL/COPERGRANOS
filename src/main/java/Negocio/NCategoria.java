package Negocio;

import Data.DCategoria;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author angela
 */
public class NCategoria {

    private DCategoria NEGOCIO_CATEGORIA;
    
    private final String TABLE = " CATEGORIA";
    private final String MESSAGE_TRYCATCH = "ERROR NEGOCIO: "+TABLE;

    public NCategoria() {}

    public Object[] guardar(String nombre, String descripcion) {
        Object[] response = null;
        try {
            NEGOCIO_CATEGORIA = new DCategoria(nombre, descripcion);
            response = NEGOCIO_CATEGORIA.guardar();
            System.out.println(Arrays.toString(response));
            NEGOCIO_CATEGORIA.desconectar(); // Llamada a desconectar después de guardar

        } catch (SQLException e) {
            // Imprimir detalles de la excepción SQLException
            System.out.println(MESSAGE_TRYCATCH + " GUARDAR");
            e.printStackTrace(); // Imprime toda la traza del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
            System.out.println("Estado SQL: " + e.getSQLState()); // Muestra el estado SQL
            System.out.println("Código de error: " + e.getErrorCode()); // Muestra el código de error SQL

        } catch (ParseException e) {
            // Imprimir detalles de la excepción ParseException
            System.out.println("Error al parsear los datos:");
            e.printStackTrace(); // Imprime la traza completa del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error

        }
        return response;
    }

    public Object[] modificar(int id, String nombre, String descripcion) {
        Object[] response = null;
        try {
            // Intentar guardar los datos
            NEGOCIO_CATEGORIA = new DCategoria(id, nombre, descripcion);
            response = NEGOCIO_CATEGORIA.modificar();
            NEGOCIO_CATEGORIA.desconectar(); // Llamada a desconectar después de guardar

        } catch (SQLException e) {
            // Imprimir detalles de la excepción SQLException
            System.out.println(MESSAGE_TRYCATCH + " MODIFICAR");
            e.printStackTrace(); // Imprime toda la traza del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
            System.out.println("Estado SQL: " + e.getSQLState()); // Muestra el estado SQL
            System.out.println("Código de error: " + e.getErrorCode()); // Muestra el código de error SQL

        } catch (ParseException e) {
            // Imprimir detalles de la excepción ParseException
            System.out.println("Error al parsear los datos:");
            e.printStackTrace(); // Imprime la traza completa del error
            System.out.println("Mensaje: " + e.getMessage()); // Muestra el mensaje del error
        }
        return response;
    }

    public boolean eliminar(int id) {
        // Intentar guardar los datos
        NEGOCIO_CATEGORIA = new DCategoria(id);
        boolean response = NEGOCIO_CATEGORIA.eliminar();
        NEGOCIO_CATEGORIA.desconectar(); // Llamada a desconectar después de guardar
        return response;
    }

    public List<String[]> listar() {
        ArrayList<String[]> listado = new ArrayList<>();
        try {
            // Intentar guardar los datos
            NEGOCIO_CATEGORIA = new DCategoria();
            listado = (ArrayList<String[]>) NEGOCIO_CATEGORIA.listar();
            NEGOCIO_CATEGORIA.desconectar();

        } catch (ClassCastException e) {
            // Manejar posibles errores de conversión de tipo
            System.out.println(MESSAGE_TRYCATCH + " LISTAR");
            System.out.println("Error al convertir el tipo de listado: " + e.getMessage());
            e.printStackTrace();

        }
        return listado;
    }

    public String[] ver(int id) {
        NEGOCIO_CATEGORIA = new DCategoria(id);
        String[] data = NEGOCIO_CATEGORIA.ver();
        NEGOCIO_CATEGORIA.desconectar();
        return data;
    }
}
