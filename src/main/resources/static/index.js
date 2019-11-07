
/* global zuix */

let drawerLayout;
let viewPager;
let topicIndicator;
let topicButtons;

// Variables globales propias
let categoriaSeleccionada;
let subcatSeleccionada;
let listadoModificado;
const servicio="https://f65b04bf.ngrok.io/";
//const servicio="http://192.168.2.51:8080/";// ESTA TIENE QUE SER LA IP DEL SERVICIO REST

//zuix.using('script', './service-worker.js');
zuix.using('style', '//zuixjs.github.io/zkit/css/flex-layout-attribute.min.css');
zuix.using('style', './index.css');

zuix.$.find('.profile').on('click', function() {
    if (drawerLayout) drawerLayout.open();
});

window.options = {
    drawerLayout: {
        autoHideWidth: -1,
        drawerWidth: 280,
        ready: function() { drawerLayout = this; this.close(); }
    },
    headerBar: {
        ready: function() {
            const view = zuix.$(this.view());
            // handle 'topic' buttons click (goto clicked topic page)
            topicButtons = view.find('.topics').children().each(function(i, el){
                this.on('click', function(e) {
                    if (viewPager) viewPager.page(i);
                });
            });
            // open drawer when the profile icon is clicked
            view.find('.profile').on('click', function() {
                if (drawerLayout) drawerLayout.open();
            });
            showPage(0);
        }
    },
    footerBar: {
        ready: function(){
            const view = zuix.$(this.view());
            const buttons = view.find('button');
            buttons.each(function(i, el) {
                // TODO:
                this.on('click', function() {
                    buttons.removeClass('active');
                    this.addClass('active');
                    showPage(i);
                });
            });
        }
    },
    ///*
    viewPager: { 
        enablePaging: true,
        startGap: 36,
        on: {
            'page:change': function(e, page) {
                syncPageIndicator(page);
                // show header/footer
                if (viewPager) {
                    const p = viewPager.get(page.in);
                    zuix.context(p).show();
                }
            }
        },
        ready: function() {
            viewPager = this;
        }
    },
    topicIndicator: { //Configuracion del zuix
        enablePaging: true,
        startGap: 36,
        ready: function() {
            topicIndicator = this;
        }
    },
    //*/
    autoHidingBars: {
        header: 'header-bar',
        footer: 'footer-bar',
        height: 56,
        on: {
            'page:scroll': function(e, data) {
                zuix.componentize();
            }
        }
    },
    content: {
        css: false
    }
};

function syncPageIndicator(page) {
    if (topicButtons) {
        topicButtons.eq(page.out).removeClass('active');
        topicButtons.eq(page.in).addClass('active');
    }
    if (topicIndicator) topicIndicator.page(page.in);
}

function showPage(i) {
    // show header top-box
    zuix.field('header-box')
        .children().hide()
        .eq(i).show();
    // show header bottom-box
    zuix.field('header-tools')
        .children().hide()
        .eq(i).show();
    // show page
    zuix.field('pages')
        .children().hide()
        .eq(i).show();
    if (viewPager) viewPager.refresh();
}

function ocultarHeader_Footer(){ //Ocultar el header para mostrar una form
    var header=document.getElementById("header");
    header.style.display="none";
    var footer=document.getElementById("footer");
    footer.style.display="none";
}

function mostrarHeader_Footer(){ //Mostrar el header al salir de una form
    var header=document.getElementById("header");
    header.style.display="block";
    var footer=document.getElementById("footer");
    footer.style.display="block";
}

function linkBoton(botonObjetivo){ //Se vincula un boton con el resultado de clickear otro boton
    var boton=document.getElementById(botonObjetivo);
    boton.click();
}

function crearBoton(imagen,texto,ubicacion,opcion,onClickFunction){ //Se crea una categoria (boton) para la primer pestaña y se adjunta despues de la ubicacion indicada
    var img=document.createElement('img');
    img.className="imgReciclable";
    img.src=imagen;
    img.alt=texto;
    var br=document.createElement('br');
    var text=document.createTextNode(texto);
    var button=document.createElement('button');
    button.className="buttonReciclable";
    button.id=texto;
    button.setAttribute('onclick',onClickFunction);//Al clickearlo se ejecutara una accion
    button.appendChild(img);
    button.appendChild(br);   
    button.appendChild(text); 
    var elem=document.getElementById(ubicacion);
    elem.insertAdjacentElement(opcion,button)
}

function cargarSubcategorias(categoria){ //Carga los botones asociados a las subcategorias
    if (obj.hasOwnProperty(categoria)) {
        const subCats=obj[categoria].subCats;
        var nuevoDiv=document.createElement('div');
        nuevoDiv.id=categoria+"Form";
        nuevoDiv.style.display="none";
        var elem=document.getElementById("formSubcategorias");
        elem.insertAdjacentElement('afterbegin',nuevoDiv);
        if(subCats!=null){
            var explicacion=document.createElement('p');
            explicacion.innerHTML="Seleccione un producto que se asemeje en volumen";
            nuevoDiv.insertAdjacentElement('afterbegin',explicacion);
        }
        for (const subKey in subCats) {
            if (subCats.hasOwnProperty(subKey)) {
                const element = subCats[subKey];
                
                crearBoton(element.src,subKey,nuevoDiv.id,'beforeend',"mostrarDatos(this.id)");
            }
        }
        
    }
}

function mostrarDatos(subcat){ //La funcion que se ejecuta al seleccionar una subcategoria
    subcatSeleccionada=subcat;
    var producto=document.getElementById('prodSeleccionado');
    var volumen=document.getElementById('volumen');
    if (obj.hasOwnProperty(categoriaSeleccionada)){
        const element = obj[categoriaSeleccionada];
        if(element.subCats.hasOwnProperty(subcat)){
            const element2=element.subCats[subcat];
            volumen.innerHTML="El volumen es: "+element2.volumen;
        }
    }
    producto.innerHTML=subcat;
}

function generarBotones(){ //Se lee un json para obtener todos los datos a cargar en la pagina (Especificamente las categorias de la primer pestaña)
    //Esto seria viable cuando Santi nos pase el codigo de las redes neuronales para cada categoria
    //crearBoton("./images/ai.png","Reconocimiento","explicacionForm",'afterend',"");//Hasta entonces solo es un placeholder
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            const element = obj[key];
            if(element.subCats!=null)
                crearBoton(element.src,key,"explicacion",'afterend',"entrarForm(this.id)");
            else
                crearBoton(element.src,key,"explicacion",'afterend',"");
            cargarSubcategorias(key);
        }
    }
}

function entrarForm(categoria){  //Abre la form para ver las subcategorias especificas de una categoria
    if (obj.hasOwnProperty(categoria)){
        element=obj[categoria];
    }
    if(element.subCats==null){
        mostrarDatos(categoria);
    }
    categoriaSeleccionada=categoria;
    var div=document.getElementById(categoriaSeleccionada+"Form");
    div.style.display="inline";
    ocultarHeader_Footer();
    document.getElementById('count').value=1;
    window.location.href='#formSubcategorias';
    
} 

function salirForm(){ //Cierra la form para ver las subcategorias especificas de una categoria
    window.location.href='#';
    mostrarHeader_Footer();
    var div=document.getElementById(categoriaSeleccionada+"Form");
    div.style.display="none";
    categoriaSeleccionada=null;
    var producto=document.getElementById('prodSeleccionado');
    var volumen=document.getElementById('volumen');
    producto.innerHTML="";
    volumen.innerHTML="";
}

const categorias = { //ESTE JSON SE OBTENDRIA DE UNA REQUEST AL SERVIDOR CADA VEZ QUE VOY A ABRIR LA PWA
    /*
    "Aceite":{ //ESTE SE MIDE CON VOLUMEN Y LISTO
        "src":"./images/aceite.jpg",
        "info":"Esta categoria corresponde a l",
        "barcodeLess":true,
        "subCats":null,
        "volumen":,
        "tipo":2
    },
    */
   /*// Irrelevante para medir
    "Carton":{
        "src":"./images/cardboard.png",
        "info":" INFORMACION sobre todos los productos de Carton que entrarian",
        "barcodeLess":true,
        "subCats":{
            "Grande":{
                "src":"./images/subcategorias/cartonGrande.jpg",
                "volumen":30,
                "id":19,
                "representante":"Cereal Nesquik 400g"
            },
            "Mediano":{
                "src":"./images/subcategorias/cartonMediano.jpg",
                "volumen":20,
                "id":20,
                "representante":"Caja de te Taragüi"
            },
            "Chico":{
                "src":"./images/subcategorias/cartonChico.jpg",
                "volumen":10,
                "id":21,
                "representante":"Caldo de gallina Knorr"
            }
        },
        "tipo":1
    }
    "Telgopor":{ // ESTE NO VALE LA PENA MEDIRLO
        "src":"./images/telgopor.jpg",
        "info":" INFORMACION sobre Telgopor",
        "barcodeLess":true,
        "subCats":null,
        "volumen":1,
        "tipo":2
    },
    // Irrelevante para medir
    "Film de embalaje":{
        "src":"./images/film.jpg",
        "info":" INFORMACION sobre Film",
        "barcodeLess":true,
        "subCats":null
    }
    */
   
   "Otros":{ // ESTA CATEGORIA HABRIA QUE VER BIEN  SI SE USARIA O SI SE SACARÍA
        "src":"./images/otros.png",
        "info":" INFORMACION sobre Electronica",
        "barcodeLess":true,
        "subCats":{
            "Monitor":{
                "src":"./images/subcategorias/monitor.jpeg",
                "info":" INFORMACION Monitor",
                "volumen":1000
            },
            "Teclado":{
                "src":"./images/subcategorias/teclado.jpg",
                "info":" INFORMACION teclado",
                "volumen":500
            },
            "Mouse":{
                "src":"./images/subcategorias/mouse.jpg",
                "info":" INFORMACION mouse",
                "volumen":250
            }
        },
        //"tipo":1
    },
    "Sachet":{ //ESTE ES IGUAL AL TIPO 1 PERO SIN OPCIONES
        "src":"./images/sachet.jpg",
        "info":"Esta categoria corresponde a todos los sachets de leche o yogur.",
        "subCats":{
            "Sachet":{
                "src":"./images/sachet.jpg",
                "info":" INFORMACION sachet (enunciar productos similares?)",
                "volumen":100,
                "representante":1
            }
        },
        //"tipo":1
    },
    "Vidrio":{
        "src":"./images/glassbottle.png",
        "info":" INFORMACION sobre todos los productos de vidrio que entrarian",
        "subCats":{
            "Vidrio grande":{
                "src":"./images/subcategorias/vidrioGrande.jpg",
                "info":" INFORMACION botella (enunciar productos similares?)",
                "volumen":1000,
                "representante":2
            },
            "Vidrio mediano":{
                "src":"./images/subcategorias/vidrioMediano.jpg",
                "info":" INFORMACION frasco",
                "volumen":600,
                "representante":3
            },
            "Vidrio chico":{
                "src":"./images/subcategorias/vidrioChico.jpg",
                "info":" INFORMACION gatorade",
                "volumen":400,
                "representante":4
            }
        },
        //"tipo":1
    },
    "Plastico":{
        "src":"./images/plasticbottle.png",
        "info":" INFORMACION sobre todos los productos de plastico que entrarian",
        "subCats":{
            "Plastico grande":{
                "src":"./images/subcategorias/plasticoGrande.jpg",
                "volumen":4000,
                "representante":5
            },
            "Plastico mediano":{
                "src":"./images/subcategorias/plasticoMediano.jpg",
                "volumen":2250,
                "representante":6
            },
            "Plastico chico":{
                "src":"./images/subcategorias/plasticoChico.jpg",
                "volumen":600,
                "representante":7
            }
        },
        //"tipo":1
    },
    "Doypack":{
        "src":"./images/subcategorias/doypackMediano.jpg",
        "info":" INFORMACION ",
        "subCats":{
            "Doypack grande":{
                "src":"./images/subcategorias/doypackGrande.jpg",
                "volumen":210,
                "representante":8
            },
            "Doypack mediano":{
                "src":"./images/subcategorias/doypackMediano.jpg",
                "volumen":190,
                "representante":9
            },
            "Doypack chico":{
                "src":"./images/subcategorias/doypackChico.jpg",
                "volumen":160,
                "representante":10
            }
        },
        //"tipo":1
    },
    "Tetrabrik":{
        "src":"./images/subcategorias/tetraGrande.jpg",
        "info":" INFORMACION ",
        "subCats":{
            "Tetrabrik grande":{
                "src":"./images/subcategorias/tetraGrande.jpg",
                "volumen":1000,
                "representante":14
            },
            "Tetrabrik mediano":{
                "src":"./images/subcategorias/tetraMediano.jpg",
                "volumen":520,
                "representante":12
            },
            "Tetrabrik chico":{
                "src":"./images/subcategorias/tetraChico.jpg",
                "volumen":125,
                "representante":14
            }
        },
        "tipo":1
    },
    "Aluminio":{
        "src":"./images/subcategorias/lataMediano.jpg",
        "info":" INFORMACION ",
        "subCats":{
            "Lata grande":{
                "src":"./images/subcategorias/lataGrande.jpg",
                "volumen":1000,
                "representante":15
            },
            "Lata mediana":{
                "src":"./images/subcategorias/lataMediano.jpg",
                "volumen":350,
                "representante":17
            },
            "Lata chica":{
                "src":"./images/subcategorias/lataChico.jpg",
                "volumen":100,
                "representante":18
            }
        },
        //"tipo":1
    },
    "Hojalata":{
        "src":"./images/subcategorias/aerosol.jpeg",
        "info":" INFORMACION ",
        "subCats":{
            "Aerosol":{
                "src":"./images/subcategorias/aerosol.jpeg",
                "volumen":1000,
                "representante":19
            },
        },
        //"tipo":1
    },
    
}

const datosUser = { //ESTE JSON SE OBTENDRIA DE UNA REQUEST AL SERVIDOR CADA VEZ QUE VOY A ABRIR LA PWA
    "Casa" : {  //PESTAÑA CASA
            "Coca cola":{
                'cantidad':2
                //'volumen':600
            },
            "Brahma 1L":{
                'cantidad':2
                //'volumen':1000
            }
    },
    "Recoleccion" :{ //PESTAÑA RECOLECCION
        "puntos":[
        ['Lat', 'Long', 'Name','Proxima Recoleccion'],
        [-37.3216705,-59.1331596,'Punto de recoleccion 1','1-10-2018'],
        [-37.3216705,-59.1311596,'Punto de recoleccion 2','10-10-2018'],
        [-37.3206705,-59.1311596,'Punto de recoleccion 3','20-10-2018'],
        [-37.3229705,-59.1331596,'Punto de recoleccion 4','30-10-2018'],
        ]
    },
    "Graficos" : { //PESTAÑA GRAFICOS
        "graph":[
            ['Material', 'Kilogramos'],
            ['Plastico',     113],
            ['Baterias',      24],
            ['Carton',  25],
            ['Vidrio', 33],
            ['Otros',    13]
          ],
        "title":"Materiales reciclados"
    },
    "Cuenta":{
        "nombreCompleto": "Gonzalo Coelho",
        "username":"gonxcoe",
        "address":"Fugl 873"
    },
    "Notificaciones":{
        "Ultima recoleccion":{
            "fecha":"2019-08-23",
            "direccion":"Pinto y Santamarina"
        },
        "Anteultima recoleccion":{
            "fecha":"2019-07-23",
            "direccion":"Pinto y Santamarina"
        }
    }
}
let jsonString=JSON.stringify(categorias);
var obj= JSON.parse(jsonString); //Parseo el JSON con la informacion de las categorias
jsonString=JSON.stringify(datosUser);
const objeto= JSON.parse(jsonString);
const datosUsuario=objeto.Casa;//Parseo el JSON con la informacion del usuario.
function loadCuenta(){ //Carga los datos del usuario en la barra lateral (drawer)
    jsonString=JSON.stringify(datosUser);
    const objeto= JSON.parse(jsonString);
    const obj=objeto.Cuenta;
    var text=document.createTextNode(obj.nombreCompleto);
    document.getElementById("divName").appendChild(text);
    text=document.createTextNode(obj.username);
    document.getElementById("divUsername").appendChild(text);
    text=document.createTextNode(obj.address);
    document.getElementById("divAddress").appendChild(text);
}
// TODO: FIJARME SI DEBERIA AGREGAR LAS CATEGORIAS DE LOS PRODUCTOS
function crearElementoListado(nombre,cantidad){ //Se crea un item que tiene un label, una cantidad y se puede modificar esta ultima.
    var pre=document.createElement('pre');
    var label=document.createTextNode(nombre.padEnd(20));//Ajusta los espacios segun requerido
    pre.appendChild(label);
    var botonMenos=document.createElement('button');
    var cantidadElement=document.createElement('span');
    cantidadElement.innerHTML= cantidad.toString().padEnd(4);
    cantidadElement.id=nombre+"count";
    cantidadElement.style.margin='8%';
    pre.appendChild(cantidadElement);
    botonMenos.className="botonModificable";
    botonMenos.id=nombre;
    botonMenos.onclick = function (){
        decrementar(this.id);
    }
    label=document.createTextNode("-");
    botonMenos.appendChild(label);
    pre.appendChild(botonMenos);
    var botonMas=document.createElement('button');
    botonMas.className="botonModificable";
    botonMas.id=nombre;
    botonMas.onclick = function (){
        incrementar(this.id);
    }
    label=document.createTextNode("+");
    botonMas.appendChild(label);
    pre.appendChild(botonMas);
    var section=document.getElementById("listado");
    section.insertAdjacentElement('afterbegin',pre);
}

function incrementar(id){ //INCREMENTA EL CONTADOR DEL PRODUCTO ASOCIADO A ESA ID EN LA PESTAÑA EN CASA
    var pre=document.getElementById(id+"count");
    var numero=parseInt(pre.innerHTML.toString(),10)
    numero++;
    
    var label=numero.toString().padEnd(4);
    pre.innerHTML = label;
}

function decrementar(id){//DECREMENTA EL CONTADOR DEL PRODUCTO ASOCIADO A ESA ID EN LA PESTAÑA EN CASA
    
    var pre=document.getElementById(id+"count");
    var numero=parseInt(pre.innerHTML.toString(),10);
    numero--;
    var label=numero.toString().padEnd(4);
    pre.innerHTML = label;
    if(numero==0){ //Si llega a 0 debe desaparecer de la lista
        pre.parentNode.parentNode.removeChild(pre.parentNode);
        delete datosUsuario[id];
    }
}

function incrementarInput(id,cant){ //Incrementa el contador del form
    count.value++;
}

function decrementarInput(id,cant){//Decrementa el contador del form
    if(count.value!=1)
        count.value--;
}

function generarListado(){ //Se lee un json para obtener todos los datos a cargar en la pagina (Especificamente los reciclables que el usuario posee en su hogar)
    var sectionVieja=document.getElementById("listado"); 
    if(sectionVieja!=null)
        sectionVieja.remove();

        //SI YA EXISTIA LO BORRO PARA ACTUALIZARLO

    var separador=document.getElementById("separadorEnCasa");
    var sectionNueva=document.createElement('section');
    sectionNueva.id="listado";
    separador.insertAdjacentElement('beforebegin',sectionNueva);
    for (const key in datosUsuario) {
        if (datosUsuario.hasOwnProperty(key)) {
            const element = datosUsuario[key];
            crearElementoListado(key,element.cantidad);
        }
    }
    
}
// Turn off debug output
window.zuixNoConsoleOutput = true;

async function confirmarProducto(){ //Se actualiza la pestania "EN CASA" y se envia una POST request al servicio
    var cantidad=document.getElementById('count');//OBTENGO LA CANTIDAD CARGADA POR EL USUARIO
    if(subcatSeleccionada!=null){
        
        var cantActual=0;
        if(datosUsuario[subcatSeleccionada]!=null)
            cantActual=parseInt(datosUsuario[subcatSeleccionada]['cantidad'],10);
        console.log(datosUsuario[subcatSeleccionada]);
        let aReciclar = {
        id_prod: obj[categoriaSeleccionada].subCats[subcatSeleccionada].representante,
        id_user: 100,
        cantidad: parseInt(cantidad.value,10)+cantActual
        };
        console.log(aReciclar);
        var url=servicio+"guardarReciclable";
        
        let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' //ESTO SOLUCIONA EL CORS (CROSS ORIGIN RESOURCE SHARING)
            },
            body: JSON.stringify(aReciclar)
        });
        if(datosUsuario.hasOwnProperty(subcatSeleccionada)){
            var elemento=datosUsuario[subcatSeleccionada];
            elemento.cantidad=elemento.cantidad+parseInt(cantidad.value,10);
        }
        else{
            var nuevoProducto={};
            nuevoProducto.cantidad=parseInt(cantidad.value,10);
            nuevoProducto.displayed=false;
            datosUsuario[subcatSeleccionada]=nuevoProducto;
        }
        subcatSeleccionada=null;
        generarListado();
        listadoModificado=true;
    }
    
      
      
    salirForm();
}
