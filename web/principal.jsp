<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Página principal - Easy Park</title>
</head>
<body>

    <%@include file="/WEB-INF/includes/nav.jspf"%>

    <main class="container contenido">

        <div class="card bienvenida">
            <div class="row mb-0 fila-bienvenida">
                <div class="col s12 m7 bienvenida-texto">
                    <span class="etiqueta">Sistema de Gestión de Parqueaderos</span>
                    <h2 class="font-bold">Proyecto <span class="verde">Easy Park</span></h2>
                    <p class="saludo">
                        Bienvenido(a), <strong><%= Seguridad.escaparHtml(usuarioSesion.getNombre() + " " + usuarioSesion.getApellido()) %></strong>.
                        Ingresaste como <strong><%= Seguridad.escaparHtml(usuarioSesion.getRol()) %></strong>.
                    </p>
                    <p>Easy Park es un sistema pensado para organizar y facilitar los procesos administrativos y operativos de un parqueadero.</p>

                    <div class="objetivo">
                        <i class="material-icons">flag</i>
                        <div>
                            <h6 class="font-bold">Nuestro objetivo</h6>
                            <p>Centralizar la información del parqueadero para evitar errores en los registros manuales y hacer más eficiente el control de entradas, salidas y pagos.</p>
                        </div>
                    </div>

                    <div class="botones">
                        <a href="<%= ctx %>/EntradaController" class="btn-large waves-effect waves-light btn-verde">
                            <i class="material-icons left">arrow_upward</i>Registrar entrada
                        </a>
                        <a href="<%= ctx %>/modulos.jsp" class="btn-large waves-effect btn-flat btn-borde">
                            <i class="material-icons left">apps</i>Panel de módulos
                        </a>
                    </div>
                </div>

                <div class="col s12 m5 bienvenida-sena center-align">
                    <img src="<%= ctx %>/img/logo-sena.png" alt="Logo del SENA" class="responsive-img logo-sena">
                    <p class="font-bold">Proyecto desarrollado para el SENA</p>
                    <p>Servicio Nacional de Aprendizaje</p>
                    <p class="lema">"Formación que transforma vidas"</p>
                </div>
            </div>
        </div>

        <div class="sobre-easypark">
            <h5 class="font-bold">Sobre Easy Park</h5>
            <p>Easy Park es una herramienta que permite registrar los vehículos que entran y salen del parqueadero, consultar los espacios disponibles y llevar el control de los pagos de forma ordenada y sencilla.</p>
        </div>

    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
