package Negocio;

import Data.DMedida;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angela
 */
public class NMedida {

    private DMedida NEGOCIO_MEDIDA;

    public NMedida() {}

    public Object[] guardar(String detalle) throws SQLException, ParseException {
        NEGOCIO_MEDIDA = new DMedida(detalle);
        Object[] response = NEGOCIO_MEDIDA.guardar();
        NEGOCIO_MEDIDA.desconectar();
        return response;
    }

    public Object[] modificar(int id, String detalle) throws SQLException, ParseException {
        NEGOCIO_MEDIDA = new DMedida(id, detalle);
        Object[] response = NEGOCIO_MEDIDA.modificar();
        NEGOCIO_MEDIDA.desconectar();
        return response;
    }

    public boolean eliminar(int id) throws SQLException {
        NEGOCIO_MEDIDA = new DMedida(id);
        boolean response = NEGOCIO_MEDIDA.eliminar();
        NEGOCIO_MEDIDA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_MEDIDA = new DMedida();
        ArrayList<String[]> medida = (ArrayList<String[]>) NEGOCIO_MEDIDA.listar();
        NEGOCIO_MEDIDA.desconectar();
        return medida;
    }

    public String[] ver(int id) throws SQLException {
        NEGOCIO_MEDIDA = new DMedida(id);
        String[] medida = NEGOCIO_MEDIDA.ver();
        NEGOCIO_MEDIDA.desconectar();
        return medida;
    }
}
