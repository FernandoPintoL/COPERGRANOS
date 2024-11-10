package NEGOCIO;

import DATA.DProveedor;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angela
 */
public class NProveedor {

    private DProveedor DATA_PROVEEDOR;

    public NProveedor() {}

    public Object[]  guardar(String nombre, String direccion, int telefono, String correo, int ci, String tipo_proveedor, String descripcion) throws SQLException, ParseException {
        DATA_PROVEEDOR = new DProveedor(nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion);
        Object[] response = DATA_PROVEEDOR.guardar();
        DATA_PROVEEDOR.desconectar();
        return response;
    }

    public Object[]  modificar(int id_proveedor, String nombre, String direccion, int telefono, String correo, int ci, String tipo_proveedor, String descripcion) throws SQLException, ParseException {
        DATA_PROVEEDOR = new DProveedor(id_proveedor, nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion);
        Object[] response = DATA_PROVEEDOR.modificar();
        DATA_PROVEEDOR.desconectar();
        return response;
    }

    public boolean eliminar(int id_proveedor) throws SQLException {
        DATA_PROVEEDOR = new DProveedor(id_proveedor);
        boolean response = DATA_PROVEEDOR.eliminar();
        DATA_PROVEEDOR.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_PROVEEDOR = new DProveedor();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_PROVEEDOR.listar();
        DATA_PROVEEDOR.desconectar();
        return categoria;
    }

    public String[] ver(int id_proveedor) throws SQLException {
        DATA_PROVEEDOR = new DProveedor(id_proveedor);
        String[] data = DATA_PROVEEDOR.ver();
        DATA_PROVEEDOR.desconectar();
        return data;
    }
}
