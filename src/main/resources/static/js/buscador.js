$(document).ready(function () {
    var timer;

    $('#buscador-boton').click(buscar);
    $('#buscador-circuito').on('change', buscar);
    $('#buscador-piloto, #buscador-coche').on('input', function () {
        clearTimeout(timer);
        timer = setTimeout(buscar, 350);
    });

    function buscar() {
        var piloto = $('#buscador-piloto').val().trim();
        var coche = $('#buscador-coche').val().trim();
        var circuito = $('#buscador-circuito').val();

        $("#cuerpo-tabla").children('tr').remove();
        $('#tabla').addClass('d-none');
        $('#reloj-2').removeClass('d-none');

        if (!piloto && !coche && !circuito) {
            return;
        }
        if (!piloto && !coche && circuito) {
            return listarTodosTiemposPorCircuito(circuito);
        }
        if (!piloto && coche && !circuito) {
            return listarTodosTiemposPorCoche(coche);
        }
        if (piloto && !coche && !circuito) {
            return listarTodosTiemposPorPiloto(piloto);
        }
        if (!piloto && coche && circuito) {
            return listarTodosTiemposPorCircuitoYCoche(circuito, coche);
        }
        if (piloto && !coche && circuito) {
            return listarTodosTiemposPorCircuitoYPiloto(circuito, piloto);
        }
        if (piloto && coche && !circuito) {
            return listarTodosTiemposPorPilotoYCoche(piloto, coche);
        }
        if (piloto && coche && circuito) {
            return listarTodosTiemposPorCircuitoYPilotoYCoche(circuito, piloto, coche);
        }
    }

    function peticionAjax(url, cuerpo) {
        return $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(cuerpo),
            contentType: "application/json; charset=UTF-8",
            headers: { Authorization: localStorage.getItem('token') }
        }).done(function (data) {
            if (data != null) {
                pintarTabla(data);
            } else {
                errorTabla();
            }
        }).fail(function () {
            errorTabla();
        });
    }

    function listarTodosTiemposPorCircuito(circuito) {
        peticionAjax("/listarTodosTiemposPorCircuito", { circuito: { nombreCircuito: circuito } });
    }

    function listarTodosTiemposPorCoche(coche) {
        peticionAjax("/listarTodosTiemposPorCoche", { coche: coche });
    }

    function listarTodosTiemposPorPiloto(piloto) {
        peticionAjax("/listarTodosTiemposPorPiloto", { piloto: { nombre: piloto } });
    }

    function listarTodosTiemposPorCircuitoYCoche(circuito, coche) {
        peticionAjax("/listarTodosTiemposPorCircuitoYCoche", { coche: coche, circuito: { nombreCircuito: circuito } });
    }

    function listarTodosTiemposPorCircuitoYPiloto(circuito, piloto) {
        peticionAjax("/listarTodosTiemposPorCircuitoYPiloto", { piloto: { nombre: piloto }, circuito: { nombreCircuito: circuito } });
    }

    function listarTodosTiemposPorPilotoYCoche(piloto, coche) {
        peticionAjax("/listarTodosTiemposPorPilotoYCoche", { coche: coche, piloto: { nombre: piloto } });
    }

    function listarTodosTiemposPorCircuitoYPilotoYCoche(circuito, piloto, coche) {
        peticionAjax("/listarTodosTiemposPorCircuitoYPilotoYCoche", {
            coche: coche,
            piloto: { nombre: piloto },
            circuito: { nombreCircuito: circuito }
        });
    }

    function escapar(texto) {
        return String(texto == null ? '' : texto)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }
    function pintarTabla(data) {
        $("#cuerpo-tabla").children('tr').remove();
        if (!data || data.length === 0) {
            $('#tabla').addClass('d-none');
            $('#reloj-2').removeClass('d-none');
            return;
        }

        $('#reloj-2').addClass('d-none');
        $('#tabla').removeClass('d-none');
        $.each(data, function (i, item) {
            if (i === 0) {
                $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td class="oro">' + escapar(data[i].piloto.nombre) + '</td><td class="oro">' + escapar(data[i].circuito.nombreCircuito) + '</td><td class="oro">' + escapar(data[i].coche) + '</td><td class="oro">' + escapar(data[i].tiempo) + '</td><td class="oro text-danger"></td></tr>');
            } else if (i === 1) {
                $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td class="plata">' + escapar(data[i].piloto.nombre) + '</td><td class="plata">' + escapar(data[i].circuito.nombreCircuito) + '</td><td class="plata">' + escapar(data[i].coche) + '</td><td class="plata">' + escapar(data[i].tiempo) + '</td><td class="plata"><strong class="text-danger">' + formatearDiferencia(data[i].diferencia) + '</strong></td></tr>');
            } else if (i === 2) {
                $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td class="bronce">' + escapar(data[i].piloto.nombre) + '</td><td class="bronce">' + escapar(data[i].circuito.nombreCircuito) + '</td><td class="bronce">' + escapar(data[i].coche) + '</td><td class="bronce">' + escapar(data[i].tiempo) + '</td><td class="bronce text-danger"><strong class="text-danger">' + formatearDiferencia(data[i].diferencia) + '</strong></td></tr>');
            } else {
                $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td>' + escapar(data[i].piloto.nombre) + '</td><td>' + escapar(data[i].circuito.nombreCircuito) + '</td><td>' + escapar(data[i].coche) + '</td><td>' + escapar(data[i].tiempo) + '</td><td class="text-danger"><strong class="text-danger">' + formatearDiferencia(data[i].diferencia) + '</strong></td></tr>');
            }
        });
        crearBotonesTabla();
    }

    function errorTabla() {
        $("#cuerpo-tabla").children('tr').remove();
        $('#tabla').addClass('d-none');
        $('#reloj-2').removeClass('d-none');
    }

    function formatearDiferencia(data) {
        if (data === null || data === undefined || data === "" || data === 0 || data === "0") {
            return "+ 0.000";
        }

        function addZM(n) {
            return ('00' + n).slice(-3);
        }
        function addZ(n) {
            return (n < 10 ? '0' : '') + n;
        }

        var ms = data % 1000;
        data = (data - ms) / 1000;
        var secs = data % 60;
        data = (data - secs) / 60;
        var mins = data % 60;

        if (mins > 0) {
            return '+ ' + addZ(mins) + ':' + addZ(secs) + '.' + addZM(ms);
        } else if (secs > 0) {
            return '+ ' + secs + '.' + addZM(ms);
        } else if (ms > 0) {
            return '+ 0.' + addZM(ms);
        }
        return '+ 0.000';
    }
});
