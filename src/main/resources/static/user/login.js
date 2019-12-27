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

function Suscribirse(tokenn){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/suscribir";
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	http.send(JSON.stringify({token:tokenn}));
}

function Notificar(){
	var http = new XMLHttpRequest();
	var url = servicio+"reciclapp/notificar";
	http.open("POST", url, true);
	http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	http.send(JSON.stringify({title:"Bienvenido a Reciclapp!",body:"Esta es una notificacion de prueba"}));
}

firebase.initializeApp({
    'messagingSenderId': '37344328198'
})

navigator.serviceWorker.register('../reciclapp/firebase-messaging-sw.js')
.then((registration) => {
  messaging.useServiceWorker(registration);

});
const messaging = firebase.messaging();

messaging.requestPermission().then(function () {
    console.log(messaging.getToken());
    messaging.getToken().then((currentToken) => {
        if (currentToken) {
            console.log(currentToken);
            Suscribirse(currentToken);
            setTimeout(Notificar(), 10000);
        }
        }).catch((err) => {
            console.log('An error occurred while retrieving token. ', err);
        });
    
        return messaging.getToken();
    })
    .then(function (token) {
    	
    })
    .catch(function (err) {
        console.log("Didn't get notification permission", err);
    });

messaging.onMessage(function (payload) {
    console.log("Message received. ", JSON.stringify(payload));
});

messaging.onTokenRefresh(function () {
    messaging.getToken().then(function (refreshedToken) {
        console.log('Token refreshed.');
    }).catch(function (err) {
        console.log('Unable to retrieve refreshed token ', err);
    });
});
