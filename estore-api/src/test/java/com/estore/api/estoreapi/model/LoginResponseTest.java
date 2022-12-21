package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoginResponseTest {
    
    @Test
    public void loginSuccessTest() {
        // Setup
        String token = "23342";
        boolean success = true;
        String message = "Login Successful";
        boolean admin = false;

        // Invoke
        LoginResponse mockLoginResponse = new LoginResponse(token, success, message, admin);

        // Analyze
        assertEquals(mockLoginResponse.getToken(), token);
        assertEquals(mockLoginResponse.getSuccess(), success);
        assertEquals(mockLoginResponse.getMessage(), message);
        assertEquals(mockLoginResponse.getAdmin(), admin);
    }

    @Test
    public void loginFailureTest() {
        // Setup
        String token = "23342";
        boolean success = false;
        String message = "Login Successful";
        boolean admin = false;

        // Invoke
        LoginResponse mockLoginResponse = new LoginResponse(token, success, message, admin);

        // Analyze
        assertEquals(mockLoginResponse.getToken(), token);
        assertEquals(mockLoginResponse.getSuccess(), success);
        assertEquals(mockLoginResponse.getMessage(), message);
        assertEquals(mockLoginResponse.getAdmin(), admin);
    }

    @Test
    public void adminLoginTest() {
        // Setup
        String token = "23342";
        boolean success = true;
        String message = "Login Successful";
        boolean admin = true;

        // Invoke
        LoginResponse mockLoginResponse = new LoginResponse(token, success, message, admin);

        // Analyze
        assertEquals(mockLoginResponse.getToken(), token);
        assertEquals(mockLoginResponse.getSuccess(), success);
        assertEquals(mockLoginResponse.getMessage(), message);
        assertEquals(mockLoginResponse.getAdmin(), admin);
    }
}
