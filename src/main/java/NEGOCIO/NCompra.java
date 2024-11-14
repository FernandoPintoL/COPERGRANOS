package NEGOCIO;

import DATA.DCompra;
import DATA.DDetalleCompra;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
/**
 *
 * @author angela
 */
public class NCompra {

    private DCompra DATA_COMPRA;

    public NCompra() {}

    public Object[] guardar(Double preciototal, String estado, int cliente_id, int administrativo_id) throws SQLException, ParseException {
        DATA_COMPRA = new DCompra(preciototal, estado, cliente_id, administrativo_id);
        Object[] response = DATA_COMPRA.guardar();
        DATA_COMPRA.desconectar();
        return response;
    }

    public Object[] modificar(int id_compra, Double preciototal, Date fechacompra,String estado) throws SQLException, ParseException {
        DATA_COMPRA = new DCompra(id_compra, preciototal, fechacompra, estado);
        Object[] response = DATA_COMPRA.modificar();
        DATA_COMPRA.desconectar();
        return response;
    }

    public Object[] finalizarCompra(int id_compra, Double preciototal) throws SQLException, ParseException {
        DATA_COMPRA = new DCompra(id_compra);
        Object[] response = DATA_COMPRA.finalizarCompra(preciototal);
        DATA_COMPRA.desconectar();
        return response;
    }

    public boolean eliminar(int id_compra) throws SQLException {
        DATA_COMPRA = new DCompra(id_compra);
        boolean response = DATA_COMPRA.eliminar();
        DATA_COMPRA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_COMPRA = new DCompra();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_COMPRA.listar();
        DATA_COMPRA.desconectar();
        return categoria;
    }

    public String[] verToCompra(int id_compra) throws SQLException {
        DATA_COMPRA = new DCompra(id_compra);
        String[] data = DATA_COMPRA.verToCompra();
        DATA_COMPRA.desconectar();
        return data;
    }

    public String[] ver(int id_compra) throws SQLException {
        DATA_COMPRA = new DCompra(id_compra);
        String[] data = DATA_COMPRA.ver();
        DATA_COMPRA.desconectar();
        return data;
    }
}
