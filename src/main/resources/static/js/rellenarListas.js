$(document).ready(function () {
    rellenarDatos();

    function rellenarDatos() {
        $.ajax({
            url: "/listarCircuitos",
            type: "GET"
        }).done(function (data) {
            if (data != null) {
                if (data.length === 0) {
                    $('#buscador-circuito').append('<option value="">No hay circuitos</option>');
                    $('#add-circuito').append('<option value="">No hay circuitos</option>');
                }
                for (var i = 0; i < data.length; i++) {
                    var nombre = escapar(data[i].nombreCircuito);
                    $('#buscador-circuito').append('<option value="' + nombre + '">' + nombre + '</option>');
                    $('#add-circuito').append('<option value="' + nombre + '">' + nombre + '</option>');
                }
            } else {
                $('#buscador-circuito').append('<option value="">HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR</option>');
                $('#add-circuito').append('<option value="">HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR</option>');
                console.log('ERROR: los circuitos vienen vacios desde el servidor');
            }
        }).fail(function (jqXHR, textStatus) {
            $('#buscador-circuito').append('<option value="">HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR</option>');
            $('#add-circuito').append('<option value="">HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR</option>');
            console.log("ERROR: la peticion de circuitos ha fallado: " + textStatus);
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
});
