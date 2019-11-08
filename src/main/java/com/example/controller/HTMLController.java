package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.ProductoServicioInterface;

@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class HTMLController {
	
	@Autowired
	ProductoServicioInterface servicio;
	
	/*@GetMapping(path = "/")
	public String home(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		
		long id = 1;
		
		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionMessages", messages);
		
		model.addAttribute("sessionMessages",servicio.findById(id).toString());

		return "index";
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value = "/index.html") 
	public String index() { 
		return "index"; 
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/login.html") 
	public String login() { 
		return "login"; 
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/changePass.html") 
	public String password() { 
		return "changePass"; 
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/createAccount.html") 
	public String createAccount() { 
		return "createAccount"; 
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/main_menu.html") 
	public String main_menu() { 
		return "main_menu"; 
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

}
