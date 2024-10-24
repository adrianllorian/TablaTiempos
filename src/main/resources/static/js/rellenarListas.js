$(document).ready(function () {
    /*Servicios necesarios
        Listar Circuitos get LISTO
    */

        rellenarDatos()

        function rellenarDatos(){
            
            $.ajax({
                url: "/listarCircuitos",
                type: "GET",
              }).done(function (data, textStatus, jqXHR) {
                if (data != null) {
                    for(var i =0 ; i< data.length; i++){ 
                        $('#buscador-circuito').append('<option value="'+ data[i].nombreCircuito +'">' + data[i].nombreCircuito + '</option>');
                        $('#add-circuito').append('<option value="'+ data[i].nombreCircuito +'">' + data[i].nombreCircuito + '</option>');
                    }
                 
                  
                }else{
                    $('#buscador-circuito').append('<option value="">' +'HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR' + '</option>');
                    $('#add-circuito').append('<option value="">' + 'HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR' + '</option>');
                    console.log('ERROR-rellenarListasInternas.js: Los datos que contenian los Circuitos viene vacios desde el servidor')
                }
          
          
              })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    $('#buscador-circuito').append('<option value="">' +'HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR' + '</option>');
                    $('#add-circuito').append('<option value="">' + 'HA HABIDO UN ERROR LOS CIRCUITOS NO SE PUEDEN CARGAR' + '</option>');
                    console.log("ERROR-rellenarListasInternas.js: La solicitud a fallado por: " + textStatus + '. La petición para pedir los circuitos ha fallado');
          
                });
          
            };
          
          
        
});