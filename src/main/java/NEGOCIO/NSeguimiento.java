package NEGOCIO;

import DATA.DSeguimiento;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author angela
 */
public class NSeguimiento {

    private DSeguimiento DATA_SEGUIMIENTO;

    public NSeguimiento() {}

    public Object[] guardar(String descripcion, String ubicacion_actual, String estado_actual, int envio_id) throws SQLException, ParseException {
        DATA_SEGUIMIENTO = new DSeguimiento(descripcion, ubicacion_actual, estado_actual, envio_id);
        Object[] response = DATA_SEGUIMIENTO.guardar();
        DATA_SEGUIMIENTO.desconectar();
        return response;
    }

    public Object[]  modificar(int id_seguimiento, Date fecha_evento, String descripcion, String ubicacion_actual, String estado_actual) throws SQLException, ParseException {
        DATA_SEGUIMIENTO = new DSeguimiento(id_seguimiento, fecha_evento, descripcion, ubicacion_actual, estado_actual);
        Object[] response = DATA_SEGUIMIENTO.modificar();
        DATA_SEGUIMIENTO.desconectar();
        return response;
    }

    public boolean eliminar(int id_seguimiento) throws SQLException {
        DATA_SEGUIMIENTO = new DSeguimiento(id_seguimiento);
        boolean response = DATA_SEGUIMIENTO.eliminar();
        DATA_SEGUIMIENTO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_SEGUIMIENTO = new DSeguimiento();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_SEGUIMIENTO.listar();
        DATA_SEGUIMIENTO.desconectar();
        return categoria;
    }

    public String[] ver(int id_seguimiento) throws SQLException {
        DATA_SEGUIMIENTO = new DSeguimiento(id_seguimiento);
        String[] data = DATA_SEGUIMIENTO.ver();
        DATA_SEGUIMIENTO.desconectar();
        return data;
    }
}
