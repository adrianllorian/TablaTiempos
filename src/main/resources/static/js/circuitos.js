$(document).ready(function () {
    cargarCircuitosAdmin();

    function cargarCircuitosAdmin() {
        $.ajax({
            url: "/listarCircuitos",
            type: "GET"
        }).done(function (data) {
            var select = $('#lista-circuitos-admin');
            select.empty();
            if (!data || data.length === 0) {
                select.append('<option value="">No hay circuitos</option>');
            } else {
                $.each(data, function (i, circuito) {
                    select.append($('<option>', {
                        value: circuito.nombreCircuito,
                        text: circuito.nombreCircuito
                    }));
                });
            }
            actualizarNombre();
        });
    }

    function actualizarNombre() {
        $('#editar-circuito-nombre').val($('#lista-circuitos-admin').val() || '');
    }

    $('#lista-circuitos-admin').on('change', actualizarNombre);

    $('#editar-circuito-btn').click(function () {
        var nombre = $('#lista-circuitos-admin').val();
        var nuevo = $('#editar-circuito-nombre').val().trim();
        if (!nombre || !nuevo) {
            mostrarMsg('Selecciona un circuito y escribe el nuevo nombre', false);
            return;
        }

        $.ajax({
            url: "/editarCircuito",
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            headers: { Authorization: localStorage.getItem('token') },
            data: JSON.stringify({ nombreCircuito: nombre, nuevoNombre: nuevo })
        }).done(function (data) {
            var ok = data && data.indexOf('exito') >= 0;
            mostrarMsg(data, ok);
            if (ok) {
                var eraFiltro = $('#buscador-circuito').val() === nombre;
                $('#buscador-circuito option, #add-circuito option').filter(function () {
                    return $(this).val() === nombre;
                }).remove();
                $('#buscador-circuito').append($('<option>', { value: nuevo, text: nuevo }));
                $('#add-circuito').append($('<option>', { value: nuevo, text: nuevo }));
                if (eraFiltro) {
                    $('#buscador-circuito').val(nuevo).trigger('change');
                }
                cargarCircuitosAdmin();
            }
        }).fail(function () {
            mostrarMsg('No se pudo editar el circuito', false);
        });
    });

    $('#add-circuito-btn').click(function () {
        var nombre = $('#nuevo-circuito').val().trim();
        if (!nombre) {
            mostrarMsg('Escribe un nombre', false);
            return;
        }

        $.ajax({
            url: "/addCircuito",
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            headers: { Authorization: localStorage.getItem('token') },
            data: JSON.stringify({ nombreCircuito: nombre })
        }).done(function (data) {
            var ok = data && data.indexOf('exito') >= 0;
            mostrarMsg(data, ok);
            if (ok) {
                $('#nuevo-circuito').val('');
                $('#buscador-circuito').append($('<option>', { value: nombre, text: nombre }));
                $('#add-circuito').append($('<option>', { value: nombre, text: nombre }));
                $('#buscador-circuito option, #add-circuito option').filter(function () {
                    return $(this).text() === 'No hay circuitos';
                }).remove();
                cargarCircuitosAdmin();
            }
        }).fail(function () {
            mostrarMsg('No se pudo guardar el circuito', false);
        });
    });

    $('#borrar-circuito-btn').click(function () {
        var nombre = $('#lista-circuitos-admin').val();
        if (!nombre) {
            mostrarMsg('Selecciona un circuito', false);
            return;
        }
        if (!confirm('¿Borrar el circuito "' + nombre + '"?')) {
            return;
        }

        $.ajax({
            url: "/borrarCircuito",
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            headers: { Authorization: localStorage.getItem('token') },
            data: JSON.stringify({ nombreCircuito: nombre })
        }).done(function (data) {
            var ok = data && data.indexOf('exito') >= 0;
            mostrarMsg(data, ok);
            if (ok) {
                var eraFiltro = $('#buscador-circuito').val() === nombre;
                $('#buscador-circuito option, #add-circuito option').filter(function () {
                    return $(this).val() === nombre;
                }).remove();
                if (eraFiltro) {
                    $('#buscador-circuito').val('').trigger('change');
                }
                cargarCircuitosAdmin();
            }
        }).fail(function () {
            mostrarMsg('No se pudo borrar el circuito', false);
        });
    });

    function mostrarMsg(msg, ok) {
        $('#circuito-msg')
            .text(msg || 'Error')
            .removeClass('d-none text-danger text-success')
            .addClass(ok ? 'text-success' : 'text-danger');
    }
});
