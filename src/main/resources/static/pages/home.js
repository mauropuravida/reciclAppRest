function mostrarHeader_Footer(){ //Mostrar el header al salir de una form
    var header=document.getElementById("header");
    header.style.display="block";
    var footer=document.getElementById("footer");
    footer.style.display="block";
}



/*function generarListado(){ //Se lee un json para obtener todos los datos a cargar en la pagina (Especificamente los reciclables que el usuario posee en su hogar)
    var sectionVieja=document.getElementById("listado"); 
    if(sectionVieja!=null)
        sectionVieja.remove();

        //SI YA EXISTIA LO BORRO PARA ACTUALIZARLO
    console.log("generando listado");

    var separador=document.getElementById("separadorEnCasa");
    var sectionNueva=document.createElement('section');
    sectionNueva.id="listado";
    separador.insertAdjacentElement('beforebegin',sectionNueva);
    for (const key in datosUsuario) {
    	console.log("leyendo ");
        if (datosUsuario.hasOwnProperty(key)) {
            const element = datosUsuario[key];
            crearElementoListado(element.descripcion,element.cantidad);
        }
    }
    
}*/

