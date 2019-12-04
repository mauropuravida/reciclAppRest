function Create(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/createU";
	var name = document.getElementById("nombre").value;
	var sername = document.getElementById("apellido").value;
	var email = document.getElementById("email").value;
	var addres = document.getElementById("direccion").value;
	var user = document.getElementById("usuario").value;
	var pass = document.getElementById("pass").value;
	
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
	http.send(JSON.stringify({username:user, password: pass, nombre: name, apellido: sername, address: addres, mail: email}));
}
  
function Back(){
	window.location="../reciclapp/login.html";
}

function checkM(){
	var email1 = document.getElementById("email").value;
	var email2 = document.getElementById("checkMa").value;
	
	if (email1 != email2){
		document.getElementById("message").style.visibility = "visible";
    	document.getElementById("message").style.backgroundColor = "red";
    	document.getElementById("message").innerHTML="El email no coincide";
	}
	else{
		document.getElementById("message").style.visibility = "hidden";
	}
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
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "red";
	    	document.getElementById("message").innerHTML="El email ya esta en uso";
	    }
	}
	http.send(JSON.stringify({mail:mail}));
}

function checkUser(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/checkUser.html";
	var user = document.getElementById("usuario").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	document.getElementById("message").style.visibility = "hidden";
	    }
	    else{
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "red";
	    	document.getElementById("message").innerHTML="El usuario ya esta en uso";
	    }
	}
	http.send(JSON.stringify({user:user}));
}