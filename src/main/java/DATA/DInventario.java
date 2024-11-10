package DATA;

import CONNECTION.SQLConnection;
import java.sql.*;
import java.util.*;
import java.sql.Date;
import UTILS.ConstPSQL;
import UTILS.ConstGlobal;
import java.text.ParseException;

public class DInventario {

    private int id;
    private int almacen_id;
    private double stock;
    private Date fecha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlmacen_id() {
        return almacen_id;
    }

    public void setAlmacen_id(int almacen_id) {
        this.almacen_id = almacen_id;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public DInventario() {
    }
    

    public DInventario(int id, int almacen_id, double stock) {
        this.id = id;
        this.almacen_id = almacen_id;
        this.stock = stock;
    }

    public DInventario(int id, int almacen_id, double stock, Date fecha) {
        this.id = id;
        this.almacen_id = almacen_id;
        this.stock = stock;
        this.fecha = fecha;
    }

    public DInventario(int id, int almacen_id) {
        this.id = id;
        this.almacen_id = almacen_id;
    }

    private final String TABLE = "inventario";
    private final String PRODUCTO_ID = "producto_id";
    private final String ALMACEN_ID = "almacen_id";
    private final String QUERY_INSERT = String.format(
            "INSERT INTO %s (stock, fecha, producto_id, almacen_id) VALUES (?,?,?,?)", TABLE);
    private final String QUERY_UPDATE = String.format(
            "UPDATE %s SET stock=?, fecha=? WHERE %s=? AND %s=?", TABLE, PRODUCTO_ID, ALMACEN_ID);
    private final String QUERY_ELIMINAR = String.format("DELETE FROM %s WHERE %s=? AND %s=?", TABLE, PRODUCTO_ID, ALMACEN_ID);
    private final String QUERY_VER = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLE, PRODUCTO_ID, ALMACEN_ID);
    private final String QUERY_LIST = "SELECT * FROM " + TABLE;
    private final String QUERY_CHECK = String.format("SELECT COUNT(*) FROM %s WHERE %s=? AND %s=?", TABLE, PRODUCTO_ID, ALMACEN_ID);
    private final String MESSAGE_TRYCATCH = " ERROR MODELO: " + TABLE.toUpperCase() + " ";
    private SQLConnection connection;
    private PreparedStatement ps;
    private ResultSet set;

    private String[] arrayData(ResultSet set) throws SQLException {
        return new String[]{
            String.valueOf(set.getInt("id_producto")),
            String.valueOf(set.getInt("id_almacen")),
            String.valueOf(set.getDouble("stock")),
            String.valueOf(set.getDate("fecha"))
        };
    }

    void preparerState() throws SQLException {
        try {
            // Intentar establecer los valores
            ps.setDouble(1, getStock());
            ps.setDate(2, getFecha());
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
            ps.setInt(3, getId());
            ps.setInt(4, getAlmacen_id());
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
                mensaje = MESSAGE_TRYCATCH + " (GUARDAR) Error al cerrar PreparedStatement: " + TABLE + e.getMessage();
            }
        }
        return new Object[]{isSuccess, mensaje};
    }

    public boolean checkId() throws SQLException, ParseException {
        boolean isSuccess = false;
        try {
            init_conexion();
            // Verificar si el id y almacen_id existen en la tabla
            ps = connection.connect().prepareStatement(QUERY_CHECK);
            ps.setInt(1, getId());
            ps.setInt(2, getAlmacen_id());
            set = ps.executeQuery();
            if (set.next() && set.getInt(1) == 0) {
                //mensaje = "Error: El id o almacen_id no existen en la tabla.";
                System.out.println("Error: El producto_id o almacen_id no existen en la tabla.");
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
                return new Object[]{isCheckedId, "IDS INGRESADOS NO SE ENCUENTRAN REGISTRADADAS EN LA TABLA: "+TABLE.toUpperCase()};
            }
            init_conexion();
            ps = connection.connect().prepareStatement(QUERY_UPDATE);
            preparerState();
            ps.setInt(3, getId());
            ps.setInt(4, getAlmacen_id());
            int execute = ps.executeUpdate();
            isSuccess = execute > 0;
            if (isSuccess) {
                mensaje = TABLE+ " - (MODIFICAR) actualizacion exitosamente.".toUpperCase();
            } else {
                mensaje = MESSAGE_TRYCATCH + " - (MODIFICAR) Error al actualizar los datos.".toUpperCase();
                //throw new SQLException("No se pudo Error al actualizar los datos.".toUpperCase());
            }
        } catch (SQLException e) {
            System.out.println(MESSAGE_TRYCATCH + " MODIFICAR " + e.getMessage());
            mensaje = MESSAGE_TRYCATCH + " MODIFICAR " + e.getMessage().toUpperCase();
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
