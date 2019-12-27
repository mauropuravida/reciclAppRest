package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.service.OkHttpPost;
import com.example.model.ChangePass;
import com.example.model.CreateUsuario;
import com.example.model.DataChange;
import com.example.model.UserRegister;
import com.example.model.Usuario;
import com.example.model.Notification;
import com.example.service.ChangePassServiceInterface;
import com.example.service.CurlPost;
import com.example.service.ProductoServicioInterface;
import com.example.service.SendMail;
import com.example.service.UnirestSender;
import com.example.service.UsuarioServicioInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class HTMLController {
	
	static final String patternStr = "[A-ZñÑáéíóúÁÉÍÓÚa-z0-9]{3,20}";
	static final String patternStrAddress = "[0-9A-Za-zñÑáéíóúÁÉÍÓÚ .-]{5,60}";
	static final String patternStrEmail = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	static final String patternStrToken = "[A-Za-z0-9]{40}";
	
	@Autowired
	ProductoServicioInterface servicio;

	@Autowired
	UsuarioServicioInterface servicioUsuario;
	
	@Autowired
	ChangePassServiceInterface changePassService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
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
			e.printStackTrace();
		}
		
	   CacheControl cacheControl = CacheControl.maxAge(31557600, TimeUnit.SECONDS);
	   return ResponseEntity.ok().cacheControl(cacheControl).body(media);
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
	
	//control de input
	private boolean checkPatterns(ArrayList<String> list) {
        Pattern pattern = Pattern.compile(patternStr);
        Pattern patternEmail = Pattern.compile(patternStrEmail);
        Pattern patternToken = Pattern.compile(patternStrToken);
        Pattern patternAddress = Pattern.compile(patternStrAddress);
		for (int i=0;i<list.size();i=i+2) {
			Matcher matcher = null;
			if (list.get(i) == "email" || list.get(i) == "user" || list.get(i) == "username") {
				matcher = patternEmail.matcher(list.get(i+1));
			}
			else
				if(list.get(i) == "direccion" || list.get(i) == "address") {
					matcher = patternAddress.matcher(list.get(i+1));
				}
				else
					if (list.get(i) != "token") {
						matcher = pattern.matcher(list.get(i+1));
					}
					else {
						matcher = patternToken.matcher(list.get(i+1));
					}
			if (!matcher.matches())
				return false;
		}
		return true;
	}
	
	//metodo de validacion de usuario para el login
	@PostMapping(path = "/validate.html", consumes = "application/json")
	public ResponseEntity<?> persistMessage(@RequestBody UserRegister userRegister, HttpServletRequest request) {
		
        if (checkPatterns(userRegister.getAll())) {
        	userRegister.setPass(sha1(userRegister.getPass()));
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
	
	//verificar si existe usuario
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

	//suscribir a notificaciones
	@PostMapping(path ="/suscribir",consumes = "application/json")
	public ResponseEntity<?> suscribir(HttpServletRequest request, @RequestBody String token) {
	
		JSONObject tokenn = new JSONObject(token);
        String tok = tokenn.getString("token");
		
		String url = "https://iid.googleapis.com/iid/v1/"+tok+"/rel/topics/notificacion";
		OkHttpPost post = new OkHttpPost();
		UnirestSender uPost = new UnirestSender();
		CurlPost cPost = new CurlPost();

		String apiKey = "AIzaSyClQ908rsePcohCqd1-BpcCNabUIHqiE8Q";
		try {
			post.post("{}", url, apiKey);
			//uPost.post(url,apiKey, "{}");
			//cPost.post(url,apiKey, "{}");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path ="/notificar",consumes = "application/json")
	public ResponseEntity<?> sendNotification(HttpServletRequest request, @RequestBody Notification notification) {
		System.out.println("NOTIFICANDO");
		String url = "https://fcm.googleapis.com/fcm/send";
		String key = "AAAACLHlOgY:APA91bFxOgxhQqW92xHbzLDAdEFbt6BrfQkYRHj3OYw4zivsxUld5LPDKfGUU7kqZGUuvNgvIPIADFiv-QsteeiVGZXuRlhRivGbN-S1VdNFjZFA0QuOsSFrJhNxi1XYj2nmRnxTs4ku";
		OkHttpPost post = new OkHttpPost();
		UnirestSender uPost = new UnirestSender();
		CurlPost cPost = new CurlPost();
		
		String mensaje = "{ \n\t\"notification\": {\n\t \"title\": \""+notification.getTitle()+"\",\n\t \"body\": \""+notification.getBody()+"\"\n\t },\n\n\"to\" : \"/topics/notificacion\"\n}";

		try {
			post.post(mensaje,url,key);
			//uPost.post(url,key, mensaje);
			//cPost.post(url,key, mensaje);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	
	//crear usuario
	@PostMapping(path ="/createU",consumes = "application/json")
	public ResponseEntity<?> check(HttpServletRequest request, @RequestBody CreateUsuario user)throws IOException {
		if (checkPatterns(user.getAll())) {
			
			String url= "https://www.google.com/recaptcha/api/siteverify?secret=6LcjCMcUAAAAAI9U-xEGhzt-9xjsaMsdS3bQynA3&response="+user.getResponse();
			
			OkHttpClient client = new OkHttpClient();
			
			Request reques = new Request.Builder().url(url).build();
			boolean verify = false;
			
			//Descomentar las lineas para que funcione el captcha
//			try (Response response = client.newCall(reques).execute()) {
				
//				String jsonData = response.body().string();
//			    JSONObject Jobject = new JSONObject(jsonData);
				
			  
				verify = true;//Jobject.getBoolean("success");
				
				Usuario us = servicioUsuario.findByUsernameAndPassword(user.getUsername(), user.getPassword());
				if (us == null && verify ) {
					Usuario u = new Usuario(0,user.getUsername(),user.getNombre(),user.getApellido(),user.getPassword(),user.getAddress());
					u.setPassword(sha1(u.getPassword()));
					if(servicioUsuario.save(u) != null) {
						return new ResponseEntity<>(HttpStatus.OK);
					}
				}

//			}
//			catch (Exception e) {
//				System.out.println("ERROR DE LA CONSULTA \n"+e);
//				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	//encriptar contraseña
	private String sha1(String txt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA1");
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
	
	@PostMapping(path ="/checkEmail.html",consumes = "application/json")
	public ResponseEntity<?> checkMail(HttpServletRequest request, @RequestBody String mail) {
		String[] usermail=mail.split(":");
		String[] usuarioM=usermail[1].split("\"");
		String userNameRef = usuarioM[1];
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("email");arr.add(userNameRef);
		if (checkPatterns(arr))
			if(servicioUsuario.findByUsername(userNameRef) == null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else		
				return new ResponseEntity<>(HttpStatus.CONFLICT);
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
				String data = usu.getUsername()+usu.getAddress()+usu.getPassword()+usu.getApellido()+ RandomStringUtils.randomAlphanumeric(10);
				String hash=getHash(data, "SHA1");
				ChangePass cp = new ChangePass();
				cp.setToken(hash);
				cp.setUser(usu.getIdUser());
				changePassService.delete(cp.getUser());
				changePassService.save(cp);
				SendMail sm = new SendMail(usu.getUsername(), hash);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path ="/changeOldPass",consumes = "application/json")
	public ResponseEntity<?> changeOldPass(HttpServletRequest request, @RequestBody DataChange data) {
		
		if (checkPatterns(data.getAll())) {
			ChangePass cp= changePassService.findByToken(data.getToken());
			
			if(cp != null && cp.getUser() == servicioUsuario.findByUsername(data.getUser()).getIdUser()) {
				data.setPass(sha1(data.getPass()));
				servicioUsuario.setPass(data.getPass(), cp.getUser());
				changePassService.delete(cp.getUser());
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
