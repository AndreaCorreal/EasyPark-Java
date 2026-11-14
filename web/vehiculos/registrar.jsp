<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Registrar vehículo - Easy Park</title>
</head>
<body>

    <%@include file="/WEB-INF/includes/nav.jspf"%>

    <main class="container contenido">
        <div class="card formulario">
            <div class="card-content">

                <div class="titulo-seccion">
                    <i class="material-icons">directions_car</i>
                    <div>
                        <h5 class="font-bold">Registrar vehículo</h5>
                        <p>Ingrese los datos del vehículo y de su propietario. Los campos con * son obligatorios.</p>
                    </div>
                </div>

                <%@include file="/WEB-INF/includes/mensaje.jspf"%>

                <form name="frmVehiculo" id="frmVehiculo" action="<%= ctx %>/VehiculoController" method="post">
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">pin</i>
                            <input id="txtPlaca" name="txtPlaca" type="text" maxlength="10" required class="mayusculas"
                                   value="<%= request.getParameter("placa") != null ? Seguridad.escaparHtml(request.getParameter("placa")) : "" %>">
                            <label for="txtPlaca">Placa *</label>
                        </div>
                        <div class="col s12 m6 campo-rol">
                            <label for="selTipo">Tipo de vehículo *</label>
                            <select id="selTipo" name="selTipo" class="browser-default" required>
                                <option value="" disabled selected>Seleccione el tipo</option>
                                <option value="Automovil">Automóvil</option>
                                <option value="Motocicleta">Motocicleta</option>
                                <option value="Camioneta">Camioneta</option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">person</i>
                            <input id="txtPropietario" name="txtPropietario" type="text" maxlength="100" required>
                            <label for="txtPropietario">Nombre del propietario *</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">fingerprint</i>
                            <input id="txtDocumento" name="txtDocumento" type="text" maxlength="20" required>
                            <label for="txtDocumento">Documento del propietario *</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">local_offer</i>
                            <input id="txtMarca" name="txtMarca" type="text" maxlength="50">
                            <label for="txtMarca">Marca</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">palette</i>
                            <input id="txtColor" name="txtColor" type="text" maxlength="30">
                            <label for="txtColor">Color</label>
                        </div>
                    </div>

                    <div class="botones right-align">
                        <a href="<%= ctx %>/VehiculoController?accion=listar" class="btn-large waves-effect btn-flat btn-borde">
                            <i class="material-icons left">search</i>Consultar vehículos
                        </a>
                        <button type="submit" name="btnRegistrar" class="btn-large waves-effect waves-light btn-verde">
                            <i class="material-icons left">save</i>Registrar vehículo
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
