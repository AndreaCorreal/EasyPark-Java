<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Recuperar contraseña - Easy Park</title>
</head>
<body>

    <main class="container contenido">
        <div class="card formulario formulario-pequeno">
            <div class="card-content">

                <div class="titulo-seccion">
                    <i class="material-icons">lock_reset</i>
                    <div>
                        <h5 class="font-bold">Recuperar contraseña</h5>
                        <p>Para verificar su identidad, escriba el correo y la cédula con los que se registró.</p>
                    </div>
                </div>

                <%@include file="/WEB-INF/includes/mensaje.jspf"%>

                <form name="frmRecuperar" id="frmRecuperar" action="<%= ctx %>/UsuarioController?accion=recuperar" method="post">

                    <div class="input-field">
                        <i class="material-icons prefix">email</i>
                        <input id="txtCorreo" name="txtCorreo" type="email" required>
                        <label for="txtCorreo">Correo electrónico</label>
                    </div>

                    <div class="input-field">
                        <i class="material-icons prefix">fingerprint</i>
                        <input id="txtCedula" name="txtCedula" type="text" required>
                        <label for="txtCedula">Cédula</label>
                    </div>

                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <input id="txtPassword" name="txtPassword" type="password" minlength="6" required>
                        <label for="txtPassword">Nueva contraseña (mínimo 6 caracteres)</label>
                    </div>

                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <input id="txtConfirmar" name="txtConfirmar" type="password" minlength="6" required>
                        <label for="txtConfirmar">Confirmar nueva contraseña</label>
                    </div>

                    <div class="botones right-align">
                        <a href="<%= ctx %>/index.jsp" class="btn-large waves-effect btn-flat btn-borde">Volver</a>
                        <button type="submit" name="btnRecuperar" class="btn-large waves-effect waves-light btn-verde">
                            <i class="material-icons left">save</i>Cambiar contraseña
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
