package CONNECTION;

import java.util.Base64;
import java.io.IOException;
import UTILS.ConstSMPT;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author angela
 */
public class Smtp extends Conexion {

    public Smtp(String servidor, int port) throws IOException {
        super(servidor, port);
    }

    String comando = "";

    public void helo() throws IOException {
        comando = ConstSMPT.HELO + servidor + ConstSMPT.FINLINE;
        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
    }

    public void mailFrom(String emailEmisor) throws IOException {
        comando = ConstSMPT.MAIL_FROM + emailEmisor + ConstSMPT.FINLINE;
        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
        System.out.println("MAIL FROM");
    }

    public void rcptTo(String emailReceptor) throws IOException {
        comando = ConstSMPT.RCPT_TO + emailReceptor + ConstSMPT.FINLINE;
        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
        System.out.println("RCPTTO");
    }

    public void data() throws IOException {
        comando = ConstSMPT.DATA;
        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
        System.out.println("DATA");
    }

    public void subject(String title, String message) throws IOException {
        comando = ConstSMPT.SUBJECT + title + ConstSMPT.FINLINE;
        comando += message + ConstSMPT.FINLINE;
        comando += ConstSMPT.FINSUBJECT;
        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
        System.out.println("SUBJECT");
    }

    public void quit() throws IOException {
        comando = ConstSMPT.QUIT;
        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
    }

    public void sendMail(String emisor, String rcpto, String subject, String mensaje) throws IOException {
        mailFrom(emisor);
        rcptTo(rcpto);
        data();
        subject(subject, mensaje);
        quit();
    }

    public void sendMailWithPdf(String emisor, String receptor, String subject, String mensaje, String rutaPdf) throws IOException {
        try {
            mailFrom(emisor);
            rcptTo(receptor);
            data();
            // Encabezados del correo
            String boundary = "==MyBoundary==";
            salida.writeBytes("MIME-Version: 1.0" + ConstSMPT.FINLINE);
            salida.writeBytes("Content-Type: multipart/mixed; boundary=\"" + boundary + "\"" + ConstSMPT.FINLINE);
            salida.writeBytes(ConstSMPT.SUBJECT + subject + ConstSMPT.FINLINE);

            // Cuerpo del mensaje
            salida.writeBytes("--" + boundary + ConstSMPT.FINLINE);
            salida.writeBytes("Content-Type: text/html; charset=\"UTF-8\"" + ConstSMPT.FINLINE);
            salida.writeBytes(ConstSMPT.FINLINE);
            salida.writeBytes(mensaje + ConstSMPT.FINLINE);

            // Verificar si el archivo PDF existe antes de adjuntarlo
            if (Files.exists(Paths.get(rutaPdf))) {
                // Adjuntar el archivo PDF
                salida.writeBytes("--" + boundary + ConstSMPT.FINLINE);
                salida.writeBytes("Content-Type: application/pdf; name=\"reporte.pdf\"" + ConstSMPT.FINLINE);
                salida.writeBytes("Content-Transfer-Encoding: base64" + ConstSMPT.FINLINE);
                salida.writeBytes("Content-Disposition: attachment; filename=\"reporte.pdf\"" + ConstSMPT.FINLINE);
                salida.writeBytes(ConstSMPT.FINLINE);

                // Leer y codificar el archivo PDF en Base64
                byte[] buffer = Files.readAllBytes(Paths.get(rutaPdf));
                String base64Encoded = Base64.getEncoder().encodeToString(buffer);
                salida.writeBytes(base64Encoded + ConstSMPT.FINLINE);
            } else {
                System.out.println("El archivo PDF no existe en la ruta especificada, se enviará el correo sin adjunto.");
            }

            // Final del multipart
            salida.writeBytes("--" + boundary + "--" + ConstSMPT.FINLINE);
            salida.writeBytes(ConstSMPT.FINSUBJECT);
            salida.flush();

            // Cerrar la conexión
            quit();

        } catch (IOException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace(); // Muestra detalles completos del stack trace en la consola
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace(); // Para excepciones que no sean de tipo IOException
        }

    }
}

/*
    public void addAttachment(File pdfFile) throws IOException {
        String boundary = "=====CorreoConAdjunto=====";
        // Definir el encabezado para archivo adjunto
        comando = "MIME-Version: 1.0" + ConstSMPT.FINLINE;
        comando += "Content-Type: multipart/mixed; boundary=\"" + boundary + "\"" + ConstSMPT.FINLINE + ConstSMPT.FINLINE;
        comando += "--" + boundary + ConstSMPT.FINLINE;
        comando += "Content-Type: text/plain; charset=\"UTF-8\"" + ConstSMPT.FINLINE;
        comando += ConstSMPT.FINLINE;
        comando += "Este es el cuerpo del correo." + ConstSMPT.FINLINE + ConstSMPT.FINLINE;

        // Adjuntar el archivo PDF codificado en Base64
        comando += "--" + boundary + ConstSMPT.FINLINE;
        comando += "Content-Type: application/pdf; name=\"" + pdfFile.getName() + "\"" + ConstSMPT.FINLINE;
        comando += "Content-Transfer-Encoding: base64" + ConstSMPT.FINLINE;
        comando += "Content-Disposition: attachment; filename=\"" + pdfFile.getName() + "\"" + ConstSMPT.FINLINE + ConstSMPT.FINLINE;

        // Leer y codificar el archivo en Base64
        try (FileInputStream fileInputStream = new FileInputStream(pdfFile)) {
            byte[] fileBytes = fileInputStream.readAllBytes();
            String encodedFile = Base64.getEncoder().encodeToString(fileBytes);

            // Dividir en líneas de 76 caracteres (recomendación del RFC 2045)
            int index = 0;
            while (index < encodedFile.length()) {
                comando += encodedFile.substring(index, Math.min(index + 76, encodedFile.length())) + ConstSMPT.FINLINE;
                index += 76;
            }
        }

        // Cerrar el cuerpo del correo
        comando += ConstSMPT.FINLINE + "--" + boundary + "--" + ConstSMPT.FINLINE;

        System.out.println("C: " + comando);
        salida.writeBytes(comando);
        System.out.println("S: " + entrada.readLine());
    }

    public void sendMailWithPdf(String emisor, String rcpto, String subject, String mensaje, File pdfFile) throws IOException {
        mailFrom(emisor);
        rcptTo(rcpto);
        data();
        subject(subject, mensaje);
        addAttachment(pdfFile);
        quit();
    }
 */
