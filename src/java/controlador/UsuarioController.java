package controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.UsuarioBO;
import modelo.UsuarioDAO;
import recursos.Seguridad;

/**
 * Servlet que gestiona los usuarios del sistema.
 * Según el parámetro "accion" realiza:
 * - registrar: registra un nuevo usuario validando que no exista.
 * - recuperar: cambia la contraseña verificando el correo y la cédula.
 */
@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            registrar(request, response);
        } else if ("recuperar".equals(accion)) {
            recuperar(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtApellido");
        String cedula = request.getParameter("txtCedula");
        String celular = request.getParameter("txtCelular");
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");
        String confirmar = request.getParameter("txtConfirmar");
        String rol = request.getParameter("selRol");

        // Si quien registra no es un Administrador con sesión iniciada,
        // el nuevo usuario siempre queda con el rol Operador
        HttpSession sesion = request.getSession(false);
        UsuarioBO logueado = sesion != null ? (UsuarioBO) sesion.getAttribute("usuarioLogueado") : null;
        if (logueado == null || !"Administrador".equals(logueado.getRol())) {
            rol = "Operador";
        }

        String mensaje;
        boolean exito = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (!Seguridad.esCampoValido(nombre) || !Seguridad.esCampoValido(apellido)
                || !Seguridad.esCampoValido(cedula) || !Seguridad.esCampoValido(correo)
                || !Seguridad.esCampoValido(password)) {
            mensaje = "Todos los campos marcados con * son obligatorios.";
        } else if (!password.equals(confirmar)) {
            mensaje = "Las contraseñas no coinciden.";
        } else if (password.length() < 6) {
            mensaje = "La contraseña debe tener mínimo 6 caracteres.";
        } else if (usuarioDAO.existeUsuario(cedula.trim(), correo.trim())) {
            // Validación de datos duplicados
            mensaje = "Ya existe un usuario registrado con esa cédula o ese correo.";
        } else {
            UsuarioBO usuario = new UsuarioBO(nombre.trim(), apellido.trim(), cedula.trim(),
                    celular != null ? celular.trim() : "", correo.trim(),
                    Seguridad.encriptar(password), rol);
            mensaje = usuarioDAO.registrarUsuario(usuario);
            exito = mensaje.contains("satisfactoriamente");
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("exito", exito);
        RequestDispatcher rd = request.getRequestDispatcher("usuarios/registro.jsp");
        rd.forward(request, response);
    }

    private void recuperar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("txtCorreo");
        String cedula = request.getParameter("txtCedula");
        String password = request.getParameter("txtPassword");
        String confirmar = request.getParameter("txtConfirmar");

        String mensaje;
        boolean exito = false;

        if (!Seguridad.esCampoValido(correo) || !Seguridad.esCampoValido(cedula)
                || !Seguridad.esCampoValido(password)) {
            mensaje = "Todos los campos son obligatorios.";
        } else if (!password.equals(confirmar)) {
            mensaje = "Las contraseñas no coinciden.";
        } else if (password.length() < 6) {
            mensaje = "La contraseña debe tener mínimo 6 caracteres.";
        } else {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            exito = usuarioDAO.recuperarPassword(correo.trim(), cedula.trim(), password);
            mensaje = exito
                    ? "Contraseña actualizada. Ya puede iniciar sesión."
                    : "El correo y la cédula no coinciden con ningún usuario.";
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("exito", exito);
        RequestDispatcher rd = request.getRequestDispatcher("usuarios/recuperar.jsp");
        rd.forward(request, response);
    }
}
