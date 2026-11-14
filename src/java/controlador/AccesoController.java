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
 * Servlet que controla el inicio y el cierre de sesión.
 * - POST: valida el correo y la contraseña contra la base de datos.
 * - GET con accion=salir: cierra la sesión.
 */
@WebServlet(name = "AccesoController", urlPatterns = {"/AccesoController"})
public class AccesoController extends HttpServlet {

    // Inicio de sesión (se envía por POST para que los datos no se vean en la URL)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");

        // Validación de campos obligatorios
        if (!Seguridad.esCampoValido(correo) || !Seguridad.esCampoValido(password)) {
            request.setAttribute("mensaje", "Debe ingresar el correo y la contraseña.");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioBO usuario = usuarioDAO.validarAcceso(correo.trim(), password);

        if (usuario != null) {
            // Datos correctos: se guarda el usuario en la sesión y se abre la página principal
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarioLogueado", usuario);
            response.sendRedirect(request.getContextPath() + "/principal.jsp");
        } else {
            // Datos incorrectos: regresa al inicio de sesión con un mensaje
            request.setAttribute("mensaje", "Correo o contraseña incorrectos.");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }

    // Cierre de sesión
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("salir".equals(accion)) {
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
