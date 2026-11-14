package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import recursos.Conexion;

/**
 * Objeto de acceso a datos (DAO) de la tabla entrada.
 * Registra el ingreso de los vehículos al parqueadero.
 */
public class EntradaDAO {

    /**
     * Verifica si un vehículo ya está dentro del parqueadero,
     * es decir, si tiene una entrada que todavía no tiene salida.
     * @param placa placa del vehículo
     * @return true si el vehículo ya está dentro
     */
    public boolean vehiculoEstaDentro(String placa) {
        boolean dentro = false;
        String sql = "SELECT e.id_entrada FROM entrada e "
                + "LEFT JOIN salida s ON s.id_entrada = e.id_entrada "
                + "WHERE e.placa = ? AND s.id_salida IS NULL";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dentro = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la entrada: " + e.getMessage());
        }
        return dentro;
    }

    /**
     * Registra la entrada de un vehículo y marca el espacio como Ocupado.
     * Las dos sentencias se ejecutan como una transacción: si una falla,
     * se deshacen las dos para no dejar datos inconsistentes.
     * @param entrada objeto con la placa, el espacio y el usuario que registra
     * @return mensaje con el resultado de la operación
     */
    public String registrarEntrada(EntradaBO entrada) {
        String mensaje;
        String sqlEntrada = "INSERT INTO entrada (fecha_hora_entrada, placa, id_espacio, id_usuario) "
                + "VALUES (?, ?, ?, ?)";
        // La condición estado = 'Disponible' evita ocupar un espacio que ya está ocupado
        String sqlEspacio = "UPDATE espacio SET estado = ? WHERE id_espacio = ? AND estado = ?";

        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Inicia la transacción

            try (PreparedStatement psEspacio = con.prepareStatement(sqlEspacio);
                    PreparedStatement psEntrada = con.prepareStatement(sqlEntrada)) {

                // 1. Ocupar el espacio
                psEspacio.setString(1, EspacioBO.ESTADO_OCUPADO);
                psEspacio.setInt(2, entrada.getIdEspacio());
                psEspacio.setString(3, EspacioBO.ESTADO_DISPONIBLE);
                int filas = psEspacio.executeUpdate();

                if (filas == 0) {
                    con.rollback();
                    return "El espacio seleccionado ya no está disponible. Seleccione otro.";
                }

                // 2. Registrar la entrada
                psEntrada.setTimestamp(1, Timestamp.valueOf(entrada.getFechaHoraEntrada()));
                psEntrada.setString(2, entrada.getPlaca());
                psEntrada.setInt(3, entrada.getIdEspacio());
                psEntrada.setInt(4, entrada.getIdUsuario());
                psEntrada.executeUpdate();
            }

            con.commit(); // Confirma las dos sentencias
            mensaje = "Entrada del vehículo " + entrada.getPlaca() + " registrada satisfactoriamente.";

        } catch (SQLException e) {
            System.out.println("Error al registrar la entrada: " + e.getMessage());
            mensaje = "No se pudo registrar la entrada.";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al deshacer la transacción: " + ex.getMessage());
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
        return mensaje;
    }

    /**
     * Consulta los vehículos que están actualmente dentro del parqueadero
     * (entradas que no tienen salida registrada).
     * @return lista de entradas activas, de la más reciente a la más antigua
     */
    public List<EntradaBO> listarEntradasActivas() {
        List<EntradaBO> lista = new ArrayList<>();
        String sql = "SELECT e.*, es.numero_espacio, CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario "
                + "FROM entrada e "
                + "INNER JOIN espacio es ON es.id_espacio = e.id_espacio "
                + "INNER JOIN usuario u ON u.id_usuario = e.id_usuario "
                + "LEFT JOIN salida s ON s.id_entrada = e.id_entrada "
                + "WHERE s.id_salida IS NULL "
                + "ORDER BY e.fecha_hora_entrada DESC";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EntradaBO entrada = new EntradaBO();
                entrada.setIdEntrada(rs.getInt("id_entrada"));
                entrada.setFechaHoraEntrada(rs.getTimestamp("fecha_hora_entrada").toLocalDateTime());
                entrada.setPlaca(rs.getString("placa"));
                entrada.setIdEspacio(rs.getInt("id_espacio"));
                entrada.setIdUsuario(rs.getInt("id_usuario"));
                entrada.setNumeroEspacio(rs.getString("numero_espacio"));
                entrada.setNombreUsuario(rs.getString("nombre_usuario"));
                lista.add(entrada);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las entradas: " + e.getMessage());
        }
        return lista;
    }
}
