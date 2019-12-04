var accion = false;
/* global zuix */

let drawerLayout;
let viewPager;
let topicIndicator;
let topicButtons;

// Variables globales propias
let categoriaSeleccionada;
let subcatSeleccionada;
let listadoModificado;

//zuix.using('script', './service-worker.js');
zuix.using('style', '//zuixjs.github.io/zkit/css/flex-layout-attribute.min.css');
zuix.using('style', '../reciclapp/index.css');

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

function cargarSubcategorias(categoria){ //Carga los todos botones asociados a las categorias y subcategorias
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

//Esta funcion genera todos los botones de las categorias principales
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
    //console.log("categoria  "+categoria);
    categoriaSeleccionada=categoria;
    var div=document.getElementById(categoriaSeleccionada+"Form");  
    
    div.style.display="inline";
    ocultarHeader_Footer();
    document.getElementById('count').value=1;
    window.location.href='#formSubcategorias';

    //seleccion por defecto del producto
    subcatSeleccionada=Object.keys(categorias[categoria].subCats)[0];
    document.getElementById(subcatSeleccionada).click();
    document.getElementById(subcatSeleccionada).focus();

    
    
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
                "representante":17
            }
        },
        //"tipo":1
    },
    "Vidrio":{
        "src":"./images/glassbottle.jpg",
        "info":" INFORMACION sobre todos los productos de vidrio que entrarian",
        "subCats":{
            "Vidrio grande":{
                "src":"./images/subcategorias/vidrioGrande.jpg",
                "info":" INFORMACION botella (enunciar productos similares?)",
                "volumen":1000,
                "representante":16
            },
            "Vidrio mediano":{
                "src":"./images/subcategorias/vidrioMediano.jpg",
                "info":" INFORMACION frasco",
                "volumen":600,
                "representante":15
            },
            "Vidrio chico":{
                "src":"./images/subcategorias/vidrioChico.jpg",
                "info":" INFORMACION gatorade",
                "volumen":400,
                "representante":14
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
                "representante":13
            },
            "Plastico mediano":{
                "src":"./images/subcategorias/plasticoMediano.jpg",
                "volumen":2250,
                "representante":12
            },
            "Plastico chico":{
                "src":"./images/subcategorias/plasticoChico.jpg",
                "volumen":600,
                "representante":11
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
                "representante":10
            },
            "Doypack mediano":{
                "src":"./images/subcategorias/doypackMediano.jpg",
                "volumen":190,
                "representante":9
            },
            "Doypack chico":{
                "src":"./images/subcategorias/doypackChico.jpg",
                "volumen":160,
                "representante":8
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
                "representante":7
            },
            "Tetrabrik mediano":{
                "src":"./images/subcategorias/tetraMediano.jpg",
                "volumen":520,
                "representante":6
            },
            "Tetrabrik chico":{
                "src":"./images/subcategorias/tetraChico.jpg",
                "volumen":125,
                "representante":5
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
                "representante":4
            },
            "Lata mediana":{
                "src":"./images/subcategorias/lataMediano.jpg",
                "volumen":350,
                "representante":3
            },
            "Lata chica":{
                "src":"./images/subcategorias/lataChico.jpg",
                "volumen":100,
                "representante":2
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
                "representante":1
            },
        },
        //"tipo":1
    },
    
}

var datosUser = {
		"Casa" : {  //PESTAÑA CASA
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
	        "graph":[],
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



const datosUser2 = { //ESTE JSON SE OBTENDRIA DE UNA REQUEST AL SERVIDOR CADA VEZ QUE VOY A ABRIR LA PWA
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
const datosUsuario=datosUser.Casa;//Parseo el JSON con la informacion del usuario.
function loadCuenta(){ //Carga los datos del usuario en la barra lateral (drawer)
	
	//consulta datos de cuenta logeada
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/usuario";
	http.open("GET", url, true);
	
	http.onreadystatechange = function() {
	    if( http.readyState == 4 && http.status == 200) {
	    	//parseo la respuesta
	    	var arr = JSON.parse(this.responseText);
	    	var infoReciclado={};
    		infoReciclado['Cuenta'] ={
		        "nombreCompleto": arr["nombre"]+" "+arr["apellido"],
		        "username": arr["username"],
		        "address": arr["address"],
    		}
	 
    	    const obj=infoReciclado.Cuenta;
    	    var text=document.createTextNode(obj.nombreCompleto);
    	    document.getElementById("divName").appendChild(text);
    	    text=document.createTextNode(obj.username);
    	    document.getElementById("divUsername").appendChild(text);
    	    text=document.createTextNode(obj.address);
    	    document.getElementById("divAddress").appendChild(text);
	    }
	}
	http.send();
}

function generarMapa(){
    
    //setTimeout(function(){
    	document.getElementById("imageDiv").style.display = "none";
    	document.getElementById("generadorMapa").style.display = "none";
    	document.getElementById("map_div").style.display = "block";
    
		//consulta datos de recoleccion
		var http = new XMLHttpRequest();
		var url = servicio+"reciclapp/puntos";
		http.open("GET", url, true);
		
		http.onreadystatechange = function() {
		    if( http.readyState == 4 && http.status == 200) {		    	
		    	// Where you want to render the map.
		    	var element = document.getElementById('map_div');
	
		    	// Create Leaflet map on map element.
		    	var map = L.map(element);
	
		    	// Add OSM tile leayer to the Leaflet map.
		    	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		    	    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
		    	}).addTo(map);
		
		    	//parseo la respuesta
		    	var arr = JSON.parse(this.responseText);	    	
		    	
		    	for(i = 0; i < arr.length; i++){
		    		datosUser.Recoleccion['puntos'].push([arr[i]["latitud"],arr[i]["longitud"],arr[i]["name"],arr[i]["fecha"]]);
		    		
			    	// Target's GPS coordinates.
		    		var latitud = arr[i]["latitud"];
		    		var longitud = arr[i]["longitud"];
		    		var target = L.latLng( latitud, longitud );
			    	
			    	var circle = L.circle([latitud, longitud], {
			    	    color: 'red',
			    	    fillColor: '#f03',
			    	    fillOpacity: 0.5,
			    	    radius: 500
			    	}).addTo(map);
			    	
			    	// Set map's center to target with zoom 14.
			    	map.setView(target, 14);
		
			    	// Place a marker on the same location.
			    	L.marker(target).addTo(map)
			        .bindPopup("<b>"+arr[i]["name"]+"</b><br>"+arr[i]["fecha"])
			        .openPopup();
		    	}
		    }
		}
		http.send();
    //}, 5*1000);
}

// TODO: FIJARME SI DEBERIA AGREGAR LAS CATEGORIAS DE LOS PRODUCTOS
function crearElementoListado(nombre,cantidad){ //Se crea un item que tiene un label, una cantidad y se puede modificar esta ultima.
    var pre=document.createElement('pre');
    var label=document.createElement('span');//Ajusta los espacios segun requerido
    label.innerHTML= nombre;
    label.style.width = "165px";
    label.style.float = 'left';
    pre.appendChild(label);
    
    var botonMenos=document.createElement('button');
    var cantidadElement=document.createElement('span');
    cantidadElement.innerHTML= cantidad;
    cantidadElement.id=nombre+"count";
    cantidadElement.style.width = "65px";
    cantidadElement.style.textAlign="center";
    cantidadElement.style.float = 'right';
    pre.style.width = "230px";
    pre.style.height = "15px";
    pre.style.textAlign="left";
    pre.appendChild(cantidadElement);
    botonMenos.className="botonModificable";
    botonMenos.id=nombre;
    botonMenos.onclick = function (){
        decrementar(this.id);
    }
    label=document.createTextNode("-");
    botonMenos.appendChild(label);
    //pre.appendChild(botonMenos);
    var botonMas=document.createElement('button');
    botonMas.className="botonModificable";
    botonMas.id=nombre;
    botonMas.onclick = function (){
        incrementar(this.id);
    }
    label=document.createTextNode("+");
    botonMas.appendChild(label);
    //pre.appendChild(botonMas);
    var section=document.getElementById("listado");
    section.insertAdjacentElement('afterbegin',pre);
    ajustarElementos();
}

//ajusta posicion de elementos en cada resize
function ajustarElementos(){
	var w = window.innerWidth
	|| document.documentElement.clientWidth
	|| document.body.clientWidth;
	
	var section=document.getElementById("listado");
	var desplazamiento = w/2 - 115;
	section.style.paddingLeft = desplazamiento+"px";
	
	generarGrafico();
	
	var h = window.innerHeight
	|| document.documentElement.clientHeight
	|| document.body.clientHeight;
	
	var extra = 120;
	if (h/(w/24) > 0)
		extra += h/(w/24);
	
	var section2=document.getElementById("filtrar");
	var desplazamiento2 = w/2 - 33;
	section2.style.marginLeft = desplazamiento2+"px";
	section2.style.marginTop = extra+"px";
	
	var section3=document.getElementById("primero");
	var desplazamiento3 = w/2 - 112;
	section3.style.marginLeft = desplazamiento3+"px";
	section3.style.marginTop = extra+"px";
	
	if (w < 650)
		document.getElementById("siteConstruct").width = w;
	
}

function incrementar(id){ //INCREMENTA EL CONTADOR DEL PRODUCTO ASOCIADO A ESA ID EN LA PESTAÑA EN CASA
    var pre=document.getElementById(id+"count");
    var numero=parseInt(pre.innerHTML.toString(),10)
    numero++;
    
    var label=numero.toString();
    pre.innerHTML = label;
}

function decrementar(id){//DECREMENTA EL CONTADOR DEL PRODUCTO ASOCIADO A ESA ID EN LA PESTAÑA EN CASA
    
    var pre=document.getElementById(id+"count");
    var numero=parseInt(pre.innerHTML.toString(),10);
    numero--;
    var label=numero.toString();
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

    var separador=document.getElementById("separadorEnCasa");
    var sectionNueva=document.createElement('section');
    sectionNueva.id="listado";
    separador.insertAdjacentElement('beforebegin',sectionNueva);
	
	//consultar productos a_reciclar de usuario
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/cargarProductosUsuario";
	http.open("GET", url, true);
	
	http.onreadystatechange = function() {
	    if( http.readyState == 4 && http.status == 200) {
	    	//parseo la respuesta
	    	var arr = JSON.parse(this.responseText);
	    	var infoReciclado={};
	    	for(i = 0; i < arr.length; i++){
	    		var item = "item"+i;
	    		infoReciclado[item] ={
	    		    categoria : arr[i]["categoria"],
	    		    cantidad :  arr[i]["cantidad"],
	    		  }
	    	}
	        for(const key in infoReciclado){
                    const element = infoReciclado[key];
                    crearElementoListado(element.categoria,element.cantidad);
	        }
	    }
	}
	http.send();

	generarGrafico();
	cargarCategorias();
}

function cargarCategorias(){
	var http2 = new XMLHttpRequest();
	var url = servicio+"reciclapp/cargarCategorias";
	
	http2.open("GET", url, true);
	
	http2.onreadystatechange = function() {
	    if(http2.readyState == 4 && http2.status == 200) {
	    	//parseo la respuesta
	    	var arr = JSON.parse(this.responseText);

	    	for(i = 0; i < arr.length; i++){
	    		var option = document.createElement("option");
	    		option.value = arr[i]["categoria"];
	    		option.text = arr[i]["categoria"];
	    		document.getElementById("brandsMulti").appendChild(option);
	    	}
		    let selectBox2 = null;
			
            selectBox2 = new vanillaSelectBox("#brandsMulti", { "maxHeight": 200, "search": true });
            let zone = document.getElementById("demo-multiple");
            buttons = zone.querySelector(".btns-active");
            //buttons.style.display = "block";
            buttons = zone.querySelector(".btns-inactive");
            //buttons.style.display = "none";
            

    	    selectBox2.setValue("all");
	    }
	}
	http2.send();
}

function generarGrafico(){	
	document.getElementById("filtrar").style.display = "block";
	document.getElementById("primero").style.display = "none";
	document.getElementById("piechart").style.display = "block";
	//consultar historico de usuario
	var http2 = new XMLHttpRequest();
	var url = servicio+"reciclapp/recuperarDatos";
	var fechaInicio = document.getElementById("fechaInicio").value;
	var fechaFin = document.getElementById("fechaFin").value;
	
    let materiales = [];
    var id = "brandsMulti";
    let collection = document.querySelectorAll("#" + id + " option");
    collection.forEach(function (x) {
        if (x.selected) {
            materiales.push(x.value);
        }
    });

	http2.open("POST", url, true);
	http2.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http2.onreadystatechange = function() {
	    if(http2.readyState == 4 && http2.status == 200) {
	    	//parseo la respuesta
	    	var arr = JSON.parse(this.responseText);
	    	var infoReciclado={};
	        infoReciclado['Graficos'] = { //PESTAÑA GRAFICOS
	            "graph":[
	                ['Material', 'Cantidad'],
	              ],
	            "title":"Historial de materiales reciclados"
	        }
	    	for(i = 0; i < arr.length; i++){
	    		var item = arr[i]["nameProducto"];
	    		var cantidad = arr[i]["cantidad"];
	    		infoReciclado.Graficos['graph'].push([item, cantidad]);
	    	}
	    	/*infoReciclado = {
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
	    	}*/
	    	
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {
                var jsonString=JSON.stringify(infoReciclado);
                const obj= JSON.parse(jsonString);
                //console.log(obj);
                var data = google.visualization.arrayToDataTable(obj.Graficos.graph);
                var options = {
                    title: obj.Graficos.title,
                    is3D: true,
                    //width:'30px',
                    //chartArea:{right:0, bottom:0,width:'80vw',height:'100vh'},
                    legend:{position: 'bottom', textStyle: {color: 'blue',alignment:'center'}},
                    backgroundColor:'#EAECEA',
//                  legend:{position: 'absolute'},
                    /*legend: {
                        position: 'labeled',

                    },*/
                    
                };
                var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                chart.draw(data, options);
                
            }
            document.getElementById("primero").style.position = "absolute";
	    }
	}
	http2.send(JSON.stringify({fechaInicio:fechaInicio, fechaFin:fechaFin, materiales:materiales}));
}

//accion de boton de filtro
function mostrarMenuFiltro(){
	document.getElementById("filtrar").style.display = "none";
	document.getElementById("primero").style.display = "block";
	document.getElementById("piechart").style.display = "none";
}

function getDate(){
    var today = new Date();
    var inicio = new Date( 2015 , 1, 1, 00, 00);
    document.getElementById("fechaFin").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
    document.getElementById("fechaInicio").value = inicio.getFullYear() + '-' + ('0' + (inicio.getMonth() + 1)).slice(-2) + '-' + ('0' + inicio.getDate()).slice(-2);

}

// Turn off debug output
window.zuixNoConsoleOutput = true;

async function confirmarProducto(){ //Se actualiza la pestania "EN CASA" y se envia una POST request al servicio
	if ( accion === true)
		return;
	
    var cantidad=document.getElementById('count');//OBTENGO LA CANTIDAD CARGADA POR EL USUARIO
    if(subcatSeleccionada!=null){
        
        var cantActual=0;
        if(datosUsuario[subcatSeleccionada]!=null)
            cantActual=parseInt(datosUsuario[subcatSeleccionada]['cantidad'],10);
        let aReciclar = {
        id_prod: obj[categoriaSeleccionada].subCats[subcatSeleccionada].representante,
        id_user: 100,
        cantidad: parseInt(cantidad.value,10)
        };
        var url = servicio+"reciclapp/guardarReciclable";
        
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

    document.getElementById("message").style.visibility = "visible";
    document.getElementById("message").style.backgroundColor = "#017396";
	document.getElementById("message").innerHTML="Producto Almacenado";
	accion = true;
	setTimeout(function(){
		accion = false;
		document.getElementById("message").style.visibility = "hidden";
		salirForm();
	}, 2500);
}



/**/
function vanillaSelectBox(domSelector, options) {
    let self = this;
    this.domSelector = domSelector;
    this.root = document.querySelector(domSelector)
    this.main;
    this.button;
    this.title;
    this.isMultiple = this.root.hasAttribute("multiple");
    this.multipleSize = this.isMultiple && this.root.hasAttribute("size") ? parseInt(this.root.getAttribute("size")) : -1;
    this.drop;
    this.top;
    this.left;
    this.options;
    this.listElements;
    this.isDisabled = false;
    this.search = false;
    this.searchZone = null;
    this.inputBox = null;
    this.ulminWidth = 140;
    this.ulminHeight = 25;
    this.userOptions = {
        maxWidth: 500,
        maxHeight: 400,
        translations: { "all": "All", "items": "items" },
        search: false,
        placeHolder: "",
		stayOpen:false
    }
    if (options) {
        if (options.maxWidth != undefined) {
            this.userOptions.maxWidth = options.maxWidth;
        }
        if (options.maxHeight != undefined) {
            this.userOptions.maxHeight = options.maxHeight;
        }
        if (options.translations != undefined) {
            this.userOptions.translations = options.translations;
        }
        if (options.placeHolder != undefined) {
            this.userOptions.placeHolder = options.placeHolder;
        }
        if (options.search != undefined) {
            this.search = options.search;
        }
		if (options.stayOpen != undefined) {
            this.userOptions.stayOpen = options.stayOpen;
        }
    }

    this.init();
}

    vanillaSelectBox.prototype.init = function () {
		let self = this;
        this.root.style.display = "none";
        let already = document.getElementById("btn-group-" + self.domSelector);
        if (already) {
            already.remove();
        }
        this.main = document.createElement("div");
        this.root.parentNode.insertBefore(this.main, this.root.nextSibling);
        this.main.classList.add("vsb-main");
        this.main.setAttribute("id", "btn-group-" + this.domSelector);
        this.main.style.marginLeft = this.main.style.marginLeft;
		if(self.userOptions.stayOpen){
			this.main.style.minHeight =  (this.userOptions.maxHeight+10) + "px";
		}

		let btnTag = self.userOptions.stayOpen ? "div" : "button";
        this.button = document.createElement(btnTag);
		
        let presentValue = this.main.value;
        this.main.appendChild(this.button);
        this.title = document.createElement("span");
        this.button.appendChild(this.title);
        this.title.classList.add("title");
        let caret = document.createElement("span");
        this.button.appendChild(caret);
        caret.classList.add("caret");
        caret.style.position = "absolute";
        caret.style.right = "8px";
        caret.style.marginTop = "8px";
		if(self.userOptions.stayOpen){
			caret.style.display = "none";
			this.title.style.paddingLeft = "20px";
			this.title.style.fontStyle = "italic";
			this.title.style.verticalAlign = "20%";
		}
        let rect = this.button.getBoundingClientRect();
        this.top = rect.bottom;
        this.left = rect.left;;
        this.drop = document.createElement("div");
        this.main.appendChild(this.drop);
        this.drop.classList.add("vsb-menu");
        let ul = document.createElement("ul");
        this.drop.appendChild(ul);

        ul.style.maxHeight = this.userOptions.maxHeight + "px";
        ul.style.minWidth = this.ulminWidth + "px";
        ul.style.minHeight = this.ulminHeight + "px";
        if (this.isMultiple) {
            ul.classList.add("multi");
        }
        let selectedTexts = ""
        let sep = "";
        let nrActives = 0;
		
        if (this.search) {
            this.searchZone = document.createElement("div");
            ul.appendChild(this.searchZone);
            this.searchZone.classList.add("vsb-js-search-zone");

            this.inputBox = document.createElement("input");
            this.searchZone.appendChild(this.inputBox);
            this.inputBox.setAttribute("type", "text");
            this.inputBox.setAttribute("id", "search_" + this.domSelector);

            let fontSizeForP = this.isMultiple ? "8px" : "6px";
            var para = document.createElement("p");
            ul.appendChild(para);
            para.style.fontSize = fontSizeForP;
            para.innerHTML = "&nbsp;";
            ul.addEventListener("scroll", function (e) {
                var y = this.scrollTop;
				console.log(e);
                self.searchZone.parentNode.style.top = y + "px";
            });
        }
        this.options = document.querySelectorAll(this.domSelector + " option");
        Array.prototype.slice.call(this.options).forEach(function (x) {
            let text = x.textContent;
            let value = x.value;
            let classes = x.getAttribute("class");
			if(classes) 
				{
					classes=classes.split(" ");
				}
			else
				{
					classes=[];
				}
            let li = document.createElement("li");
            let isSelected = x.hasAttribute("selected");
            ul.appendChild(li);
            li.setAttribute("data-value", value);
            li.setAttribute("data-text", text);
            if (classes.length != 0) {
				classes.forEach(function(x){
					li.classList.add(x);
				});
                
            }
            if (isSelected) {
                nrActives++;
                selectedTexts += sep + text;
                sep = ",";
                li.classList.add("active");
                if (!self.isMultiple) {
                    self.title.textContent = text;
					if (classes.length != 0) {
						classes.forEach(function(x){
							self.title.classList.add(x);
						});
                    }
                }
            }
            li.appendChild(document.createTextNode(text));
        });
        if (self.multipleSize != -1) {
            if (nrActives > self.multipleSize) {
                let wordForItems = self.userOptions.translations.items || "items"
                selectedTexts = nrActives + " " + wordForItems;
            }
        }
        if (self.isMultiple) {
            self.title.innerHTML = selectedTexts;
        }
        if (self.userOptions.placeHolder != "" && self.title.textContent == "") {
            self.title.textContent = self.userOptions.placeHolder;
        }
        this.listElements = this.drop.querySelectorAll("li");
        if (self.search) {
            self.inputBox.addEventListener("keyup", function (e) {
                let searchValue = e.target.value.toUpperCase();
                let searchValueLength = searchValue.length;
                if (searchValueLength < 2) {
                    Array.prototype.slice.call(self.listElements).forEach(function (x) {
                        x.classList.remove("hide");
                    });
                } else {
                    Array.prototype.slice.call(self.listElements).forEach(function (x) {
                        let text = x.getAttribute("data-text").toUpperCase();
                        if (text.indexOf(searchValue) == -1) {
                            x.classList.add("hide");
                        } else {
                            x.classList.remove("hide");
                        }
                    });
                }
            });
        }
		
		if(self.userOptions.stayOpen){
            self.drop.style.display = "block";	
			self.drop.style.boxShadow = "none";
			self.drop.style.minHeight =  (this.userOptions.maxHeight+10) + "px";
			self.drop.style.position = "relative";
			self.drop.style.left = "0px";
			self.drop.style.top = "0px";
			self.button.style.border = "none";
		}else{
			this.main.addEventListener("click", function (e) {
				if (self.isDisabled) return;
				self.drop.style.left = self.left + "px";
				self.drop.style.top = self.top + "px";
				self.drop.style.display = "block";
				document.addEventListener("click", docListener);
				e.preventDefault();
				e.stopPropagation();
				});
		}
        this.drop.addEventListener("click", function (e) {
            if (!e.target.hasAttribute("data-value")) {
                e.preventDefault();
                e.stopPropagation();
                return;
            }
            let choiceValue = e.target.getAttribute("data-value");
            let choiceText = e.target.getAttribute("data-text");
            let className = e.target.getAttribute("class");
            if (!self.isMultiple) {
                self.root.value = choiceValue;
                self.title.textContent = choiceText;
                if (className) {
                    self.title.setAttribute("class", className + " title");
                } else {
                    self.title.setAttribute("class", "title");
                }
                Array.prototype.slice.call(self.listElements).forEach(function (x) {
                    x.classList.remove("active");
                });
                if (choiceText != "") {
                    e.target.classList.add("active");
                }
                self.privateSendChange();
				if(!self.userOptions.stayOpen){
					docListener();
				}
            } else {
                let wasActive = false;
                if (className) {
                    wasActive = className.indexOf("active") != -1;
                }
                if (wasActive) {
                    e.target.classList.remove("active");
                } else {
                    e.target.classList.add("active");
                }
                let selectedTexts = ""
                let sep = "";
                let nrActives = 0;
                let nrAll = 0;
                
                for (let i = 0; i < self.options.length; i++) {
                    nrAll++;
                    if (self.options[i].value == choiceValue) {
                        self.options[i].selected = !wasActive;
                    }
                    if (self.options[i].selected) {
                        nrActives++;
                        selectedTexts += sep + self.options[i].textContent;
                        sep = ",";

                        if ( self.options[i].value == "All"){
                        	self.setValue("all");
                        	self.options[i].selected = false;
                        }
                    }
                }
                if (nrAll == nrActives) {
                    let wordForAll = self.userOptions.translations.all || "all";
                    selectedTexts = wordForAll;
                } else if (self.multipleSize != -1) {
                    if (nrActives > self.multipleSize) {
                        let wordForItems = self.userOptions.translations.items || "items"
                        selectedTexts = nrActives + " " + wordForItems;
                    }
                }
                self.title.textContent = selectedTexts;
                self.privateSendChange();
            }
            e.preventDefault();
            e.stopPropagation();
            if (self.userOptions.placeHolder != "" && self.title.textContent == "") {
                self.title.textContent = self.userOptions.placeHolder;
            }
        });
        function docListener() {
            document.removeEventListener("click", docListener);
            self.drop.style.display = "none";
			if(self.search){
				self.inputBox.value = "";
				Array.prototype.slice.call(self.listElements).forEach(function (x) {
                   x.classList.remove("hide");
                });
			}
        }
    }

    vanillaSelectBox.prototype.setValue = function (values) {
		let self = this;
        if (values == null || values == undefined || values == "") {
            self.empty();
        } else {
            if (self.isMultiple) {
                if (type(values) == "string") {
                    if (values == "all") {
                        values = [];
                        Array.prototype.slice.call(self.options).forEach(function (x) {
                            values.push(x.value);
                        });
                    } else {
                        values = values.split(",");
                    }
                }
                let foundValues = [];
                if (type(values) == "array") {
                    Array.prototype.slice.call(self.options).forEach(function (x) {
                        if (values.indexOf(x.value) != -1) {
                            x.selected = true;
                            foundValues.push(x.value);
                        } else {
                            x.selected = false;
                        }
                    });
                    let selectedTexts = ""
                    let sep = "";
                    let nrActives = 0;
                    let nrAll = 0;
                    Array.prototype.slice.call(self.listElements).forEach(function (x) {
                        nrAll++;
                        if (foundValues.indexOf(x.getAttribute("data-value")) != -1) {
                            x.classList.add("active");
                            nrActives++;
                            selectedTexts += sep + x.getAttribute("data-text");
                            sep = ",";
                        } else {
                            x.classList.remove("active");
                        }
                    });
                    if (nrAll == nrActives) {
                        let wordForAll = self.userOptions.translations.all || "all";
                        selectedTexts = wordForAll;
                    } else if (self.multipleSize != -1) {
                        if (nrActives > self.multipleSize) {
                            let wordForItems = self.userOptions.translations.items || "items"
                            selectedTexts = nrActives + " " + wordForItems;
                        }
                    }
                    self.title.textContent = selectedTexts;
                    self.privateSendChange();
                }
            } else {
                let found = false;
                let text = "";
                let classNames = ""
                Array.prototype.slice.call(self.listElements).forEach(function (x) {
                    if (x.getAttribute("data-value") == values) {
                        x.classList.add("active");
                        found = true;
                        text = x.getAttribute("data-text")
                    } else {
                        x.classList.remove("active");
                    }
                });
                Array.prototype.slice.call(self.options).forEach(function (x) {
                    if (x.value == values) {
                        x.selected = true;
                        className = x.getAttribute("class");
                        if (!className) className = "";
                    } else {
                        x.selected = false;
                    }
                });
                if (found) {
                    self.title.textContent = text;
                    if (self.userOptions.placeHolder != "" && self.title.textContent == "") {
                        self.title.textContent = self.userOptions.placeHolder;
                    }
                    if (className != "") {
                        self.title.setAttribute("class", className + " title");
                    } else {
                        self.title.setAttribute("class", "title");
                    }
                }
            }
        }
        function type(target) {
            const computedType = Object.prototype.toString.call(target);
            const stripped = computedType.replace("[object ", "").replace("]", "");
            const lowercased = stripped.toLowerCase();
            return lowercased;
        }
    }

    vanillaSelectBox.prototype.privateSendChange = function () {
        let event = document.createEvent('HTMLEvents');
        event.initEvent('change', true, false);
        this.root.dispatchEvent(event);
    
	}

	vanillaSelectBox.prototype.empty = function () {
        Array.prototype.slice.call(this.listElements).forEach(function (x) {
            x.classList.remove("active");
        });
        Array.prototype.slice.call(this.options).forEach(function (x) {
            x.selected = false;
        });
        this.title.textContent = "";
        if (this.userOptions.placeHolder != "" && this.title.textContent == "") {
            this.title.textContent = this.userOptions.placeHolder;
        }
        this.privateSendChange();
    }
	
    vanillaSelectBox.prototype.destroy = function () {
        let already = document.getElementById("btn-group-" + this.domSelector);
        if (already) {
            already.remove();
            this.root.style.display = "inline-block";
        }
    }
    vanillaSelectBox.prototype.disable = function () {
        let already = document.getElementById("btn-group-" + this.domSelector);
        if (already) {
            button = already.querySelector("button")
            button.classList.add("disabled");
            this.isDisabled = true;
        }
    }
    vanillaSelectBox.prototype.enable = function () {
        let already = document.getElementById("btn-group-" + this.domSelector);
        if (already) {
            button = already.querySelector("button")
            button.classList.remove("disabled");
            this.isDisabled = false;
        }
    }

vanillaSelectBox.prototype.showOptions = function(){
	console.log(this.userOptions);
}

if (!('remove' in Element.prototype)) {
    Element.prototype.remove = function () {
        if (this.parentNode) {
            this.parentNode.removeChild(this);
        }
    };
}
