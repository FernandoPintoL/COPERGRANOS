package NEGOCIO;

import DATA.DTransaccionBancaria;
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

    private DTransaccionBancaria DATA_TRANSACCIONBANCARIA;

    public NTransaccionBancaria() {}

    public Object[] guardar(int num_transaccion, String banco_origen, String banco_destino) throws SQLException, ParseException {
        DATA_TRANSACCIONBANCARIA = new DTransaccionBancaria(num_transaccion, banco_origen, banco_destino);
        Object[] response = DATA_TRANSACCIONBANCARIA.guardar();
        DATA_TRANSACCIONBANCARIA.desconectar();
        return response;
    }

    public Object[] modificar(int id_transbanc, int num_transaccion, String banco_origen, String banco_destino, Date fecha_transaccion) throws SQLException, ParseException {
        DATA_TRANSACCIONBANCARIA = new DTransaccionBancaria(id_transbanc, num_transaccion, banco_origen, banco_destino, fecha_transaccion);
        Object[] response = DATA_TRANSACCIONBANCARIA.modificar();
        DATA_TRANSACCIONBANCARIA.desconectar();
        return response;
    }

    public boolean eliminar(int id_transbanc) throws SQLException {
        DATA_TRANSACCIONBANCARIA = new DTransaccionBancaria(id_transbanc);
        boolean response = DATA_TRANSACCIONBANCARIA.eliminar();
        DATA_TRANSACCIONBANCARIA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_TRANSACCIONBANCARIA = new DTransaccionBancaria();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_TRANSACCIONBANCARIA.listar();
        DATA_TRANSACCIONBANCARIA.desconectar();
        return categoria;
    }

    public String[] ver(int id_transbanc) throws SQLException {
        DATA_TRANSACCIONBANCARIA = new DTransaccionBancaria(id_transbanc);
        String[] data = DATA_TRANSACCIONBANCARIA.ver();
        DATA_TRANSACCIONBANCARIA.desconectar();
        return data;
    }
}
