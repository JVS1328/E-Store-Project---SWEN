package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.RegistryDAO;
import com.estore.api.estoreapi.UserTokenManager;
import com.estore.api.estoreapi.model.LoginRequest;
import com.estore.api.estoreapi.model.LoginResponse;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Registry;
import com.estore.api.estoreapi.model.User;

@RestController
@RequestMapping("users")
public class RegistryController {
	private static final Logger LOG = Logger.getLogger(RegistryController.class.getName());
	private Registry dao;

	/**
	 * Creates a REST API controller to reponds to requests
	 * 
	 * @param inventoryDao The {@link UserDAO User Data Access Object} to perform
	 *                     CRUD operations <br>
	 *                     This dependency is injected by the Spring Framework
	 */
	public RegistryController(Registry dao) {
		this.dao = dao;
	}

	/**
	 * Responds to the GET request for a {@linkplain User user} for the given id
	 * 
	 * @param id The id used to locate the {@link User user}
	 * 
	 * @return ResponseEntity with {@link User user} object and HTTP status of OK if
	 *         found<br>
	 *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		LOG.info("GET /users/" + id);
		User user = this.dao.getUser(id);
		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{token}/cart")
	public ResponseEntity<Product[]> getCart(@PathVariable String token) {
		LOG.info("GET cart from " + token);

		User user = UserTokenManager.getUserFromToken(token);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		ArrayList<Product> result = this.dao.getCartProducts(user.id);
		return new ResponseEntity<Product[]>(result.toArray(new Product[result.size()]), HttpStatus.OK);
	}

	@PostMapping("/{type}/{token}")
	public ResponseEntity<Product> saveProduct(@PathVariable String type, @PathVariable String token, @RequestBody Product product) throws IOException {
		LOG.info("POST " + type + " product to cart");
		User user = UserTokenManager.getUserFromToken(token);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (type.equals("remove")) {
			user.getCart().removeItem(product);
		} else {
			user.getCart().addItem(product);
		}
		
		this.dao.save();
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
		LOG.info("POST login");
		try {
			User u = this.dao.getUserByUsername(loginRequest.getUsername());
			if (u == null) {
				return new ResponseEntity<LoginResponse>(new LoginResponse("", false, "No such user", false),
						HttpStatus.NOT_FOUND);
			} else {
				if (u.checkPassword(loginRequest.getPassword())) {
					String token = UserTokenManager.loginUser(u);
					return new ResponseEntity<LoginResponse>(new LoginResponse(token, true, "Success", u.isAdmin()), HttpStatus.OK);
				} else {
					return new ResponseEntity<LoginResponse>(new LoginResponse("", false, "Incorrect password", false),
							HttpStatus.UNAUTHORIZED);
				}
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Register a user
	 * 
	 * @param loginRequest login request packet (username and password)
	 * @return login response containing token
	 * @author Paul Harrison
	 */
	@PostMapping("/register")
	public ResponseEntity<LoginResponse> registerUser(@RequestBody LoginRequest loginRequest) {
		LOG.info("POST register");
		try {
			User u = this.dao.getUserByUsername(loginRequest.getUsername());
			if (u == null) {
				User newu = new User(loginRequest.getUsername(), loginRequest.getPassword());
				User daou = this.dao.createUser(newu);
				String token = UserTokenManager.loginUser(daou);
				return new ResponseEntity<LoginResponse>(new LoginResponse(token, true, "", false), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<LoginResponse>(new LoginResponse("", false, "User exists", false),
						HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{token}/logout")
	public ResponseEntity<String> logoutUser(@PathVariable String token) {
		LOG.info("GET logout");
		UserTokenManager.logoutUser(token);
		return new ResponseEntity<String>("{\"message\": \"logged out\"}", HttpStatus.OK);
	}
	
	@GetMapping("/{token}/alive")
	public ResponseEntity<String> checkAlive(@PathVariable String token) {
		LOG.info("GET alive");
		if (UserTokenManager.getUserFromToken(token) == null) {
			return new ResponseEntity<String>("{\"alive\": false}", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("{\"alive\": true}", HttpStatus.OK);
		}
	}
}
