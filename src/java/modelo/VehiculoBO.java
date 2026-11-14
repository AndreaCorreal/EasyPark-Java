package modelo;

/**
 * Objeto de valor (BO) que representa la tabla vehiculo.
 */
public class VehiculoBO {

    private String placa;
    private String tipoVehiculo;
    private String propietario;
    private String documentoPropietario;
    private String marca;
    private String color;

    // Constructor vacío
    public VehiculoBO() {
        this.placa = "";
        this.tipoVehiculo = "";
        this.propietario = "";
        this.documentoPropietario = "";
        this.marca = "";
        this.color = "";
    }

    // Constructor con parámetros
    public VehiculoBO(String placa, String tipoVehiculo, String propietario,
            String documentoPropietario, String marca, String color) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.propietario = propietario;
        this.documentoPropietario = documentoPropietario;
        this.marca = marca;
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getDocumentoPropietario() {
        return documentoPropietario;
    }

    public void setDocumentoPropietario(String documentoPropietario) {
        this.documentoPropietario = documentoPropietario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
