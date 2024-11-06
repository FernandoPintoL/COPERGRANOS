/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author fpl
 */
public class ReportGenerator {
    
    Document document;
    PdfPTable table;
    
    /**
     * Método para crear un gráfico de torta a partir de cualquier tipo de datos.
     *
     * @param data             Los datos en formato ArrayList<String[]>.
     * @param chartFilePath    La ruta donde se guardará el archivo de la imagen.
     * @param labelIndex       El índice de la columna a usar como etiqueta.
     * @param valueIndex       El índice de la columna a usar como valor numérico.
     */
    
    // Método para crear el gráfico de torta
    public void createPieChart(ArrayList<String[]> data, String chartFilePath, int labelIndex, int valueIndex) {
        try {
            DefaultPieDataset dataset = new DefaultPieDataset();

            // Agregar los datos al gráfico usando los índices especificados
            for (String[] row : data) {
                String label = "ALMACEN: "+row[2]+" : "+row[labelIndex] + " : " +row[valueIndex]; // Etiqueta (por ejemplo, nombre del producto)
                double value = Double.parseDouble(row[valueIndex]); // Valor numérico (por ejemplo, stock)
                dataset.setValue(label, value);
            }

            // Crear el gráfico de torta
            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Distribución de Stock por Producto",
                    dataset,
                    true,
                    true,
                    false
            );

            // Guardar el gráfico como archivo PNG
            ChartUtils.saveChartAsPNG(new File(chartFilePath), pieChart, 800, 600);
            System.out.println("Gráfico de torta guardado en: " + chartFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void generatePdfReport(ArrayList<String[]> data, String name_pdfFilePath, String chartFilePath) {
        try {
            // Crear el documento y el escritor de PDF            
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name_pdfFilePath));
            //PdfWriter.getInstance(document, new FileOutputStream(name_pdfFilePath));
            
            // Agregar el evento de pie de página
            FooterPageEvent event = new FooterPageEvent();
            writer.setPageEvent(event);
            
            document.open();
            // Agregar título al documento
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph title = new Paragraph("REPORTE DE PRODUCTOS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            if (data.isEmpty()) {
                document.add(new Paragraph("NO HAY DATOS DISPONIBLES"));
            } else {
                // Determinar el número de columnas a partir del primer elemento
                int numColumns = data.get(0).length;
                table = new PdfPTable(numColumns);                
                // Agregar encabezados de columna (opcional, si tu ArrayList no tiene encabezados)
                // Agregar encabezados de columna con fondo verde y texto blanco
                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE); // Texto blanco
                for (String header : data.get(0)) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setBackgroundColor(BaseColor.BLUE);
                    table.addCell(headerCell);
                }
                // Agregar datos al cuerpo de la tabla (a partir del segundo elemento)
                for (int i = 1; i < data.size(); i++) {
                    for (String cellData : data.get(i)) {
                        PdfPCell cellDatas = new PdfPCell(new Phrase(cellData));
                        cellDatas.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cellDatas);
                    }
                }
                // Agregar la tabla al documento
                document.add(table);
            }
            
            // Insertar el gráfico de torta en el PDF
            document.add(new Paragraph("\n"));
            Image chartImage = Image.getInstance(chartFilePath);
            chartImage.scaleToFit(500, 300); // Escalar la imagen si es necesario
            chartImage.setAlignment(Element.ALIGN_CENTER);
            document.add(chartImage);
            
            // Cerrar el documento
            document.close();
            System.out.println("PDF generado: " + name_pdfFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
