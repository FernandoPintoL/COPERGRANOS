/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DReporte;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author fpl
 */
public class NReporte {

    DReporte NEGOCIO_REPORTE;
    ReportGenerator reporte;

    public List<String[]> listarProductoAlmacen(String name_pdfFilePath, String chartFilePath) throws SQLException {
        NEGOCIO_REPORTE = new DReporte();
        reporte = new ReportGenerator();
        String[] encabezado = {"ID PRODUCTO", "ID ALMACEN", "CODIGO ALMACEN", "NOMBRE PRODUCTO", "STOCK"};
        ArrayList<String[]> listado = (ArrayList<String[]>) NEGOCIO_REPORTE.listarProductoAlmacen();
        reporte.createPieChart(listado, chartFilePath, 3, 4);
        //agregamos encabezado
        listado.add(0, encabezado);
        reporte.generatePdfReport(listado, name_pdfFilePath, chartFilePath);
        NEGOCIO_REPORTE.desconectar();
        //QUITAMOS EL ENCABEZADO
        listado.remove(0);
        return listado;
    }

    public List<String[]> listarProductoVendido() throws SQLException {
        NEGOCIO_REPORTE = new DReporte();
        String[] encabezado = {"PRODUCTO ID", "PRODUCTO ALMACEN", "CODIGO ALMACEN", "NOMBRE PRODUCTO", "STOCK"};
        ArrayList<String[]> listado = (ArrayList<String[]>) NEGOCIO_REPORTE.listarProductoVendido();
        listado.add(0, encabezado);
        NEGOCIO_REPORTE.desconectar();
        return listado;
    }

}
