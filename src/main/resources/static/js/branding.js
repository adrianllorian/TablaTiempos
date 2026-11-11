$(document).ready(function () {
    cargarBranding();

    window.cargarBranding = cargarBranding;

    function cargarBranding() {
        $.ajax({
            url: "/settings",
            type: "GET"
        }).done(function (data) {
            if (!data) {
                return;
            }

            if (data.titulo) {
                $('#app-titulo').text(data.titulo);
                document.title = data.titulo;
            }

            $('#app-logo').off('error').on('error', function () {
                $(this).hide();
                $('#app-titulo').show();
            });

            if (data.logoUrl) {
                $('#app-logo').attr('src', data.logoUrl).show();
                $('#app-titulo').hide();
            } else {
                $('#app-logo').hide().attr('src', '');
                $('#app-titulo').show();
            }

            var $main = $('.main');
            if (data.fondoUrl) {
                $main.css({
                    'background-image': 'url(' + data.fondoUrl + ')',
                    'background-size': 'cover',
                    'background-position': 'center'
                });
            } else {
                $main.css('background-image', '');
            }
        });
    }
});
