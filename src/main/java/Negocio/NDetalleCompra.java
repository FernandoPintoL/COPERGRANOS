package Negocio;

import Data.DDetalleCompra;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author angela
 */
public class NDetalleCompra {

    private DDetalleCompra NEGOCIO_DETALLECOMPRA;

    public NDetalleCompra() {}

    public Object[] guardar(Double cantidad, Double precio_unitario, Double sub_total, int compra_id, int producto_id) throws SQLException, ParseException {
        NEGOCIO_DETALLECOMPRA = new DDetalleCompra(cantidad, precio_unitario, sub_total, compra_id, producto_id);
        Object[] response = NEGOCIO_DETALLECOMPRA.guardar();
        NEGOCIO_DETALLECOMPRA.desconectar();
        return response;
    }

    public Object[] modificar(int id_detellacompra, Double cantidad, Double precio_unitario, Double sub_total) throws SQLException, ParseException {
        NEGOCIO_DETALLECOMPRA = new DDetalleCompra(id_detellacompra, cantidad, precio_unitario, sub_total);
        Object[] response = NEGOCIO_DETALLECOMPRA.modificar();
        NEGOCIO_DETALLECOMPRA.desconectar();
        return response;
    }

    public boolean eliminar(int id_detellacompra) throws SQLException {
        NEGOCIO_DETALLECOMPRA = new DDetalleCompra(id_detellacompra);
        boolean response = NEGOCIO_DETALLECOMPRA.eliminar();
        NEGOCIO_DETALLECOMPRA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_DETALLECOMPRA = new DDetalleCompra();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_DETALLECOMPRA.listar();
        NEGOCIO_DETALLECOMPRA.desconectar();
        return categoria;
    }

    public String[] ver(int id_detellacompra) throws SQLException {
        NEGOCIO_DETALLECOMPRA = new DDetalleCompra(id_detellacompra);
        String[] data = NEGOCIO_DETALLECOMPRA.ver();
        NEGOCIO_DETALLECOMPRA.desconectar();
        return data;
    }
}
