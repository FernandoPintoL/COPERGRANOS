package UTILS;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author angela
 */
public class Help {

    public static final String ADD = "ADD";
    public static final String MOD = "MOD";
    public static final String DEL = "DEL";
    public static final String ACT = "ACT";
    public static final String DES = "DES";
    public static final String LIS = "LIS";
    public static final String VER = "VER";
    public static final String CAN = "CAN";
    public static final String REP = "REP";
    public static final String END = "END";

    // List de todas las tablas
    public static final String ADMINISTRATIVO = "ADM";
    public static final String ALMACEN = "ALM";
    public static final String CATEGORIA = "CAT";
    public static final String CLIENTE = "CLI";
    public static final String COMPRA = "CMP";
    public static final String DETALLECOMPRA = "DCMP";
    public static final String ENVIO = "ENV";
    public static final String INVENTARIO = "INV";
    public static final String MEDIDA = "MED";
    public static final String METODOPAGO = "MPAG";
    public static final String PAGO = "PAG";
    public static final String PERSONA = "PER";
    public static final String PRECIO = "PRE";
    public static final String PRODUCTO = "PRO";
    public static final String PROVEEDOR = "PRV";
    public static final String SEGUIMIENTO = "SEG";
    public static final String TRANSACCIONBANCARIA = "TBAN";
    public static final String STOCK = "STOCK";
    public static final String PRODUCTO_VENDIDO = "PRODUCTOVENDIDO";
    
    public static final String PATH = "/home/fpl/NetBeansProjects/COPERGRANOS/";


    //help
    public static final String HELP = "HELP";

    public static final int LENPARAM1 = 1;
    public static final int LENPARAM2 = 2;
    public static final int LENPARAM3 = 3;
    public static final int LENPARAM4 = 4;
    public static final int LENPARAM5 = 5;
    public static final int LENPARAM6 = 6;
    public static final int LENPARAM7 = 7;
    public static final int LENPARAM8 = 8;
    public static final int LENPARAM9 = 9;
    public static final int LENPARAM10 = 10;

    public static final String[] administrativoHeader = {"ID","CARGO","NOMBRE","DIRECCION","TELEFONO","CORREO","CI","FECHA INGRESO"};
    public static final String[] almacenHeader = {"ID", "CODIGO", "DIRECCION"};
    public static final String[] categoriaHeader = {"ID", "NOMBRE", "DESCRIPCION"};
    public static final String[] clienteHeader = {"ID", "NOMBRE", "DIRECCION","TELEFONO","CORREO","CI", "TIPO CLIENTE"};
    public static final String[] compraHeader = {"ID", "PRECIO TOTAL", "FECHA COMPRA", "ESTADO", "CLIENTE ID", "ADMINISTRATIVO ID"};
    public static final String[] compraToReportHeader = {"ID", "PRECIO TOTAL", "FECHA COMPRA", "ESTADO", "CLIENTE", "ADMINISTRATIVO"};
    public static final String[] detallecompraHeader = {"ID", "CANTIDAD", "PRECIO UNITARIO", "SUB TOTAL", "COMPRA ID", "PRODUCTO ID"};
    public static final String[] detallecompraToReportHeader = {"ID", "PRODUCTO","CANTIDAD", "PRECIO UNITARIO", "SUB TOTAL"};
    public static final String[] envioHeader = {"ID", "FECHA ENVIO", "DIRECCION ENVIO", "CIUDAD ENVIO", "PAIS DESTINO", "ESTADO ENVIO", "FECHA ENTREGA", "METODO ENVIO", "TRANSPORTE", "COMPRA ID"};
    public static final String[] inventarioHeader = {"PRODUCTO ID", "ALAMACEN ID", "STOCK", "FECHA"};
    public static final String[] medidaHeader = {"ID", "DETALLE"};
    public static final String[] metodopagoHeader = {"ID", "NOMBRE"};
    public static final String[] pagoHeader = {"ID", "MONTO", "MONEDA", "FECHA PAGO", "ESTADO PAGO", "COMPRO ID", "METODO PAGO"};
    public static final String[] personaHeader = {"ID", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "CORREO", "CI"};
    //public static final String[] precioHeader = {"ID", "PRECIO", "DETALLE MEDIDA"};
    public static final String[] productoHeader = {"ID","CODIGO", "NOMBRE", "DESCRIPCION", "PRECIO", "CATEGORIA ID", "MEDIDA ID"};
    public static final String[] proveedorHeader = {"ID", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "CI", "TIPO PROVEEDOR", "DESCRIPCION"};
    public static final String[] seguimientoHeader = {"ID", "FECHA EVENTO", "DESCRIPCION", "UBICACION ACTUAL", "ESTADO ACTUAL", "ENVIO ID"};
    //public static final String[] transaccionbancariaHeader = {"ID", "NUMERO DE TRANSACCION", "BANCO ORIGEN", "BANCO DESTINO", "FECHA TRANSACCION"};
    public static final String[] productoAlmacenHeader = {"ID PRODUCTO", "ID ALMACEN", "COD ALMACEN", "PRODUCTO NOMBRE", "STOCK"};
    public static final String[] productoVendidoHeader = {"ID PRODUCTO", "NOMBRE", "PRECIO UNITARIO", "CANTIDAD VENDIDA", "TOTAL VENDIDO"};

    public static String ContenidoHelp() {
        return table();
    }

    public static String errorMensaje(String title, String error, String comando) {
        return "Content-Type: text/html; charset=\"UTF-8\" \r\n\r\n"
                + "<h1 >!!! OCURRIO UN ERROR !!! </h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black;\"> \r\n\r\n"
                + "<tr> \r\n\r\n"
                + "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> TITULO </th> \r\n\r\n"
                + "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> ERROR </th> \r\n\r\n"
                + "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> COMANDO </th> \r\n\r\n"
                + "</tr> \r\n\r\n"
                + "<tr> \r\n\r\n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + title + "</td> \r\n\r\n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\"> " + error + " </td> \r\n\r\n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\"> " + comando + ";</td> \r\n\r\n"
                + "</tr> \r\n\r\n"
                + "<tr> \r\n\r\n"
                + "</table>"
                + "<h3 style=\"color:red\"> PARA MAS AYUDA ENVIE UN CORREO CON LA PALABRA <b> HELP </b> </h3>";
    }
    
    public static String listMensajeToPdf(String title, String[] header, List<String[]> listaObject) {
        String response = "<h1>" + title.toUpperCase() + " - HELP</h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black;\"> \r\n\r\n";
        response += trStart;
        for (String head : header) {
            System.out.println("HEADER : "+head);
            response += thHeader(head);
        }
        response += trEnd;

        for (String[] cadenas : listaObject) {
            response += trStart;
            for (String cad : cadenas) {
                System.out.println("OBJETOS : "+cad);
                response += td(cad);
            }
            response += trEnd;
        }
        response += footerTable();
        return response;
    }
    

    public static String listMensaje(String title, String[] header, List<String[]> listaObject) {
        String response = "Content-Type: text/html; charset=\"UTF-8\" \r\n\r\n"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black;\"> \r\n\r\n";
        response += trStart;
        for (String head : header) {
            response += thHeader(head);
        }
        response += trEnd;

        for (String[] cadenas : listaObject) {
            response += trStart;
            for (String cad : cadenas) {
                response += td(cad);
            }
            response += trEnd;
        }
        response += footerTable();
        return response;
    }

    public static String ver(String title, String[] header, String[] cad) {
        String response = "Content-Type: text/html; charset=\"UTF-8\" \r\n\r\n"
                + "<h1>" + title.toUpperCase() + " - HELP</h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black;\"> \r\n\r\n";
        response += trStart;
        for (String head : header) {
            response += thHeader(head);
        }
        response += trEnd;
        response += trStart;
        for (String c : cad) {
            response += td(c);
        }
        response += trEnd;
        response += footerTable();
        return response;
    }

    private static String thHeader(String title) {
        return "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> " + title + " </th> \r\n\r\n";
    }

    private static String headerTable(String title) {
        return "Content-Type: text/html; charset=\"UTF-8\" \r\n\r\n"
                + "<h1>" + title.toUpperCase() + " - HELP</h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid black;\"> \r\n\r\n"
                + "<tr> \r\n\r\n"
                + "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> CASOS DE USOS </th> \r\n\r\n"
                + "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> METODOS </th> \r\n\r\n"
                + "<th style=\"text-align: center; padding: 8px; background-color: green; color: white; border: 1px solid black;\"> COMANDOS </th> \r\n\r\n"
                + "</tr> \r\n\r\n";
    }

    private static String trStart = "<tr> \r\n\r\n";
    private static String trEnd = "</tr> \r\n\r\n";

    private static String table() {
        String header = headerTable("SISTEMA VIA EMAIL Copergranos".toUpperCase());
        List<CasosUso> listCasosUso = getCasosUso();
        String body = "";
        for (CasosUso casoUso : listCasosUso) {
            String title = casoUso.titulo;
            for (Option option : casoUso.options) {
                body += trStart;
                body += td(title);
                body += td(option.title);
                body += td(option.parametros);
                body += trEnd;
            }
        }
        header += body;
        header += footerTable();
        return header;
    }

    private static String td(String content) {
        return "<td style=\"text-align: left; padding: 8px; border: 1px solid black;\"> " + content + " </td> \r\n\r\n";
    }

    private static String footerTable() {
        return "<tr> \r\n\r\n"
                + "</table>";
    }

    private static List<CasosUso> getCasosUso() {
        List<CasosUso> casos = new LinkedList<>();
        //REPORTES #1
        CasosUso reporte = new CasosUso("CU1. GESTIONAR REPORTES ");
        reporte.addCaso(new Option("CONTROL DE STOCK", STOCK + "_REP[];"));
        reporte.addCaso(new Option("CANTIDAD Y MONTO DE PRODUCTOS VENDIDODOS", PRODUCTO_VENDIDO + "_REP[];"));

        //ALMACEN #2
        CasosUso almacen = new CasosUso("CU2. GESTIONAR ALMACEN ");
        almacen.addCaso(new Option("GUARDAR ALMACEN", ALMACEN + "_ADD[codigo,direccion];"));
        almacen.addCaso(new Option("MODIFICAR ALMACEN", ALMACEN + "_MOD[id_almacen,codigo,direccion];"));
        almacen.addCaso(new Option("ELIMINAR ALMACEN", ALMACEN + "_DEL[id_almacen];"));
        almacen.addCaso(new Option("VER ALMACEN", ALMACEN + "_VER[id_almacen];"));
        almacen.addCaso(new Option("LISTAR ALMACEN", ALMACEN + "_LIS[];"));

        //INVENTARIO #3
        CasosUso inventario = new CasosUso("CU3. GESTIONAR INVENTARIO ");
        inventario.addCaso(new Option("GUARDAR INVENTARIO", INVENTARIO+"_ADD[producto_id,almacen_id,stock];"));
        inventario.addCaso(new Option("MODIFICAR INVENTARIO", INVENTARIO+"_MOD[producto_id,almacen_id,stock,fecha];"));
        inventario.addCaso(new Option("ELIMINAR INVENTARIO", INVENTARIO+"_DEL[producto_id,almacen_id];"));
        inventario.addCaso(new Option("VER INVENTARIO", INVENTARIO+"_VER[producto_id,almacen_id];"));
        inventario.addCaso(new Option("LISTAR INVENTARIO", INVENTARIO+"_LIS[];"));

        //PAGO #4
        CasosUso pago = new CasosUso("CU4. GESTIONAR PAGO ");
        pago.addCaso(new Option("GUARDAR PAGO", PAGO + "_ADD[monto, moneda, estado_pago, compra_id, metodo_pago];"));
        pago.addCaso(new Option("MODIFICAR PAGO", PAGO + "_MOD[id_pago, monto, moneda, estado_pago];"));
        pago.addCaso(new Option("ELIMINAR PAGO", PAGO + "_DEL[id_pago];"));
        pago.addCaso(new Option("LISTAR PAGO", PAGO + "_LIS[];"));
        pago.addCaso(new Option("VER PAGO", PAGO + "_VER[id_pago];"));

        //ADMINISTRATIVO #5
        CasosUso administrativo = new CasosUso("CU5. GESTIONAR ADMINISTRATIVO ");
        administrativo.addCaso(new Option("GUARDAR ADMINISTRATIVO " , ADMINISTRATIVO + "_ADD[nombre, direccion, telefono, correo, ci, cargo];"));
        administrativo.addCaso(new Option("MODIFICAR ADMINISTRATIVO ", ADMINISTRATIVO + "_MOD[id_administrativo, nombre, direccion, telefono, correo, ci, cargo, fecha_ingreso];"));
        administrativo.addCaso(new Option("ELIMINAR ADMINISTRATIVO ", ADMINISTRATIVO + "_DEL[id_administrativo];"));
        administrativo.addCaso(new Option("VER ADMINISTRATIVO ", ADMINISTRATIVO + "_VER[id_administrativo];"));
        administrativo.addCaso(new Option("LISTAR ADMINISTRATIVO ", ADMINISTRATIVO + "_LIS[];"));

        //CLIENTE #5
        CasosUso cliente = new CasosUso("CU5. GESTIONAR CLIENTE ");
        cliente.addCaso(new Option("GUARDAR CLIENTE", CLIENTE + "_ADD[id_cliente,nombre,direccion,telefono,correo,ci,cargo,tipo_cliente];"));
        cliente.addCaso(new Option("MODIFICAR CLIENTE", CLIENTE + "_MOD[id_cliente,nombre,direccion,telefono,correo,ci,cargo,tipo_cliente];"));
        cliente.addCaso(new Option("ELIMINAR CLIENTE", CLIENTE + "_DEL[id_cliente];"));
        cliente.addCaso(new Option("VER CLIENTE", CLIENTE + "_VER[id_cliente];"));
        cliente.addCaso(new Option("LISTAR CLIENTE", CLIENTE + "_LIS[];"));

        //PROVEEDOR #5
        CasosUso proveedor = new CasosUso("CU5. GESTIONAR PROVEEDOR ");
        proveedor.addCaso(new Option("GUARDAR PROVEEDOR", PROVEEDOR + "_ADD[nombre, direccion, telefono, correo, ci,tipo_proveedor, descripcion];"));
        proveedor.addCaso(new Option("MODIFICAR PROVEEDOR", PROVEEDOR + "_MOD[id_proveedor,nombre, direccion, telefono, correo, ci, tipo_proveedor, descripcion];"));
        proveedor.addCaso(new Option("ELIMINAR PROVEEDOR", PROVEEDOR + "_DEL[id_proveedor];"));
        proveedor.addCaso(new Option("VER PROVEEDOR", PROVEEDOR + "_VER[id_proveedor];"));
        proveedor.addCaso(new Option("LISTAR PROVEEDOR", PROVEEDOR + "_LIS[];"));

        //PRODUCTO #6
        CasosUso producto = new CasosUso("CU6. GESTIONAR PRODUCTO ");
        producto.addCaso(new Option("GUARDAR PRODUCTO", PRODUCTO+"_ADD[nombre,codigo,descripcion,precio,categoria_id,medida_id,cantidad_stock];"));
        producto.addCaso(new Option("MODIFICAR PRODUCTO", PRODUCTO+"_MOD[id_producto,nombre,codigo,descripcion,precio,categoria_id,medida_id];"));
        producto.addCaso(new Option("ELIMINAR PRODUCTO", PRODUCTO+"_DEL[id_producto];"));
        producto.addCaso(new Option("VER PRODUCTO", PRODUCTO+"_VER[id_producto];"));
        producto.addCaso(new Option("LISTAR PRODUCTO", PRODUCTO+"_LIS[];"));

        //CATEGORIA #6
        CasosUso categoria = new CasosUso("CU6. GESTIONAR CATEGORIA ");
        categoria.addCaso(new Option("GUARDAR CATEGORIA", CATEGORIA + "_ADD[nombre,descripcion];"));
        categoria.addCaso(new Option("MODIFICAR CATEGORIA", CATEGORIA + "_MOD[id_catgoria,nombre,descripcion];"));
        categoria.addCaso(new Option("ELIMINAR CATEGORIA", CATEGORIA + "_DEL[id_catgoria];"));
        categoria.addCaso(new Option("VER CATEGORIA", CATEGORIA + "_VER[id_catgoria];"));
        categoria.addCaso(new Option("LISTAR CATEGORIA", CATEGORIA + "_LIS[];"));

        //MEDIDA #6
        CasosUso medida = new CasosUso("CU6. GESTIONAR MEDIDA ");
        medida.addCaso(new Option("GUARDAR MEDIDA", MEDIDA+"_ADD[detalle];"));
        medida.addCaso(new Option("MODIFICAR MEDIDA", MEDIDA+"_MOD[id_medida,detalle];"));
        medida.addCaso(new Option("ELIMINAR MEDIDA", MEDIDA+"_DEL[id_medida];"));
        medida.addCaso(new Option("VER MEDIDA", MEDIDA+"_VER[id_medida];"));
        medida.addCaso(new Option("LISTAR MEDIDA", MEDIDA+"_LIS[];"));

        //COMPRA #7
        CasosUso compra = new CasosUso("CU7. GESTIONAR COMPRA ");
        compra.addCaso(new Option("GUARDAR COMPRA", COMPRA + "_ADD[cliente_id,administrativo_id];"));
        compra.addCaso(new Option("FINALIZAR COMPRA", COMPRA + "_END[compra_id];"));
        compra.addCaso(new Option("MODIFICAR COMPRA", COMPRA + "_MOD[id_compra,preciototal,fechacompra,estado];"));
        compra.addCaso(new Option("ELIMINAR COMPRA", COMPRA + "_DEL[id_compra];"));
        compra.addCaso(new Option("LISTAR COMPRA", COMPRA + "_LIS[];"));
        compra.addCaso(new Option("VER COMPRA", COMPRA + "_VER[id_compra];"));

        //DETALLECOMPRA #7
        CasosUso detallecompra = new CasosUso("CU7. GESTIONAR DETALLE COMPRA ");
        detallecompra.addCaso(new Option("GUARDAR DETALLE COMPRA", DETALLECOMPRA + "_ADD[compra_id, producto_id, cantidad];"));
        detallecompra.addCaso(new Option("MODIFICAR DETALLE COMPRA", DETALLECOMPRA + "_MOD[id_detallecompra,cantidad];"));
        detallecompra.addCaso(new Option("ELIMINAR DETALLE COMPRA", DETALLECOMPRA + "_DEL[id_detallecompra];"));
        detallecompra.addCaso(new Option("VER DETALLE COMPRA", DETALLECOMPRA + "_VER[id_detallecompra];"));
        detallecompra.addCaso(new Option("LISTAR DETALLE COMPRA", DETALLECOMPRA + "_LIS[];"));

        //ENVIO #8
        CasosUso envio = new CasosUso("CU8. GESTIONAR ENVIO ");
        envio.addCaso(new Option("GUARDAR ENVIO", ENVIO + "_ADD[direccion_envio,ciudad_envio,pais_destino,estado_envio,fecha_entrega,metodo_envio,transporte,compra_id];"));
        envio.addCaso(new Option("MODIFICAR ENVIO", ENVIO + "_MOD[id_envio,fecha_envio,direccion_envio,ciudad_envio,pais_destino,estado_envio,fecha_entrega,metodo_envio,transporte];"));
        envio.addCaso(new Option("ELIMINAR ENVIO", ENVIO + "_DEL[id_envio];"));
        envio.addCaso(new Option("VER ENVIO", ENVIO + "_VER[id_envio];"));
        envio.addCaso(new Option("LISTAR ENVIO", ENVIO + "_LIS[];"));

        //SEGUIMIENTO #6
        CasosUso seguimiento = new CasosUso("CU8. GESTIONAR SEGUIMIENTO ");
        seguimiento.addCaso(new Option("GUARDAR SEGUIMIENTO", SEGUIMIENTO + "_ADD[descripcion, ubicacion_actual, estado_actual, envio_id];"));
        seguimiento.addCaso(new Option("MODIFICAR SEGUIMIENTO", SEGUIMIENTO + "_MOD[id_seguimiento, descripcion, ubicacion_actual, estado_actual];"));
        seguimiento.addCaso(new Option("ELIMINAR SEGUIMIENTO", SEGUIMIENTO + "_DEL[id_seguimiento];"));
        seguimiento.addCaso(new Option("VER SEGUIMIENTO", SEGUIMIENTO + "_VER[id_seguimiento];"));
        seguimiento.addCaso(new Option("LISTAR SEGUIMIENTO", SEGUIMIENTO + "_LIS[];"));

        //1
        casos.add(reporte);
        //2
        casos.add(almacen);
        //3
        casos.add(inventario);
        //4
        casos.add(pago);
        //5
        casos.add(administrativo);
        casos.add(cliente);
        casos.add(proveedor);
        //6
        casos.add(producto);
        casos.add(categoria);
        casos.add(medida);
        //7
        casos.add(compra);
        casos.add(detallecompra);
        //8
        casos.add(envio);
        casos.add(seguimiento);
        return casos;
    }


}
