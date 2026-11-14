<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Iniciar sesión - Easy Park</title>
</head>
<%
    // Si ya hay una sesión iniciada, se envía directamente a la página principal
    if (usuarioSesion != null) {
        response.sendRedirect(ctx + "/principal.jsp");
        return;
    }
%>
<body>

    <main class="page-content login-page">
        <div class="container">
            <div class="row valign-wrapper">

                <div class="col s12 m6">
                    <div class="marca-login">
                        <i class="material-icons">directions_car</i>
                        <div>
                            <h4 class="font-bold">Easy Park</h4>
                            <p>Sistema de Gestión de Parqueaderos</p>
                        </div>
                    </div>
                </div>

                <div class="col s12 m6">
                    <div class="card login-card z-depth-3">
                        <div class="card-content">

                            <div class="center-align">
                                <h5 class="font-bold green-text">Iniciar Sesión</h5>
                            </div>

                            <%@include file="/WEB-INF/includes/mensaje.jspf"%>

                            <!-- El formulario se envía por POST para que los datos no se vean en la URL -->
                            <form name="frmAcceso" id="frmAcceso" action="<%= ctx %>/AccesoController" method="post">

                                <div class="input-field">
                                    <i class="material-icons prefix">email</i>
                                    <input id="txtCorreo" name="txtCorreo" type="email" required>
                                    <label for="txtCorreo">Correo electrónico</label>
                                </div>

                                <div class="input-field">
                                    <i class="material-icons prefix">lock</i>
                                    <input id="txtPassword" name="txtPassword" type="password" required>
                                    <label for="txtPassword">Contraseña</label>
                                </div>

                                <p>
                                    <label>
                                        <input type="checkbox" id="chkMostrar" onclick="mostrarPassword()">
                                        <span>Mostrar contraseña</span>
                                    </label>
                                </p>

                                <br>
                                <button type="submit" name="btnIngresar" class="btn waves-effect waves-light green btn-login">
                                    Ingresar <i class="material-icons right">login</i>
                                </button>
                            </form>
                        </div>

                        <div class="card-action center-align enlaces-login">
                            <a href="<%= ctx %>/usuarios/recuperar.jsp" class="green-text">¿Olvidaste tu contraseña?</a>
                            <a href="<%= ctx %>/usuarios/registro.jsp" class="green-text">Registrarse</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>

    <script>
        // Muestra u oculta la contraseña escrita
        function mostrarPassword() {
            var campo = document.getElementById("txtPassword");
            campo.type = document.getElementById("chkMostrar").checked ? "text" : "password";
        }
    </script>
</body>
</html>
