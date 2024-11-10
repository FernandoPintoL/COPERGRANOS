package NEGOCIO;

import DATA.DMetodoPago;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author angela
 */
public class NMetodoPago {

    private DMetodoPago DATA_METODOPAGO;

    public NMetodoPago() {}

    public Object[] guardar(String nombre) throws SQLException, ParseException {
        DATA_METODOPAGO = new DMetodoPago(nombre);
        Object[] response = DATA_METODOPAGO.guardar();
        DATA_METODOPAGO.desconectar();
        return response;
    }

    public Object[] modificar(int id_metodopago, String nombre) throws SQLException, ParseException {
        DATA_METODOPAGO = new DMetodoPago(id_metodopago, nombre);
        Object[] response = DATA_METODOPAGO.modificar();
        DATA_METODOPAGO.desconectar();
        return response;
    }

    public boolean eliminar(int id_metodopago) throws SQLException {
        DATA_METODOPAGO = new DMetodoPago(id_metodopago);
        boolean response = DATA_METODOPAGO.eliminar();
        DATA_METODOPAGO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_METODOPAGO = new DMetodoPago();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_METODOPAGO.listar();
        DATA_METODOPAGO.desconectar();
        return categoria;
    }

    public String[] ver(int id_metodopago) throws SQLException {
        DATA_METODOPAGO = new DMetodoPago(id_metodopago);
        String[] data = DATA_METODOPAGO.ver();
        DATA_METODOPAGO.desconectar();
        return data;
    }
}
