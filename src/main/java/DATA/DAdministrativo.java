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
import java.util.Arrays;

/**
 *
 * @author fpl
 */
public class DAdministrativo {
    int id;
    int id_persona;
    String cargo;
    Date fecha_ingreso;
    String nombre;
    String direccion;
    int telefono;
    String correo;
    int ci;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFecha_ingreso() {
        if (this.fecha_ingreso == null || this.fecha_ingreso.toString().length() == 0) {
            return Date.valueOf(LocalDate.now());
        } else {
            return fecha_ingreso;
        }
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        if (this.fecha_ingreso == null || fecha_ingreso.toString().length() == 0) {
            this.fecha_ingreso = Date.valueOf(LocalDate.now());
        } else {
            this.fecha_ingreso = fecha_ingreso;
        }

    }
    
    public DAdministrativo() {
    }

    public DAdministrativo(int id, String cargo, Date fecha_ingreso, String nombre, String direccion, int telefono, String correo, int ci) {
        this.id = id;
        this.cargo = cargo;
        this.nombre = nombre;
        this.fecha_ingreso = fecha_ingreso;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ci = ci;
    }

    public DAdministrativo(String cargo, String nombre, String direccion, int telefono, String correo, int ci) {
        this.cargo = cargo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ci = ci;
    }  
    

    public DAdministrativo(int id_administrativo) {
        this.id = id_administrativo;
    }
    
    private final String TABLE = "administrativo";
    private final String QUERY_ID = "id_administrativo";
    private final String Q_CI = "ci";
    private final String QUERY_INSERT = String.format(
            "INSERT INTO %s (nombre, apellido, direccion, telefono, correo, ci, cargo, fecha_ingreso) VALUES (?,?,?,?,?,?,?,?)", TABLE);
    private final String QUERY_UPDATE = String.format(
            "UPDATE %s SET nombre=?, apellido=?, direccion=?, telefono=?, correo=?, ci=?, cargo=?, fecha_ingreso=? WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_ELIMINAR = String.format("DELETE FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_VER = String.format("SELECT * FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_LIST = "SELECT * FROM " + TABLE;
    private final String QUERY_CHECK = String.format("SELECT COUNT(*) FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_CI = String.format("SELECT * FROM %s WHERE %s=?", TABLE, Q_CI);
    private final String MESSAGE_TRYCATCH = "ERROR MODELO: " + TABLE.toUpperCase() + " ";
    private SQLConnection connection;
    private PreparedStatement ps;
    private ResultSet set;

    private String[] arrayData(ResultSet set) throws SQLException {
        return new String[]{
            String.valueOf(set.getInt("id_administrativo")),
            String.valueOf(set.getString("cargo")),
            String.valueOf(set.getInt("ci")),
            String.valueOf(set.getString("nombre")),
            String.valueOf(set.getString("direccion")),
            String.valueOf(set.getInt("telefono")),
            String.valueOf(set.getString("correo")),
            String.valueOf(set.getDate("fecha_ingreso"))
        };
    }

    void preparerState() throws SQLException {
        try {
            // Intentar establecer los valores
            ps.setString(1, getNombre());
            ps.setString(2, getDireccion());
            ps.setInt(3, getTelefono());
            ps.setString(4, getCorreo());
            ps.setInt(5, getCi());
            ps.setString(6, getCargo());
            ps.setDate(7, getFecha_ingreso());

        } catch (SQLException e) {
            // Manejar la excepción SQL
            System.out.println(MESSAGE_TRYCATCH + TABLE);
            System.out.println("Mensaje de error: " + e.getMessage());
            System.out.println("Estado SQL: " + e.getSQLState());
            System.out.println("Código de error SQL: " + e.getErrorCode());
            e.printStackTrace(); // Imprimir la traza completa del error
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
        String[] exists = null;
        try {
            exists = existe(getCi());
            if (exists != null) {
                mensaje = "La persona ya está registrada. ID: ".toUpperCase()+exists[0];
                return new Object[]{false, mensaje, null};
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_INSERT);
            preparerState();
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = TABLE+" Registro insertado exitosamente. ".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH+" Error al intentar guardar los datos.".toUpperCase();
                //throw new SQLException("No se pudo insertar el registro en la base de datos.".toUpperCase());
            }
        } catch (SQLException e) {
            // Imprimir detalles del error SQLException
            mensaje = MESSAGE_TRYCATCH+" (GUARDAR) Error en la base de datos: " + e.getMessage();
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
                mensaje = MESSAGE_TRYCATCH+" (GUARDAR) Error al cerrar PreparedStatement: " + TABLE + e.getMessage();
            }
        }
        return new Object[]{isSuccess, mensaje};
    }
    
    public String[] existe(int ci) throws SQLException {
        String[] data = null;
        setCi(ci);
        try {
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_CI);
            ps.setInt(1, getCi());
            set = ps.executeQuery();
            if (set.next()) {
                data = arrayData(set);
                System.out.println(Arrays.toString(data));
            }
        } catch (SQLException e) {
            // Imprimir detalles de la excepción SQLException
            System.out.println(MESSAGE_TRYCATCH + " VER");
            System.out.println("Mensaje de error: " + e.getMessage());  // Mensaje detallado del error
            System.out.println("Código de estado SQL: " + e.getSQLState());  // Código SQL estándar del error
            System.out.println("Código de error del proveedor: " + e.getErrorCode());  // Código de error específico del proveedor de la base de datos
            e.printStackTrace();  // Muestra la traza completa del error
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
                System.out.println("Error al cerrar recursos: " + TABLE + e.getMessage());
            }
        }
        return data;
    }
    
    public boolean checkId() throws SQLException, ParseException {
        boolean isSuccess = false;
        try {
            init_conexion();
            // Verificar si el id y almacen_id existen en la tabla
            ps = connection.connect().prepareStatement(QUERY_CHECK);
            ps.setInt(1, getId());
            set = ps.executeQuery();
            if (set.next() && set.getInt(1) == 0) {
                //mensaje = "Error: El id o almacen_id no existen en la tabla.";
                System.out.println("Error: NO EXISTE ID"+getId());
                isSuccess = false;
                //throw new SQLException(mensaje);
            } else {
                isSuccess = true;
            }
        } catch (SQLException e) {
            //mensaje = MESSAGE_TRYCATCH + " MODIFICAR " + e.getMessage().toUpperCase();
            isSuccess = false;
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Estado SQL: " + e.getSQLState());
            System.out.println("Código de error: " + e.getErrorCode());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                isSuccess = false;
                //mensaje = " (MODIFICAR) Error al cerrar PreparedStatement: ".toUpperCase() + MESSAGE_TRYCATCH + TABLE + e.getMessage();
                System.out.println(" (MODIFICAR) Error al cerrar PreparedStatement: ".toUpperCase() + MESSAGE_TRYCATCH + TABLE + e.getMessage());
            }
        }
        return isSuccess;
    }
    
    public Object[] modificar() throws SQLException, ParseException {
        boolean isSuccess = false;
        String mensaje = "";
        try {
            boolean isCheckedId = checkId();
            if (!isCheckedId) {
                return new Object[]{isCheckedId, "IDS INGRESADOS NO SE ENCUENTRAN REGISTRADADAS EN LA TABLA: " + TABLE.toUpperCase()};
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_UPDATE);
            preparerState();
            ps.setInt(8, getId());
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = TABLE+" - (MODIFICAR) actualizacion exitosamente.".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH+" - (MODIFICAR) Error al actualizar los datos.".toUpperCase();
                //throw new SQLException("No se pudo Error al actualizar los datos.".toUpperCase());
            }
        } catch (SQLException e) {
            System.out.println(MESSAGE_TRYCATCH + " MODIFICAR");
            mensaje = MESSAGE_TRYCATCH + " MODIFICAR "+e.getMessage();
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
                System.out.println(" (MODIFICAR) Error al cerrar PreparedStatement: ".toUpperCase() + TABLE + e.getMessage());
                mensaje = MESSAGE_TRYCATCH+" (MODIFICAR) Error al cerrar PreparedStatement: ".toUpperCase() + TABLE + e.getMessage();
            }
        }
        return new Object[]{isSuccess, mensaje};
    }

    public boolean eliminar() {
        boolean eliminado = false;
        try {
            String[] exists = ver();
            if(exists == null){
                System.out.println(MESSAGE_TRYCATCH+" NO EXISTE");
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
            System.out.println("Mensaje: " + e.getMessage()); // Mensaje del error SQL
            System.out.println("Estado SQL: " + e.getSQLState()); // Estado SQL asociado al error
            System.out.println("Código de error: " + e.getErrorCode()); // Código de error del proveedor
            e.printStackTrace(); // Esto imprime toda la traza del error, útil para depurar
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
