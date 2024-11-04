package Negocio;

import Data.DSeguimiento;
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
public class NSeguimiento {

    private DSeguimiento NEGOCIO_SEGUIMIENTO;

    public NSeguimiento() {}

    public Object[] guardar(String descripcion, String ubicacion_actual, String estado_actual, int envio_id) throws SQLException, ParseException {
        NEGOCIO_SEGUIMIENTO = new DSeguimiento(descripcion, ubicacion_actual, estado_actual, envio_id);
        Object[] response = NEGOCIO_SEGUIMIENTO.guardar();
        NEGOCIO_SEGUIMIENTO.desconectar();
        return response;
    }

    public Object[]  modificar(int id_seguimiento, Date fecha_evento, String descripcion, String ubicacion_actual, String estado_actual) throws SQLException, ParseException {
        NEGOCIO_SEGUIMIENTO = new DSeguimiento(id_seguimiento, fecha_evento, descripcion, ubicacion_actual, estado_actual);
        Object[] response = NEGOCIO_SEGUIMIENTO.modificar();
        NEGOCIO_SEGUIMIENTO.desconectar();
        return response;
    }

    public boolean eliminar(int id_seguimiento) throws SQLException {
        NEGOCIO_SEGUIMIENTO = new DSeguimiento(id_seguimiento);
        boolean response = NEGOCIO_SEGUIMIENTO.eliminar();
        NEGOCIO_SEGUIMIENTO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_SEGUIMIENTO = new DSeguimiento();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_SEGUIMIENTO.listar();
        NEGOCIO_SEGUIMIENTO.desconectar();
        return categoria;
    }

    public String[] ver(int id_seguimiento) throws SQLException {
        NEGOCIO_SEGUIMIENTO = new DSeguimiento(id_seguimiento);
        String[] data = NEGOCIO_SEGUIMIENTO.ver();
        NEGOCIO_SEGUIMIENTO.desconectar();
        return data;
    }
}
