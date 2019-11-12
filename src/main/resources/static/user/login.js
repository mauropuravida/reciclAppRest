

function Login(){

	var http = new XMLHttpRequest();
	var url = servicio+"validate.html";
	var user = document.getElementById("user").value;
	var pass = document.getElementById("pass").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	window.location="../index.html";
	    }
	    else{
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").innerHTML="El usuario y/o la contraseña son invalidos";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 4*1000);
	    }
	}
	http.send(JSON.stringify({user:user, pass: pass}));
}
