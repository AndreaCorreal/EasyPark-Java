package recursos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de crear la conexión con la base de datos easy_park en MySQL.
 * Se reutiliza en todas las clases DAO del proyecto.
 */
public class Conexion {

    // Datos de conexión a la base de datos
    private static final String IP = "localhost";
    private static final String PUERTO = "3306";
    private static final String BD = "easy_park";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";

    // Cadena de conexión JDBC
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + BD
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Bogota";

    // Driver JDBC de MySQL (Connector/J)
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Registra el driver y devuelve una conexión abierta con la base de datos.
     * @return objeto Connection listo para usar
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al registrar el driver JDBC: " + e.getMessage());
            throw new SQLException("No se encontró el driver de MySQL. Verifique que el conector esté en Libraries.");
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
