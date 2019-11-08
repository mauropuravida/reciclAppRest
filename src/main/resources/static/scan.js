let json;
//const servicio="http://192.168.2.51:8080/";// ESTA TIENE QUE SER LA IP y puerto DEL SERVICIO REST
const servicio="https://f65b04bf.ngrok.io/"
function salirFormScan(){
    window.location.href='#';
}

async function confirmar(){//ENVIA LA POST REQUEST al servidor con el producto que se reciclo
    salirFormScan();
    restartQuagga();
    //
    //SE LE PEGARIA AL SERVIDOR CON LO ELEGIDO
    //
    
    let aReciclar = {
        id_prod: json.id_prod,
        id_user: 1030,
        cantidad: 50//countScan.value
      };
      var url=servicio+"guardarReciclable";
      let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', //ESTO SOLUCIONA EL CORS (CROSS ORIGIN RESOURCE SHARING)
            'mode': 'no-cors'
        },
        body: JSON.stringify(aReciclar)
      });
}

function quit(){ //Se ejecuta al cancelar y se sale de la form.
    salirFormScan();
    restartQuagga();
}

function incrementarInput(){//Incrementa el contador de la form al cargar un producto
    countScan.value++;
}

function decrementarInput(){//Decrementa el contador de la form al cargar un producto
    if(countScan.value!=1)
        countScan.value--;
}



async function entrarFormScan(codigo){ //SE ENVIA UNA GET REQUEST AL SERVIDOR CON EL CODIGO DE BARRAS PARA OBTENER INFORMACION DEL PRODUCTO
    var url=servicio;
    url+="codigo/"+codigo;
    var sectionVieja=document.getElementById('ProductoScanned');
    if(sectionVieja!=null)
        sectionVieja.remove();
    let response = await fetch(url);

    if (response.ok) { // if HTTP-status is 200-299
        // get the response body (the method explained below)
        console.log("PRODUCTO DETECTADO");
        json = await response.json();
        console.log(json);

        window.location.href='#formCodigoDetectado';
        const referencia=document.getElementById("scanText");
        var sectionNueva=document.createElement('section');
        sectionNueva.id='ProductoScanned';

        var p=document.createElement('p');// MUESTRO EL NOMBRE DEL PRODUCTO
        p.innerHTML=json.descripcion;
        if(json.descripcion!=null)
        sectionNueva.appendChild(p);
        var p2=document.createElement('p');//MUESTRO LA CATEGORIA DEL PRODUCTO
        p2.innerHTML=json.categoria;
        sectionNueva.appendChild(p2);
        referencia.insertAdjacentElement('beforeend',sectionNueva);
    } else {
        entrarFormDesconocido();
    }
}

function entrarFormDesconocido(){ //ESTA form se activaria cuando se desconoce el producto
    console.log("El codigo de barras ingresado no esta asociado a un producto valido aun");
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
     console.log("Initialization finished. Ready to start");
     Quagga.start();
 });
}
function loadFormProductoDesconocido(){
    window.location.href = '#formCargarProductoDesconocido';
    // Hay que ver como acceder al JSON que estoy usando en index.js. Ese json es el que me devolveria el servicio REST en una request
    // al abrir la web. Esto se hace asi porque se cargan los botones a partir de ese json y no estaticamente. Esto esta bueno por si 
    //hay que agregar nuevas categorias

    //
    // var id_prod_generico = get_id_prod (subcategoria elegida por el usuario) <-- esta es una request del servicio REST
    // Una vez que obtengo un id_prod que represente el tamaño acorde a la categoria que eligio el usuario debo hacer 2 POST requests
    // El orden no importa. 
    // La primera seria :
    // @PostMapping(value = "/guardarReciclable", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    // La segunda
    //@PostMapping(value = "/guardarProdDesc", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})

}