reciclAppRest

Descargar el repositorio
Puede correrlo con una herramienta como Spring Tool Suite/Eclipse
Una vez exportado el proyecto se actualizaran y descargaran las dependencias necesarias

Para correr el proyecto usted debe hacer run el archivo src/main/java/com.example.demo2/Demo2Application.java

Si desea exportar el proyecto en un .jar es necesario tener instalado Maven en su maquina y correr las instrucciones :
$mvn compile
$mvn package

esto generara un jar en la carpeta "target".

-------------
DOCKER
-------------

Para correr el archivo .jar con docker es necesario

Crear un archivo "Dockerfile" en la carpeta del proyecto con:

-------------------------
CONTENIDO DEL Dockerfile
-------------------------

FROM openjdk:8-jdk-alpine

ENV EXPOSED_PORT 8080

ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} demo.jar

EXPOSE $EXPOSED_PORT

ENTRYPOINT ["java","-jar","/demo.jar"]

------------------------------------
------------------------------------

Correr la instruccion:
$sudo docker build ./

Con la salida de la instruccion anterior correr:
$sudo docker create --name NameContainer -p 8080:8080 *salida_comando_anterior*

Finalmente lo corremos con:
$sudo docker start NameContainer





-------------------------------------
Mas información en https://docs.docker.com

-------------------------------------

Este proyecto se encuentra configurado para trabajar en localhost, en el puerto 8080.
Para cambiar esta configuración es necesario cambiar la ruta en el archivo "conexion.js" y en el caso que sea la maquina servidor 
para donde se diseño , se requiere habilitar el certificado SSL en el documento "aplication.properties" descomentando las lineas correspondientes.



