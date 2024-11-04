package Negocio;

import Data.DInventario;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class NInventario {

    private DInventario NEGOCIO_INVENTARIO;

    public NInventario() {
    }

    public Object[] guardar(int producto_id, int almacen_id, double stock) throws SQLException, ParseException {
        NEGOCIO_INVENTARIO = new DInventario(producto_id, almacen_id, stock);
        Object[] response = NEGOCIO_INVENTARIO.guardar();
        NEGOCIO_INVENTARIO.desconectar();
        return response;
    }

    public Object[] modificar(int producto_id, int almacen_id, double stock, Date fecha) throws SQLException, ParseException {
        NEGOCIO_INVENTARIO = new DInventario(producto_id, almacen_id, stock, fecha);
        Object[] response = NEGOCIO_INVENTARIO.modificar();
        NEGOCIO_INVENTARIO.desconectar();
        return response;
    }

    public boolean eliminar(int producto_id, int almacen_id) throws SQLException {
        NEGOCIO_INVENTARIO = new DInventario(almacen_id, almacen_id);
        boolean response = NEGOCIO_INVENTARIO.eliminar();
        NEGOCIO_INVENTARIO.desconectar();
        return response;
    }

    public List<String[]> listar() throws SQLException {
        NEGOCIO_INVENTARIO = new DInventario();
        ArrayList<String[]> inventario = (ArrayList<String[]>) NEGOCIO_INVENTARIO.listar();
        NEGOCIO_INVENTARIO.desconectar();
        return inventario;
    }

    public String[] ver(int producto_id, int almacen_id) throws SQLException {
        NEGOCIO_INVENTARIO = new DInventario(almacen_id, almacen_id);
        String[] producto = NEGOCIO_INVENTARIO.ver();
        NEGOCIO_INVENTARIO.desconectar();
        return producto;
    }
}
