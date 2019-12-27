function Create(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/createU";
	var name = document.getElementById("nombre").value;
	var sername = document.getElementById("apellido").value;
	var email = document.getElementById("email").value;
	var addres = document.getElementById("direccion").value;
	var pass = document.getElementById("pass").value;
	var resp = grecaptcha.getResponse();
	
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "#017396";
	    	document.getElementById("message").innerHTML="La cuenta ha sido creada";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 4*1000);
	    }
	    else{
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "red";
	    	document.getElementById("message").innerHTML="Los campos son incorrectos";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 4*1000);
	    }
	}
	http.send(JSON.stringify({response: resp, password: pass, nombre: name, apellido: sername, address: addres, username: email}));
}
  
function Back(){
	window.location="../reciclapp/login.html";
}

function checkC(){
	var email1 = document.getElementById("pass").value;
	var email2 = document.getElementById("passCh").value;
	
	if (email1 != email2){
		document.getElementById("message").style.visibility = "visible";
    	document.getElementById("message").style.backgroundColor = "red";
    	document.getElementById("message").innerHTML="La clave no coincide";
	}
	else{
		document.getElementById("message").style.visibility = "hidden";
	}
}

function checkMe(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/checkEmail.html";
	var mail = document.getElementById("email").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	document.getElementById("message").style.visibility = "hidden";
	    }
	    else{
	    	if (http.readyState == 4 && http.status == 409){
		    	document.getElementById("message").style.visibility = "visible";
		    	document.getElementById("message").style.backgroundColor = "red";
		    	document.getElementById("message").innerHTML="El email ya esta en uso";
	    	}
	    	else
	    		{
		    		document.getElementById("message").style.visibility = "visible";
			    	document.getElementById("message").style.backgroundColor = "red";
			    	document.getElementById("message").innerHTML="Formato de email invalido";
	    		}
	    }
	}
	http.send(JSON.stringify({mail:mail}));
}

function checkAddress(){
    var openStreetMapGeocoder = GeocoderJS.createGeocoder('openstreetmap');
    openStreetMapGeocoder.geocode('R. de Castro 830, Tandil,Argentina', function(result) {
      console.log(result);
    });

    /*openStreetMapGeocoder.geodecode("44.915", "-93.21", function(result) {
      console.log(result);
    });*/
}
