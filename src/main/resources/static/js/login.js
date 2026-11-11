$(document).ready(function () {
    /*Servicios necesarios
        EnviarLogin -post
        CerrarSeasion ?
    */
    comprobarSesionAbierta();
    recogerLogin();
    botonCerrarSesion();
    

    function recogerLogin() {
        $('#botonLogin').click(function (e) {
            //e.preventDefault();
            var us = $('#usuario').val().trim();
            var pas = $('#pass').val();

            if (us == '') {
                $('#mensaje-error-usuario').removeClass('d-none');
            } else {
                $('#mensaje-error-usuario').addClass('d-none');
            }
            if (pas == '') {
                $('#mensaje-error-pass').removeClass('d-none');
            } else {
                $('#mensaje-error-pass').addClass('d-none');
            }
            if (us == '' || pas == '') {
                $('#msepu').removeClass('d-none');
                return;
            }
            $('#msepu').addClass('d-none');

            var envio = {
                'nombre': us,
                'pass': pas
            };

            $.ajax({
                url: "/login",
                type: "POST",
                data: JSON.stringify(envio),
                contentType: "application/json; charset=UTF-8"
            }).done(function (data, textStatus, jqXHR) {
                if (data != null) {
                    localStorage.setItem('token', data.pass);
                    localStorage.setItem('piloto', us);
                    localStorage.setItem('rol', data.rol || '');
                    aplicarRol(data.rol);
                    $('#Saludo').text(us);
                    $('#pass').val('');
                    $('#caja-login').addClass('d-none');
                    $('#contenedor-portada').addClass('d-none');
                    $('#contenedor-tabla').removeClass('d-none');
                    $('#contenedor-menu').removeClass('d-none');
                    $('#reloj-1').addClass('d-none');
                    $('#reloj-2').removeClass('d-none');
                    $('#mensaje-error-usuario').addClass('d-none');
                    $('#mensaje-error-pass').addClass('d-none');
                    $('#msepu').addClass('d-none');
                } else {
                    $('#msepu').removeClass('d-none');
                    $('#mensaje-error-usuario').addClass('d-none');
                    $('#mensaje-error-pass').addClass('d-none');
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                $('#msepu').removeClass('d-none');
                $('#mensaje-error-usuario').addClass('d-none');
                $('#mensaje-error-pass').addClass('d-none');
                console.log("La solicitud ha fallado: " + textStatus);
            });
        });
    }
    function botonCerrarSesion() {
        $('#CerrarSesion').click(function () {
            localStorage.removeItem('token');
            localStorage.removeItem('piloto');
            localStorage.removeItem('rol');
            aplicarRol(null);
            $('#caja-login').removeClass('d-none');
            $('#contenedor-portada').removeClass('d-none');
            $('#contenedor-tabla').addClass('d-none');
            $('#contenedor-menu').addClass('d-none');
            $('#reloj-1').removeClass('d-none');
            $('#contenedor-tabla').addClass('d-none');
            $('#reloj-2').addClass('d-none');


        }
        );
    }

    function comprobarSesionAbierta() {
        if (localStorage.getItem('token') != null) {
            //activado
            aplicarRol(localStorage.getItem('rol'));
            var pilotoGuardado = localStorage.getItem('piloto');
            if (pilotoGuardado) {
                $('#Saludo').html(pilotoGuardado);
            }
            $('#caja-login').addClass('d-none');
            $('#contenedor-portada').addClass('d-none');
            $('#contenedor-tabla').removeClass('d-none');
            $('#contenedor-menu').removeClass('d-none');
            $('#reloj-1').addClass('d-none');
            $('#contenedor-tabla').removeClass('d-none');
            $('#reloj-2').removeClass('d-none');
            
        }
        else {
            //desactivado
            aplicarRol(null);
            $('#caja-login').removeClass('d-none');
            $('#contenedor-portada').removeClass('d-none');
            $('#contenedor-tabla').addClass('d-none');
            $('#contenedor-menu').addClass('d-none');
            $('#reloj-1').removeClass('d-none');
            $('#contenedor-tabla').addClass('d-none');
            $('#reloj-2').addClass('d-none');
            
        }

    }

    function aplicarRol(rol) {
        if (rol === 'ROLE_ADMIN') {
            $('#boton-add-piloto').show();
            $('#boton-circuitos').show();
            $('#boton-pilotos-admin').show();
            $('#boton-ajustes').show();
        } else {
            $('#boton-add-piloto').hide();
            $('#boton-circuitos').hide();
            $('#boton-pilotos-admin').hide();
            $('#boton-ajustes').hide();
        }
    }
});