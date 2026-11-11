$(document).ready(function () {
    $('#boton-pilotos-admin').click(cargarPilotos);

    function cargarPilotos() {
        $.ajax({
            url: "/listarPilotos",
            type: "GET",
            headers: { Authorization: localStorage.getItem('token') }
        }).done(function (data) {
            var select = $('#lista-pilotos-admin');
            select.empty();
            if (!data || data.length === 0) {
                select.append('<option value="">No hay pilotos</option>');
                return;
            }
            $.each(data, function (i, piloto) {
                select.append($('<option>', {
                    value: piloto.nombre,
                    text: piloto.nombre + (piloto.rol ? ' (' + piloto.rol + ')' : '')
                }));
            });
            actualizarNombre();
        });
    }

    function actualizarNombre() {
        var nombre = $('#lista-pilotos-admin').val();
        $('#admin-piloto-nombre').val(nombre);
        $('#admin-piloto-pass').val('');
    }

    $('#lista-pilotos-admin').on('change', actualizarNombre);

    $('#editar-piloto-btn').click(function () {
        var nombre = $('#lista-pilotos-admin').val();
        if (!nombre) {
            mostrarMensaje('Selecciona un piloto', false);
            return;
        }
        var body = {
            nombre: nombre,
            nuevoNombre: $('#admin-piloto-nombre').val().trim(),
            pass: $('#admin-piloto-pass').val()
        };
        $.ajax({
            url: "/editarPiloto",
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            headers: { Authorization: localStorage.getItem('token') },
            data: JSON.stringify(body)
        }).done(function (data) {
            var ok = data && data.indexOf('exito') >= 0;
            mostrarMensaje(data, ok);
            if (ok) {
                $('#admin-piloto-pass').val('');
                cargarPilotos();
            }
        }).fail(function () {
            mostrarMensaje('No se pudo editar el piloto', false);
        });
    });

    $('#borrar-piloto-btn').click(function () {
        var nombre = $('#lista-pilotos-admin').val();
        if (!nombre) {
            mostrarMensaje('Selecciona un piloto', false);
            return;
        }
        if (!confirm('¿Borrar el piloto "' + nombre + '"?')) {
            return;
        }
        $.ajax({
            url: "/borrarPiloto",
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            headers: { Authorization: localStorage.getItem('token') },
            data: JSON.stringify({ nombre: nombre })
        }).done(function (data) {
            var ok = data && data.indexOf('exito') >= 0;
            mostrarMensaje(data, ok);
            if (ok) {
                cargarPilotos();
            }
        }).fail(function () {
            mostrarMensaje('No se pudo borrar el piloto', false);
        });
    });

    function mostrarMensaje(msg, ok) {
        $('#piloto-admin-msg')
            .text(msg || 'Error')
            .removeClass('d-none text-danger text-success')
            .addClass(ok ? 'text-success' : 'text-danger');
    }
});
