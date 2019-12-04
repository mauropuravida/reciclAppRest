function Change(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/requestCode";
	var user = document.getElementById("user").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "#017396";
	    	document.getElementById("message").innerHTML="Verifique su email!";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 6*1000);
	    	
	    }
	    else{
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "red";
	    	document.getElementById("message").innerHTML="El usuario no existe";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 4*1000);
	    }
	}
	http.send(JSON.stringify({user:user}));
}

function changePass(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/changeOldPass";
	var user = document.getElementById("user").value;
	var token = document.getElementById("code").value;
	var pass = document.getElementById("pass").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "#017396";
	    	document.getElementById("message").innerHTML="Contraseña cambiada con exito!";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 6*1000);
	    }
	    else{
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "red";
	    	document.getElementById("message").innerHTML="Los datos fueron erroneos";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 4*1000);
	    }
	}
	http.send(JSON.stringify({user:user, token:token, pass:pass}));
}

function Back(){
  window.location="../reciclapp/login.html";
}