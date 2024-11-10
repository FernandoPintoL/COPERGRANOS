/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import UTILS.ConstGlobal;
import UTILS.ConstPSQL;
import CONNECTION.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author fpl
 */
public class DEnvio {

    private int id;
    private Date fecha_envio;
    private String direccion_envio;
    private String ciudad_envio;
    private String pais_destino;
    private String estado_envio;
    private Date fecha_entrega;
    private String metodo_envio;
    private String transporte;
    private int compra_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha_envio() {
        if (this.fecha_envio == null || this.fecha_envio.toString().length() == 0) {
            return Date.valueOf(LocalDate.now());
        } else {
            return fecha_envio;
        }
    }

    public void setFecha_envio(Date fecha_envio) {
        if (fecha_envio == null || fecha_envio.toString().length() == 0) {
            this.fecha_envio = Date.valueOf(LocalDate.now());
        } else {
            this.fecha_envio = fecha_envio;
        }
    }

    public String getDireccion_envio() {
        return direccion_envio;
    }

    public void setDireccion_envio(String direccion_envio) {
        this.direccion_envio = direccion_envio;
    }

    public String getCiudad_envio() {
        return ciudad_envio;
    }

    public void setCiudad_envio(String ciudad_envio) {
        this.ciudad_envio = ciudad_envio;
    }

    public String getPais_destion() {
        return pais_destino;
    }

    public void setPais_destion(String pais_destino) {
        this.pais_destino = pais_destino;
    }

    public String getEstado_envio() {
        return estado_envio;
    }

    public void setEstado_envio(String estado_envio) {
        this.estado_envio = estado_envio;
    }

    public Date getFecha_entrega() {
        if (this.fecha_entrega == null || this.fecha_entrega.toString().length() == 0) {
            return Date.valueOf(LocalDate.now());
        } else {
            return fecha_entrega;
        }
    }

    public void setFecha_entrega(Date fecha_entrega) {
        if (fecha_entrega == null || fecha_entrega.toString().length() == 0) {
            this.fecha_entrega = Date.valueOf(LocalDate.now());
        } else {
            this.fecha_entrega = fecha_entrega;
        }
    }

    public String getMetodo_envio() {
        return metodo_envio;
    }

    public void setMetodo_envio(String metodo_envio) {
        this.metodo_envio = metodo_envio;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public int getCompra_id() {
        return compra_id;
    }

    public void setCompra_id(int compra_id) {
        this.compra_id = compra_id;
    }

    public DEnvio() {
    }

    public DEnvio(int id) {
        this.id = id;
    }

    public DEnvio(String direccion_envio, String ciudad_envio, String pais_destion, String estado_envio, Date fecha_entrega, String metodo_envio, String transporte, int compra_id) {
        this.direccion_envio = direccion_envio;
        this.ciudad_envio = ciudad_envio;
        this.pais_destino = pais_destion;
        this.estado_envio = estado_envio;
        this.fecha_entrega = fecha_entrega;
        this.metodo_envio = metodo_envio;
        this.transporte = transporte;
        this.compra_id = compra_id;
    }

    public DEnvio(int id, Date fecha_envio, String direccion_envio, String ciudad_envio, String pais_destino, String estado_envio, Date fecha_entrega, String metodo_envio, String transporte) {
        this.id = id;
        this.fecha_envio = fecha_envio;
        this.direccion_envio = direccion_envio;
        this.ciudad_envio = ciudad_envio;
        this.pais_destino = pais_destino;
        this.estado_envio = estado_envio;
        this.fecha_entrega = fecha_entrega;
        this.metodo_envio = metodo_envio;
        this.transporte = transporte;
    }

    private final String TABLE = "seguimiento";
    private final String QUERY_ID = "id_envio";
    private final String QUERY_INSERT = String.format(
            "INSERT INTO %s (fecha_envio, direccion_envio, ciudad_envio, pais_destino, estado_envio, fecha_entrega, metodo_envio, transporte, compra_id) VALUES (?,?,?,?,?,?,?,?,?)", TABLE);
    private final String QUERY_UPDATE = String.format(
            "UPDATE %s SET fecha_envio=?, direccion_envio=?, ciudad_envio=?, pais_destino=?, estado_envio=?, fecha_entrega=?, metodo_envio=?, transporte=?, compra_id=? WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_ELIMINAR = String.format("DELETE FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_VER = String.format("SELECT * FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_LIST = "SELECT * FROM " + TABLE;
    private final String MESSAGE_TRYCATCH = "ERROR MODELO: " + TABLE.toUpperCase() + " ";
    private SQLConnection connection;
    private PreparedStatement ps;
    private ResultSet set;

    private String[] arrayData(ResultSet set) throws SQLException {
        return new String[]{
            String.valueOf(set.getInt("id_envio")),
            String.valueOf(set.getDate("fecha_envio")),
            String.valueOf(set.getString("direccion_envio")),
            String.valueOf(set.getString("ciudad_envio")),
            String.valueOf(set.getString("pais_destino")),
            String.valueOf(set.getString("estado_envio")),
            String.valueOf(set.getDate("fecha_entrega")),
            String.valueOf(set.getString("metodo_envio")),
            String.valueOf(set.getString("transporte")),
            String.valueOf(set.getInt("compra_id"))
        };
    }

    void preparerState() throws SQLException {
        try {
            // Intentar establecer los valores
            ps.setDate(1, getFecha_envio());
            ps.setString(2, getDireccion_envio());
            ps.setString(3, getCiudad_envio());
            ps.setString(4, getPais_destion());
            ps.setString(5, getEstado_envio());
            ps.setDate(6, getFecha_entrega());
            ps.setString(7, getMetodo_envio());
            ps.setString(8, getTransporte());

        } catch (SQLException e) {
            // Manejar la excepción SQL
            System.out.println(MESSAGE_TRYCATCH + TABLE);
            e.printStackTrace(); // Imprimir la traza completa del error
            System.out.println("Mensaje de error: " + e.getMessage());
            System.out.println("Estado SQL: " + e.getSQLState());
            System.out.println("Código de error SQL: " + e.getErrorCode());

        }
    }

    private void init_conexion() {
        connection = new SQLConnection(
                ConstPSQL.user,
                ConstPSQL.pass,
                ConstGlobal.SERVIDOR,
                ConstGlobal.PORT_DB,
                ConstPSQL.dbName);
    }

    public Object[] guardar() throws SQLException, ParseException {
        boolean isSuccess = false;
        String mensaje = "";
        try {
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_INSERT);
            preparerState();
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = TABLE+" Registro insertado exitosamente.".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH + " Error al intentar guardar los datos.".toUpperCase();
                //throw new SQLException("No se pudo insertar el registro en la base de datos.".toUpperCase());
            }
        } catch (SQLException e) {
            // Imprimir detalles del error SQLException
            mensaje = MESSAGE_TRYCATCH + " (GUARDAR) Error en la base de datos: " + e.getMessage();
            System.out.println(MESSAGE_TRYCATCH + " GUARDAR");
            System.out.println("Mensaje: " + e.getMessage()); // Mensaje detallado del error
            System.out.println("Estado SQL: " + e.getSQLState()); // Código de estado SQL
            System.out.println("Código de error: " + e.getErrorCode()); // Código de error del proveedor de la BD
            e.printStackTrace(); // Imprime toda la traza del error para depurar
        } finally {
            // Cerrar el PreparedStatement
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + TABLE + e.getMessage());
                mensaje = MESSAGE_TRYCATCH + " (GUARDAR) Error al cerrar PreparedStatement: " + TABLE + e.getMessage();
            }
        }
        return new Object[]{isSuccess, mensaje};
    }

    public Object[] modificar() throws SQLException, ParseException {
        boolean isSuccess = false;
        String mensaje = "";
        try {
            String[] exists = ver();
            if(exists == null){
                System.out.println(MESSAGE_TRYCATCH+" NO EXISTE");
                return new Object[]{false, "IDS INGRESADOS NO SE ENCUENTRAN REGISTRADADAS EN LA TABLA: "+TABLE.toUpperCase()};
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_UPDATE);
            preparerState();
            ps.setInt(9, getId());
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = TABLE+ " - (MODIFICAR) actualizacion exitosamente.".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH + " - (MODIFICAR) Error al actualizar los datos.".toUpperCase();
                //throw new SQLException("No se pudo Error al actualizar los datos.".toUpperCase());
            }
        } catch (SQLException e) {
            System.out.println(MESSAGE_TRYCATCH + " MODIFICAR");
            mensaje = MESSAGE_TRYCATCH + " MODIFICAR";
            e.printStackTrace(); // Imprime toda la traza del error para depurar
            System.out.println("Mensaje: " + e.getMessage()); // Mensaje detallado del error
            System.out.println("Estado SQL: " + e.getSQLState()); // Código de estado SQL
            System.out.println("Código de error: " + e.getErrorCode()); // Código de error del proveedor de la BD
        } finally {
            // Cerrar el PreparedStatement
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println(" (MODIFICAR) Error al cerrar PreparedStatement: ".toUpperCase() + TABLE + e.getMessage());
                mensaje = MESSAGE_TRYCATCH + " (MODIFICAR) Error al cerrar PreparedStatement: ".toUpperCase() + TABLE + e.getMessage();
            }
        }
        return new Object[]{isSuccess, mensaje};
    }

    public boolean eliminar() {
        boolean eliminado = false;
        try {
            String[] exists = ver();
            if (exists == null) {
                System.out.println("EL ADMINSITRATIVO NO EXISTE");
                return false;
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_ELIMINAR);
            ps.setInt(1, getId());
            if (ps.executeUpdate() == 0) {
                eliminado = false;
                System.err.println(MESSAGE_TRYCATCH + " No se pudo eliminar la persona");
                //throw new SQLException("Error al intentar eliminar el registro.");
            }
            eliminado = true; // Si el executeUpdate tuvo éxito, se actualiza el valor
        } catch (SQLException e) {
            eliminado = false;
            // Muestra detalles de la excepción SQL
            System.err.println("Error de SQL: " + e.getMessage());
            System.err.println("Estado SQL: " + e.getSQLState());
            System.err.println("Código de Error: " + e.getErrorCode());
            e.printStackTrace(); // Imprime la pila de llamadas para más detalles
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                eliminado = false;
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return eliminado;
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> datas = new ArrayList<>();
        try {
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_LIST);
            set = ps.executeQuery();
            while (set.next()) {
                datas.add(arrayData(set));
            }
        } catch (SQLException e) {
            // Imprimir el error en consola con detalles
            System.out.println(MESSAGE_TRYCATCH + " LISTAR");
            e.printStackTrace(); // Esto imprime toda la traza del error, útil para depurar
            System.out.println("Mensaje: " + e.getMessage()); // Mensaje del error SQL
            System.out.println("Estado SQL: " + e.getSQLState()); // Estado SQL asociado al error
            System.out.println("Código de error: " + e.getErrorCode()); // Código de error del proveedor
        } finally {
            // Cerrar recursos para evitar fugas de memoria
            try {
                if (set != null) {
                    set.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println(e.toString() + TABLE);
                e.printStackTrace(); // Imprimir cualquier error al cerrar recursos
            }
        }
        return datas;
    }

    public String[] ver() {
        String[] data = null;
        try {
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_VER);
            ps.setInt(1, getId());
            //ps.setInt(2,getCi());
            set = ps.executeQuery();
            if (set.next()) {
                data = arrayData(set);
            }
        } catch (SQLException e) {
            // Muestra detalles de la excepción SQL
            System.err.println("Error de SQL: " + e.getMessage());
            System.err.println("Estado SQL: " + e.getSQLState());
            System.err.println("Código de Error: " + e.getErrorCode());
            e.printStackTrace(); // Imprime la pila de llamadas para más detalles
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return data;
    }

    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }

    }
}
