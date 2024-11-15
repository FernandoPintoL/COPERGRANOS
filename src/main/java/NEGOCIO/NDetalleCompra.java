package NEGOCIO;

import DATA.DDetalleCompra;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author angela
 */
public class NDetalleCompra {

    private DDetalleCompra DATA_DETALLECOMPRA;

    public NDetalleCompra() {}

    public Object[] guardar(Double cantidad, Double precio_unitario, Double sub_total, int compra_id, int producto_id) throws SQLException, ParseException {
        DATA_DETALLECOMPRA = new DDetalleCompra(cantidad, precio_unitario, sub_total, compra_id, producto_id);
        Object[] response = DATA_DETALLECOMPRA.guardar();
        DATA_DETALLECOMPRA.desconectar();
        return response;
    }

    public Object[] modificar(int id_detellacompra, Double cantidad, Double precio_unitario, Double sub_total) throws SQLException, ParseException {
        DATA_DETALLECOMPRA = new DDetalleCompra(id_detellacompra, cantidad, precio_unitario, sub_total);
        Object[] response = DATA_DETALLECOMPRA.modificar();
        DATA_DETALLECOMPRA.desconectar();
        return response;
    }
    public double totalCompra(int id_compra) throws SQLException, ParseException {
        DATA_DETALLECOMPRA = new DDetalleCompra();
        double total = DATA_DETALLECOMPRA.obtenerSumaSubtotal(id_compra);
        DATA_DETALLECOMPRA.desconectar();
        return total;
    }

    public boolean eliminar(int id_detellacompra) throws SQLException {
        DATA_DETALLECOMPRA = new DDetalleCompra(id_detellacompra);
        boolean response = DATA_DETALLECOMPRA.eliminar();
        DATA_DETALLECOMPRA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_DETALLECOMPRA = new DDetalleCompra();
        ArrayList<String[]> lista = (ArrayList<String[]>) DATA_DETALLECOMPRA.listar();
        DATA_DETALLECOMPRA.desconectar();
        return lista;
    }

    public List<String[]> listarToCompra(int id_compra) throws SQLException {
        DATA_DETALLECOMPRA = new DDetalleCompra();
        ArrayList<String[]> lista = (ArrayList<String[]>) DATA_DETALLECOMPRA.listToCompra(id_compra);
        DATA_DETALLECOMPRA.desconectar();
        return lista;
    }

    public String[] ver(int id_detellacompra) throws SQLException {
        DATA_DETALLECOMPRA = new DDetalleCompra(id_detellacompra);
        String[] data = DATA_DETALLECOMPRA.ver();
        DATA_DETALLECOMPRA.desconectar();
        return data;
    }
    
    public String[] existe_compra_producto(int compra_id, int producto_id) throws SQLException {
        DATA_DETALLECOMPRA = new DDetalleCompra(compra_id, producto_id);
        String[] data = DATA_DETALLECOMPRA.existe_compra_producto();
        DATA_DETALLECOMPRA.desconectar();
        return data;
    }
}
