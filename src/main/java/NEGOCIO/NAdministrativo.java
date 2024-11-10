package NEGOCIO;

import DATA.DAdministrativo;
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
    private DAdministrativo DATA_ADMINISTRATIVO;

    public NAdministrativo() {}

    public Object[] guardar(String nombre, String direccion, int telefono, String correo, int ci, String cargo) throws SQLException, ParseException {
        DATA_ADMINISTRATIVO = new DAdministrativo(cargo, nombre, direccion, telefono, correo, ci);
        Object[] response = DATA_ADMINISTRATIVO.guardar();
        System.out.println(Arrays.toString(response));
        DATA_ADMINISTRATIVO.desconectar();
        return response;
    }

    public Object[] modificar(int id_administrativo, String nombre, String direccion, int telefono, String correo, int ci, String cargo, Date fecha_ingreso) throws SQLException, ParseException {
        DATA_ADMINISTRATIVO = new DAdministrativo(id_administrativo, cargo, fecha_ingreso, nombre, direccion, telefono, correo, ci);
        Object[] response = DATA_ADMINISTRATIVO.modificar();
        DATA_ADMINISTRATIVO.desconectar();
        return response;
    }

    public boolean eliminar(int id_administrativo) throws SQLException {
        DATA_ADMINISTRATIVO = new DAdministrativo(id_administrativo);
        boolean response = DATA_ADMINISTRATIVO.eliminar();
        DATA_ADMINISTRATIVO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_ADMINISTRATIVO = new DAdministrativo();
        ArrayList<String[]> categoria = (ArrayList<String[]>) DATA_ADMINISTRATIVO.listar();
        DATA_ADMINISTRATIVO.desconectar();
        return categoria;
    }

    public String[] ver(int id) throws SQLException {
        DATA_ADMINISTRATIVO = new DAdministrativo(id);
        String[] data = DATA_ADMINISTRATIVO.ver();
        DATA_ADMINISTRATIVO.desconectar();
        return data;
    }
}
