package NEGOCIO;

import DATA.DInventario;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class NInventario {

    private DInventario DATA_INVENTARIO;

    public NInventario() {
    }

    public Object[] guardar(int producto_id, int almacen_id, double stock) throws SQLException, ParseException {
        DATA_INVENTARIO = new DInventario(producto_id, almacen_id, stock);
        Object[] response = DATA_INVENTARIO.guardar();
        DATA_INVENTARIO.desconectar();
        return response;
    }

    public Object[] modificar(int producto_id, int almacen_id, double stock, Date fecha) throws SQLException, ParseException {
        DATA_INVENTARIO = new DInventario(producto_id, almacen_id, stock, fecha);
        Object[] response = DATA_INVENTARIO.modificar();
        DATA_INVENTARIO.desconectar();
        return response;
    }

    public boolean eliminar(int producto_id, int almacen_id) throws SQLException {
        DATA_INVENTARIO = new DInventario(almacen_id, almacen_id);
        boolean response = DATA_INVENTARIO.eliminar();
        DATA_INVENTARIO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        DATA_INVENTARIO = new DInventario();
        ArrayList<String[]> inventario = (ArrayList<String[]>) DATA_INVENTARIO.listar();
        DATA_INVENTARIO.desconectar();
        return inventario;
    }

    public String[] ver(int producto_id, int almacen_id) throws SQLException {
        DATA_INVENTARIO = new DInventario(almacen_id, almacen_id);
        String[] producto = DATA_INVENTARIO.ver();
        DATA_INVENTARIO.desconectar();
        return producto;
    }
}
