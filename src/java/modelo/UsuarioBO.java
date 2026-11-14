package modelo;

/**
 * Objeto de valor (BO) que representa la tabla usuario.
 * Cada atributo corresponde a una columna de la tabla.
 */
public class UsuarioBO {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String cedula;
    private String celular;
    private String correo;
    private String passwordHash;
    private String rol;

    // Constructor vacío
    public UsuarioBO() {
        this.idUsuario = 0;
        this.nombre = "";
        this.apellido = "";
        this.cedula = "";
        this.celular = "";
        this.correo = "";
        this.passwordHash = "";
        this.rol = "";
    }

    // Constructor con parámetros (sin id, porque es autoincrementable)
    public UsuarioBO(String nombre, String apellido, String cedula, String celular,
            String correo, String passwordHash, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.celular = celular;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
