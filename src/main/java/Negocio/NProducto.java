package Negocio;

import Data.DProducto;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NProducto {

    private DProducto NEGOCIO_PRODUCTO;

    public NProducto() {}

    public Object[] guardar(String nombre, int codigo, String descripcion, Double precio, int categoria_id, int medida_id) throws SQLException, ParseException {
        NEGOCIO_PRODUCTO = new DProducto(nombre, codigo, descripcion, precio, categoria_id, medida_id);
        Object[] response = NEGOCIO_PRODUCTO.guardar();
        NEGOCIO_PRODUCTO.desconectar();
        return response;
    }

    public Object[] modificar(int id, String nombre, int codigo, String descripcion, Double precio, int categoria_id, int medida_id) throws SQLException, ParseException {
        NEGOCIO_PRODUCTO = new DProducto(id, nombre, codigo, descripcion, precio);
        Object[] response = NEGOCIO_PRODUCTO.modificar();
        NEGOCIO_PRODUCTO.desconectar();
        return response;
    }

    public boolean eliminar(int id) throws SQLException {
        NEGOCIO_PRODUCTO = new DProducto(id);
        boolean response = NEGOCIO_PRODUCTO.eliminar();
        NEGOCIO_PRODUCTO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_PRODUCTO = new DProducto();
        ArrayList<String[]> producto = (ArrayList<String[]>) NEGOCIO_PRODUCTO.listar();
        NEGOCIO_PRODUCTO.desconectar();
        return producto;
    }

    public String[] ver(int id) throws SQLException {
        NEGOCIO_PRODUCTO = new DProducto(id);
        String[] producto = NEGOCIO_PRODUCTO.ver();
        NEGOCIO_PRODUCTO.desconectar();
        return producto;
    }
}
