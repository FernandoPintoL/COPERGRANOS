package Negocio;

import Data.DPago;
import Data.DTransaccionBancaria;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author angela
 */
public class NTransaccionBancaria {

    private DTransaccionBancaria NEGOCIO_TRANSACCIONBANCARIA;

    public NTransaccionBancaria() {}

    public Object[] guardar(int num_transaccion, String banco_origen, String banco_destino) throws SQLException, ParseException {
        NEGOCIO_TRANSACCIONBANCARIA = new DTransaccionBancaria(num_transaccion, banco_origen, banco_destino);
        Object[] response = NEGOCIO_TRANSACCIONBANCARIA.guardar();
        NEGOCIO_TRANSACCIONBANCARIA.desconectar();
        return response;
    }

    public Object[] modificar(int id_transbanc, int num_transaccion, String banco_origen, String banco_destino, Date fecha_transaccion) throws SQLException, ParseException {
        NEGOCIO_TRANSACCIONBANCARIA = new DTransaccionBancaria(id_transbanc, num_transaccion, banco_origen, banco_destino, fecha_transaccion);
        Object[] response = NEGOCIO_TRANSACCIONBANCARIA.modificar();
        NEGOCIO_TRANSACCIONBANCARIA.desconectar();
        return response;
    }

    public boolean eliminar(int id_transbanc) throws SQLException {
        NEGOCIO_TRANSACCIONBANCARIA = new DTransaccionBancaria(id_transbanc);
        boolean response = NEGOCIO_TRANSACCIONBANCARIA.eliminar();
        NEGOCIO_TRANSACCIONBANCARIA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_TRANSACCIONBANCARIA = new DTransaccionBancaria();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_TRANSACCIONBANCARIA.listar();
        NEGOCIO_TRANSACCIONBANCARIA.desconectar();
        return categoria;
    }

    public String[] ver(int id_transbanc) throws SQLException {
        NEGOCIO_TRANSACCIONBANCARIA = new DTransaccionBancaria(id_transbanc);
        String[] data = NEGOCIO_TRANSACCIONBANCARIA.ver();
        NEGOCIO_TRANSACCIONBANCARIA.desconectar();
        return data;
    }
}
