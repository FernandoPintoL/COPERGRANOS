package Data;

import connection.SQLConnection;
import java.sql.*;
import java.util.*;
import Utils.ConstPSQL;
import Utils.ConstGlobal;
import java.text.ParseException;

public class DPrecio {

    private int id;
    private double precio;
    private String detalle_medida;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDetalle_medida() {
        return detalle_medida;
    }

    public void setDetalle_medida(String detalle_medida) {
        this.detalle_medida = detalle_medida;
    }

    public DPrecio() {
    }

    public DPrecio(int id) {
        this.id = id;
    }

    public DPrecio(double precio, String detalle_medida) {
        this.precio = precio;
        this.detalle_medida = detalle_medida;
    }

    public DPrecio(int id, double precio, String detalle_medida) {
        this.id = id;
        this.precio = precio;
        this.detalle_medida = detalle_medida;
    }
    
    
    private final String TABLE = "precio";
    private final String QUERY_ID = "id_precio";
    private final String QUERY_INSERT = String.format(
            "INSERT INTO %s (precio, detalle_medida) VALUES (?,?)", TABLE);
    private final String QUERY_UPDATE = String.format(
            "UPDATE %s SET precio=?, detalle_medida=? WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_ELIMINAR = String.format("DELETE FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_VER = String.format("SELECT * FROM %s WHERE %s=?", TABLE, QUERY_ID);
    private final String QUERY_LIST = "SELECT * FROM " + TABLE;
    private final String MESSAGE_TRYCATCH = "ERROR MODELO: " + TABLE.toUpperCase() + " ";
    private SQLConnection connection;
    private PreparedStatement ps;
    private ResultSet set;

    private String[] arrayData(ResultSet set) throws SQLException {
        return new String[]{
            String.valueOf(set.getInt("id_precio")),
            //String.valueOf(set.getBigDecimal("precio")),
            String.valueOf(set.getDouble("precio")),
            String.valueOf(set.getString("detalle_medida")),};
    }

    void preparerState() throws SQLException {
        try {
            // Intentar establecer los valores
            ps.setDouble(1, getPrecio());
            ps.setString(2, getDetalle_medida());
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
                mensaje = MESSAGE_TRYCATCH+" Error al intentar guardar los datos.".toUpperCase();
                //throw new SQLException("No se pudo insertar el registro en la base de datos.".toUpperCase());
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
                System.out.println("Error al cerrar PreparedStatement: " + TABLE + e.getMessage());
                mensaje = MESSAGE_TRYCATCH+" (GUARDAR) Error al cerrar PreparedStatement: " + TABLE + e.getMessage();
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
                mensaje = TABLE+" - (MODIFICAR) actualizacion exitosamente.".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH+" - (MODIFICAR) Error al actualizar los datos.".toUpperCase();
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
                throw new SQLException("Error al intentar eliminar el registro.");
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
