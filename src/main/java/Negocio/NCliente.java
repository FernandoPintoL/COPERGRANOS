package Negocio;

import Data.DCliente;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author angela
 */
public class NCliente {

    private DCliente NEGOCIO_CLIENTE;

    public NCliente() {}

    public Object[] guardar(String nombre, String direccion, int telefono, String correo, int ci, String tipo_cliente) throws SQLException, ParseException {
        NEGOCIO_CLIENTE = new DCliente(tipo_cliente, nombre, direccion, telefono, correo, ci);
        Object[] response = NEGOCIO_CLIENTE.guardar();
        System.out.println(Arrays.toString(response));
        NEGOCIO_CLIENTE.desconectar();
        return response;
    }

    public Object[] modificar(int id_cliente, String nombre, String direccion, int telefono, String correo, int ci, String tipo_cliente) throws SQLException, ParseException {
        NEGOCIO_CLIENTE = new DCliente(ci, tipo_cliente, nombre, direccion, telefono, correo, ci);
        Object[] response = NEGOCIO_CLIENTE.modificar();
        NEGOCIO_CLIENTE.desconectar();
        return response;
    }

    public boolean eliminar(int id_cliente) throws SQLException {
        NEGOCIO_CLIENTE = new DCliente(id_cliente);
        boolean response = NEGOCIO_CLIENTE.eliminar();
        NEGOCIO_CLIENTE.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_CLIENTE = new DCliente();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_CLIENTE.listar();
        NEGOCIO_CLIENTE.desconectar();
        return categoria;
    }

    public String[] ver(int id_cliente) throws SQLException {
        NEGOCIO_CLIENTE = new DCliente(id_cliente);
        String[] data = NEGOCIO_CLIENTE.ver();
        NEGOCIO_CLIENTE.desconectar();
        return data;
    }
}
