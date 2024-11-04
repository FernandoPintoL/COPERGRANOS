/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consultas;

/**
 *
 * @author fpl
 */

//import jakarta.mail.internet.*;*/
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ReportGenerator {
    
    // Método para realizar la consulta y generar el PDF
    public void generatePdfReport(String dbUrl, String user, String password, String pdfFilePath) {
        try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
             Statement stmt = conn.createStatement()) {
            
            String query = "SELECT * FROM producto"; // Cambia esto a tu consulta
            ResultSet rs = stmt.executeQuery(query);
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            document.add(new Paragraph("Reporte de Productos"));

            // Agregar contenido de la consulta al PDF
            PdfPTable table = new PdfPTable(3); // Cambia el número de columnas según tu tabla
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Precio");

            while (rs.next()) {
                table.addCell(String.valueOf(rs.getInt("id_producto"))); // Cambia "id" al nombre de tu columna
                table.addCell(rs.getString("nombre")); // Cambia "nombre" al nombre de tu columna
                table.addCell(String.valueOf(rs.getDouble("precio"))); // Cambia "precio" al nombre de tu columna
            }
            document.add(table);
            document.close();
            System.out.println("PDF generado: " + pdfFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para enviar el correo con el PDF adjunto
    /*public void sendEmailWithAttachment(String host, String port, final String userName, final String password,
                                                String toAddress, String subject, String message, String attachFilePath) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "false");

        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {                
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new java.util.Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);

            MimeBodyPart attachPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachFilePath);
            attachPart.setDataHandler(new DataHandler(source));
            attachPart.setFileName(source.getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachPart);

            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("Correo enviado con éxito.");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Configuración de la base de datos
        String dbUrl = "jdbc:postgresql://mail.tecnoweb.org.bo:5432/db_grupo02sa";
        String user = "grupo02sa"; // Cambia esto
        String password = "grup002grup002*"; // Cambia esto
        String pdfFilePath = "productos_report.pdf";

        // Generar el PDF
        generatePdfReport(dbUrl, user, password, pdfFilePath);

        // Configuración de correo
        String host = "mail.tecnoweb.org.bo"; // Cambia esto
        String port = "25"; // Cambia esto
        String mailFrom = "grupo02sa@tecnoweb.org.bo"; // Cambia esto
        String emailPassword = "grup002grup002*"; // Cambia esto
        String mailTo = "pintolinofernando@gmail.com"; // Cambia esto
        String subject = "Reporte de Productos";
        String message = "Adjunto encontrarás el reporte de productos.";

        // Enviar el correo
        sendEmailWithAttachment(host, port, mailFrom, emailPassword, mailTo, subject, message, pdfFilePath);
    }*/
    
}
