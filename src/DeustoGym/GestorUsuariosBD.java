package DeustoGym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestorUsuariosBD {
    private Connection conexion;

    public GestorUsuariosBD() {
        conectar();
        crearTablaUsuarios();
    }

    private void conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:gimnasio.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void crearTablaUsuarios() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT,"
                + "apellido TEXT,"
                + "edad INTEGER,"
                + "contrasenia TEXT,"
                + "usuario_id TEXT);";

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, edad, contrasenia, usuario_id) VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setInt(3, usuario.getEdad());
            statement.setString(4, usuario.getContraseña());
            statement.setString(5, usuario.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}