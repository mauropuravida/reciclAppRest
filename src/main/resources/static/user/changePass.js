function Change(){
	var http = new XMLHttpRequest();
	var url = servicio+"requestCode";
	var user = document.getElementById("user").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	document.getElementById("message").style.visibility = "visible";
	    	document.getElementById("message").style.backgroundColor = "green";
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

function Back(){
  window.location="../login.html";
}