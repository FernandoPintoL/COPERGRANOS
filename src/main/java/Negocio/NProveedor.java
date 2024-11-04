package Negocio;

import Data.DProveedor;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angela
 */
public class NProveedor {

    private DProveedor NEGOCIO_PROVEEDOR;

    public NProveedor() {}

    public Object[]  guardar(String nombre, String direccion, int telefono, String correo, int ci, String tipo_proveedor, String descripcion) throws SQLException, ParseException {
        NEGOCIO_PROVEEDOR = new DProveedor(nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion);
        Object[] response = NEGOCIO_PROVEEDOR.guardar();
        NEGOCIO_PROVEEDOR.desconectar();
        return response;
    }

    public Object[]  modificar(int id_proveedor, String nombre, String direccion, int telefono, String correo, int ci, String tipo_proveedor, String descripcion) throws SQLException, ParseException {
        NEGOCIO_PROVEEDOR = new DProveedor(id_proveedor, nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion);
        Object[] response = NEGOCIO_PROVEEDOR.modificar();
        NEGOCIO_PROVEEDOR.desconectar();
        return response;
    }

    public boolean eliminar(int id_proveedor) throws SQLException {
        NEGOCIO_PROVEEDOR = new DProveedor(id_proveedor);
        boolean response = NEGOCIO_PROVEEDOR.eliminar();
        NEGOCIO_PROVEEDOR.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_PROVEEDOR = new DProveedor();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_PROVEEDOR.listar();
        NEGOCIO_PROVEEDOR.desconectar();
        return categoria;
    }

    public String[] ver(int id_proveedor) throws SQLException {
        NEGOCIO_PROVEEDOR = new DProveedor(id_proveedor);
        String[] data = NEGOCIO_PROVEEDOR.ver();
        NEGOCIO_PROVEEDOR.desconectar();
        return data;
    }
}
