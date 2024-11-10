package NEGOCIO;

import DATA.DCliente;
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

    private DCliente DATA_CLIENTE;

    public NCliente() {}

    public Object[] guardar(String nombre, String direccion, int telefono, String correo, int ci, String tipo_cliente) throws SQLException, ParseException {
        DATA_CLIENTE = new DCliente(tipo_cliente, nombre, direccion, telefono, correo, ci);
        Object[] response = DATA_CLIENTE.guardar();
        System.out.println(Arrays.toString(response));
        DATA_CLIENTE.desconectar();
        return response;
    }

    public Object[] modificar(int id_cliente, String nombre, String direccion, int telefono, String correo, int ci, String tipo_cliente) throws SQLException, ParseException {
        DATA_CLIENTE = new DCliente(ci, tipo_cliente, nombre, direccion, telefono, correo, ci);
        Object[] response = DATA_CLIENTE.modificar();
        DATA_CLIENTE.desconectar();
        return response;
    }

    public boolean eliminar(int id_cliente) throws SQLException {
        DATA_CLIENTE = new DCliente(id_cliente);
        boolean response = DATA_CLIENTE.eliminar();
        DATA_CLIENTE.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_CLIENTE = new DCliente();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_CLIENTE.listar();
        DATA_CLIENTE.desconectar();
        return categoria;
    }

    public String[] ver(int id_cliente) throws SQLException {
        DATA_CLIENTE = new DCliente(id_cliente);
        String[] data = DATA_CLIENTE.ver();
        DATA_CLIENTE.desconectar();
        return data;
    }
}
