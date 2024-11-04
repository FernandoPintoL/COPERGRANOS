package Negocio;

import Data.DAdministrativo; 
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Arrays;

/**
 *
 * @author angela
 */
public class NAdministrativo {
    private DAdministrativo NEGOCIO_ADMINISTRATIVO;

    public NAdministrativo() {}

    public Object[] guardar(String nombre, String direccion, int telefono, String correo, int ci, String cargo) throws SQLException, ParseException {
        NEGOCIO_ADMINISTRATIVO = new DAdministrativo(cargo, nombre, direccion, telefono, correo, ci);
        Object[] response = NEGOCIO_ADMINISTRATIVO.guardar();
        System.out.println(Arrays.toString(response));
        NEGOCIO_ADMINISTRATIVO.desconectar();
        return response;
    }

    public Object[] modificar(int id_administrativo, String nombre, String direccion, int telefono, String correo, int ci, String cargo, Date fecha_ingreso) throws SQLException, ParseException {
        NEGOCIO_ADMINISTRATIVO = new DAdministrativo(id_administrativo, cargo, fecha_ingreso, nombre, direccion, telefono, correo, ci);
        Object[] response = NEGOCIO_ADMINISTRATIVO.modificar();
        NEGOCIO_ADMINISTRATIVO.desconectar();
        return response;
    }

    public boolean eliminar(int id_administrativo) throws SQLException {
        NEGOCIO_ADMINISTRATIVO = new DAdministrativo(id_administrativo);
        boolean response = NEGOCIO_ADMINISTRATIVO.eliminar();
        NEGOCIO_ADMINISTRATIVO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_ADMINISTRATIVO = new DAdministrativo();
        ArrayList<String[]> categoria = (ArrayList<String[]>) NEGOCIO_ADMINISTRATIVO.listar();
        NEGOCIO_ADMINISTRATIVO.desconectar();
        return categoria;
    }

    public String[] ver(int id) throws SQLException {
        NEGOCIO_ADMINISTRATIVO = new DAdministrativo(id);
        String[] data = NEGOCIO_ADMINISTRATIVO.ver();
        NEGOCIO_ADMINISTRATIVO.desconectar();
        return data;
    }
}
