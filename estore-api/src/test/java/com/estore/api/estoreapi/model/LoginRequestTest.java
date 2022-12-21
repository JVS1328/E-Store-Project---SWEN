package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoginRequestTest {

    @Test
    public void validUsernamePasswordTest() {
        // Setup
        String username = "valid_username";
        String password = "valid_password";

        // Invoke
        LoginRequest mockLoginRequest = new LoginRequest(username, password);

        // Analyze
        assertEquals(mockLoginRequest.getUsername(), username);
        assertEquals(mockLoginRequest.getPassword(), password);
    }

    @Test
    public void invalidUsernameTest() {
        try {
            // Setup
            String username = "invalid username";
            String password = "valid_password";

            // Invoke
            LoginRequest mockLoginRequest = new LoginRequest(username, password);

            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test
    public void invalidPasswordTest() {
        try {
            // Setup
            String username = "valid username";
            String password = "invalid password";

            // Invoke
            LoginRequest mockLoginRequest = new LoginRequest(username, password);

            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }
}
