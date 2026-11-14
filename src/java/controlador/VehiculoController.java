package controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.VehiculoBO;
import modelo.VehiculoDAO;
import recursos.Seguridad;

/**
 * Servlet que gestiona los vehículos.
 * - POST (accion=registrar): registra un vehículo validando que la placa no exista.
 * - GET (accion=consultar o accion=listar): consulta un vehículo por placa o todos.
 *   La consulta se hace por GET porque no envía información sensible.
 */
@WebServlet(name = "VehiculoController", urlPatterns = {"/VehiculoController"})
public class VehiculoController extends HttpServlet {

    // Registro de vehículos
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String placa = request.getParameter("txtPlaca");
        String tipo = request.getParameter("selTipo");
        String propietario = request.getParameter("txtPropietario");
        String documento = request.getParameter("txtDocumento");
        String marca = request.getParameter("txtMarca");
        String color = request.getParameter("txtColor");

        String mensaje;
        boolean exito = false;

        if (!Seguridad.esCampoValido(placa) || !Seguridad.esCampoValido(tipo)
                || !Seguridad.esCampoValido(propietario) || !Seguridad.esCampoValido(documento)) {
            mensaje = "Los campos marcados con * son obligatorios.";
        } else {
            placa = placa.trim().toUpperCase();
            VehiculoDAO vehiculoDAO = new VehiculoDAO();

            // Validación de duplicados: se busca primero por la llave primaria (placa)
            VehiculoBO existente = vehiculoDAO.findByPrimaryKey(placa);

            if (existente.getPlaca().equals("")) {
                VehiculoBO vehiculo = new VehiculoBO(placa, tipo, propietario.trim(),
                        documento.trim(), marca != null ? marca.trim() : "",
                        color != null ? color.trim() : "");
                mensaje = vehiculoDAO.registrarVehiculo(vehiculo);
                exito = mensaje.contains("satisfactoriamente");
            } else {
                mensaje = "Ya existe un vehículo registrado con la placa " + placa + ".";
            }
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("exito", exito);
        RequestDispatcher rd = request.getRequestDispatcher("vehiculos/registrar.jsp");
        rd.forward(request, response);
    }

    // Consulta individual (por placa) o total de vehículos
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        VehiculoDAO vehiculoDAO = new VehiculoDAO();
        List<VehiculoBO> resultados = new ArrayList<>();
        String mensaje = null;

        if ("consultar".equals(accion)) {
            String placa = request.getParameter("txtPlaca");
            if (Seguridad.esCampoValido(placa)) {
                VehiculoBO vehiculo = vehiculoDAO.findByPrimaryKey(placa.trim().toUpperCase());
                if (vehiculo.getPlaca().equals("")) {
                    mensaje = "No se encontró ningún vehículo con la placa " + placa.trim().toUpperCase() + ".";
                } else {
                    resultados.add(vehiculo);
                }
            } else {
                mensaje = "Escriba una placa para buscar.";
            }
        } else {
            // accion=listar o sin acción: consulta total
            resultados = vehiculoDAO.listarVehiculos();
            if (resultados.isEmpty()) {
                mensaje = "No hay vehículos registrados.";
            }
        }

        request.setAttribute("resultados", resultados);
        request.setAttribute("mensaje", mensaje);
        RequestDispatcher rd = request.getRequestDispatcher("vehiculos/consultar.jsp");
        rd.forward(request, response);
    }
}
