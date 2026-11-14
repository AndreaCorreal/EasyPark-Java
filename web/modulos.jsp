<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="/WEB-INF/includes/head.jspf"%>
    <title>Panel de módulos - Easy Park</title>
</head>
<body>

    <%@include file="/WEB-INF/includes/nav.jspf"%>

    <main class="container contenido">

        <div class="titulo-seccion">
            <i class="material-icons">apps</i>
            <div>
                <h5 class="font-bold">Panel de módulos</h5>
                <p>Seleccione el módulo con el que desea trabajar.</p>
            </div>
        </div>

        <div class="row grid-modulos">

            <div class="col s12 m6 l4">
                <div class="card card-modulo">
                    <div class="card-content center-align">
                        <div class="icono-modulo"><i class="material-icons">directions_car</i></div>
                        <span class="card-title">Vehículos</span>
                        <p>Registrar y consultar los vehículos del parqueadero.</p>
                        <a href="<%= ctx %>/vehiculos/registrar.jsp" class="enlace-modulo">Registrar <i class="material-icons tiny">arrow_forward</i></a>
                        <a href="<%= ctx %>/VehiculoController?accion=listar" class="enlace-modulo">Consultar <i class="material-icons tiny">arrow_forward</i></a>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l4">
                <div class="card card-modulo">
                    <div class="card-content center-align">
                        <div class="icono-modulo"><i class="material-icons">face</i></div>
                        <span class="card-title">Usuarios</span>
                        <p>Registrar las personas que tienen acceso al sistema.</p>
                        <a href="<%= ctx %>/usuarios/registro.jsp" class="enlace-modulo">Registrar usuario <i class="material-icons tiny">arrow_forward</i></a>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l4">
                <div class="card card-modulo">
                    <div class="card-content center-align">
                        <div class="icono-modulo"><i class="material-icons">arrow_upward</i></div>
                        <span class="card-title">Entradas</span>
                        <p>Registrar la entrada de un vehículo a un espacio disponible.</p>
                        <a href="<%= ctx %>/EntradaController" class="enlace-modulo">Registrar entrada <i class="material-icons tiny">arrow_forward</i></a>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l4">
                <div class="card card-modulo modulo-pendiente">
                    <div class="card-content center-align">
                        <div class="icono-modulo"><i class="material-icons">local_parking</i></div>
                        <span class="card-title">Parqueaderos</span>
                        <p>Gestionar los espacios del parqueadero.</p>
                        <span class="etiqueta-pendiente">En construcción</span>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l4">
                <div class="card card-modulo modulo-pendiente">
                    <div class="card-content center-align">
                        <div class="icono-modulo"><i class="material-icons">arrow_downward</i></div>
                        <span class="card-title">Salidas</span>
                        <p>Registrar la salida de un vehículo y el valor a pagar.</p>
                        <span class="etiqueta-pendiente">En construcción</span>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l4">
                <div class="card card-modulo modulo-pendiente">
                    <div class="card-content center-align">
                        <div class="icono-modulo"><i class="material-icons">insert_chart</i></div>
                        <span class="card-title">Reportes</span>
                        <p>Ver reportes y estadísticas del parqueadero.</p>
                        <span class="etiqueta-pendiente">En construcción</span>
                    </div>
                </div>
            </div>

        </div>
    </main>

    <%@include file="/WEB-INF/includes/footer.jspf"%>
</body>
</html>
