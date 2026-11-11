$(document).ready(function () {
    comprobarSetup();

    function comprobarSetup() {
        $.ajax({
            url: "/setup/status",
            type: "GET"
        }).done(function (data) {
            if (data && data.required) {
                $('#caja-login').addClass('d-none');
                $('#caja-setup').removeClass('d-none');
                $('#contenedor-menu').addClass('d-none');
                $('#contenedor-tabla').addClass('d-none');
                $('#reloj-1').removeClass('d-none');
                $('#reloj-2').addClass('d-none');
            }
        });
    }

    $('#botonSetup').click(function () {
        var nombre = $('#setup-nombre').val().trim();
        var pass = $('#setup-pass').val();
        var pass2 = $('#setup-pass2').val();

        $('#setup-error').addClass('d-none');
        $('#setup-ok').addClass('d-none');

        if (nombre.length < 3) {
            mostrarError('El usuario debe tener al menos 3 caracteres');
            return;
        }
        if (pass.length < 4) {
            mostrarError('La contrasena debe tener al menos 4 caracteres');
            return;
        }
        if (pass !== pass2) {
            mostrarError('Las contrasenas no coinciden');
            return;
        }

        $('#botonSetup').prop('disabled', true);
        var dataForm = new FormData();
        dataForm.append('nombre', nombre);
        dataForm.append('pass', pass);
        var titulo = $('#setup-titulo').val().trim();
        if (titulo) {
            dataForm.append('titulo', titulo);
        }
        var logo = $('#setup-logo')[0].files[0];
        if (logo) {
            dataForm.append('logo', logo);
        }
        var fondo = $('#setup-fondo')[0].files[0];
        if (fondo) {
            dataForm.append('fondo', fondo);
        }

        $.ajax({
            url: "/setup",
            type: "POST",
            data: dataForm,
            processData: false,
            contentType: false
        }).done(function () {
            if (window.cargarBranding) {
                window.cargarBranding();
            }
            $('#setup-ok').removeClass('d-none');
            setTimeout(function () {
                $('#caja-setup').addClass('d-none');
                $('#caja-login').removeClass('d-none');
                $('#usuario').val(nombre);
                $('#setup-ok').addClass('d-none');
                $('#pass').focus();
            }, 1500);
        }).fail(function (xhr) {
            var msg = (xhr.responseJSON && xhr.responseJSON.mensaje)
                ? xhr.responseJSON.mensaje
                : 'No se pudo crear el administrador';
            mostrarError(msg);
        }).always(function () {
            $('#botonSetup').prop('disabled', false);
        });
    });

    function mostrarError(msg) {
        $('#setup-error small').text(msg);
        $('#setup-error').removeClass('d-none');
    }
});
