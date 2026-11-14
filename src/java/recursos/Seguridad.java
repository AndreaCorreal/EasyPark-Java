package recursos;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Métodos de apoyo para la seguridad del sistema.
 * Las contraseñas no se guardan en texto plano, sino encriptadas con SHA-256.
 */
public class Seguridad {

    /**
     * Encripta un texto con el algoritmo SHA-256.
     * @param texto contraseña escrita por el usuario
     * @return contraseña encriptada en formato hexadecimal (64 caracteres)
     */
    public static String encriptar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder();
            for (byte b : hash) {
                resultado.append(String.format("%02x", b));
            }
            return resultado.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    /**
     * Verifica que un campo obligatorio tenga información.
     * @param valor texto a validar
     * @return true si el campo tiene contenido
     */
    public static boolean esCampoValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    /**
     * Convierte los caracteres especiales de HTML antes de mostrar un texto en la página.
     * Evita que alguien escriba código HTML o JavaScript en un formulario y se ejecute.
     * @param texto texto a mostrar
     * @return texto seguro para mostrar en HTML
     */
    public static String escaparHtml(Object texto) {
        if (texto == null) {
            return "";
        }
        return texto.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
