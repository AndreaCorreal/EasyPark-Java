package controlador;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtro que se ejecuta antes de cada petición.
 * 1. Configura la codificación UTF-8 para que las tildes y la ñ se guarden bien.
 * 2. Protege las páginas internas: si no hay un usuario con sesión iniciada,
 *    lo envía a la página de inicio de sesión.
 */
@WebFilter("/*")
public class FiltroAcceso implements Filter {

    // Páginas y recursos que se pueden ver sin iniciar sesión
    private static final String[] RUTAS_PUBLICAS = {
        "/index.jsp", "/AccesoController", "/UsuarioController",
        "/usuarios/registro.jsp", "/usuarios/recuperar.jsp",
        "/style/", "/img/", "/materialize/"
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding("UTF-8");

        String ruta = request.getRequestURI().substring(request.getContextPath().length());
        HttpSession sesion = request.getSession(false);
        boolean haySesion = sesion != null && sesion.getAttribute("usuarioLogueado") != null;

        if (haySesion || esRutaPublica(ruta)) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    private boolean esRutaPublica(String ruta) {
        if (ruta.equals("/") || ruta.isEmpty()) {
            return true;
        }
        for (String publica : RUTAS_PUBLICAS) {
            if (ruta.startsWith(publica)) {
                return true;
            }
        }
        return false;
    }
}
