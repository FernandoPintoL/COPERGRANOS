package NEGOCIO;

import DATA.DPrecio;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NPrecio {

    private DPrecio DATA_PRECIO;

    public NPrecio() {
    }

    public Object[] guardar(Double precio, String detalle) throws SQLException, ParseException {
        DATA_PRECIO = new DPrecio(precio, detalle);
        Object[] response = DATA_PRECIO.guardar();
        DATA_PRECIO.desconectar();
        return response;
    }

    public Object[] modificar(int id, Double precio, String detalle) throws SQLException, ParseException {
        DATA_PRECIO = new DPrecio(id, precio, detalle);
        Object[] response = DATA_PRECIO.modificar();
        DATA_PRECIO.desconectar();
        return response;
    }

    public boolean eliminar(int id) throws SQLException {
        DATA_PRECIO = new DPrecio(id);
        boolean response = DATA_PRECIO.eliminar();
        DATA_PRECIO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_PRECIO = new DPrecio();
        ArrayList<String[]> precio = (ArrayList<String[]>) DATA_PRECIO.listar();
        DATA_PRECIO.desconectar();
        return precio;
    }

    public String[] ver(int id) throws SQLException {
        DATA_PRECIO = new DPrecio(id);
        String[] precio = DATA_PRECIO.ver();
        DATA_PRECIO.desconectar();
        return precio;
    }
}
