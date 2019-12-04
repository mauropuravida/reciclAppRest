function Login(){
	
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/validate.html";
	var user = document.getElementById("user").value;
	var pass = document.getElementById("pass").value;
	
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.send(JSON.stringify({user:user, pass: pass}));
	
	http.onreadystatechange = function() {
	    if(http.status == 200) {
	    	location="../reciclapp/index.html";
	    }
	    else{
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").innerHTML="Datos invalidos!";
	    	setTimeout(function(){document.getElementById("message").style.visibility = "hidden";}, 4*1000);
	    }
	}
}