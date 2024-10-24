
    
    function crearBotonesTabla(){
        
        $('tr[id^="filaTabla"]').click(function () {
            var piloto = localStorage.getItem('piloto'); // lo recogo del localStorage
            var preparandoClave = $(this).attr('id').split("filaTabla");
            var idTiempo = preparandoClave[1];
            //$('#ModalAñadirTiempo').modal('show');
            var envio = {
                "id": idTiempo,
            };
            $.ajax({
                url: "/listarPorIdTiempoYPiloto",
                type: "POST",
                data: JSON.stringify(envio),
                contentType: "application/json; charset=UTF-8",
                headers: {
                    "Authorization": localStorage.getItem('token')
                  }
            }).done(function (data, textStatus, jqXHR) {
                if (data != null) {
                   
                   
                        var circuito = data[0].circuito.nombreCircuito;
                        var coche = data[0].coche;
                        var tiempo1 = data[0].tiempo.split(".");
                        var tiempo2 = tiempo1[0].split(":");
                        var minuto = tiempo2[0];
                        var segundo = tiempo2[1];
                        var milesima = tiempo1[1];
                        
                        $('#add-id').val(data[0].id);
                        $('#add-circuito').val(circuito).change();  
                        $('#add-coche').val(coche);
                        $('#minuto').val(minuto);
                        $('#segundo').val(segundo);
                        $('#milesima').val(milesima);

                        var cadena = 'data:image/jpg;base64,' + data[0].imagen;
                        $('#foto-tiempos').attr('src', cadena);
                        $('#caja-foto').removeClass('d-none');
                    if(piloto == data[0].piloto.nombre && idTiempo== data[0].id){
                        activarCamposTiempo();
                        $('#tituloModalTiempos').html('Editar Tiempo');
                        $('#ModalAñadirTiempo').modal('show');
                        $('#borrar-tiempo').removeClass('d-none');
                    }
                    else{
                        $('#tituloModalTiempos').html('Ver Tiempo');
                        $("#add-circuito").prop('disabled', true); 
                        $("#add-coche").prop('disabled', true); 
                        $("#caja-crono").prop('disabled', true);
                        $("#minuto").prop('disabled', true); 
                        $("#segundo").prop('disabled', true); 
                        $("#milesima").prop('disabled', true);
                        $("#confirmar-tiempo").addClass('d-none');
                        $("#subirFoto").addClass('d-none');
                        $('#ModalAñadirTiempo').modal('show');
                    }
                    
                }else{
                    console.log('botonesTabla.js: ERROR Tiempos no fue listado los datos del servidor vienen vacios');
                }
        }) .fail(function (jqXHR, textStatus, errorThrown) {
            console.log("botonesTabla.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarPorIdTiempoYPiloto');
        });
        });

        
    }


    function activarCamposTiempo(){
        $("#add-circuito").prop('disabled', false); 
        $("#add-coche").prop('disabled', false); 
        $("#caja-crono").prop('disabled', false);
        $("#minuto").prop('disabled', false); 
        $("#segundo").prop('disabled', false); 
        $("#milesima").prop('disabled', false);
        $("#confirmar-tiempo").removeClass('d-none');
        $("#subirFoto").removeClass('d-none');
        $('#ModalAñadirTiempo').modal('show');
    }