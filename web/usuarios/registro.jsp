<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Registro de usuarios - Easy Park</title>
</head>
<%
    // Solo un Administrador con sesión iniciada puede elegir el rol del nuevo usuario
    boolean esAdministrador = usuarioSesion != null && "Administrador".equals(usuarioSesion.getRol());
    String paginaVolver = usuarioSesion != null ? ctx + "/modulos.jsp" : ctx + "/index.jsp";
%>
<body>

    <% if (usuarioSesion != null) { %>
        <%@include file="/WEB-INF/includes/nav.jspf"%>
    <% } %>

    <main class="container contenido">
        <div class="card formulario">
            <div class="card-content">

                <div class="titulo-seccion">
                    <i class="material-icons">person_add</i>
                    <div>
                        <h5 class="font-bold">Registro de usuarios</h5>
                        <p>Ingrese los datos de la persona que tendrá acceso al sistema. Los campos con * son obligatorios.</p>
                    </div>
                </div>

                <%@include file="/WEB-INF/includes/mensaje.jspf"%>

                <form name="frmRegistro" id="frmRegistro" action="<%= ctx %>/UsuarioController?accion=registrar" method="post">
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">badge</i>
                            <input id="txtNombre" name="txtNombre" type="text" maxlength="30" required>
                            <label for="txtNombre">Nombres *</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">badge</i>
                            <input id="txtApellido" name="txtApellido" type="text" maxlength="50" required>
                            <label for="txtApellido">Apellidos *</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">fingerprint</i>
                            <input id="txtCedula" name="txtCedula" type="text" maxlength="15" required>
                            <label for="txtCedula">Cédula *</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">phone</i>
                            <input id="txtCelular" name="txtCelular" type="text" maxlength="15">
                            <label for="txtCelular">Celular</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">email</i>
                            <input id="txtCorreo" name="txtCorreo" type="email" maxlength="100" required>
                            <label for="txtCorreo">Correo electrónico *</label>
                        </div>
                        <div class="col s12 m6 campo-rol">
                            <label for="selRol">Rol</label>
                            <% if (esAdministrador) { %>
                                <select id="selRol" name="selRol" class="browser-default">
                                    <option value="Operador">Operador</option>
                                    <option value="Administrador">Administrador</option>
                                </select>
                            <% } else { %>
                                <select id="selRol" name="selRol" class="browser-default" disabled>
                                    <option value="Operador">Operador</option>
                                </select>
                            <% } %>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">lock</i>
                            <input id="txtPassword" name="txtPassword" type="password" minlength="6" required>
                            <label for="txtPassword">Contraseña * (mínimo 6 caracteres)</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <i class="material-icons prefix">lock</i>
                            <input id="txtConfirmar" name="txtConfirmar" type="password" minlength="6" required>
                            <label for="txtConfirmar">Confirmar contraseña *</label>
                        </div>
                    </div>

                    <div class="botones right-align">
                        <a href="<%= paginaVolver %>" class="btn-large waves-effect btn-flat btn-borde">Volver</a>
                        <button type="submit" name="btnRegistrar" class="btn-large waves-effect waves-light btn-verde">
                            <i class="material-icons left">save</i>Registrar usuario
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
