$(document).ready(function () {
    $('#boton-ajustes').click(function () {
        $.ajax({
            url: "/settings",
            type: "GET"
        }).done(function (data) {
            if (data) {
                $('#ajustes-titulo').val(data.titulo || 'Tabla de Tiempos');
            }
            $('#ajustes-logo').val('');
            $('#ajustes-fondo').val('');
            $('#ajustes-quitar-logo').prop('checked', false);
            $('#ajustes-quitar-fondo').prop('checked', false);
            $('#ajustes-msg').addClass('d-none');
        });
    });

    $('#guardar-ajustes').click(function () {
        var dataForm = new FormData();
        var titulo = $('#ajustes-titulo').val().trim();
        if (titulo) {
            dataForm.append('titulo', titulo);
        }
        var logo = $('#ajustes-logo')[0].files[0];
        if (logo) {
            dataForm.append('logo', logo);
        }
        var fondo = $('#ajustes-fondo')[0].files[0];
        if (fondo) {
            dataForm.append('fondo', fondo);
        }
        if ($('#ajustes-quitar-logo').is(':checked')) {
            dataForm.append('quitarLogo', 'true');
        }
        if ($('#ajustes-quitar-fondo').is(':checked')) {
            dataForm.append('quitarFondo', 'true');
        }

        $('#guardar-ajustes').prop('disabled', true);
        $.ajax({
            url: "/settings",
            type: "POST",
            data: dataForm,
            processData: false,
            contentType: false,
            headers: { Authorization: localStorage.getItem('token') }
        }).done(function () {
            mostrarMensaje('Ajustes guardados', true);
            if (window.cargarBranding) {
                window.cargarBranding();
            }
            $('#ajustes-logo').val('');
            $('#ajustes-fondo').val('');
            $('#ajustes-quitar-logo').prop('checked', false);
            $('#ajustes-quitar-fondo').prop('checked', false);
        }).fail(function (xhr) {
            var msg = (xhr.responseJSON && xhr.responseJSON.error)
                ? xhr.responseJSON.error
                : 'No se pudieron guardar los ajustes';
            mostrarMensaje(msg, false);
        }).always(function () {
            $('#guardar-ajustes').prop('disabled', false);
        });
    });

    function mostrarMensaje(msg, ok) {
        $('#ajustes-msg')
            .text(msg)
            .removeClass('d-none text-danger text-success')
            .addClass(ok ? 'text-success' : 'text-danger');
    }
});
