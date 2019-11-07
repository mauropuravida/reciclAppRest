package com.example.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AReciclar;
import com.example.model.Categoria;
import com.example.model.Producto;
import com.example.model.Producto_Desconocido;
import com.example.model.Usuario;
import com.example.service.AReciclarServicioInterface;
import com.example.service.CategoriaServicioInterface;
import com.example.service.ProductoDesconocidoServicioInterface;
import com.example.service.ProductoServicioInterface;
import com.example.service.UsuarioServicioInterface;
 
@RestController
@RequestMapping (path ="/")
public class WebController {
  
  @Autowired
  ProductoServicioInterface servicio;
  
  @Autowired
  ProductoDesconocidoServicioInterface servicioProdDesc;

  @Autowired
  CategoriaServicioInterface servicioCategoria;
  
  @Autowired
  AReciclarServicioInterface servicioAReciclar;
  
  @Autowired
  UsuarioServicioInterface servicioUsuario;
  
  @GetMapping
  public String check(){ //Mensaje que muestra al iniciar
	  return "Funcionando...";
  }
  
  @GetMapping(path = "/guardarConParam/{id_prod},{barcode},{descripcion},{categoria}") //Guardar un producto en Producto harcodeado
  public String unProducto(@PathVariable(value="id_prod") long id_prod,@PathVariable(value="barcode") String barcode,@PathVariable(value="descripcion") String desc,@PathVariable(value="categoria") String categ){
	  servicio.save(new Producto(id_prod,barcode, desc,categ));
	  return "Se guardo el producto";
  }
	  
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(path = "/findall") //Halla todos los productos de la tabla Productos
	public List<Producto> findAll(){
	  return servicio.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(path = "/findbyid") //Halla un producto de la tabla Productos por id
	public String findById(@RequestParam("id") long id){
	  return servicio.findById(id).toString();
	}
	  
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/codigo/{barcode}") //Halla un producto de la tabla Productos por barcode
	public ResponseEntity<Producto> getEmployeeById(@PathVariable(value="barcode") String barcode){
		Producto prod=servicio.findByBarcode(barcode);
		if(prod==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(prod);
	}
	
	/*
	.header("Access-Control-Allow-Origin", "*")
	.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	.allow("OPTIONS").build();
	*/
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/create", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda un producto en la tabla Productos de un json que viene 
	public String guardarProducto(@RequestBody Producto prod) {
	    servicio.save(prod);
	    return "Se guardo un producto en la tabla Productos";
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/guardarProdDesc", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda un producto en la tabla Producto_desconocido de un json que viene 
	public String guardarProductoDesconocido(@RequestBody Producto_Desconocido prod) {
	    servicioProdDesc.save(prod);
	    return "Se guardo un producto en la tabla Productos_Desconocido";
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/guardarReciclable", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda una tupla en la tabla A_Reciclar de un json que viene 
	public String ReciclarUsuarioProducto(@RequestBody AReciclar reciclable) {
	    servicioAReciclar.save(reciclable);
	    return "Se guardo un producto en la tabla A_Reciclar";
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/categoria/{categoria}") //Halla el id_prod de la tabla categoria dado una categoria
	public long getIdProd(@PathVariable(value="categoria") String categ){
		return servicioCategoria.findByCategoria(categ).getId_prod();
	}
	
	@CrossOrigin(origins = "http://localhost:8080")	
	@DeleteMapping(path = "borrarProducto/{iduser},{idprod}") //borra una entrada en la tabla a_reciclar con un id_user y un id_prod como entrada
	public String clearUser(@PathVariable("iduser") long iduser, @PathVariable("idprod") long idprod){
	    servicioAReciclar.deleteByIdUserAndIdProd(iduser, idprod);
	    return "Se borro";
	}
	
	@CrossOrigin(origins = "http://localhost:8080") //ESTO SIRVE PARA CARGAR LAS CATEGORIAS SI SE QUIERE HACER EN UN FUTURO
	@GetMapping(value = "/cargarCategorias", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Categoria> cargarCateg(){
		return servicioCategoria.findAll();
		/*if(prod==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(prod);*/
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/guardarUsuario", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda una usuario en la tabla Usuario de un json que viene 
	public String guardarUsuario(@RequestBody Usuario usu) {
	    servicioUsuario.save(usu);
	    return "Se guardo un usuario en la tabla Usuario";
	}
	
}