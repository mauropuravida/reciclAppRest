

function Login(){

	var http = new XMLHttpRequest();
	var url = "http://localhost:8080/validate.html";
	var user = document.getElementById("user").value;
	var pass = document.getElementById("pass").value;
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	http.onreadystatechange = function() {
	    if(http.readyState == 4 && http.status == 200) {
	    	window.location="../index.html";
	    }
	    else
	    	window.location="../login.html";
	}
	http.send(JSON.stringify({user:user, pass: pass}));
}