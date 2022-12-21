package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.UserTokenManager;
import com.estore.api.estoreapi.model.LoginRequest;
import com.estore.api.estoreapi.model.LoginResponse;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Registry;
import com.estore.api.estoreapi.model.User;

@Tag("Controller-tier")
public class RegistryControllerTest {
    private RegistryController mockRegistryController;
    private Registry mockRegistry;
    private UserTokenManager mockUserTokenManager;

    /**
     * Before each test, create a new RegistryController object and inject
     * a mock DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockRegistry = mock(Registry.class);
        mockRegistryController = new RegistryController(mockRegistry);
        mockUserTokenManager = new UserTokenManager();
    }

    @Test
    public void getUserTest() throws IOException {
        // Setup
        User user = new User(1,"USERNAME");

        when(mockRegistry.getUser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = mockRegistryController.getUser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void getUserTestNotFound() throws Exception {
        // Setup
        int id = 999;
        when(mockRegistry.getUser(id)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = mockRegistryController.getUser(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getCartTest() throws IOException {
        // Setup
        User user = new User(null, null);
        user.id = 999;
        when(mockRegistry.getCartProducts(0)).thenReturn(null);
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.getUserFromToken("token")).thenReturn(user);

            // Invoke
            ResponseEntity<Product[]> response = mockRegistryController.getCart("token");

            // Analyze
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }   
    }

    @Test
    public void getCartFailureTest() throws IOException {
        // Setup
        User user = new User(null, null);
        user.id = 999;
        when(mockRegistry.getCartProducts(0)).thenReturn(null);
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.getUserFromToken("token")).thenReturn(null);

            // Invoke
            ResponseEntity<Product[]> response = mockRegistryController.getCart("token");

            // Analyze
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }   
    }

    @Test
    public void checkAliveTest() throws IOException {
        // Setup
        User user = new User(null, null);
        user.id = 999;
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.getUserFromToken("token")).thenReturn(null);

            // Invoke
            ResponseEntity<String> response = mockRegistryController.checkAlive("token");

            // Analyze
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.getUserFromToken("token")).thenReturn(user);

            // Invoke
            ResponseEntity<String> response = mockRegistryController.checkAlive("token");

            // Analyze
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } 
    }

    @Test
    public void logoutUserTest() throws IOException {
        // Setup
        User user = new User(null, null);
        user.id = 999;
        when(mockRegistry.getCartProducts(0)).thenReturn(null);
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.logoutUser("token")).thenAnswer((Answer<Void>) invocation -> null);

            // Invoke
            ResponseEntity<String> response = mockRegistryController.logoutUser("token");

            // Analyze
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }   
    }

    @Test
    public void saveProductFailureTest() throws IOException {
        // Setup
        User user = new User(null, null);
        Product product = new Product(0, null, null, 0, null);
        user.id = 999;
        when(mockRegistry.getCartProducts(0)).thenReturn(null);
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.getUserFromToken("token")).thenReturn(null);

            // Invoke
            ResponseEntity<Product> response = mockRegistryController.saveProduct("type", "token", product);

            // Analyze
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }   
    }

    @Test
    public void saveProductTest() throws IOException {
        // Setup
        User user = new User(null, null);
        Product product = new Product(0, null, null, 0, null);
        user.id = 999;
        when(mockRegistry.getCartProducts(0)).thenReturn(null);
        try (MockedStatic mockStatic = mockStatic(UserTokenManager.class)) {
            mockStatic.when(() -> UserTokenManager.getUserFromToken("token")).thenReturn(user);

            // Invoke
            ResponseEntity<Product> response1 = mockRegistryController.saveProduct("remove", "token", product);
            ResponseEntity<Product> response2 = mockRegistryController.saveProduct("add", "token", product);

            // Analyze
            assertEquals(HttpStatus.OK, response1.getStatusCode());
            assertEquals(HttpStatus.OK, response2.getStatusCode());
        }   
    }

    @Test
    public void loginUserExceptionTest() throws IOException {
        // Setup
        when(mockRegistry.getUserByUsername(null)).thenThrow(new IOException());
        // Invoke
        ResponseEntity<LoginResponse> response = mockRegistryController.loginUser(new LoginRequest("username", "password"));
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void loginUserBranchTest() throws IOException {
        // Setup
        User mockUser = mock(User.class);
        when(mockRegistry.getUserByUsername("username")).thenReturn(mockUser);
        when(mockUser.checkPassword("password")).thenReturn(true);
        // Invoke
        ResponseEntity<LoginResponse> response = mockRegistryController.loginUser(new LoginRequest("username", "password"));
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void loginUserFailureTest() throws IOException {
        // Setup
        User mockUser = mock(User.class);
        when(mockRegistry.getUserByUsername("username")).thenReturn(mockUser);
        when(mockUser.checkPassword("password")).thenReturn(false);
        // Invoke
        ResponseEntity<LoginResponse> response = mockRegistryController.loginUser(new LoginRequest("username", "password"));
        // Analyze
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void registerUserTest() throws IOException {
        // Setup
        when(mockRegistry.getUserByUsername(null)).thenThrow(new IOException());
        // Invoke
        ResponseEntity<LoginResponse> response = mockRegistryController.registerUser(new LoginRequest("username", "password"));
        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void registerUserConflictTest() throws IOException {
        // Setup
        User mockUser = mock(User.class);
        when(mockRegistry.getUserByUsername("username")).thenReturn(mockUser);
        when(mockUser.checkPassword("password")).thenReturn(true);
        // Invoke
        ResponseEntity<LoginResponse> response = mockRegistryController.registerUser(new LoginRequest("username", "password"));
        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}