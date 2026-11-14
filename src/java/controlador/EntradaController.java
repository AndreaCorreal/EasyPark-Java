package controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import modelo.EntradaBO;
import modelo.EntradaDAO;
import modelo.EspacioDAO;
import modelo.UsuarioBO;
import modelo.VehiculoBO;
import modelo.VehiculoDAO;
import recursos.Seguridad;

/**
 * Servlet del proceso principal de Easy Park: el registro de entradas.
 * - GET: carga los espacios disponibles y los vehículos que están dentro.
 * - POST: registra la entrada de un vehículo en un espacio disponible.
 */
@WebServlet(name = "EntradaController", urlPatterns = {"/EntradaController"})
public class EntradaController extends HttpServlet {

    // Muestra el formulario con la información actualizada
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrarPagina(request, response);
    }

    // Registra la entrada
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String placa = request.getParameter("txtPlaca");
        String idEspacioTexto = request.getParameter("selEspacio");
        String mensaje;
        boolean exito = false;

        // El usuario que registra la entrada es el que inició sesión
        UsuarioBO usuario = (UsuarioBO) request.getSession().getAttribute("usuarioLogueado");

        if (!Seguridad.esCampoValido(placa) || !Seguridad.esCampoValido(idEspacioTexto)) {
            mensaje = "Debe escribir la placa y seleccionar un espacio.";
        } else {
            placa = placa.trim().toUpperCase();
            VehiculoBO vehiculo = new VehiculoDAO().findByPrimaryKey(placa);
            EntradaDAO entradaDAO = new EntradaDAO();

            if (vehiculo.getPlaca().equals("")) {
                mensaje = "El vehículo " + placa + " no está registrado. Regístrelo primero en el módulo Vehículos.";
                // Se envía la placa para mostrar el botón que lleva al registro del vehículo
                request.setAttribute("placaNoRegistrada", placa);
            } else if (entradaDAO.vehiculoEstaDentro(placa)) {
                mensaje = "El vehículo " + placa + " ya se encuentra dentro del parqueadero.";
            } else {
                // Se convierte el id del espacio de texto a número
                int idEspacio = Integer.parseInt(idEspacioTexto);
                EntradaBO entrada = new EntradaBO(placa, idEspacio, usuario.getIdUsuario());
                mensaje = entradaDAO.registrarEntrada(entrada);
                exito = mensaje.contains("satisfactoriamente");
            }
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("exito", exito);
        mostrarPagina(request, response);
    }

    // Carga las listas que necesita la página y la muestra
    private void mostrarPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("espacios", new EspacioDAO().listarDisponibles());
        request.setAttribute("entradas", new EntradaDAO().listarEntradasActivas());
        RequestDispatcher rd = request.getRequestDispatcher("entradas/registrar.jsp");
        rd.forward(request, response);
    }
}
