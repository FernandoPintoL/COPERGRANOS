package Negocio;

import Data.DMetodoPago;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author angela
 */
public class NMetodoPago {

    private DMetodoPago NEGOCIO_METODOPAGO;

    public NMetodoPago() {}

    public Object[] guardar(String nombre) throws SQLException, ParseException {
        NEGOCIO_METODOPAGO = new DMetodoPago(nombre);
        Object[] response = NEGOCIO_METODOPAGO.guardar();
        NEGOCIO_METODOPAGO.desconectar();
        return response;
    }

    public Object[] modificar(int id_metodopago, String nombre) throws SQLException, ParseException {
        NEGOCIO_METODOPAGO = new DMetodoPago(id_metodopago, nombre);
        Object[] response = NEGOCIO_METODOPAGO.modificar();
        NEGOCIO_METODOPAGO.desconectar();
        return response;
    }

    public boolean eliminar(int id_metodopago) throws SQLException {
        NEGOCIO_METODOPAGO = new DMetodoPago(id_metodopago);
        boolean response = NEGOCIO_METODOPAGO.eliminar();
        NEGOCIO_METODOPAGO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_METODOPAGO = new DMetodoPago();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_METODOPAGO.listar();
        NEGOCIO_METODOPAGO.desconectar();
        return categoria;
    }

    public String[] ver(int id_metodopago) throws SQLException {
        NEGOCIO_METODOPAGO = new DMetodoPago(id_metodopago);
        String[] data = NEGOCIO_METODOPAGO.ver();
        NEGOCIO_METODOPAGO.desconectar();
        return data;
    }
}
