package Negocio;

import Data.DPago;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author angela
 */
public class NPago {

    private DPago NEGOCIO_PAGO;

    public NPago() {}

    public Object[] guardar(Double monto, String moneda, String estado_pago, int compra_id, String metodo_pago) throws SQLException, ParseException {
        NEGOCIO_PAGO = new DPago(monto, moneda, estado_pago, compra_id, metodo_pago);
        Object[] response = NEGOCIO_PAGO.guardar();
        NEGOCIO_PAGO.desconectar();
        return response;
    }

    public Object[] modificar(int id_pago, Double monto, String moneda, Date fecha_pago, String estado_pago, String metodo_pago) throws SQLException, ParseException {
        NEGOCIO_PAGO = new DPago(id_pago, monto, moneda, fecha_pago, estado_pago, metodo_pago);
        Object[] response = NEGOCIO_PAGO.modificar();
        NEGOCIO_PAGO.desconectar();
        return response;
    }

    public boolean eliminar(int id_pago) throws SQLException {
        NEGOCIO_PAGO = new DPago(id_pago);
        boolean response = NEGOCIO_PAGO.eliminar();
        NEGOCIO_PAGO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_PAGO = new DPago();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_PAGO.listar();
        NEGOCIO_PAGO.desconectar();
        return categoria;
    }

    public String[] ver(int id_pago) throws SQLException {
        NEGOCIO_PAGO = new DPago(id_pago);
        String[] data = NEGOCIO_PAGO.ver();
        NEGOCIO_PAGO.desconectar();
        return data;
    }
}
