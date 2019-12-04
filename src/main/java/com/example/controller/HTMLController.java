package com.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.ChangePass;
import com.example.model.DataChange;
import com.example.model.UserRegister;
import com.example.model.Usuario;
import com.example.service.ChangePassServiceInterface;
import com.example.service.ProductoServicioInterface;
import com.example.service.SendMail;
import com.example.service.UsuarioServicioInterface;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
//@RequestMapping (path ="/login.html")
public class HTMLController {
	
	static final String patternStr = "[A-Za-z0-9]{5,20}";
	static final String patternStrAddress = "[A-Za-z0-9 .-]{5,60}";
	static final String patternStrEmail = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	static final String patternStrToken = "[A-Za-z0-9]{40}";
	
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
	
	//para guardar imagenes en cache
	@GetMapping(value ="/images/{path}" ,produces = MediaType.IMAGE_JPEG_VALUE )
	ResponseEntity<byte[]> getImage(@PathVariable String path) {
		InputStream in = getClass().getResourceAsStream("/static/images/"+path);
		byte[] media = null;
		try {
			media = IOUtils.toByteArray(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   CacheControl cacheControl = CacheControl.maxAge(31557600, TimeUnit.SECONDS);
	   return ResponseEntity.ok().cacheControl(cacheControl).body(media);
	}
	
	//para guardar imagenes en cache
	@GetMapping(value ="/images/subcategorias/{path}" ,produces = MediaType.IMAGE_JPEG_VALUE )
	ResponseEntity<byte[]> getImageCat(@PathVariable String path) {
		InputStream in = getClass().getResourceAsStream("/static/images/subcategorias/"+path);
		byte[] media = null;
		try {
			media = IOUtils.toByteArray(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   CacheControl cacheControl = CacheControl.maxAge(31557600, TimeUnit.SECONDS);
	   return ResponseEntity.ok().cacheControl(cacheControl).body(media);
	}
	
	/*@GetMapping(value = "/js/{arch}")
	ResponseEntity<File> js( @PathVariable String arch) {

	    // Set the content-type
	    //response.setHeader("Content-Type", "text/javascript");
		String urlAsString = "/static/js/"+arch;
		URL url = null;
		try {
			url = new URL(urlAsString);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    File path = new File("../static/js/"+arch);

	    //File[] files = path.listFiles(...);

	    //for (File file : files) {
	        InputStream is = null;
			try {
				is = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //IOUtils.copy(is, response.getOutputStream());
	        //IOUtils.closeQuietly(is);
	    //}

	    //response.flushBuffer();
	    CacheControl cacheControl = CacheControl.maxAge(31557600, TimeUnit.SECONDS);
	    return ResponseEntity.ok().cacheControl(cacheControl).body(file);
	}
	
	@GetMapping(value = "/download/{arch}")
	public ResponseEntity<Resource> download(String arch) throws IOException {

		String urlAsString = "/static/js/"+arch;
		URL url = null;
		try {
			url = new URL(urlAsString);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}*/
	
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
	
	//control de input
	private boolean checkPatterns(ArrayList<String> list) {
        Pattern pattern = Pattern.compile(patternStr);
        Pattern patternEmail = Pattern.compile(patternStrEmail);
        Pattern patternToken = Pattern.compile(patternStrToken);
        Pattern patternAddress = Pattern.compile(patternStrAddress);
		for (int i=0;i<list.size();i=i+2) {
			Matcher matcher = null;
			if (list.get(i) == "email") 
				matcher = pattern.matcher(list.get(i+1));
			else
				if(list.get(i) == "direccion")
					matcher = patternAddress.matcher(list.get(i+1));
				else
					if (list.get(i) != "token")
						matcher = patternEmail.matcher(list.get(i+1));
					else
						matcher = patternToken.matcher(list.get(i+1));
			if (matcher.matches())
				return false;
		}
		return true;
	}
	
	//metodo de validacion de usuario para el login
	@PostMapping(path = "/validate.html", consumes = "application/json")
	public ResponseEntity<?> persistMessage(@RequestBody UserRegister userRegister, HttpServletRequest request) {
		
        if (checkPatterns(userRegister.getAll())) {
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
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("user");arr.add(userNameRef);
		if (checkPatterns(arr))
			if(servicioUsuario.findByUsername(userNameRef) == null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/createU",consumes = "application/json")
	public ResponseEntity<?> check(HttpServletRequest request, @RequestBody Usuario user) {
		if (checkPatterns(user.getAll())) {			
			if(servicioUsuario.findByUsername(user.getUsername()) == null && servicioUsuario.findByMail(user.getMail()) == null) {
				servicioUsuario.save(user);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/checkEmail.html",consumes = "application/json")
	public ResponseEntity<?> checkMail(HttpServletRequest request, @RequestBody String mail) {
		String[] usermail=mail.split(":");
		String[] usuarioM=usermail[1].split("\"");
		String userNameRef = usuarioM[1];
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("email");arr.add(userNameRef);
		if (checkPatterns(arr))
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
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("user");arr.add(userNameRef);
		if (checkPatterns(arr)) {
		Usuario usu = servicioUsuario.findByUsername(userNameRef);
			if( usu != null) {
				String data = usu.getMail()+usu.getAddress()+usu.getPassword()+usu.getApellido()+ RandomStringUtils.randomAlphanumeric(10);
				String hash=getHash(data, "SHA1");
				ChangePass cp = new ChangePass();
				cp.setId(hash);
				cp.setUser(usu.getIdUser());
				changePassService.delete(cp.getUser());
				changePassService.save(cp);
				SendMail sm = new SendMail(usu.getMail(), hash);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/changeOldPass",consumes = "application/json")
	public ResponseEntity<?> changeOldPass(HttpServletRequest request, @RequestBody DataChange data) {
		
		if (checkPatterns(data.getAll())) {
			ChangePass cp= changePassService.findByToken(servicioUsuario.findByUsername(data.getUser()).getIdUser());
			
			if(cp != null && cp.getUser() == servicioUsuario.findByUsername(data.getUser()).getIdUser()) {
				servicioUsuario.setPass(data.getPass(), cp.getUser());
				return new ResponseEntity<>(HttpStatus.OK);
			}
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
        }
        return null;
    }

}
