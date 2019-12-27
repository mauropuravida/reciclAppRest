package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.AReciclar;
import com.example.model.Categoria;
import com.example.model.Historico;
import com.example.model.HistoricoResponse;
import com.example.model.Notificacion;
import com.example.model.Producto;
import com.example.model.Producto_Desconocido;
import com.example.model.RecoleccionesHistorial;
import com.example.model.Usuario;
import com.example.model.UsuarioResponse;
import com.example.model.Filtro;
import com.example.returns.InfoProducto;
import com.example.service.AReciclarServicioInterface;
import com.example.service.CategoriaServicioInterface;
import com.example.service.HistoricoServicioInterface;
import com.example.service.NotificacionesServicioInterface;
import com.example.service.ProductoDesconocidoServicioInterface;
import com.example.service.ProductoServicioInterface;
import com.example.service.RecoleccionesHistorialServicioInterface;
import com.example.service.UsuarioServicioInterface;
 
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class WebController {
	
	private static final int LIMITE_CONSENSO= 5;
  
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
  
  @Autowired
  HistoricoServicioInterface servicioHistorico;
  
  @Autowired
  RecoleccionesHistorialServicioInterface servicioRecolecciones;
  
  @Autowired
  NotificacionesServicioInterface servicioNotificaciones;
  
  List<AReciclar> recicladosPorConfirmar = new ArrayList<>(); 
	  
	@GetMapping(path = "/findall") //Halla todos los productos de la tabla Productos
	public List<Usuario> findAll(){
	  return servicioUsuario.findAll();
	}

	@GetMapping(path = "/usuario") //Halla un usuario de la tabla Usuario por id
	public UsuarioResponse usuario(HttpServletRequest request){
		UsuarioResponse ur = new UsuarioResponse();
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds != null) {
			Usuario user = servicioUsuario.findByUsernameAndPassword(creds.get(0), creds.get(1));
			ur.setAddress(user.getAddress());
			ur.setApellido(user.getApellido());
			ur.setNombre(user.getNombre());
			ur.setUsername(user.getUsername());
		}
		return ur;
	}
	
	//buscar producto por barcode
	@GetMapping("/codigo/{barcode}")
	public ResponseEntity<InfoProducto> getEmployeeById(@PathVariable(value="barcode") String barcode){
		
		InfoProducto infoProducto;
		//buscar en la tabla producto
		Producto prod=servicio.findByBarcode(barcode);
		
		if(prod==null) {
			//si no lo encuentro busco en producto desconocido
			Producto_Desconocido productoDesconocido = servicioProdDesc.findByBarcode(barcode);
			
			if (productoDesconocido == null) {
				infoProducto = new InfoProducto(0, barcode, "No hay descripcion, el producto no existe", servicioCategoria.findByIdProd(0), 1);
			}
			else {
				infoProducto = new InfoProducto(productoDesconocido.getId(), productoDesconocido.getBarcode(), productoDesconocido.getDescripcion(), servicioCategoria.findByIdProd((long)productoDesconocido.getCategoria()), 1);
				
			}
			infoProducto.setConsenso(true);
		}
		else
			infoProducto = new InfoProducto(prod.getId_prod(), prod.getCodigoBarras(), prod.getDescripcion(), servicioCategoria.findByIdProd((long)prod.getCategoria()), 1);
		return ResponseEntity.ok().body(infoProducto);
	}
	
	 //Guarda un producto en la tabla Productos de un json que viene 
	@PostMapping(value = "/create", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public String guardarProducto(@RequestBody Producto prod) {
	    servicio.save(prod);
	    return "Se guardo un producto en la tabla Productos";
	}
	
	@PostMapping(value = "/guardarProdDesc", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda un producto en la tabla Producto_desconocido de un json que viene 
	public String guardarProductoDesconocido(@RequestBody Producto_Desconocido prod) {
	    servicioProdDesc.save(prod);
	    return "Se guardo un producto en la tabla Productos_Desconocido";
	}
	
	//Metodo para accion de consenso de productos por metodo de barcode
	@PostMapping(value = "/guardarReciclableConsenso", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda una tupla en la tabla A_Reciclar de un json que viene 
	public ResponseEntity<?> ReciclarUsuarioProductoConsenso(@RequestBody InfoProducto reciclable, HttpServletRequest request) {
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds != null) {
			Usuario user = servicioUsuario.findByUsernameAndPassword(creds.get(0), creds.get(1));
			
			System.out.println("guardando consenso "+reciclable.toString());
			
			//agregar producto a la tabla a_reciclar
			AReciclar areciclar = new AReciclar();
			areciclar.setCantidad((int)reciclable.getCantidad());
			areciclar.setId_prod(reciclable.getId_prod());
			areciclar.setId_user(user.getIdUser());
			servicioAReciclar.save(areciclar);
			
			//actualizar producto en producto_desconocido
			Producto_Desconocido productoDesconocido = servicioProdDesc.findByBarcode(reciclable.getBarcode());
			
			if (productoDesconocido == null) {
				System.out.println("NO EXISTE EL PRODUCTO ");
				//creo producto y lo incorporo a los productos desconocidos para seguir consensuando
				productoDesconocido = new Producto_Desconocido();
				productoDesconocido.setBarcode(reciclable.getBarcode());
				productoDesconocido.setCategoria((int)reciclable.getId_prod());
				productoDesconocido.setDescripcion(reciclable.getDescripcion());
				productoDesconocido.setConsenso(1);
				productoDesconocido.setPeso(reciclable.getPeso());
				productoDesconocido.setVolumen(reciclable.getVolumen());
				servicioProdDesc.save(productoDesconocido);
				
			}else {
				System.out.println("EXISTE EL PRODUCTO ");
				//El producto existe, lo actualizo
				Producto_Desconocido productoDesconocidoActualizado = new Producto_Desconocido();
				productoDesconocidoActualizado.setId(productoDesconocido.getId());
				productoDesconocidoActualizado.setBarcode(productoDesconocido.getBarcode());
				productoDesconocidoActualizado.setCategoria((int)productoDesconocido.getCategoria());
				productoDesconocidoActualizado.setDescripcion(productoDesconocido.getDescripcion());
				productoDesconocidoActualizado.setConsenso(productoDesconocido.getConsenso());
				productoDesconocidoActualizado.setPeso(productoDesconocido.getPeso());
				productoDesconocidoActualizado.setVolumen(productoDesconocido.getVolumen());
				productoDesconocidoActualizado.setTexto_c(productoDesconocido.getTexto_c());
				
				
				//cualquier modificacion del producto vuelve a poner el contador de consenso en 1
				if (!reciclable.getDescripcion().isEmpty() && reciclable.getDescripcion() != "" && reciclable.getDescripcion() != productoDesconocido.getDescripcion()) {
					productoDesconocidoActualizado.setDescripcion(reciclable.getDescripcion());
					productoDesconocidoActualizado.setConsenso(1);
				}
				
				if (reciclable.getPeso() > 0 && reciclable.getPeso() != productoDesconocido.getPeso() ) {
					productoDesconocidoActualizado.setPeso(reciclable.getPeso());
					productoDesconocidoActualizado.setConsenso(1);
				}
				
				if (reciclable.getVolumen() > 0 && reciclable.getVolumen() != productoDesconocido.getVolumen() ) {
					productoDesconocidoActualizado.setVolumen(reciclable.getVolumen());
					productoDesconocidoActualizado.setConsenso(1);
				}
				
				if (reciclable.getId_prod() != productoDesconocido.getCategoria()) {
					productoDesconocidoActualizado.setCategoria((int)reciclable.getId_prod());
					productoDesconocidoActualizado.setConsenso(1);
				}
				else
					productoDesconocidoActualizado.setConsenso(productoDesconocido.getConsenso()+1);
				
				servicioProdDesc.delete(productoDesconocido.getId());
				
				//control de consenso
				if (productoDesconocido.getConsenso() >= LIMITE_CONSENSO) {
					Producto producto = new Producto();
					producto.setCodigoBarras(productoDesconocido.getBarcode());
					producto.setCategoria(productoDesconocido.getCategoria());
					producto.setDescripcion(productoDesconocido.getDescripcion());
					
					servicio.save(producto);
				}else
					servicioProdDesc.save(productoDesconocidoActualizado);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value = "/guardarReciclable", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Guarda una tupla en la tabla A_Reciclar de un json que viene 
	public ResponseEntity<?> ReciclarUsuarioProducto(@RequestBody AReciclar reciclable, HttpServletRequest request) {
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds != null) {
			Usuario user = servicioUsuario.findByUsernameAndPassword(creds.get(0), creds.get(1));
			reciclable.setId_user(user.getIdUser());
			System.out.print("guardando "+reciclable.toString());
			servicioAReciclar.save(reciclable);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/categoria/{categoria}") //Halla el id_prod de la tabla categoria dado una categoria
	public long getIdProd(@PathVariable(value="categoria") String categ){
		return servicioCategoria.findByCategoria(categ).getId_prod();
	}
	
	//eliminar un producto por idproducto
	@DeleteMapping(path = "/borrarProducto/{iduser},{idprod}") //borra una entrada en la tabla a_reciclar con un id_user y un id_prod como entrada
	public String clearUser(@PathVariable("iduser") long iduser, @PathVariable("idprod") long idprod){
	    servicioAReciclar.deleteByIdUserAndIdProd(iduser, idprod);
	    return "Se borro";
	}
	
	//retorna todas las categorias existentes en la bd
	@GetMapping(value = "/cargarCategorias", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Categoria> cargarCateg(){
		return servicioCategoria.findAll();
	}
	
	@GetMapping(path = "/actualizarReciclados/{iduser},{idprod}") 
	public String actualizarReciclable(@PathVariable("iduser") long iduser, @PathVariable("idprod") long idprod) { //cuando se marca con tilde un producto que se esta por confirmar 
		AReciclar reciclable= servicioAReciclar.findByIdUserAndIdProd(iduser,idprod); 
		reciclable.setConfirmacion(true); 
		recicladosPorConfirmar.add(reciclable); 
		return "Se cambio la confirmacion"; 
	} 
	 
	@PutMapping(path = "/confirmarReciclados") 
	public String confirmarReciclados() { 
		for (AReciclar reciclable : recicladosPorConfirmar){ 
			servicioAReciclar.confirmar(reciclable); 
		} 
		recicladosPorConfirmar.clear(); 
		return "se confirmo"; 
	} 
	
	//recuperar datos de puntos de recoleccion
	@GetMapping("/puntos")
	public List<RecoleccionesHistorial> puntos(HttpServletRequest request){
		return servicioRecolecciones.findAll();
	}
	
	//recuperar datos de notificaciones
	@GetMapping("/notificaciones")
	public List<Notificacion> notificaciones(HttpServletRequest request){
		return servicioNotificaciones.findAll();
	}
	
	//recuperar datos de usuario para mostrar en la parte de En Casa"
	@GetMapping("/cargarProductosUsuario")
	public List<InfoProducto> cargarProductosUsuario(HttpServletRequest request){
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds != null) {
			Usuario user = servicioUsuario.findByUsernameAndPassword(creds.get(0), creds.get(1));
			List<AReciclar> reciclables = servicioAReciclar.findByIdUser(user.getIdUser());
			List<InfoProducto> productos = new ArrayList<>();
			List<Categoria> cats = servicioCategoria.findAll();
			for (AReciclar rec : reciclables){
				String nameCat =( cats.get((int) (rec.getId_prod())).getCategoria());
				InfoProducto infoProducto = new InfoProducto( rec.getId_prod(), "", "", nameCat, rec.getCantidad());
				productos.add(infoProducto);
			} 
			return productos;
		}
		return new ArrayList<InfoProducto>();
	}
	
	//para recuperar datos de los graficos estadisticos de torta
	@PostMapping(value = "/recuperarDatos", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<HistoricoResponse> recuperarDatos(@RequestBody Filtro filtro, HttpServletRequest request){
		
		List<HistoricoResponse> response =new ArrayList<HistoricoResponse>();
		
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds != null) {
			List<Categoria> cats = servicioCategoria.findAll();
			Usuario user = servicioUsuario.findByUsernameAndPassword(creds.get(0), creds.get(1));
			if( filtro.getFechaInicio() == null && filtro.getFechaFin() == null) {
				for (Historico h:servicioHistorico.findByIdUser(user.getIdUser())) {
					String nameCat =( cats.get((int) (h.getIdProd()-1)).getCategoria());
					HistoricoResponse hr = new HistoricoResponse();
					hr.setCantidad(h.getCantidad());
					hr.setFecha(h.getFecha());
					hr.setIdProd(h.getIdProd());
					hr.setNameProducto(nameCat);
					response.add(hr);
				}
			}
			else
				if( filtro.getFechaInicio() != null && filtro.getFechaFin() != null){
					List<Integer> materiales = new ArrayList<Integer>();
					for (String st:filtro.getMateriales()) {
						
						if (servicioCategoria.findByCategoria(st) != null)
							materiales.add((int) servicioCategoria.findByCategoria(st).getId_prod());
					}						
					if (materiales.size() > 0)
						for (Historico h:servicioHistorico.recuperarDatosConFechas(user.getIdUser(), filtro.getFechaInicio(), filtro.getFechaFin(), materiales)) {
							String nameCat =( cats.get((int) (h.getIdProd()-1)).getCategoria());
							HistoricoResponse hr = new HistoricoResponse();
							hr.setCantidad(h.getCantidad());
							hr.setFecha(h.getFecha());
							hr.setIdProd(h.getIdProd());
							hr.setNameProducto(nameCat);
							response.add(hr);
						}
				}
		}
		return response;
	}
}