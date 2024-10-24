$(document).ready(function () {
/*
Servicios que necesito
Añadir/Cambiar Contraseña Piloto -post

*/
    cambiarModo()
    recogerPiloto()

var modoCambiarContraseña = 'off'

//Cambiar modo cambiar contraseña añadir usuario
    function cambiarModo(){
        $('#boton-cambiar-pass').click(function (e) { 
            //e.preventDefault();
            if(modoCambiarContraseña == 'off'){
                $('#boton-cambiar-pass').text('Añadir Piloto');
                $('#titulo-piloto').html('Cambiar Contraseña');
                $('#add-piloto').val(localStorage.getItem('piloto')) //Meter el usuario de la sesion
                $('#add-llave').val(''); 
                $('#add-llave-conf').val('');
                $('#confirmar-piloto').text('Confirmar Contraseña');
                $('#add-piloto').prop('disabled', true);
                modoCambiarContraseña='on';
            }else{
                $('#boton-cambiar-pass').text('Cambiar Contraseña');
                $('#titulo-piloto').html('Añadir Piloto');
                $('#add-piloto').val('');
                $('#add-llave').val(''); 
                $('#add-llave-conf').val('');
                $('#confirmar-piloto').text('Confirmar Piloto'); 
                $('#add-piloto').prop('disabled', false);
                modoCambiarContraseña='off'
            }
        });
    }

    //Reoger los datos del piloto
    function recogerPiloto(){
        $('#confirmar-piloto').click(function (e) { 
            //e.preventDefault();
            var piloto = $('#add-piloto')
            var pass = $('#add-llave')
            var pass_conf = $('#add-llave-conf')
    
            
            if(piloto.val() == '' || pass.val() == '' || pass_conf == ''){
                $('#faltan-datos-piloto').removeClass('d-none');
            }

            if(piloto.val().length < 2 || pass.val().length < 2 || pass_conf.val().length < 2){
                $('#minimo-datos-piloto').removeClass('d-none');
            }

            if(pass.val() != pass_conf.val()){
                $('#pass-no-valido').removeClass('d-none');
            }

            else{
                $('#pass-no-valido').addClass('d-none');
                $('#minimo-datos-piloto').addClass('d-none');
                $('#faltan-datos-piloto').addClass('d-none');
                //Metodo ajax
                

                var envioNombre = piloto.val()
                var envioPass = pass.val();
                var envio ={
                    "nombre":envioNombre,
                    "pass": envioPass
                }
               if(modoCambiarContraseña == "off"){
  
                $.ajax({
                    url: "/addPiloto",
                    type: "POST",
                    data: JSON.stringify(envio),
                    contentType: "application/json; charset=UTF-8",
                    headers: {
                        "Authorization": localStorage.getItem('token')
                      }
                  }).done(function (data, textStatus, jqXHR) {
                    if (data != null) {
                        if (data == "El nombre ya esta en uso"){
                                $('#piloto-repetido').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-no-guardado').addClass('d-none');                    
                               

                        }else if(data == "No ha llegado correctamente el piloto al servidor"){
                                $('#piloto-no-guardado').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                        }
                        else if(data == "Ha habido un error al guardar el Piloto"){
                                $('#piloto-no-guardado').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                        }
                        else if(data == "Piloto guardao con exito"){
                        
                                $('#piloto-guardado').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-no-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                        setTimeout(function (){ 
                           
                                $('#ModalAñadirPiloto').modal('toggle');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-no-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                             
                        }, 1500);

                    }
                        
                    }else{
                        $('#piloto-no-guardado').removeClass('d-none');
                        $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                        console.log('addPiloto.js: ERROR Piloto no fue guardado los datos del servidor vienen vacios');
                    }
              
              
                  })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                      $('#piloto-no-guardado').removeClass('d-none');
                      $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                      console.log("addPiloto.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al guardar el piloto');
              
                    });
                }
                else if(modoCambiarContraseña == "on"){
                    $.ajax({
                        url: "/cambiarPass",
                        type: "POST",
                        data: JSON.stringify(envio),
                        contentType: "application/json; charset=UTF-8",
                        headers: {
                            "Authorization": localStorage.getItem('token')
                          }
                      }).done(function (data, textStatus, jqXHR) {
                        if (data != null) {
                            if(data == "No ha llegado correctamente el piloto al servidor"){
                                $('#piloto-no-guardado').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                            }
                            else if(data == "Ha habido un error al guardar la  Piloto"){
                                $('#piloto-no-guardado').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                            }
                            else if(data == "Contraseña cambiada con exito"){
                                $('#piloto-guardado').removeClass('d-none');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-no-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                                piloto.val('');
                                pass.val('');
                                pass_conf.val('');
                        setTimeout(function (){ 
                           
                                $('#ModalAñadirPiloto').modal('toggle');
                                $('#pass-no-valido').addClass('d-none');
                                $('#minimo-datos-piloto').addClass('d-none');
                                $('#faltan-datos-piloto').addClass('d-none');
                                $('#piloto-guardado').addClass('d-none');
                                $('#piloto-no-guardado').addClass('d-none');
                                $('#piloto-repetido').addClass('d-none');
                             
                        }, 1500);
                            }
                            piloto.val('');
                            pass.val('');
                            pass_conf.val('');

                        }else{
                            $('#piloto-no-guardado').removeClass('d-none');
                            $('#pass-no-valido').addClass('d-none');
                                    $('#minimo-datos-piloto').addClass('d-none');
                                    $('#faltan-datos-piloto').addClass('d-none');
                                    $('#piloto-guardado').addClass('d-none');
                                    $('#piloto-repetido').addClass('d-none');
                            console.log('addPiloto.js: ERROR Contraseña no fue guardada los datos del servidor vienen vacios');
                        }
                      }) .fail(function (jqXHR, textStatus, errorThrown) {
                        $('#piloto-no-guardado').removeClass('d-none');
                        $('#pass-no-valido').addClass('d-none');
                                  $('#minimo-datos-piloto').addClass('d-none');
                                  $('#faltan-datos-piloto').addClass('d-none');
                                  $('#piloto-guardado').addClass('d-none');
                                  $('#piloto-repetido').addClass('d-none');
                        console.log("addPiloto.js: ERROR La solicitud a fallado: " + textStatus + ' son cosas que pasan al guardar la contraseña');
                
                      })
                }
            }
            piloto.val('');
            pass.val('');
            pass_conf.val('');
        });
    }
});