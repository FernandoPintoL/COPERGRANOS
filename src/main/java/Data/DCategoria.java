package Data;

import connection.SQLConnection;
import java.sql.*;
import java.util.*;
import Utils.ConstPSQL;
import Utils.ConstGlobal;
import java.text.ParseException;

/**
 *
 * @author angela
 */
public final class DCategoria {

    private int id;
    private String nombre;
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DCategoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }   
    
    public DCategoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public DCategoria(int id) {
        this.id = id;
    }

    public DCategoria() {
    }
    
    private final String TABLE = "categoria";
    private final String QUERY_ID = "id_categoria";
    private final String QUERY_NOMBRE = "nombre";
    private final String QUERY_INSERT = String.format(
            "INSERT INTO %s (nombre, descripcion) VALUES (?, ?)", TABLE);
    private final String QUERY_UPDATE = String.format(
            "UPDATE %s SET nombre=?, descripcion=? WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_ELIMINAR = String.format("DELETE FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_VER = String.format("SELECT * FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_EXISTS = String.format("SELECT * FROM %s WHERE %s=?", TABLE, QUERY_NOMBRE);
    private final String QUERY_LIST = "SELECT * FROM " + TABLE;
    private final String MESSAGE_TRYCATCH = "ERROR MODELO: "+TABLE.toUpperCase()+" ";
    private SQLConnection connection;
    private PreparedStatement ps;
    private ResultSet set;
    
    private void init_conexion() {
        connection = new SQLConnection(
                ConstPSQL.user,
                ConstPSQL.pass,
                ConstGlobal.SERVIDOR,
                ConstGlobal.PORT_DB,
                ConstPSQL.dbName);
    }

    private String[] arrayData(ResultSet set) throws SQLException {
        return new String[]{
            String.valueOf(set.getInt("id_categoria")),
            String.valueOf(set.getString("nombre")),
            String.valueOf(set.getString("descripcion"))
        };
    }

    void preparerState() throws SQLException {
        try {
            // Intentar establecer los valores
            ps.setString(1, getNombre());
            ps.setString(2, getDescripcion());

        } catch (SQLException e) {
            // Manejar la excepción SQL
            System.out.println(MESSAGE_TRYCATCH+TABLE);
            e.printStackTrace(); // Imprimir la traza completa del error
            System.out.println("Mensaje de error: " + e.getMessage());
            System.out.println("Estado SQL: " + e.getSQLState());
            System.out.println("Código de error SQL: " + e.getErrorCode());

        }
    }

    public Object[] guardar() throws SQLException, ParseException {
        boolean isSuccess = false;
        String mensaje = "";
        String[] exists = null;
        int ids = 0;
        try {
            exists = existe(getNombre());
            if (exists != null) {
                mensaje = MESSAGE_TRYCATCH+" ya esta registrado. ID: ".toUpperCase()+exists[0];
                return new Object[]{false, mensaje, null};
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_INSERT);
            preparerState();
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = MESSAGE_TRYCATCH+" Registro insertado exitosamente.".toUpperCase();
                exists = existe(getNombre());
                ids = Integer.parseInt(exists[0]);
            } else {
                mensaje = MESSAGE_TRYCATCH+" Error al intentar guardar los datos.".toUpperCase();
                throw new SQLException(" No se pudo insertar el registro en la base de datos.".toUpperCase());
            }

        } catch (SQLException e) {
            // Imprimir detalles del error SQLException
            mensaje = MESSAGE_TRYCATCH+" (GUARDAR) Error en la base de datos: " + e.getMessage();
            System.out.println(MESSAGE_TRYCATCH + " GUARDAR");
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
                mensaje = MESSAGE_TRYCATCH+" (GUARDAR) " + "Error al cerrar PreparedStatement: " + TABLE + e.getMessage();
                System.out.println(" Error al cerrar PreparedStatement: " + TABLE + e.getMessage());
            }
        }
        return new Object[]{isSuccess, mensaje, ids};
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
            ps.setInt(3, getId());
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = TABLE+" (MODIFICAR) actualizacion exitosamente.".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH+" (MODIFICAR) Error al actualizar los datos.".toUpperCase();
                //throw new SQLException("No se pudo Error al actualizar los datos.".toUpperCase());
            }
        } catch (SQLException e) {
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
                System.out.println(MESSAGE_TRYCATCH+": NO EXISTE");
                return false;
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_ELIMINAR);
            ps.setInt(1, getId());
            if (ps.executeUpdate() == 0) {
                eliminado = false;
                System.err.println(MESSAGE_TRYCATCH + " No se pudo eliminar EL ALMACEN".toUpperCase());
                throw new SQLException("Error al intentar eliminar el registro.".toUpperCase());
            }

            eliminado = true; // Si el executeUpdate tuvo éxito, se actualiza el valor
        } catch (SQLException e) {
            eliminado = false;
            // Imprimir detalles del error SQLException
            System.out.println(MESSAGE_TRYCATCH + " ELIMINAR");
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
                System.out.println(MESSAGE_TRYCATCH+": Error al cerrar PreparedStatement: ".toUpperCase());
            }
        }
        return eliminado;
    }

    public List<String[]> listar() {
        List<String[]> datas = new ArrayList<>();
        ResultSet set = null;
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

    public String[] ver(){
        String[] data = null;
        try {
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_VER);
            ps.setInt(1, getId());
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
                System.err.println(MESSAGE_TRYCATCH+" Error al cerrar recursos: " + e.getMessage());
            }
        }
        return data;
    }
    
    public String[] existe(String nombre) throws SQLException {
        String[] data = null;
        setNombre(nombre);
        try {
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_EXISTS);
            ps.setString(1, getNombre());
            set = ps.executeQuery();
            if (set.next()) {
                data = arrayData(set);
                System.out.println(Arrays.toString(data));
            }
        } catch (SQLException e) {
            // Imprimir detalles de la excepción SQLException
            System.out.println(MESSAGE_TRYCATCH + " EXISTE");
            e.printStackTrace();  // Muestra la traza completa del error
            System.out.println("Mensaje de error: " + e.getMessage());  // Mensaje detallado del error
            System.out.println("Código de estado SQL: " + e.getSQLState());  // Código SQL estándar del error
            System.out.println("Código de error del proveedor: " + e.getErrorCode());  // Código de error específico del proveedor de la base de datos
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
                System.out.println(MESSAGE_TRYCATCH+" Error al cerrar recursos: ");
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
