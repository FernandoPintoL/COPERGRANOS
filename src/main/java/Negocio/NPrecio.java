package Negocio;

import Data.DPrecio;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NPrecio {

    private DPrecio NEGOCIO_PRECIO;

    public NPrecio() {
    }

    public Object[] guardar(Double precio, String detalle) throws SQLException, ParseException {
        NEGOCIO_PRECIO = new DPrecio(precio, detalle);
        Object[] response = NEGOCIO_PRECIO.guardar();
        NEGOCIO_PRECIO.desconectar();
        return response;
    }

    public Object[] modificar(int id, Double precio, String detalle) throws SQLException, ParseException {
        NEGOCIO_PRECIO = new DPrecio(id, precio, detalle);
        Object[] response = NEGOCIO_PRECIO.modificar();
        NEGOCIO_PRECIO.desconectar();
        return response;
    }

    public boolean eliminar(int id) throws SQLException {
        NEGOCIO_PRECIO = new DPrecio(id);
        boolean response = NEGOCIO_PRECIO.eliminar();
        NEGOCIO_PRECIO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_PRECIO = new DPrecio();
        ArrayList<String[]> precio = (ArrayList<String[]>) NEGOCIO_PRECIO.listar();
        NEGOCIO_PRECIO.desconectar();
        return precio;
    }

    public String[] ver(int id) throws SQLException {
        NEGOCIO_PRECIO = new DPrecio(id);
        String[] precio = NEGOCIO_PRECIO.ver();
        NEGOCIO_PRECIO.desconectar();
        return precio;
    }
}
