package com.example.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 
//@RestController
@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE})
//@RequestMapping (path ="/")
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
  
	/*@GetMapping("/")
	public String home(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		
		String barcode="";
		if (messages == null) {
			messages = new ArrayList<>();
		}
		else
			barcode= messages.get(0);
		
		model.addAttribute("sessionMessages", messages);
		
		model.addAttribute("sessionMessages",servicio.findByBarcode(barcode));

		return "access";
	}*/
  
	  @RequestMapping(method = RequestMethod.GET, value = "/")
	  public String index() {
	      return "index";
	  }

	@PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (msgs == null) {
			msgs = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		}
		msgs.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		return "redirect:/";
	}

	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
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
	

	@GetMapping(path = "/findbyid/{id}") //Halla un producto de la tabla Productos por id
	public ResponseEntity<Usuario> findById(@PathVariable(value="id") long id){
		Usuario user = (Usuario) servicioUsuario.findById(id);
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
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
	public String actualizarReciclable(@PathVariable("iduser") long iduser, @PathVariable("idprod") long idprod) { //cuando se marca con tilde un producto que se esta por confirmar 
		AReciclar reciclable= servicioAReciclar.findByIdUserAndIdProd(iduser,idprod); 
		reciclable.setConfirmacion(true); 
		recicladosPorConfirmar.add(reciclable); 
		return "Se cambio la confirmacion"; 
	} 
	 
	/*@PutMapping(path = "confirmarReciclados") 
	public String confirmarReciclados() { 
		for (AReciclar reciclable : recicladosPorConfirmar){ 
			servicioAReciclar.confirmar(reciclable); 
		} 
		recicladosPorConfirmar.clear(); 
		return "se confirmo"; 
	} */
	
}