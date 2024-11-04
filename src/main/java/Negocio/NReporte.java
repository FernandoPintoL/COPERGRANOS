/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DReporte;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
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

    public List<String[]> listarProductoAlmacen(String name_pdfFilePath) throws SQLException {
        NEGOCIO_REPORTE = new DReporte();
        ArrayList<String[]> listado = (ArrayList<String[]>) NEGOCIO_REPORTE.listarProductoAlmacen();
        generatePdfReport(listado, name_pdfFilePath);
        NEGOCIO_REPORTE.desconectar();
        return listado;
    }

    public List<String[]> listarProductoVendido() throws SQLException {
        NEGOCIO_REPORTE = new DReporte();
        ArrayList<String[]> listado = (ArrayList<String[]>) NEGOCIO_REPORTE.listarProductoVendido();
        NEGOCIO_REPORTE.desconectar();
        return listado;
    }

    public void generatePdfReport(ArrayList<String[]> data, String name_pdfFilePath) {
        try {
            System.out.println("DESDE EL NEGOCIO REPORTE");
            // Crear el documento y el escritor de PDF
            System.out.println(Arrays.toString(data.toArray()));
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(name_pdfFilePath));
            document.open();

            // Agregar título al documento
            document.add(new Paragraph("Reporte de Productos"));

            if (data.isEmpty()) {
                document.add(new Paragraph("No hay datos disponibles."));
            } else {
                // Determinar el número de columnas a partir del primer elemento
                int numColumns = data.get(0).length;
                PdfPTable table = new PdfPTable(numColumns);

                // Agregar encabezados de columna (opcional, si tu ArrayList no tiene encabezados)
                for (String header : data.get(0)) {
                    table.addCell(header);
                }

                // Agregar datos al cuerpo de la tabla (a partir del segundo elemento)
                for (int i = 1; i < data.size(); i++) {
                    for (String cellData : data.get(i)) {
                        table.addCell(cellData);
                    }
                }

                // Agregar la tabla al documento
                document.add(table);
            }

            // Cerrar el documento
            document.close();
            System.out.println("PDF generado: " + name_pdfFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
