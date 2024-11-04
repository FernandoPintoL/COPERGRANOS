package Negocio;

import Data.DCompra;
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

    private DCompra NEGOCIO_COMPRA;

    public NCompra() {}

    public Object[] guardar(Double preciototal, String estado, int cliente_id, int administrativo_id) throws SQLException, ParseException {
        NEGOCIO_COMPRA = new DCompra(preciototal, estado, cliente_id, administrativo_id);
        Object[] response = NEGOCIO_COMPRA.guardar();
        NEGOCIO_COMPRA.desconectar();
        return response;
    }

    public Object[] modificar(int id_compra, Double preciototal, Date fechacompra,String estado) throws SQLException, ParseException {
        NEGOCIO_COMPRA = new DCompra(id_compra, preciototal, fechacompra, estado);
        Object[] response = NEGOCIO_COMPRA.modificar();
        NEGOCIO_COMPRA.desconectar();
        return response;
    }

    public boolean eliminar(int id_compra) throws SQLException {
        NEGOCIO_COMPRA = new DCompra(id_compra);
        boolean response = NEGOCIO_COMPRA.eliminar();
        NEGOCIO_COMPRA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_COMPRA = new DCompra();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_COMPRA.listar();
        NEGOCIO_COMPRA.desconectar();
        return categoria;
    }

    public String[] ver(int id_compra) throws SQLException {
        NEGOCIO_COMPRA = new DCompra(id_compra);
        String[] data = NEGOCIO_COMPRA.ver();
        NEGOCIO_COMPRA.desconectar();
        return data;
    }
}
