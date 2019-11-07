package com.example.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE})
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
  
  List<AReciclar> recicladosPorConfirmar = new ArrayList<>();
  
  @GetMapping
  public String check(){ //Mensaje que muestra al iniciar
	  return "Funcionando...";
  }
  
  @GetMapping(path = "/guardarConParam/{id_prod},{barcode},{descripcion},{categoria}") //Guardar un producto en Producto harcodeado
  public String unProducto(@PathVariable(value="id_prod") long id_prod,@PathVariable(value="barcode") String barcode,@PathVariable(value="descripcion") String desc,@PathVariable(value="categoria") String categ){
	  servicio.save(new Producto(id_prod,barcode, desc,categ));
	  return "Se guardo el producto";
  }
	  

	@GetMapping(path = "/findall") //Halla todos los productos de la tabla Productos
	public List<Producto> findAll(){
	  return servicio.findAll();
	}
	

	@GetMapping(path = "/findbyid") //Halla un producto de la tabla Productos por id
	public String findById(@RequestParam("id") long id){
	  return servicio.findById(id).toString();
	}
	  

	@GetMapping("/codigo/{barcode}") //Halla un producto de la tabla Productos por barcode
	public ResponseEntity<Producto> getEmployeeById(@PathVariable(value="barcode") String barcode){
		Producto prod=servicio.findByBarcode(barcode);
		if(prod==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(prod);
	}
	

	@PostMapping(value = "/create", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda un producto en la tabla Productos de un json que viene 
	public String guardarProducto(@RequestBody Producto prod) {
	    servicio.save(prod);
	    return "Se guardo un producto en la tabla Productos";
	}
	
	@PostMapping(value = "/guardarProdDesc", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda un producto en la tabla Producto_desconocido de un json que viene 
	public String guardarProductoDesconocido(@RequestBody Producto_Desconocido prod) {
	    servicioProdDesc.save(prod);
	    return "Se guardo un producto en la tabla Productos_Desconocido";
	}
	
	@PostMapping(value = "/guardarReciclable", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda una tupla en la tabla A_Reciclar de un json que viene 
	public String ReciclarUsuarioProducto(@RequestBody AReciclar reciclable) {
	    servicioAReciclar.save(reciclable);
	    return "Se guardo un producto en la tabla A_Reciclar";
	}
	
	@GetMapping("/categoria/{categoria}") //Halla el id_prod de la tabla categoria dado una categoria
	public long getIdProd(@PathVariable(value="categoria") String categ){
		return servicioCategoria.findByCategoria(categ).getId_prod();
	}
	
	@DeleteMapping(path = "borrarProducto/{iduser},{idprod}") //borra una entrada en la tabla a_reciclar con un id_user y un id_prod como entrada
	public String clearUser(@PathVariable("iduser") long iduser, @PathVariable("idprod") long idprod){
	    servicioAReciclar.deleteByIdUserAndIdProd(iduser, idprod);
	    return "Se borro";
	}
	
	@GetMapping(value = "/cargarCategorias", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Categoria> cargarCateg(){
		return servicioCategoria.findAll();
	}
	
	@PostMapping(value = "/guardarUsuario", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda una usuario en la tabla Usuario de un json que viene 
	public String guardarUsuario(@RequestBody Usuario usu) {
	    servicioUsuario.save(usu);
	    return "Se guardo un usuario en la tabla Usuario";
	}
	
	/*@GetMapping(value = "/confirmarReciclados")
	public String confirmarReciclados(String user){
		servicioAReciclar.confirmarReciclados(user);
		return "Se guardaron los datos correctamente";
	}*/
	
	@GetMapping(path = "actualizarReciclados/{iduser},{idprod}")
	//@GetMapping(value = "/actualizarReciclados", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Forma de hacerlo recibiendo un JSON 
	//public String actualizarReciclados(@RequestBody AReciclar reciclable) {
	public String actualizarReciclable(@PathVariable("iduser") long iduser, @PathVariable("idprod") long idprod) { //cuando se marca con tilde un producto que se esta por confirmar
		AReciclar reciclable= servicioAReciclar.findByIdUserAndIdProd(iduser,idprod);
		reciclable.setConfirmacion(true);
		recicladosPorConfirmar.add(reciclable);
		return "Se cambio la confirmacion";
	}
	
	@PutMapping(path = "confirmarReciclados")
	public String confirmarReciclados() {
		for (AReciclar reciclable : recicladosPorConfirmar){
			servicioAReciclar.confirmar(reciclable);
		}
		recicladosPorConfirmar.clear();
		return "se confirmo";
	}
	
	
}