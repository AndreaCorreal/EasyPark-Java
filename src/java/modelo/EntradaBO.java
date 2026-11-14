package modelo;

import java.time.LocalDateTime;

/**
 * Objeto de valor (BO) que representa la tabla entrada.
 * Además de las columnas de la tabla, guarda datos de apoyo para mostrar
 * en pantalla (número de espacio y nombre del usuario que registró).
 */
public class EntradaBO {

    private int idEntrada;
    private LocalDateTime fechaHoraEntrada;
    private String placa;
    private int idEspacio;
    private int idUsuario;

    // Datos de apoyo para las consultas (no son columnas de la tabla entrada)
    private String numeroEspacio;
    private String nombreUsuario;

    // Constructor vacío
    public EntradaBO() {
        this.idEntrada = 0;
        this.placa = "";
        this.numeroEspacio = "";
        this.nombreUsuario = "";
    }

    // Constructor con los datos necesarios para registrar una entrada
    public EntradaBO(String placa, int idEspacio, int idUsuario) {
        this.placa = placa;
        this.idEspacio = idEspacio;
        this.idUsuario = idUsuario;
        this.fechaHoraEntrada = LocalDateTime.now();
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumeroEspacio() {
        return numeroEspacio;
    }

    public void setNumeroEspacio(String numeroEspacio) {
        this.numeroEspacio = numeroEspacio;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
