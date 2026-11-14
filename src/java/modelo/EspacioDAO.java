package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos.Conexion;

/**
 * Objeto de acceso a datos (DAO) de la tabla espacio.
 */
public class EspacioDAO {

    /**
     * Consulta los espacios que están disponibles para recibir un vehículo.
     * @return lista de espacios con estado Disponible
     */
    public List<EspacioBO> listarDisponibles() {
        List<EspacioBO> lista = new ArrayList<>();
        String sql = "SELECT * FROM espacio WHERE estado = ? ORDER BY numero_espacio";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, EspacioBO.ESTADO_DISPONIBLE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EspacioBO espacio = new EspacioBO();
                    espacio.setIdEspacio(rs.getInt("id_espacio"));
                    espacio.setNumeroEspacio(rs.getString("numero_espacio"));
                    espacio.setEstado(rs.getString("estado"));
                    espacio.setTipo(rs.getString("tipo"));
                    lista.add(espacio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los espacios: " + e.getMessage());
        }
        return lista;
    }
}
