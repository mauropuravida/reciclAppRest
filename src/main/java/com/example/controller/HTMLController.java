package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.model.ChangePass;
import com.example.model.DataChange;
import com.example.model.UserRegister;
import com.example.model.Usuario;
import com.example.service.ChangePassServiceInterface;
import com.example.service.ProductoServicioInterface;
import com.example.service.SendMail;
import com.example.service.UsuarioServicioInterface;
import org.apache.commons.lang3.RandomStringUtils;

@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
//@RequestMapping (path ="/login.html")
public class HTMLController {
	
	@Autowired
	ProductoServicioInterface servicio;

	@Autowired
	UsuarioServicioInterface servicioUsuario;
	
	@Autowired
	ChangePassServiceInterface changePassService;
	
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
	public String password(HttpServletRequest request) {
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "changePass";
		}
		return "index";
	} 
	 
	@RequestMapping(method = RequestMethod.GET, value = "/createAccount.html") 
	public String createAccount(HttpServletRequest request) {
		List<String> creds = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (creds == null) {
			return "createAccount";
		}
		return "index";
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/layout/header.html")
	public String header(HttpServletRequest request) {
		return "header";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/input_box.html")
	public String input_box(HttpServletRequest request) {
		return "input_box";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/offline.html")
	public String offline(HttpServletRequest request) {
		return "offline";
	}
	
	@PostMapping(path ="/checkUser.html",consumes = "application/json")
	public ResponseEntity<?> checkUser(HttpServletRequest request, @RequestBody String user) {
		String[] usuario=user.split(":");
		String[] usuarioName=usuario[1].split("\"");
		String userNameRef = usuarioName[1];
		if(servicioUsuario.findByUsername(userNameRef) == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/createU",consumes = "application/json")
	public ResponseEntity<?> check(HttpServletRequest request, @RequestBody Usuario user) {
		if(user.getNombre() == "" ||  user.getAddress() == "" ||  user.getApellido() == "" ||  user.getUsername() == "" ||  user.getMail() == "" || user.getPassword() == "")
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if(servicioUsuario.findByUsername(user.getUsername()) == null && servicioUsuario.findByMail(user.getMail()) == null) {
			servicioUsuario.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/checkEmail.html",consumes = "application/json")
	public ResponseEntity<?> checkMail(HttpServletRequest request, @RequestBody String mail) {
		String[] usermail=mail.split(":");
		String[] usuarioM=usermail[1].split("\"");
		String userNameRef = usuarioM[1];
		if(servicioUsuario.findByMail(userNameRef) == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/requestCode",consumes = "application/json")
	public ResponseEntity<?> requestCode(HttpServletRequest request, @RequestBody String user) {
		String[] usuario=user.split(":");
		String[] usuarioName=usuario[1].split("\"");
		String userNameRef = usuarioName[1];
		Usuario usu = servicioUsuario.findByUsername(userNameRef);
		if( usu != null) {
			String data = usu.getMail()+usu.getAddress()+usu.getPassword()+usu.getApellido()+ RandomStringUtils.randomAlphanumeric(10);
			String hash=getHash(data, "SHA1");
			ChangePass cp = new ChangePass();
			cp.setId(hash);
			cp.setUser(usu.getIdUser());
			changePassService.save(cp);
			SendMail sm = new SendMail(usu.getMail(), hash);
			//System.out.print(hash+"\n"+cp.getFecha()+"\n");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/changeOldPass",consumes = "application/json")
	public ResponseEntity<?> changeOldPass(HttpServletRequest request, @RequestBody DataChange data) {
		
		if (data.getToken() == "" || data.getPass() == "") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ChangePass cp= changePassService.findByToken(servicioUsuario.findByUsername(data.getUser()).getIdUser());
		
		if(cp != null && cp.getUser() == servicioUsuario.findByUsername(data.getUser()).getIdUser()) {
			servicioUsuario.setPass(data.getPass(), cp.getUser());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
    private String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
