/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NEGOCIO;

import DATA.DReporte;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fpl
 */
public class NReporte {

    DReporte DATA_REPORTE;
    ReportGenerator reporte;

    public List<String[]> listarProductoAlmacen(String name_pdfFilePath, String chartFilePath) throws SQLException {
        DATA_REPORTE = new DReporte();
        reporte = new ReportGenerator();
        String[] encabezado = {"ID PRODUCTO", "ID ALMACEN", "CODIGO ALMACEN", "NOMBRE PRODUCTO", "STOCK"};
        ArrayList<String[]> listado = (ArrayList<String[]>) DATA_REPORTE.listarProductoAlmacen();
        if(!listado.isEmpty()){
            reporte.createPieChart(listado, chartFilePath, 3, 4);
            //agregamos encabezado
            listado.add(0, encabezado);
            reporte.generatePdfReport(listado, name_pdfFilePath, chartFilePath);
            listado.remove(0);
        }
        DATA_REPORTE.desconectar();
        //QUITAMOS EL ENCABEZADO
        return listado;
    }

    public List<String[]> listarProductoVendido(String name_pdfFilePath, String chartFilePath) throws SQLException {
        DATA_REPORTE = new DReporte();
        String[] encabezado = {"PRODUCTO ID", "NOMBRE", "PRECIO", "CANTIDAD", "TOTAL VENDIDO"};
        ArrayList<String[]> listado = (ArrayList<String[]>) DATA_REPORTE.listarProductoVendido();
        if (!listado.isEmpty()) {
            reporte.createPieChart(listado, chartFilePath, 3, 4);
            listado.add(0, encabezado);
            reporte.generatePdfReport(listado, name_pdfFilePath, chartFilePath);
            listado.remove(0);
        }
        DATA_REPORTE.desconectar();
        return listado;
    }

}
