let json;

function salirFormScan(){
    window.location.href='#';
}

async function confirmar(id){//ENVIA LA POST REQUEST al servidor con el producto que se reciclo
	
    let aReciclar;
    var url;
    if (json.consenso.toString() == "true"){
    	var e = document.getElementById("categoria");
    	var value = e.selectedIndex;
    	console.log("el barcode es "+json.barcode);
    	
    	aReciclar = {
	        id_prod: Number(value),
	        cantidad: id.value,
	        barcode: json.barcode,
	        descripcion: document.getElementById('description').value,
    		categoria: Number(value),
	    	volumen: document.getElementById('volumen').value,
	    	peso: document.getElementById('peso').value
    	};
    	url=servicio+"reciclapp/guardarReciclableConsenso";
    
    }else{
	    aReciclar = {
	        id_prod: json.id_prod,
	        cantidad: id.value
		};
	    
	    url=servicio+"reciclapp/guardarReciclable";
    }
    
	let response = await fetch(url, {
		method: 'POST',
		headers: {
		    'Content-Type': 'application/json', //ESTO SOLUCIONA EL CORS (CROSS ORIGIN RESOURCE SHARING)
		    'mode': 'no-cors'
		},
	    body: JSON.stringify(aReciclar)
	});
	
	quit();
}

function quit(){ //Se ejecuta al cancelar y se sale de la form.
    salirFormScan();
    restartQuagga();
}

function incrementar(id){//Incrementa el contador de la form al cargar un producto	
	id.value++;
}

function decrementar(id){//Decrementa el contador de la form al cargar un producto
    if(id.value!=1)
    	id.value--;
}

async function entrarFormScan(codigo){ //SE ENVIA UNA GET REQUEST AL SERVIDOR CON EL CODIGO DE BARRAS PARA OBTENER INFORMACION DEL PRODUCTO
    var url=servicio+"reciclapp/codigo/"+codigo;
    var sectionVieja=document.getElementById('ProductoScanned');
    if(sectionVieja!=null)
        sectionVieja.remove();
    let response = await fetch(url);
    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        console.log("PRODUCTO DETECTADO");
        json = await response.json();
        console.log(json);

        var ref;
        if (json.consenso.toString() == "true"){
        	window.location.href='#formConsenso';
        	
        	document.getElementById("barcode").value = json.barcode;
        	document.getElementById("description").value = json.descripcion;
        	document.getElementById("peso").value = json.peso;
        	document.getElementById("volumen").value = json.volumen;
        	cargarCategorias();
        	console.log("dentro de consenso");
        }
        else{
        	console.log("fuera de consenso");
        	window.location.href='#formCodigoDetectado';
        	ref=document.getElementById("scanText1");
        	
            var sectionNueva=document.createElement('section');
            sectionNueva.id='ProductoScanned';

            var p5=document.createElement('p');//MUESTRO EL BARCODE DEL PRODUCTO
            p5.innerHTML="Barcode : "+json.barcode;
            sectionNueva.appendChild(p5);
            var p=document.createElement('p');// MUESTRO EL NOMBRE DEL PRODUCTO
            p.innerHTML="Nombre : "+json.descripcion;
            if(json.descripcion!=null)
            sectionNueva.appendChild(p);
            var p2=document.createElement('p');//MUESTRO LA CATEGORIA DEL PRODUCTO
            p2.innerHTML="Categoria : "+json.categoria;
            sectionNueva.appendChild(p2);
            var p3=document.createElement('p');// MUESTRO EL VOLUMEN DEL PRODUCTO
            p3.innerHTML="Volumen : "+json.volumen;
            if(json.descripcion!=null)
            sectionNueva.appendChild(p3);
            var p4=document.createElement('p');//MUESTRO EL PESO DEL PRODUCTO
            p4.innerHTML="Peso : "+json.peso;
            sectionNueva.appendChild(p4);
            
            ref.insertAdjacentElement('beforeend',sectionNueva);
        }
    } else 
		if (response.status >= 400 && response.status < 600) 
			entrarFormDesconocido();
}

function entrarFormDesconocido(){ //ESTA form se activaria cuando se desconoce el producto
    window.location.href='#formCodigoNoDetectado';
}

function restartQuagga(){//Inicializa Quagga de nuevo para otra lectura de codigo de barras
    Quagga.init({
        inputStream : {
          name : "Live",
          type : "LiveStream",
          constraints: {
             width: w,
             height: h,
             facingMode: "environment" // or user
         },
          target: document.querySelector('#camera')    // Or '#yourElement' (optional)
   },
   decoder : {
     readers : ["ean_reader","code_128_reader","code_39_reader","codabar_reader","upc_reader","i2of5_reader","code_93_reader",{
       format: "ean_reader",
       config: {
           supplements: [
               'ean_5_reader', 'ean_2_reader'
           ]
       }
   }]
   }
 }, function(err) {
     if (err) {
         console.log(err);
         return
     }
     console.log("Initialization finished. Ready to start js");
     Quagga.start();
 });
}

async function cargarCategorias(){
	var http2 = new XMLHttpRequest();
	var url = servicio+"reciclapp/cargarCategorias";
	
	http2.open("GET", url, true);
	
	http2.onreadystatechange = function() {
	    if(http2.readyState == 4 && http2.status == 200) {
	    	//parseo la respuesta
	    	var arr = JSON.parse(this.responseText);
	    	document.getElementById("categoria").options.length = 0;

	    	for(i = 0; i < arr.length; i++){
	    		var option = document.createElement("option");
	    		option.value = arr[i]["categoria"];
	    		option.text = arr[i]["categoria"];
	    		
	    		var c = arr[i]["categoria"];
	    		if( c == json.categoria){
	    			option.selected = true;
	    		}
	    		
	    		document.getElementById("categoria").appendChild(option);
	    	}
	    }
	}
	http2.send();
}