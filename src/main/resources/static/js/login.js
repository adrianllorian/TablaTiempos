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
            var us = $('#usuario').val();
            var pas = $('#pass').val();
            if (us == '') {
                $('#mensaje-error-usuario').removeClass('d-none');
                $('#msepu').removeClass('d-none');
                
            }
            if (pas == '') {
                $('#mensaje-error-pass').removeClass('d-none');
                $('#msepu').removeClass('d-none');
                
            }
            else {
                $('#mensaje-error-usuario').addClass('d-none');
                $('#mensaje-error-pass').addClass('d-none');
                
                var envio = {
                    'nombre': us,
                    'pass': pas
                }
                
                $.ajax({
                    url: "/login",
                    type: "POST",
                    data: JSON.stringify(envio),
                    contentType: "application/json; charset=UTF-8",

                }).done(function (data, textStatus, jqXHR) {
                    if (data != null) {
                       
                        localStorage.setItem('token', data.pass);
                        localStorage.setItem('piloto', us);
                        $('#Saludo').html(us)
                        $('#pass').val('');
                        $('#caja-login').addClass('d-none');
                        $('#contenedor-portada').addClass('d-none');
                        $('#contenedor-tabla').removeClass('d-none');
                        $('#contenedor-menu').removeClass('d-none');
                        $('#reloj-1').addClass('d-none');
                        $('#contenedor-tabla').removeClass('d-none');
                        $('#reloj-2').removeClass('d-none');
                        $('#mensaje-error-usuario').addClass('d-none');
                        $('#mensaje-error-pass').addClass('d-none');
                        $('#msepu').addClass('d-none');
                        
                        //Aqui se pide al usuario o usuarios dependiendo del rol que tiene


                    }else{
						$('#msepu').removeClass('d-none');
                        $('#mensaje-error-usuario').addClass('d-none');
                        $('#mensaje-error-pass').addClass('d-none');
						}

                })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                        $('#msepu').removeClass('d-none');
                        $('#mensaje-error-usuario').addClass('d-none');
                        $('#mensaje-error-pass').addClass('d-none');
                        console.log("La solicitud a fallado: " + textStatus + ' son cosas que pasan');

                    });
            }
        });
    }

    function botonCerrarSesion() {
        $('#CerrarSesion').click(function () {
            localStorage.removeItem('token');
            localStorage.removeItem('piloto');
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
            $('#caja-login').removeClass('d-none');
            $('#contenedor-portada').removeClass('d-none');
            $('#contenedor-tabla').addClass('d-none');
            $('#contenedor-menu').addClass('d-none');
            $('#reloj-1').removeClass('d-none');
            $('#contenedor-tabla').addClass('d-none');
            $('#reloj-2').addClass('d-none');
            
        }

    }
});