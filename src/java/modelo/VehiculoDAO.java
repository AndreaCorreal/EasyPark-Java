package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos.Conexion;

/**
 * Objeto de acceso a datos (DAO) de la tabla vehiculo.
 * Permite registrar vehículos y consultarlos de forma individual o total.
 */
public class VehiculoDAO {

    /**
     * Busca un vehículo por su llave primaria (la placa).
     * @param placa placa a buscar
     * @return el vehículo encontrado, o un objeto vacío si no existe
     */
    public VehiculoBO findByPrimaryKey(String placa) {
        VehiculoBO vehiculo = new VehiculoBO();
        String sql = "SELECT * FROM vehiculo WHERE placa = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = cargarVehiculo(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el vehículo: " + e.getMessage());
        }
        return vehiculo;
    }

    /**
     * Registra un nuevo vehículo.
     * @param vehiculo objeto con los datos del formulario
     * @return mensaje con el resultado de la operación
     */
    public String registrarVehiculo(VehiculoBO vehiculo) {
        String mensaje;
        String sql = "INSERT INTO vehiculo (placa, tipo_vehiculo, propietario, documento_propietario, marca, color) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getTipoVehiculo());
            ps.setString(3, vehiculo.getPropietario());
            ps.setString(4, vehiculo.getDocumentoPropietario());
            ps.setString(5, vehiculo.getMarca());
            ps.setString(6, vehiculo.getColor());
            ps.executeUpdate();

            mensaje = "Vehículo con placa " + vehiculo.getPlaca() + " registrado satisfactoriamente.";
        } catch (SQLException e) {
            System.out.println("Error al registrar el vehículo: " + e.getMessage());
            mensaje = "No se pudo registrar el vehículo.";
        }
        return mensaje;
    }

    /**
     * Consulta todos los vehículos registrados.
     * @return lista de vehículos ordenada por placa
     */
    public List<VehiculoBO> listarVehiculos() {
        List<VehiculoBO> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo ORDER BY placa";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // Como pueden ser varios registros, se recorre el ResultSet con while
            while (rs.next()) {
                lista.add(cargarVehiculo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los vehículos: " + e.getMessage());
        }
        return lista;
    }

    // Pasa los datos de una fila del ResultSet a un objeto VehiculoBO
    private VehiculoBO cargarVehiculo(ResultSet rs) throws SQLException {
        VehiculoBO vehiculo = new VehiculoBO();
        vehiculo.setPlaca(rs.getString("placa"));
        vehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
        vehiculo.setPropietario(rs.getString("propietario"));
        vehiculo.setDocumentoPropietario(rs.getString("documento_propietario"));
        vehiculo.setMarca(rs.getString("marca"));
        vehiculo.setColor(rs.getString("color"));
        return vehiculo;
    }
}
