/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consultas;

/*
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
*/
/**
 *
 * @author fpl
 */
public class PDFGenerator {

    /*public static File createPdfWithData(Map<String, Integer> pieChartData, ResultSet tableData) throws IOException, SQLException {
        File pdfFile = File.createTempFile("reporte", ".pdf");

        PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Agregar un título
        document.add(new Paragraph("Reporte de Datos"));

        // Crear y agregar la tabla usando los datos de la base de datos
        float[] columnWidths = {100F, 100F, 100F};
        Table table = new Table(columnWidths);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Email");

        while (tableData.next()) {
            table.addCell(tableData.getString("id"));
            table.addCell(tableData.getString("nombre"));
            table.addCell(tableData.getString("email"));
        }
        document.add(table);

        // Crear un gráfico de torta con los datos de la base de datos
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : pieChartData.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribución de Categorías", dataset, true, true, false);

        // Guardar el gráfico como imagen temporal
        File chartFile = File.createTempFile("pie_chart", ".png");
        ChartUtils.saveChartAsPNG(chartFile, pieChart, 500, 400);

        // Agregar la imagen del gráfico al PDF
        Image chartImage = new Image(ImageDataFactory.create(chartFile.getAbsolutePath()));
        document.add(chartImage);

        document.close();
        return pdfFile;
    }*/
}
