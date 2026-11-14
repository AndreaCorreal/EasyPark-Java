package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import recursos.Conexion;
import recursos.Seguridad;

/**
 * Objeto de acceso a datos (DAO) de la tabla usuario.
 * Contiene las sentencias SQL para validar el acceso, registrar usuarios
 * y recuperar la contraseña.
 */
public class UsuarioDAO {

    /**
     * Valida el acceso al sistema con el correo y la contraseña.
     * @param correo correo digitado en el formulario
     * @param password contraseña digitada (sin encriptar)
     * @return el usuario encontrado, o null si los datos no coinciden
     */
    public UsuarioBO validarAcceso(String correo, String password) {
        UsuarioBO usuario = null;
        String sql = "SELECT * FROM usuario WHERE correo = ? AND password_hash = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, Seguridad.encriptar(password));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = cargarUsuario(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el acceso: " + e.getMessage());
        }
        return usuario;
    }

    /**
     * Busca si ya existe un usuario con la cédula o el correo indicados.
     * Se usa para no registrar datos duplicados.
     * @return true si ya existe un usuario con esos datos
     */
    public boolean existeUsuario(String cedula, String correo) {
        boolean existe = false;
        String sql = "SELECT id_usuario FROM usuario WHERE cedula = ? OR correo = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setString(2, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        }
        return existe;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param usuario objeto con los datos del formulario (contraseña ya encriptada)
     * @return mensaje con el resultado de la operación
     */
    public String registrarUsuario(UsuarioBO usuario) {
        String mensaje;
        String sql = "INSERT INTO usuario (nombre, apellido, cedula, celular, correo, password_hash, rol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCedula());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, usuario.getPasswordHash());
            ps.setString(7, usuario.getRol());
            ps.executeUpdate();

            mensaje = "Usuario " + usuario.getNombre() + " " + usuario.getApellido()
                    + " registrado satisfactoriamente.";
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            mensaje = "No se pudo registrar el usuario.";
        }
        return mensaje;
    }

    /**
     * Cambia la contraseña de un usuario, verificando primero su correo y su cédula.
     * @return true si los datos coinciden y la contraseña se actualizó
     */
    public boolean recuperarPassword(String correo, String cedula, String nuevaPassword) {
        boolean actualizado = false;
        String sql = "UPDATE usuario SET password_hash = ? WHERE correo = ? AND cedula = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Seguridad.encriptar(nuevaPassword));
            ps.setString(2, correo);
            ps.setString(3, cedula);

            // executeUpdate devuelve el número de filas modificadas
            actualizado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al recuperar la contraseña: " + e.getMessage());
        }
        return actualizado;
    }

    // Pasa los datos de una fila del ResultSet a un objeto UsuarioBO
    private UsuarioBO cargarUsuario(ResultSet rs) throws SQLException {
        UsuarioBO usuario = new UsuarioBO();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setCedula(rs.getString("cedula"));
        usuario.setCelular(rs.getString("celular"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setRol(rs.getString("rol"));
        return usuario;
    }
}
