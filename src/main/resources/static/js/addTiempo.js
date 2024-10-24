$(document).ready(function () {
    /*Servicios necesarios
    guardarTiempos -Post
    
    */
    comprobarEditar();
    ponerBotonesModal()
    limitarNumeros()
    funcionamientoCrono()
    filePreview()
    recogerTiempos()

    /*Comprobar si es para editar*/
    function comprobarEditar(){
        $('#modal-tiempo').click(function (e) { 
            //e.preventDefault();
             $('#add-coche').val('');
             $('#minuto').val('');
             $('#segundo').val('');
             $('#milesima').val('');
             $('#add-id').val('');
             $('#add-circuito').val('circuito').change();
             $('#tituloModalTiempos').html('Añadir Tiempo');
             $('#borrar-tiempo').addClass('d-none');
             activarCamposTiempo();
            
        });
    } 



    /*Recojo los datos del formulario*/
    function recogerTiempos() {
        $('#confirmar-tiempo').click(function (e) {
            //e.preventDefault();
            var piloto = localStorage.getItem('piloto'); //lo recogo del almacen
            var coche = $('#add-coche').val();
            var circuito = $('#add-circuito').val();
            var minuto = $('#minuto').val();
            var segundo = $('#segundo').val();
            var milesima = $('#milesima').val();
            var fotoSubir =  document.getElementById("subirFoto");
            var foto = fotoSubir.files[0];

            if (piloto == '' || coche == '' || circuito == '' || circuito =='circuito' || segundo == '' || milesima == '' || foto == null) {
                $('#faltanDatosAddTiempo').removeClass('d-none');
            } else {
                $('#faltanDatosAddTiempo').addClass('d-none');
                //Para editar
                var envio;
                if($('#add-id').val() == ''){
                    
                    envio = {
                        "tiempo": formatearTiempos(minuto, segundo, milesima),
                        "coche": coche,
                        "piloto": {
                            "nombre": piloto,
                        },
                        "circuito": {
                            nombreCircuito: circuito,
                        }
                    };
                }

                
                else{
                    envio = {
                        "id":$('#add-id').val(),
                        "tiempo": formatearTiempos(minuto, segundo, milesima),
                        "coche": coche,
                        "piloto": {
                            "nombre": piloto,
                        },
                        "circuito": {
                            nombreCircuito: circuito,
                        }
                    };
                }

                var dataForm = new FormData();
                dataForm.append('foto',foto);
                dataForm.append('tiempos',JSON.stringify(envio));
               
               
                $.ajax({
                    url: "/addTiempo",
                    type: "POST",
                    data: dataForm,
                    contentType:false,
                    processData: false,
                    headers: {
                        "Authorization": localStorage.getItem('token')
                      }
                }).done(function (data, textStatus, jqXHR) {
                    if (data != null) {

                            $('#faltanDatosAddTiempo').addClass('d-none');
                            $('#erroDatosAddTiempo').addClass('d-none');
                            $('#completoDatosAddTiempo').removeClass('d-none');

                        setTimeout(function () {
                            $('#ModalAñadirTiempo').modal('toggle');
                            $('#faltanDatosAddTiempo').addClass('d-none');
                            $('#erroDatosAddTiempo').addClass('d-none');
                            $('#completoDatosAddTiempo').addClass('d-none');
                            $('#caja-foto').addClass('d-none');
                            $('#add-piloto').val('');
                            $('#add-coche').val('');
                            $('#minuto').val('');
                            $('#segundo').val('');
                            $('#milesima').val('');
                            console.log('Tiempos guardados con exito ' + data);
                        }, 2000)
                        $('#add-piloto').val('');
                        $('#add-coche').val('');
                        $('#minuto').val('');
                        $('#segundo').val('');
                        $('#milesima').val('');

                    } else {
                        $('#faltanDatosAddTiempo').addClass('d-none');
                        $('#erroDatosAddTiempo').removeClass('d-none');
                        $('#completoDatosAddTiempo').addClass('d-none');
                        console.log('addTiempo.js: ERROR Piloto no fue guardado los datos del servidor vienen vacios');
                    }


                })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                        $('#faltanDatosAddTiempo').addClass('d-none');
                        $('#erroDatosAddTiempo').removeClass('d-none');
                        $('#completoDatosAddTiempo').addClass('d-none');
                        console.log("addTiempo.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al guardar los Tiempos');

                    });           
            }
        });
    }

    /*Cambio el focus del input del crono para ajustarlo a 60 minutos*/
    function funcionamientoCrono() {

        var minuto = $('#minuto');
        var segundo = $('#segundo');
        var milesima = $('#milesima');


        minuto.keyup(function (e) {
            //e.preventDefault();
            if (minuto.val().length > 1) {
                segundo.focus();
            }
            if (parseInt(minuto.val()) > 5 && minuto.val().length == 1) {
                minuto.val('0' + minuto.val());
                segundo.focus();
            }
        });



        segundo.keyup(function (e) {
            //e.preventDefault();
            if (segundo.val().length > 1) {
                milesima.focus();
            }

            if (parseInt(segundo.val()) > 5 && segundo.val().length == 1) {
                segundo.val('0' + segundo.val());
                milesima.focus();
            }

            if (segundo.length > 1) {
                milesima.focus();
            }

        });

        milesima.keyup(function (e) {
            //e.preventDefault();
            if (milesima.val().length > 2) {
                milesima.focus();
            }
        });

    }

    function ponerBotonesModal(){
        $('#editar-tiempo').addClass('d-none');
        $('#confirmar-tiempo').removeClass('d-none');
        $('#borrar-tiempo').removeClass('d-none');
    }

    //Creo el metodo para guardar el tiempo en un formato determinado
    function formatearTiempos(minuto, segundo, milesima) {
        return minuto + ':' + segundo + '.' + milesima;
    }

    //Esta funcion delimita a solo numeros los input del crono
    function limitarNumeros() {

        var minuto = $('#minuto');
        var segundo = $('#segundo');
        var milesima = $('#milesima');

        minuto.keypress(function (e) {
            var charCode = (e.which) ? e.which : e.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
        });

        segundo.keypress(function (e) {
            var charCode = (e.which) ? e.which : e.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
        });

        milesima.keypress(function (e) {
            var charCode = (e.which) ? e.which : e.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
        });

    }



    function filePreview() {
           $('#subirFoto').change(function (e) { 
            console.log('Entra al menos')
            var input =  document.getElementById("subirFoto");
            
            $('#caja-foto').removeClass('d-none');
            
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#foto-tiempos').remove();
                    $('#caja-foto').append('<img id="foto-tiempos" src="'+e.target.result+'" class="card-img-bottom" alt="Foto No Cargada"/>');
                }
                reader.readAsDataURL(input.files[0]);
            }
            
        });
        
    }

});