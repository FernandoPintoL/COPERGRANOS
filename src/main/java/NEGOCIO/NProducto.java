package NEGOCIO;

import DATA.DProducto;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NProducto {

    private DProducto DATA_PRODUCTO;

    public NProducto() {}

    public Object[] guardar(String nombre, int codigo, String descripcion, Double precio, int categoria_id, int medida_id) throws SQLException, ParseException {
        DATA_PRODUCTO = new DProducto(nombre, codigo, descripcion, precio, categoria_id, medida_id);
        Object[] response = DATA_PRODUCTO.guardar();
        DATA_PRODUCTO.desconectar();
        return response;
    }

    public Object[] modificar(int id, String nombre, int codigo, String descripcion, Double precio, int categoria_id, int medida_id) throws SQLException, ParseException {
        DATA_PRODUCTO = new DProducto(id, nombre, codigo, descripcion, precio);
        Object[] response = DATA_PRODUCTO.modificar();
        DATA_PRODUCTO.desconectar();
        return response;
    }

    public boolean eliminar(int id) throws SQLException {
        DATA_PRODUCTO = new DProducto(id);
        boolean response = DATA_PRODUCTO.eliminar();
        DATA_PRODUCTO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_PRODUCTO = new DProducto();
        ArrayList<String[]> producto = (ArrayList<String[]>) DATA_PRODUCTO.listar();
        DATA_PRODUCTO.desconectar();
        return producto;
    }

    public String[] ver(int id) throws SQLException {
        DATA_PRODUCTO = new DProducto(id);
        String[] producto = DATA_PRODUCTO.ver();
        System.out.println("Producto: " + Arrays.toString(producto));
        DATA_PRODUCTO.desconectar();
        return producto;
    }
}
