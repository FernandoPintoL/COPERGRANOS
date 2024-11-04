package Negocio;

import Data.DAlmacen;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NAlmacen {

    private DAlmacen NEGOCIO_ALMACEN;

    private final String TABLE = " ALMACEN";
    private final String MESSAGE_TRYCATCH = "ERROR NEGOCIO: " + TABLE;

    public NAlmacen() {
    }

    public Object[] guardar(int codigo, String direccion) throws SQLException, ParseException {
        try {
            // Intentar guardar los datos
            NEGOCIO_ALMACEN = new DAlmacen(codigo, direccion);
            Object[] response = NEGOCIO_ALMACEN.guardar();
            NEGOCIO_ALMACEN.desconectar(); // Llamada a desconectar después de guardar
            return response;
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
        return new Object[]{false, " (NEGOCIO) ERROR GUARDAR ", null};
    }

    public Object[] modificar(int id, int codigo, String direccion) throws SQLException, ParseException {
        try {
            // Intentar guardar los datos
            NEGOCIO_ALMACEN = new DAlmacen(id, codigo, direccion);
            Object[] response = NEGOCIO_ALMACEN.modificar();
            NEGOCIO_ALMACEN.desconectar(); // Llamada a desconectar después de guardar
            return response;
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
        return new Object[]{false, " (NEGOCIO) ERROR MODIFICAR ", null};
    }

    public boolean eliminar(int id) {
        // Intentar guardar los datos
        NEGOCIO_ALMACEN = new DAlmacen(id);
        boolean delete = NEGOCIO_ALMACEN.eliminar();
        NEGOCIO_ALMACEN.desconectar(); // Llamada a desconectar después de guardar
        return delete;
    }

    public List<String[]> listar() throws SQLException {
        ArrayList<String[]> listado = new ArrayList<>();
        try {
            // Intentar guardar los datos
            NEGOCIO_ALMACEN = new DAlmacen();
            listado = (ArrayList<String[]>) NEGOCIO_ALMACEN.listar();
            NEGOCIO_ALMACEN.desconectar();

        } catch (ClassCastException e) {
            // Manejar posibles errores de conversión de tipo
            System.out.println(MESSAGE_TRYCATCH + " LISTAR");
            System.out.println("Error al convertir el tipo de listado: " + e.getMessage());
            e.printStackTrace();

        }
        return listado;
    }

    public String[] ver(int id) throws SQLException {
        NEGOCIO_ALMACEN = new DAlmacen(id);
        String[] data = NEGOCIO_ALMACEN.ver();
        System.out.println(Arrays.toString(data));
        NEGOCIO_ALMACEN.desconectar();
        return data;
    }
}
