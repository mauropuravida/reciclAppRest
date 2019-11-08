package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.UserRegister;
import com.example.model.Usuario;
import com.example.service.ProductoServicioInterface;
import com.example.service.UsuarioServicioInterface;

@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
//@RequestMapping (path ="/login.html")
public class HTMLController {
	
	@Autowired
	ProductoServicioInterface servicio;

	@Autowired
	UsuarioServicioInterface servicioUsuario;
	
	@GetMapping(path = "/")
	public String empty(HttpServletRequest request) { 
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "login"; 
		}
		return "index";
	} 
	
	@RequestMapping(method = RequestMethod.GET, value = "/index.html") 
	public String index(HttpServletRequest request) { 
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "login"; 
		}
		return "index";
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/login.html") 
	public String login(HttpServletRequest request) { 
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "login"; 
		}
		return "index";
	}
	
	@PostMapping(path = "/validate.html", consumes = "application/json")
	public ResponseEntity<?> persistMessage(@RequestBody UserRegister userRegister, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Usuario user = servicioUsuario.findByUsernameAndPassword(userRegister.getUser(), userRegister.getPass());
		if (user != null) {
			List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
			if (creds == null) {
				creds = new ArrayList<>();
				request.getSession().setAttribute("MY_SESSION_MESSAGES", creds);
			}
			creds.add(userRegister.getUser());
			creds.add(userRegister.getPass());
			request.getSession().setAttribute("MY_SESSION_MESSAGES", creds);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
	}
	 
	@RequestMapping(method = RequestMethod.GET, value = "/changePass.html") 
	public String password() { 
		return "changePass"; 
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/createAccount.html") 
	public String createAccount() { 
		return "createAccount"; 
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/shared/main_menu.html") 
	public String main_menu(HttpServletRequest request) { 
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "login"; 
		}
		return "main_menu"; 
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/scan.html") 
	public String scan(HttpServletRequest request) {
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "login"; 
		}
		return "scan"; 
	} 

	@PostMapping("/logout")
	public ResponseEntity<?> destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
