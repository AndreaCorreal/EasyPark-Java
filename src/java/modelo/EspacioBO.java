package modelo;

/**
 * Objeto de valor (BO) que representa la tabla espacio.
 */
public class EspacioBO {

    public static final String ESTADO_DISPONIBLE = "Disponible";
    public static final String ESTADO_OCUPADO = "Ocupado";

    private int idEspacio;
    private String numeroEspacio;
    private String estado;
    private String tipo;

    // Constructor vacío
    public EspacioBO() {
        this.idEspacio = 0;
        this.numeroEspacio = "";
        this.estado = ESTADO_DISPONIBLE;
        this.tipo = "";
    }

    // Constructor con parámetros
    public EspacioBO(int idEspacio, String numeroEspacio, String estado, String tipo) {
        this.idEspacio = idEspacio;
        this.numeroEspacio = numeroEspacio;
        this.estado = estado;
        this.tipo = tipo;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getNumeroEspacio() {
        return numeroEspacio;
    }

    public void setNumeroEspacio(String numeroEspacio) {
        this.numeroEspacio = numeroEspacio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
