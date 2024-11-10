package NEGOCIO;

import DATA.DMedida;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angela
 */
public class NMedida {

    private DMedida DATA_MEDIDA;

    public NMedida() {}

    public Object[] guardar(String detalle) throws SQLException, ParseException {
        DATA_MEDIDA = new DMedida(detalle);
        Object[] response = DATA_MEDIDA.guardar();
        DATA_MEDIDA.desconectar();
        return response;
    }

    public Object[] modificar(int id, String detalle) throws SQLException, ParseException {
        DATA_MEDIDA = new DMedida(id, detalle);
        Object[] response = DATA_MEDIDA.modificar();
        DATA_MEDIDA.desconectar();
        return response;
    }

    public boolean eliminar(int id) throws SQLException {
        DATA_MEDIDA = new DMedida(id);
        boolean response = DATA_MEDIDA.eliminar();
        DATA_MEDIDA.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_MEDIDA = new DMedida();
        ArrayList<String[]> medida = (ArrayList<String[]>) DATA_MEDIDA.listar();
        DATA_MEDIDA.desconectar();
        return medida;
    }

    public String[] ver(int id) throws SQLException {
        DATA_MEDIDA = new DMedida(id);
        String[] medida = DATA_MEDIDA.ver();
        DATA_MEDIDA.desconectar();
        return medida;
    }
}
