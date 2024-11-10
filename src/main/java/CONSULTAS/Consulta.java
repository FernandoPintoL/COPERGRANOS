package CONSULTAS;

import NEGOCIO.NAdministrativo;
import UTILS.ConstGlobal;
import UTILS.Help;
import UTILS.Subject;
import NEGOCIO.NCategoria;
import NEGOCIO.NMedida;
import NEGOCIO.NPrecio;
import NEGOCIO.NProducto;
import NEGOCIO.NAlmacen;
import NEGOCIO.NCliente;
import NEGOCIO.NCompra;
import NEGOCIO.NDetalleCompra;
import NEGOCIO.NEnvio;
import NEGOCIO.NInventario;
import NEGOCIO.NMetodoPago;
import NEGOCIO.NPago;
import NEGOCIO.NPersona;
import NEGOCIO.NProveedor;
import NEGOCIO.NReporte;
import NEGOCIO.NSeguimiento;
import NEGOCIO.NTransaccionBancaria;
import CONNECTION.Pop3;
import CONNECTION.Smtp;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import OBJECT.Mensaje;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consulta {

    private final NAdministrativo NEGOCIO_ADMINISTRATIVO;
    private final NAlmacen NEGOCIO_ALMACEN;
    private final NCategoria NEGOCIO_CATEGORIA;
    private final NCliente NEGOCIO_CLIENTE;
    private final NCompra NEGOCIO_COMPRA;
    private final NDetalleCompra NEGOCIO_DETALLECOMPRA;
    private final NEnvio NEGOCIO_ENVIO;
    private final NInventario NEGOCIO_INVENTARIO;
    private final NMedida NEGOCIO_MEDIDA;
    private final NMetodoPago NEGOCIO_METODOPAGO;
    private final NPago NEGOCIO_PAGO;
    private final NPersona NEGOCIO_PERSONA;
    //private final NPrecio NEGOCIO_PRECIO;
    private final NProducto NEGOCIO_PRODUCTO;
    private final NProveedor NEGOCIO_PROVEEDOR;
    private final NSeguimiento NEGOCIO_SEGUIMIENTO;
    private final NTransaccionBancaria NEGOCIO_TRANSACCIONBANACARIA;
    private final NReporte NEGOCIO_REPORTE;
    // private Smtp smtp;
    private Pop3 pop3;

    public Consulta() throws IOException {
        // negocio o bussinness
        NEGOCIO_ALMACEN = new NAlmacen();
        NEGOCIO_ADMINISTRATIVO = new NAdministrativo();
        NEGOCIO_CATEGORIA = new NCategoria();
        NEGOCIO_CLIENTE = new NCliente();
        NEGOCIO_COMPRA = new NCompra();
        NEGOCIO_DETALLECOMPRA = new NDetalleCompra();
        NEGOCIO_ENVIO = new NEnvio();
        NEGOCIO_INVENTARIO = new NInventario();
        NEGOCIO_MEDIDA = new NMedida();
        NEGOCIO_METODOPAGO = new NMetodoPago();
        NEGOCIO_PAGO = new NPago();
        NEGOCIO_PERSONA = new NPersona();
        //NEGOCIO_PRECIO = new NPrecio();
        NEGOCIO_PRODUCTO = new NProducto();
        NEGOCIO_PROVEEDOR = new NProveedor();
        NEGOCIO_SEGUIMIENTO = new NSeguimiento();
        NEGOCIO_TRANSACCIONBANACARIA = new NTransaccionBancaria();
        NEGOCIO_REPORTE = new NReporte();
    }

    public int getCantidadMails() throws IOException {
        pop3 = new Pop3(ConstGlobal.SERVIDOR, ConstGlobal.PORT_POP3);
        pop3.login(ConstGlobal.USER, ConstGlobal.PASS);
        pop3.list();
        String number = pop3.number;
        pop3.retr(Integer.parseInt(number));
        pop3.quit();
        pop3.close();
        return Integer.parseInt(number);
    }

    public void newMensaje() throws IOException {
        String token = pop3.Token();
        System.out.println("TOKEN: " + token);
        String email = pop3.getEmail();
        if (token.toUpperCase().contains(Help.HELP)) {
            sendMail(email, "AYUDA", Help.ContenidoHelp());
            return;
        }
        Mensaje msj = Subject.subject(token, email);
        if (msj == null) {
            sendMail(email, "Error de comandos", comandoIncorrecto(token));
            return;
        }
        try {
            negocioAction(msj);
        } catch (NumberFormatException | ParseException e) {
            sendMail(msj.getEmisor(), "Error al convertir un parametros".toUpperCase(), errorConvertirParametros(msj));
        } catch (SQLException | IOException ex) {
            sendMail(msj.getEmisor(), "Error de conexion".toUpperCase(), errorConexion(ex.toString(), msj));
        }
    }

    private void negocioAction(Mensaje msj) throws IOException, SQLException, ParseException {
        String PARAMETROS_INCORRECTOS = "PARAMETROS INCORRECTOS | " + msj.getParametros();
        String DATE_INCORRECTO = "FECHA INCORRECTA | FORMATO CORRECTO (dd-MM-yyyy)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha_actual = LocalDate.now();
        String tableAction = msj.tableAction();
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("HH-mm-ss");
        String horaFormateada = horaActual.format(formatter_time);
        switch (tableAction) {
            case Help.PRODUCTO_VENDIDO + "_" + Help.REP: {
                String name_pdf = Help.PATH + "PRODUCTO_VENDIDO_" + horaFormateada + ".pdf";
                String chart_image = Help.PATH + "PRODUCTO_VENDIDO_" + horaFormateada + ".png";
                List<String[]> lista = NEGOCIO_REPORTE.listarProductoVendido(name_pdf, chart_image);
                if (lista.isEmpty()) {
                    sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCUENTRAN DATOS REGISTRADOS PARA ESTA CONSULTA".toUpperCase());
                } else {
                    listWithReport(Help.productoVendidoHeader, lista, msj, name_pdf);
                }
                break;
            }
            case Help.STOCK + "_" + Help.REP: {
                String name_pdf = Help.PATH + "PRODUCTO_ALMACEN_" + horaFormateada + ".pdf";
                String chart_image = Help.PATH + "PRODUCTO_ALMACEN_" + horaFormateada + ".png";
                List<String[]> lista = NEGOCIO_REPORTE.listarProductoAlmacen(name_pdf, chart_image);
                if (lista.isEmpty()) {
                    sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCUENTRAN DATOS REGISTRADOS PARA ESTA CONSULTA".toUpperCase());
                } else {
                    listWithReport(Help.productoAlmacenHeader, lista, msj, name_pdf);
                }
                break;
            }
            //ADMINISTRATIVO
            case Help.ADMINISTRATIVO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_ADMINISTRATIVO.listar();
                list(Help.administrativoHeader, lista, msj);
                break;
            }
            case Help.ADMINISTRATIVO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM6) {
                    String nombre = msj.getParametros().get(0);
                    String direccion = msj.getParametros().get(1);
                    int telefono = Integer.parseInt(msj.getParametros().get(2).trim());
                    String correo = msj.getParametros().get(3);
                    int ci = Integer.parseInt(msj.getParametros().get(4).trim());
                    String cargo = msj.getParametros().get(5);
                    if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 2) {
                        sendMail(msj.getEmisor(), msj.evento(), " El nombre es requerido. || Mayor a 3 caracteres.".toUpperCase());
                        break;
                    }
                    if (ci <= 0 || String.valueOf(ci).length() <= 2) {
                        sendMail(msj.getEmisor(), msj.evento(), " El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres".toUpperCase());
                        break;
                    }
                    Object[] responsse = NEGOCIO_ADMINISTRATIVO.guardar(nombre, direccion, telefono, correo, ci, cargo);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ADMINISTRATIVO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM8) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String nombre = msj.getParametros().get(1);
                    String direccion = msj.getParametros().get(2);
                    int telefono = Integer.parseInt(msj.getParametros().get(3).trim());
                    String correo = msj.getParametros().get(4);
                    int ci = Integer.parseInt(msj.getParametros().get(5).trim());
                    if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), " El nombre es requerido. || Mayor a 3 caracteres.".toUpperCase());
                        break;
                    }
                    if (ci <= 0 || String.valueOf(ci).length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), " El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres".toUpperCase());
                        break;
                    }
                    String cargo = msj.getParametros().get(6);
                    Date fecha_ingreso = Date.valueOf(fecha_actual);
                    try {
                        LocalDate fecha_correo = LocalDate.parse(msj.getParametros().get(7).trim(), formatter);
                        fecha_ingreso = java.sql.Date.valueOf(fecha_correo);
                    } catch (DateTimeParseException e) {
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + " " + fecha_ingreso + " \n" + e.getMessage());
                        break;
                    }
                    Object[] responsse = NEGOCIO_ADMINISTRATIVO.modificar(id, nombre, direccion, telefono, correo, ci, cargo, fecha_ingreso);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                    break;
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                    break;
                }
            }
            case Help.ADMINISTRATIVO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean delete = NEGOCIO_ADMINISTRATIVO.eliminar(id);
                    String message = delete ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ADMINISTRATIVO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id;
                    try {
                        id = Integer.parseInt(msj.getParametros().get(0).trim());
                    } catch (NumberFormatException e) {
                        sendMail(msj.getEmisor(), msj.evento(), "El número de ID debe ser un número válido.".toUpperCase());
                        break;
                    }
                    String[] data = NEGOCIO_ADMINISTRATIVO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN ADMINISTRATIVO CON ESTE ID: " + id);
                        break;
                    } else {
                        ver(Help.administrativoHeader, data, msj);
                        break;
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                    break;
                }
            }
            //END ADMINISTRATIVO            
            //ALMACEN             
            case Help.ALMACEN + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_ALMACEN.listar();
                list(Help.almacenHeader, lista, msj);
                break;
            }
            case Help.ALMACEN + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    int codigo = Integer.parseInt(msj.getParametros().get(0).trim());
                    String direccion = msj.getParametros().get(1);
                    Object[] response = NEGOCIO_ALMACEN.guardar(codigo, direccion);
                    String message = (String) response[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ALMACEN + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM3) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    int codigo = Integer.parseInt(msj.getParametros().get(1).trim());
                    String direccion = msj.getParametros().get(2);
                    Object[] response = NEGOCIO_ALMACEN.modificar(id, codigo, direccion);
                    String message = (String) response[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ALMACEN + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean delete = NEGOCIO_ALMACEN.eliminar(id);
                    String message = delete ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ALMACEN + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id;
                    try {
                        id = Integer.parseInt(msj.getParametros().get(0).trim());
                    } catch (NumberFormatException e) {
                        sendMail(msj.getEmisor(), msj.evento(), "El número de ID debe ser un número válido.".toUpperCase());
                        break;
                    }
                    String[] data = NEGOCIO_ALMACEN.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN ALMACEN CON ESTE ID: " + id);
                        break;
                    } else {
                        ver(Help.almacenHeader, data, msj);
                        break;
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                    break;
                }
            }
            //ALMACEN END
            //CATEGORIA 
            case Help.CATEGORIA + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_CATEGORIA.listar();
                list(Help.categoriaHeader, lista, msj);
                break;
            }
            case Help.CATEGORIA + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    System.out.println(msj.getParametros());
                    String nombre = msj.getParametros().get(0);
                    String descripcion = msj.getParametros().get(1);
                    Object[] responsse = NEGOCIO_CATEGORIA.guardar(nombre, descripcion);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.CATEGORIA + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM3) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim()); // Obtener el id
                    String nombre = msj.getParametros().get(1);
                    String descripcion = msj.getParametros().get(2);
                    Object[] responsse = NEGOCIO_CATEGORIA.modificar(id, nombre, descripcion);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.CATEGORIA + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_CATEGORIA.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.CATEGORIA + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_CATEGORIA.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA CATEGORIA CON ESTE ID: " + id);
                        break;
                    } else {
                        ver(Help.categoriaHeader, data, msj);
                        break;
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                    break;
                }
            }
            //CATEGORIA END
            //START CLIENTE
            case Help.CLIENTE + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM6) {
                    String nombre = msj.getParametros().get(0);
                    String direccion = msj.getParametros().get(1);
                    int telefono = Integer.parseInt(msj.getParametros().get(2).trim());
                    String correo = msj.getParametros().get(3);
                    int ci = Integer.parseInt(msj.getParametros().get(4).trim());
                    if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), " El nombre es requerido. || Mayor a 3 caracteres.".toUpperCase());
                        break;
                    }
                    if (ci <= 0 || String.valueOf(ci).length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), " El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres".toUpperCase());
                        break;
                    }
                    String tipo_cliente = msj.getParametros().get(5);
                    Object[] responsse = NEGOCIO_CLIENTE.guardar(nombre, direccion, telefono, correo, ci, tipo_cliente);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.CLIENTE + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM7) {
                    int id_cliente = Integer.parseInt(msj.getParametros().get(0).trim());
                    String nombre = msj.getParametros().get(1);
                    String direccion = msj.getParametros().get(2);
                    int telefono = Integer.parseInt(msj.getParametros().get(3).trim());
                    String correo = msj.getParametros().get(4);
                    int ci = Integer.parseInt(msj.getParametros().get(5).trim());
                    if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 2) {
                        sendMail(msj.getEmisor(), msj.evento(), "Los datos para la tabla ".toUpperCase() + msj.getTable() + ": El nombre es requerido. || Mayor a 3 caracteres.");
                        break;
                    }
                    if (ci <= 0 || String.valueOf(ci).length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), "Los datos para la tabla ".toUpperCase() + msj.getTable() + ": El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres");
                        break;
                    }
                    String tipo_cliente = msj.getParametros().get(6);
                    Object[] responsse = NEGOCIO_CLIENTE.modificar(id_cliente, nombre, direccion, telefono, correo, ci, tipo_cliente);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.CLIENTE + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id_cliente = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_CLIENTE.eliminar(id_cliente);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.CLIENTE + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_CLIENTE.ver(id);
                    //sendMail(msj.getEmisor(), msj.evento(), "El dato de la tabla " + msj.getTable());
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN CLIENTE CON ESTE ID: " + id);
                        break;
                    } else {
                        ver(Help.clienteHeader, data, msj);
                        break;
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                    break;
                }
            }
            case Help.CLIENTE + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_PERSONA.listar();
                list(Help.clienteHeader, lista, msj);
                break;
            }
            //END CLIENTE
            //START COMPRA
            case Help.COMPRA + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM4) {
                    Double preciototal = Double.valueOf(msj.getParametros().get(0).trim());
                    String estado = msj.getParametros().get(1);
                    int cliente_id = Integer.parseInt(msj.getParametros().get(2).trim());
                    int administrativo_id = Integer.parseInt(msj.getParametros().get(3).trim());
                    Object[] responsse = NEGOCIO_COMPRA.guardar(preciototal, estado, cliente_id, administrativo_id);
                    boolean isSucces = (boolean) responsse[0];
                    String message = (String) responsse[1];
                    if(isSucces){

                    }
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.COMPRA + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM4) {
                    int id_compra = Integer.parseInt(msj.getParametros().get(0).trim());
                    Double preciototal = Double.valueOf(msj.getParametros().get(1).trim());
                    Date fecha_compra = Date.valueOf(fecha_actual);
                    try {
                        LocalDate fecha_correo = LocalDate.parse(msj.getParametros().get(2).trim(), formatter);
                        fecha_compra = java.sql.Date.valueOf(fecha_correo); // Convertir a java.sql.Date si es necesario
                    } catch (DateTimeParseException e) {
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + " " + fecha_compra + " " + e.getMessage());
                        break;
                    }
                    String estado = msj.getParametros().get(3);
                    Object[] responsse = NEGOCIO_COMPRA.modificar(id_compra, preciototal, fecha_compra, estado);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.COMPRA + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_COMPRA.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.COMPRA + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_COMPRA.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA COMPRA CON ESTE ID: " + id);
                    } else {
                        ver(Help.compraHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.COMPRA + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_COMPRA.listar();
                list(Help.compraHeader, lista, msj);
                break;
            }
            //END COMPRA
            //START DETALLECOMPRA
            case Help.DETALLECOMPRA + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM5) {
                    Double cantidad = Double.valueOf(msj.getParametros().get(0).trim());
                    Double prescio_unitario = Double.valueOf(msj.getParametros().get(1).trim());
                    Double subtotal = Double.valueOf(msj.getParametros().get(2).trim());
                    int compra_id = Integer.parseInt(msj.getParametros().get(3).trim());
                    int producto_id = Integer.parseInt(msj.getParametros().get(4).trim());
                    Object[] responsse = NEGOCIO_DETALLECOMPRA.guardar(cantidad, prescio_unitario, subtotal, compra_id, producto_id);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.DETALLECOMPRA + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM4) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    Double cantidad = Double.valueOf(msj.getParametros().get(1).trim());
                    Double prescio_unitario = Double.valueOf(msj.getParametros().get(2).trim());
                    Double subtotal = Double.valueOf(msj.getParametros().get(3).trim());
                    Object[] responsse = NEGOCIO_DETALLECOMPRA.modificar(id, cantidad, prescio_unitario, subtotal);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.DETALLECOMPRA + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_DETALLECOMPRA.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.DETALLECOMPRA + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_DETALLECOMPRA.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN DETALLE COMPRA CON ESTE ID: " + id);
                    } else {
                        ver(Help.detallecompraHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.DETALLECOMPRA + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_DETALLECOMPRA.listar();
                list(Help.compraHeader, lista, msj);
                break;
            }
            //END DETALLECOMPRA            
            //START ENVIO
            case Help.ENVIO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM8) {
                    String direccion_envio = msj.getParametros().get(0);
                    String ciudad_envio = msj.getParametros().get(1);
                    String pais_destino = msj.getParametros().get(2);
                    String estado_envio = msj.getParametros().get(3);
                    //Date fecha_entrega = Date.valueOf(msj.getParametros().get(4));

                    Date fecha_entrega = Date.valueOf(fecha_actual);
                    try {
                        LocalDate fecha_correo = LocalDate.parse(msj.getParametros().get(4).trim(), formatter);
                        fecha_entrega = java.sql.Date.valueOf(fecha_correo); // Convertir a java.sql.Date si es necesario
                    } catch (DateTimeParseException e) {
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + " " + fecha_entrega + " " + e.getMessage());
                        break;
                    }

                    String metodo_envio = msj.getParametros().get(5);
                    String transporte = msj.getParametros().get(6);
                    int compra_id = Integer.parseInt(msj.getParametros().get(7).trim());
                    Object[] responsse = NEGOCIO_ENVIO.guardar(direccion_envio, ciudad_envio, pais_destino, estado_envio, fecha_entrega, metodo_envio, transporte, compra_id);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ENVIO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM9) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    //Date fecha_envio = Date.valueOf(msj.getParametros().get(1));

                    Date fecha_envio = Date.valueOf(fecha_actual);
                    try {
                        LocalDate fecha_correo = LocalDate.parse(msj.getParametros().get(1).trim(), formatter);
                        fecha_envio = java.sql.Date.valueOf(fecha_correo); // Convertir a java.sql.Date si es necesario
                    } catch (DateTimeParseException e) {
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + " " + fecha_envio + " " + e.getMessage());
                        break;
                    }

                    String direccion_envio = msj.getParametros().get(2);
                    String ciudad_envio = msj.getParametros().get(3);
                    String pais_destino = msj.getParametros().get(4);
                    String estado_envio = msj.getParametros().get(5);
                    //Date fecha_entrega = Date.valueOf(msj.getParametros().get(6));

                    Date fecha_entrega = Date.valueOf(fecha_actual);
                    try {
                        LocalDate fecha_correo1 = LocalDate.parse(msj.getParametros().get(6).trim(), formatter);
                        fecha_entrega = java.sql.Date.valueOf(fecha_correo1); // Convertir a java.sql.Date si es necesario
                    } catch (DateTimeParseException e) {
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + " " + fecha_entrega + " " + e.getMessage());
                        break;
                    }

                    String metodo_envio = msj.getParametros().get(7);
                    String transporte = msj.getParametros().get(8);
                    Object[] responsse = NEGOCIO_ENVIO.modificar(id, fecha_envio, direccion_envio, ciudad_envio, pais_destino, estado_envio, fecha_entrega, metodo_envio, transporte);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ENVIO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_ENVIO.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ENVIO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_ENVIO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN ENVIO CON ESTE ID: " + id);
                    } else {
                        ver(Help.envioHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.ENVIO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_ENVIO.listar();
                list(Help.compraHeader, lista, msj);
                break;
            }
            //END ENVIO
            //INVENTARIO
            case Help.INVENTARIO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM3) {
                    int producto_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    int almacen_id = Integer.parseInt(msj.getParametros().get(1).trim());
                    double stock = Double.parseDouble(msj.getParametros().get(2).trim());
                    Object[] responsse = NEGOCIO_INVENTARIO.guardar(producto_id, almacen_id, stock);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                    break;
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.INVENTARIO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM4) {
                    int producto_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    int almacen_id = Integer.parseInt(msj.getParametros().get(1).trim());
                    int stock = Integer.parseInt(msj.getParametros().get(2).trim());
                    //String fechaString = msj.getParametros().get(3);
                    Date fecha = Date.valueOf(fecha_actual);
                    try {
                        LocalDate local_fecha = LocalDate.parse(msj.getParametros().get(3).trim(), formatter);
                        fecha = java.sql.Date.valueOf(local_fecha); // Convertir a java.sql.Date si es necesario
                    } catch (DateTimeParseException e) {
                        System.out.println("Error de formato de fecha: " + e.getMessage());
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + fecha + e.getMessage());
                        break;
                    }
                    Object[] responsse = NEGOCIO_INVENTARIO.modificar(producto_id, almacen_id, stock, fecha);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.INVENTARIO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    int producto_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    int almacen_id = Integer.parseInt(msj.getParametros().get(1).trim());
                    boolean responsse = NEGOCIO_INVENTARIO.eliminar(producto_id, almacen_id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.INVENTARIO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    int producto_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    int almacen_id = Integer.parseInt(msj.getParametros().get(1).trim());
                    String[] data = NEGOCIO_INVENTARIO.ver(producto_id, almacen_id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN INVENTARIO ID: " + producto_id + " / " + almacen_id);
                    } else {
                        ver(Help.inventarioHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.INVENTARIO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_INVENTARIO.listar();
                list(Help.inventarioHeader, lista, msj);
                break;
            }
            //INVENTARIO END
            //MEDIDA 
            case Help.MEDIDA + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_MEDIDA.listar();
                list(Help.medidaHeader, lista, msj);
                break;
            }
            case Help.MEDIDA + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    String detalle = msj.getParametros().get(0);
                    Object[] responsse = NEGOCIO_MEDIDA.guardar(detalle);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.MEDIDA + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim()); // Obtener el id
                    String detalle = msj.getParametros().get(1);
                    Object[] responsse = NEGOCIO_MEDIDA.modificar(id, detalle);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.MEDIDA + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_MEDIDA.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.MEDIDA + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int medida_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_MEDIDA.ver(medida_id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA MEDIDA ID: " + medida_id);
                    } else {
                        ver(Help.medidaHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //MEDIDA END
            //METODO PAGO 
            case Help.METODOPAGO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_METODOPAGO.listar();
                list(Help.metodopagoHeader, lista, msj);
                break;
            }
            case Help.METODOPAGO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_METODOPAGO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA MEDIDA ID: " + id);
                    } else {
                        ver(Help.metodopagoHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.METODOPAGO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    String detalle = msj.getParametros().get(0);
                    Object[] responsse = NEGOCIO_METODOPAGO.guardar(detalle);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.METODOPAGO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim()); // Obtener el id
                    String detalle = msj.getParametros().get(1);
                    Object[] responsse = NEGOCIO_METODOPAGO.modificar(id, detalle);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.METODOPAGO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_METODOPAGO.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //METODO PAGO END
            //PAGO 
            case Help.PAGO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_PAGO.listar();
                list(Help.pagoHeader, lista, msj);
                break;
            }
            case Help.PAGO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_PAGO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN PAGO ID: " + id);
                    } else {
                        ver(Help.pagoHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS.toUpperCase(), erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PAGO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM6) {
                    Double monto = Double.valueOf(msj.getParametros().get(0).trim());
                    String moneda = msj.getParametros().get(1);
                    String estado_pago = msj.getParametros().get(2);
                    int compra_id = Integer.parseInt(msj.getParametros().get(3).trim());
                    String metodo_pago = msj.getParametros().get(4).trim();
                    //int transaccion_id = Integer.parseInt(msj.getParametros().get(5).trim());
                    Object[] responsse = NEGOCIO_PAGO.guardar(monto, moneda, estado_pago, compra_id, metodo_pago);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PAGO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM4) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    Double monto = Double.valueOf(msj.getParametros().get(1).trim());
                    String moneda = msj.getParametros().get(2);
                    //Date fecha_pago = Date.valueOf(msj.getParametros().get(3));

                    Date fecha_pago = Date.valueOf(fecha_actual);
                    try {
                        LocalDate local_fecha = LocalDate.parse(msj.getParametros().get(3).trim(), formatter);
                        fecha_pago = java.sql.Date.valueOf(local_fecha); // Convertir a java.sql.Date si es necesario
                    } catch (DateTimeParseException e) {
                        System.out.println("Error de formato de fecha: " + e.getMessage());
                        sendMail(msj.getEmisor(), msj.evento(), DATE_INCORRECTO + fecha_pago + e.getMessage());
                        break;
                    }

                    String estado_pago = msj.getParametros().get(4);
                    String metodo_pago = msj.getParametros().get(5).trim();
                    Object[] responsse = NEGOCIO_PAGO.modificar(id, monto, moneda, fecha_pago, estado_pago, metodo_pago);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PAGO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_PAGO.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //PAGO END
            //START PERSONA 
            case Help.PERSONA + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM6) {
                    String nombre = msj.getParametros().get(0);
                    String apellido = msj.getParametros().get(1);
                    String direccion = msj.getParametros().get(2);
                    int telefono = Integer.parseInt(msj.getParametros().get(3).trim());
                    String correo = msj.getParametros().get(4);
                    int ci = Integer.parseInt(msj.getParametros().get(5).trim());
                    Object[] responsse = NEGOCIO_PERSONA.guardar(nombre, apellido, direccion, telefono, correo, ci);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PERSONA + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM7) {
                    int persona_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String nombre = msj.getParametros().get(1);
                    String apellido = msj.getParametros().get(2);
                    String direccion = msj.getParametros().get(3);
                    int telefono = Integer.parseInt(msj.getParametros().get(4).trim());
                    String correo = msj.getParametros().get(5);
                    int ci = Integer.parseInt(msj.getParametros().get(6).trim());
                    Object[] responsse = NEGOCIO_PERSONA.modificar(persona_id, nombre, apellido, direccion, telefono, correo, ci);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PERSONA + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int persona_id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_PERSONA.eliminar(persona_id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PERSONA + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_PERSONA.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA PERSONA ID: " + id);
                    } else {
                        ver(Help.personaHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PERSONA + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_PERSONA.listar();
                list(Help.personaHeader, lista, msj);
                break;
            }
            //END PERSONA
            /*/PRECIO
            case Help.PRECIO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_PRECIO.listar();
                list(Help.precioHeader, lista, msj);
                break;
            }
            case Help.PRECIO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_PRECIO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA PERSONA ID: " + id);
                    } else {
                        ver(Help.precioHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PRECIO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM2) {
                    double precios = Double.parseDouble(msj.getParametros().get(0).trim());
                    String detalle = msj.getParametros().get(1);
                    Object[] responsse = NEGOCIO_PRECIO.guardar(precios, detalle);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PRECIO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM3) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    Double precios = Double.valueOf(msj.getParametros().get(1).trim());
                    String detalle = msj.getParametros().get(2);
                    Object[] responsse = NEGOCIO_PRECIO.modificar(id, precios, detalle);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PRECIO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_PRECIO.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //PRECIO END*/
            //PRODUCTO             
            case Help.PRODUCTO + "_" + Help.LIS: {
                System.out.println("INGRESAMOS AL METODO");
                List<String[]> lista = NEGOCIO_PRODUCTO.listar();
                list(Help.productoHeader, lista, msj);
                break;
            }
            case Help.PRODUCTO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_PRODUCTO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUNA PRODUCTO ID: " + id);
                    } else {
                        ver(Help.productoHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PRODUCTO + "_" + Help.ADD: {
                System.out.println("LLEGANDO AL METODO PRODUCTO");
                if (msj.getParametros().size() == Help.LENPARAM6) {
                    String nombre = msj.getParametros().get(0);
                    int codigo = Integer.parseInt(msj.getParametros().get(1).trim());
                    String descripcion = msj.getParametros().get(2);
                    double precios = Double.parseDouble(msj.getParametros().get(3).trim());
                    int categoria_id = Integer.parseInt(msj.getParametros().get(4).trim());
                    int medida_id = Integer.parseInt(msj.getParametros().get(5).trim());
                    Object[] responsse = NEGOCIO_PRODUCTO.guardar(nombre, codigo, descripcion, precios, categoria_id, medida_id);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PRODUCTO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM7) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String nombre = msj.getParametros().get(1);
                    int codigo = Integer.parseInt(msj.getParametros().get(2).trim());
                    String descripcion = msj.getParametros().get(3);
                    Double precios = Double.valueOf(msj.getParametros().get(4).trim());
                    int categoria_id = Integer.parseInt(msj.getParametros().get(5).trim());
                    int medida_id = Integer.parseInt(msj.getParametros().get(6).trim());
                    Object[] responsse = NEGOCIO_PRODUCTO.modificar(id, nombre, codigo, descripcion, precios, categoria_id, medida_id);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), "Parametros incorrectos", erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PRODUCTO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_PRODUCTO.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //PRODUCTO END            
            //PROVEEDOR             
            case Help.PROVEEDOR + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_PROVEEDOR.listar();
                list(Help.proveedorHeader, lista, msj);
                break;
            }
            case Help.PROVEEDOR + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_PROVEEDOR.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN PROVEEDOR ID: " + id);
                    } else {
                        ver(Help.proveedorHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PROVEEDOR + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM7) {
                    String nombre = msj.getParametros().get(0);
                    String direccion = msj.getParametros().get(1);
                    int telefono = Integer.parseInt(msj.getParametros().get(2).trim());
                    String correo = msj.getParametros().get(3);
                    int ci = Integer.parseInt(msj.getParametros().get(4).trim());
                    if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 2) {
                        sendMail(msj.getEmisor(), msj.evento(), "Los datos para la tabla ".toUpperCase() + msj.getTable() + ": El nombre es requerido. || Mayor a 3 caracteres.");
                        break;
                    }
                    if (ci <= 0 || String.valueOf(ci).length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), "Los datos para la tabla ".toUpperCase() + msj.getTable() + ": El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres");
                        break;
                    }
                    String tipo_proveedor = msj.getParametros().get(5);
                    String descripcion = msj.getParametros().get(6);
                    Object[] responsse = NEGOCIO_PROVEEDOR.guardar(nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PROVEEDOR + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM9) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String nombre = msj.getParametros().get(1);
                    String direccion = msj.getParametros().get(3);
                    int telefono = Integer.parseInt(msj.getParametros().get(4).trim());
                    String correo = msj.getParametros().get(5);
                    int ci = Integer.parseInt(msj.getParametros().get(6).trim());
                    if (nombre == null || nombre.trim().isEmpty() || nombre.length() <= 2) {
                        sendMail(msj.getEmisor(), msj.evento(), "Los datos para la tabla ".toUpperCase() + msj.getTable() + ": El nombre es requerido. || Mayor a 3 caracteres.");
                        break;
                    }
                    if (ci <= 0 || String.valueOf(ci).length() <= 1) {
                        sendMail(msj.getEmisor(), msj.evento(), "Los datos para la tabla ".toUpperCase() + msj.getTable() + ": El CI es requerido y debe ser mayor a 0. || Mayor a 3 caracteres");
                        break;
                    }
                    String tipo_proveedor = msj.getParametros().get(7);
                    String descripcion = msj.getParametros().get(8);
                    Object[] responsse = NEGOCIO_PROVEEDOR.modificar(id, nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), "RESPUESTA DE LA TABLA " + msj.getTable() + message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), "Parametros incorrectos", erorrLengthParametros(msj));
                }
                break;
            }
            case Help.PROVEEDOR + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_PROVEEDOR.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //PROVEEDOR END
            //SEGUIMIENTO         
            case Help.SEGUIMIENTO + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_SEGUIMIENTO.listar();
                list(Help.seguimientoHeader, lista, msj);
                break;
            }
            case Help.SEGUIMIENTO + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_SEGUIMIENTO.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN SEGUIMIENTO ID: " + id);
                    } else {
                        ver(Help.seguimientoHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.SEGUIMIENTO + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM4) {
                    String descripcion = msj.getParametros().get(0);
                    String ubicacion_actual = msj.getParametros().get(1);
                    String estado_actual = msj.getParametros().get(2);
                    int envio_id = Integer.parseInt(msj.getParametros().get(3).trim());
                    Object[] responsse = NEGOCIO_SEGUIMIENTO.guardar(descripcion, ubicacion_actual, estado_actual, envio_id);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.SEGUIMIENTO + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM5) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    Date fecha_evento = Date.valueOf(msj.getParametros().get(1));
                    String descripcion = msj.getParametros().get(2);
                    String ubicacion_actual = msj.getParametros().get(3);
                    String estado_actual = msj.getParametros().get(4);
                    Object[] responsse = NEGOCIO_SEGUIMIENTO.modificar(id, fecha_evento, descripcion, ubicacion_actual, estado_actual);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), "Parametros incorrectos", erorrLengthParametros(msj));
                }
                break;
            }
            case Help.SEGUIMIENTO + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_SEGUIMIENTO.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //SEGUIMIENTO END
            /*/TRANSACCION BANCARIA
            case Help.TRANSACCIONBANCARIA + "_" + Help.LIS: {
                List<String[]> lista = NEGOCIO_TRANSACCIONBANACARIA.listar();
                list(Help.transaccionbancariaHeader, lista, msj);
                break;
            }
            case Help.TRANSACCIONBANCARIA + "_" + Help.VER: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    String[] data = NEGOCIO_TRANSACCIONBANACARIA.ver(id);
                    if (data == null) {
                        sendMail(msj.getEmisor(), msj.evento(), "NO SE ENCONTRO NINGUN SEGUIMIENTO ID: " + id);
                    } else {
                        ver(Help.transaccionbancariaHeader, data, msj);
                    }
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.TRANSACCIONBANCARIA + "_" + Help.ADD: {
                if (msj.getParametros().size() == Help.LENPARAM3) {
                    int num_transaccion = Integer.parseInt(msj.getParametros().get(0).trim());
                    String banco_origen = msj.getParametros().get(1);
                    String banco_destino = msj.getParametros().get(2);
                    Object[] responsse = NEGOCIO_TRANSACCIONBANACARIA.guardar(num_transaccion, banco_origen, banco_destino);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            case Help.TRANSACCIONBANCARIA + "_" + Help.MOD: {
                if (msj.getParametros().size() == Help.LENPARAM5) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    int num_transaccion = Integer.parseInt(msj.getParametros().get(1).trim());
                    String banco_origen = msj.getParametros().get(2);
                    String banco_destino = msj.getParametros().get(3);
                    Date fecha_transaccion = Date.valueOf(msj.getParametros().get(4));
                    Object[] responsse = NEGOCIO_TRANSACCIONBANACARIA.modificar(id, num_transaccion, banco_origen, banco_destino, fecha_transaccion);
                    String message = (String) responsse[1];
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), "Parametros incorrectos", erorrLengthParametros(msj));
                }
                break;
            }
            case Help.TRANSACCIONBANCARIA + "_" + Help.DEL: {
                if (msj.getParametros().size() == Help.LENPARAM1) {
                    int id = Integer.parseInt(msj.getParametros().get(0).trim());
                    boolean responsse = NEGOCIO_TRANSACCIONBANACARIA.eliminar(id);
                    String message = responsse ? " se elimino con exito".toUpperCase() : " (ERROR AL ELIMNAR).".toUpperCase();
                    sendMail(msj.getEmisor(), msj.evento(), message.toUpperCase());
                } else {
                    sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, erorrLengthParametros(msj));
                }
                break;
            }
            //TRANSACCION BANCARIA END*/
            default:
                sendMail(msj.getEmisor(), PARAMETROS_INCORRECTOS, tablaOActionNotFount(msj));
                break;
        }
    }

    private String errorConexion(String exception, Mensaje msj) {
        String helpError = Help.errorMensaje("Error de conexión", "Error al conectarse al servidor, intente nuevamente.",
                exception + " No se pudo ejecutar el comando: " + msj.toString());
        return helpError;
    }

    private String erorrLengthParametros(Mensaje msj) {
        String helpError = Help.errorMensaje("Error Parametros", "Cantidad de parametros incorrectos.", msj.toString());
        return helpError;
    }

    private String errorConvertirParametros(Mensaje msj) {
        String helpError = Help.errorMensaje("Error convercion", "Error al convertir elementos del parametro", msj.toString());
        return helpError;
        //return "ERROR AL CONVERTIR PARAMETROS: \n" + msj.toString();
    }

    private String comandoIncorrecto(String token) {
        String helpError = Help.errorMensaje("Error de comandos", "No se pudo decifrar el comando", " comando: " + token);
        //return "No se pudo decifrar el comando: \n" + token + "\n PARA MAS AYUDA ENVIE UN CORREO CON LA PALABRA \t HELP \t";
        return helpError;
    }

    private String tablaOActionNotFount(Mensaje msj) {
        String helpError = Help.errorMensaje("Error TABLA o ACCCION", "Tabla O Accion no encontrada", msj.toString());
        return helpError;
        //return "!!!!TABLA O ACCION NO ENCONTRADA!!! \n Error en el comando: \n" + msj.toString() +"\n PARA MAS AYUDA ENVIE UN CORREO CON LA PALABRA \t HELP\t!!!!";
    }

    private void list(String[] header, List<String[]> lista, Mensaje msj) throws IOException {
        String body = Help.listMensaje(msj.tableAction(), header, lista);
        sendMail(msj.getEmisor(), msj.evento(), body);
    }

    private void listWithReport(String[] header, List<String[]> lista, Mensaje msj, String name_pdfFile) throws IOException {
        String body = Help.listMensajeToPdf(msj.tableAction(), header, lista);
        sendMailPdf(msj.getEmisor(), msj.evento(), body, name_pdfFile);
    }

    private void ver(String[] header, String[] cu, Mensaje msj) throws IOException {
        String body = Help.ver(msj.tableAction(), header, cu);
        sendMail(msj.getEmisor(), msj.evento(), body);
    }

    private void sendMail(String rcpt, String titulo, String mensaje) throws IOException {
        System.out.println("rcpt: " + rcpt + "titulo: " + titulo + "mensaje: " + mensaje);
        Smtp smtp = new Smtp(ConstGlobal.SERVIDOR, ConstGlobal.PORT_SMPT);
        smtp.sendMail(ConstGlobal.EMAIL, "<" + rcpt + ">", titulo, mensaje);
    }

    private void sendMailPdf(String rcpt, String titulo, String mensaje, String name_pdfFile) throws IOException {
        System.out.println("rcpt: " + rcpt + "titulo: " + titulo + "mensaje: " + mensaje);
        Smtp smtp = new Smtp(ConstGlobal.SERVIDOR, ConstGlobal.PORT_SMPT);
        smtp.sendMailWithPdf(ConstGlobal.EMAIL, "<" + rcpt + ">", titulo, mensaje, name_pdfFile);
    }

    private void generatePDF() {

    }

    /*private void reporte(String[] header, String[] cu, Mensaje msj) throws IOException {
        sendMail(msj.getEmisor(), msj.evento(), body);
    }*/
}
