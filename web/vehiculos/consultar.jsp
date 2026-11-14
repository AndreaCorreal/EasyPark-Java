<%@page import="java.util.List"%>
<%@page import="modelo.VehiculoBO"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Consultar vehículos - Easy Park</title>
</head>
<%
    // Lista que envía el VehiculoController con los resultados de la consulta
    List<VehiculoBO> resultados = (List<VehiculoBO>) request.getAttribute("resultados");
    String placaBuscada = request.getParameter("txtPlaca");
%>
<body>

    <%@include file="/WEB-INF/includes/nav.jspf"%>

    <main class="container contenido">

        <div class="titulo-seccion">
            <i class="material-icons">search</i>
            <div>
                <h5 class="font-bold">Consultar vehículos</h5>
                <p>Busque un vehículo por su placa o consulte todos los vehículos registrados.</p>
            </div>
        </div>

        <div class="card">
            <div class="card-content">
                <!-- La consulta se envía por GET porque no tiene información sensible -->
                <form name="frmConsulta" id="frmConsulta" action="<%= ctx %>/VehiculoController" method="get" class="form-busqueda">
                    <input type="hidden" name="accion" value="consultar">
                    <div class="input-field">
                        <i class="material-icons prefix">pin</i>
                        <input id="txtPlaca" name="txtPlaca" type="text" maxlength="10" class="mayusculas"
                               value="<%= Seguridad.escaparHtml(placaBuscada) %>" required>
                        <label for="txtPlaca" class="<%= placaBuscada != null ? "active" : "" %>">Placa del vehículo</label>
                    </div>
                    <div class="botones">
                        <button type="submit" class="btn waves-effect waves-light btn-verde">
                            <i class="material-icons left">search</i>Buscar
                        </button>
                        <a href="<%= ctx %>/VehiculoController?accion=listar" class="btn waves-effect btn-flat btn-borde">
                            <i class="material-icons left">list</i>Ver todos
                        </a>
                        <a href="<%= ctx %>/vehiculos/registrar.jsp" class="btn waves-effect btn-flat btn-borde">
                            <i class="material-icons left">add</i>Nuevo vehículo
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <%@include file="/WEB-INF/includes/mensaje.jspf"%>

        <% if (resultados != null && !resultados.isEmpty()) { %>
        <div class="card">
            <div class="card-content">
                <p class="total-resultados"><%= resultados.size() %> vehículo(s) encontrado(s)</p>
                <div class="tabla-scroll">
                <table class="striped highlight tabla-easypark">
                    <thead>
                        <tr>
                            <th>Placa</th>
                            <th>Tipo</th>
                            <th>Propietario</th>
                            <th>Documento</th>
                            <th>Marca</th>
                            <th>Color</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (VehiculoBO v : resultados) { %>
                        <tr>
                            <td class="font-bold"><%= Seguridad.escaparHtml(v.getPlaca()) %></td>
                            <td><%= Seguridad.escaparHtml(v.getTipoVehiculo()) %></td>
                            <td><%= Seguridad.escaparHtml(v.getPropietario()) %></td>
                            <td><%= Seguridad.escaparHtml(v.getDocumentoPropietario()) %></td>
                            <td><%= Seguridad.escaparHtml(v.getMarca()) %></td>
                            <td><%= Seguridad.escaparHtml(v.getColor()) %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
        <% } %>

    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
