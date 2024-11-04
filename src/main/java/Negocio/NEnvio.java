package Negocio;

import Data.DEnvio;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author angela
 */
public class NEnvio {

    private DEnvio NEGOCIO_ENVIO;

    public NEnvio() {
        NEGOCIO_ENVIO = new DEnvio();
    }

    public Object[] guardar(String direccion_envio, String ciudad_envio, String pais_destino, String estado_envio, Date fecha_entrega, String metodo_envio, String transporte, int compra_id) throws SQLException, ParseException {
        NEGOCIO_ENVIO = new DEnvio(direccion_envio, ciudad_envio, pais_destino, estado_envio, fecha_entrega, metodo_envio, transporte, compra_id);
        Object[] response = NEGOCIO_ENVIO.guardar();
        NEGOCIO_ENVIO.desconectar();
        return response;
    }

    public Object[] modificar(int id_envio, Date fecha_envio, String direccion_envio, String ciudad_envio, String pais_destino, String estado_envio, Date fecha_entrega, String metodo_envio, String transporte) throws SQLException, ParseException {
        NEGOCIO_ENVIO = new DEnvio(id_envio, fecha_envio, direccion_envio, ciudad_envio, pais_destino, estado_envio, fecha_entrega, metodo_envio, transporte);
        Object[] response = NEGOCIO_ENVIO.modificar();
        NEGOCIO_ENVIO.desconectar();
        return response;
    }

    public boolean eliminar(int id_envio) throws SQLException {
        NEGOCIO_ENVIO = new DEnvio(id_envio);
        boolean response = NEGOCIO_ENVIO.eliminar();
        NEGOCIO_ENVIO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_ENVIO = new DEnvio();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_ENVIO.listar();
        NEGOCIO_ENVIO.desconectar();
        return categoria;
    }

    public String[] ver(int id_envio) throws SQLException {
        NEGOCIO_ENVIO = new DEnvio(id_envio);
        String[] data = NEGOCIO_ENVIO.ver();
        NEGOCIO_ENVIO.desconectar();
        return data;
    }
}
