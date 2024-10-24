$(document).ready(function () {
/*Servicios necesarios
enviarBuscador -Post

*/ 
    recogerBuscador();

    function recogerBuscador(){

        $('#buscador-boton').click(function (e) { 
            //e.preventDefault();
            var piloto = $('#buscador-piloto').val();
            var coche = $('#buscador-coche').val();
            var circuito = $('#buscador-circuito').val();
            if(piloto=='' && coche=='' && circuito==''){
                $("#cuerpo-tabla").children( 'tr' ).remove();
                $('#tabla').addClass('d-none');//No hay datos en la tabla
                $('#reloj-2').removeClass('d-none');
                console.log('No se realiza la consulta');
            }
            else{
                if(piloto=='' && coche=='' && circuito!=''){
                    listarTodosTiemposPorCircuito(circuito);
                }
                else if(piloto=='' && coche!='' && circuito==''){
                    listarTodosTiemposPorCoche(coche)
                }      
                else if(piloto!='' && coche=='' && circuito==''){
                    listarTodosTiemposPorPiloto(piloto)
                }
                else if(piloto=='' && coche!='' && circuito!=''){
                    listarTodosTiemposPorCircuitoYCoche(circuito, coche);
                }
                else if(piloto!='' && coche=='' && circuito!=''){
                    listarTodosTiemposPorCircuitoYPiloto(circuito, piloto)
                }
                else if(piloto!='' && coche!='' && circuito==''){
                    listarTodosTiemposPorPilotoYCoche(piloto, coche);
                }
                else if(piloto!='' && coche!='' && circuito!=''){
                    listarTodosTiemposPorCircuitoYPilotoYCoche(circuito, piloto, coche);
                }
            }
        });
    }
    
    function listarTodosTiemposPorCircuito(circuito){
        var envio = {
            "circuito": {
                nombreCircuito: circuito,
            }
        };
        $.ajax({
            url: "/listarTodosTiemposPorCircuito",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data)
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorCircuito');
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorCircuito');
    });
    }   

    function  listarTodosTiemposPorCoche(coche){
        var envio = {
            "coche": coche,
        };
        $.ajax({
            url: "/listarTodosTiemposPorCoche",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data)
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorCoche');
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorCoche');
    });
    } 

    function  listarTodosTiemposPorPiloto(piloto){
        var envio = {
            "piloto": {
                "nombre": piloto,
            }
        };
        $.ajax({
            url: "/listarTodosTiemposPorPiloto",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data);
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorPiloto');
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorPiloto');
    });
    } 

    function listarTodosTiemposPorCircuitoYCoche(circuito, coche){
        var envio = {
            "coche": coche,
            "circuito": {
                nombreCircuito: circuito,
            }
        };
        $.ajax({
            url: "/listarTodosTiemposPorCircuitoYCoche",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data)
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorCircuitoYCoche');
            
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorCircuitoYCoche');
    });
    } 

    function listarTodosTiemposPorCircuitoYPiloto(circuito, piloto){
        var envio = {
            "piloto": {
                "nombre": piloto,
            },
            "circuito": {
                nombreCircuito: circuito,
            }
        };
        $.ajax({
            url: "/listarTodosTiemposPorCircuitoYPiloto",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data)
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorCircuitoYPiloto');
  
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorCircuitoYPiloto');
    });
    } 

    function listarTodosTiemposPorPilotoYCoche(piloto, coche){
        var envio = {
            "coche": coche,
            "piloto": {
                "nombre": piloto,
            }
        };
        $.ajax({
            url: "/listarTodosTiemposPorPilotoYCoche",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data)
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorPilotoYCoche');
  
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorPilotoYCoche');
    });
    } 

    function listarTodosTiemposPorCircuitoYPilotoYCoche(circuito, piloto, coche){
        var envio = {
            "coche": coche,
            "piloto": {
                "nombre": piloto,
            },
            "circuito": {
                nombreCircuito: circuito,
            }
        };
        $.ajax({
            url: "/listarTodosTiemposPorCircuitoYPilotoYCoche",
            type: "POST",
            data: JSON.stringify(envio),
            contentType: "application/json; charset=UTF-8",
            headers: {
                "Authorization": localStorage.getItem('token')
              }
        }).done(function (data, textStatus, jqXHR) {
            if (data != null) {
                pintarTabla(data)
            }else{
                console.log('buscador.js: ERROR Tiempos no fue encontrado los datos del servidor vienen vacios /listarTodosTiemposPorCircuitoYPilotoYCoche');
  
            }
    }) .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("buscador.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al consultar los Tiempos en listarTodosTiemposPorCircuitoYPilotoYCoche');
    });
    } 

    function pintarTabla(data){
        $("#cuerpo-tabla").children( 'tr' ).remove();
       
        if(data==''){
            //Tabla viene vacia
            $('#tabla').addClass('d-none');//No hay datos en la tabla
            $('#reloj-2').removeClass('d-none');
        }else{
            //la tabla tiene elementos
            $('#reloj-2').addClass('d-none');
            $('#tabla').removeClass('d-none');
                $.each(data, function (i, item) {
                    if(i == 0){
                        $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td class="oro">'+data[i].piloto.nombre+'</td><td class="oro">'+data[i].circuito.nombreCircuito+'</td><td class="oro">'+data[i].coche+'</td><td class="oro">'+data[i].tiempo+'</td><td class="oro text-danger">'+'</td></tr>');
                    }
                    else if(i == 1){
                        $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td class="plata">'+data[i].piloto.nombre+'</td><td class="plata">'+data[i].circuito.nombreCircuito+'</td><td class="plata">'+data[i].coche+'</td><td class="plata">'+data[i].tiempo+'</td><td class="plata"><strong class="text-danger">'+ formatearDiferencia(data[i].diferencia) +'</strong></td></tr>');
                    }
                    else if(i == 2){
                        $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td class="bronce">'+data[i].piloto.nombre+'</td><td class="bronce">'+data[i].circuito.nombreCircuito+'</td><td class="bronce">'+data[i].coche+'</td><td class="bronce">'+data[i].tiempo+'</td><td class="bronce text-danger"><strong class="text-danger">'+ formatearDiferencia(data[i].diferencia) +'</strong></td></tr>');
                    }else{
                        $('#cuerpo-tabla').append('<tr id="filaTabla' + data[i].id + '"><td>'+data[i].piloto.nombre+'</td><td>'+data[i].circuito.nombreCircuito+'</td><td>'+data[i].coche+'</td><td>'+data[i].tiempo+'</td><td class="text-danger"><strong class="text-danger">'+ formatearDiferencia(data[i].diferencia) +'</strong></td></tr>');
                    }
                   
                    
                });
                crearBotonesTabla();
            
        }
    }

    function formatearDiferencia(data){
    
        if(data != 0){

        function addZM(n) {
            return (n<10? '00':'') + n;
          }
          function addZ(n) {
            return (n<10? '0':'') + n;
          }

        var ms = data % 1000;
	    data = (data - ms) / 1000;
	    var secs = data % 60;
	    data = (data - secs) / 60;
	    var mins = data % 60;
       
        if(mins > 0){
            return '+ ' + addZ(mins) + ':' + addZ(secs)+ '.' + addZM(ms);
        }
        else if(secs > 0){
            return  '+ ' + secs + '.'+ addZM(ms);
        }
        else if(ms > 0){
            return '+ 0.'+ addZM(ms);
        }
            
    }
    else{
        return '+ 0.000';
    }
}
    
});