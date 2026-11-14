<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="modelo.EspacioBO"%>
<%@page import="modelo.EntradaBO"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Registrar entrada - Easy Park</title>
</head>
<%
    // Listas que envía el EntradaController
    List<EspacioBO> espacios = (List<EspacioBO>) request.getAttribute("espacios");
    List<EntradaBO> entradas = (List<EntradaBO>) request.getAttribute("entradas");

    // Si se abre la página directamente (sin pasar por el servlet), se redirige al servlet
    if (espacios == null || entradas == null) {
        response.sendRedirect(ctx + "/EntradaController");
        return;
    }
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
%>
<body>

    <%@include file="/WEB-INF/includes/nav.jspf"%>

    <main class="container contenido">

        <div class="titulo-seccion">
            <i class="material-icons">arrow_upward</i>
            <div>
                <h5 class="font-bold">Registrar entrada de vehículos</h5>
                <p>Registre el ingreso de un vehículo y asígnele un espacio disponible.</p>
            </div>
        </div>

        <%@include file="/WEB-INF/includes/mensaje.jspf"%>

        <%
            // Si la placa no está registrada, se ofrece ir al registro con la placa ya escrita
            String placaNoRegistrada = (String) request.getAttribute("placaNoRegistrada");
            if (placaNoRegistrada != null) {
        %>
        <a class="btn waves-effect btn-verde boton-registrar-placa"
           href="<%= ctx %>/vehiculos/registrar.jsp?placa=<%= java.net.URLEncoder.encode(placaNoRegistrada, "UTF-8") %>">
            <i class="material-icons left">add</i>Registrar este vehículo
        </a>
        <% } %>

        <div class="row grid-modulos">

            <!-- Formulario de registro -->
            <div class="col s12 l5">
                <div class="card ancho-completo">
                    <div class="card-content">
                        <span class="card-title font-bold verde">Nueva entrada</span>

                        <% if (espacios.isEmpty()) { %>
                            <p class="sin-datos">No hay espacios disponibles en este momento.</p>
                        <% } else { %>
                        <form name="frmEntrada" id="frmEntrada" action="<%= ctx %>/EntradaController" method="post">
                            <div class="input-field">
                                <i class="material-icons prefix">pin</i>
                                <input id="txtPlaca" name="txtPlaca" type="text" maxlength="10" class="mayusculas" required>
                                <label for="txtPlaca">Placa del vehículo</label>
                            </div>

                            <label for="selEspacio">Espacio disponible</label>
                            <select id="selEspacio" name="selEspacio" class="browser-default" required>
                                <option value="" disabled selected>Seleccione un espacio</option>
                                <% for (EspacioBO e : espacios) { %>
                                    <option value="<%= e.getIdEspacio() %>">
                                        <%= Seguridad.escaparHtml(e.getNumeroEspacio() + " - " + e.getTipo()) %>
                                    </option>
                                <% } %>
                            </select>

                            <p class="nota">La fecha y hora de entrada se registran automáticamente.
                                Registrado por: <strong><%= Seguridad.escaparHtml(usuarioSesion.getNombre() + " " + usuarioSesion.getApellido()) %></strong></p>

                            <div class="botones">
                                <button type="submit" name="btnRegistrar" class="btn-large waves-effect waves-light btn-verde ancho-completo">
                                    <i class="material-icons left">save</i>Registrar entrada
                                </button>
                            </div>
                        </form>
                        <% } %>

                        <p class="nota">¿El vehículo no está registrado?
                            <a href="<%= ctx %>/vehiculos/registrar.jsp" class="verde font-bold">Regístrelo aquí</a></p>
                    </div>
                </div>
            </div>

            <!-- Vehículos que están dentro del parqueadero -->
            <div class="col s12 l7">
                <div class="card ancho-completo">
                    <div class="card-content">
                        <span class="card-title font-bold verde">Vehículos dentro del parqueadero (<%= entradas.size() %>)</span>

                        <% if (entradas.isEmpty()) { %>
                            <p class="sin-datos">No hay vehículos dentro del parqueadero.</p>
                        <% } else { %>
                        <table class="striped highlight tabla-easypark">
                            <thead>
                                <tr>
                                    <th>Placa</th>
                                    <th>Espacio</th>
                                    <th>Fecha y hora de entrada</th>
                                    <th>Registró</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (EntradaBO en : entradas) { %>
                                <tr>
                                    <td class="font-bold"><%= Seguridad.escaparHtml(en.getPlaca()) %></td>
                                    <td><span class="etiqueta"><%= Seguridad.escaparHtml(en.getNumeroEspacio()) %></span></td>
                                    <td><%= en.getFechaHoraEntrada().format(formato) %></td>
                                    <td><%= Seguridad.escaparHtml(en.getNombreUsuario()) %></td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                        <% } %>
                    </div>
                </div>
            </div>

        </div>
    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
