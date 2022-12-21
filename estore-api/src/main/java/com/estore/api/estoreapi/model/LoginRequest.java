package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Login Request Struct
 * @author Paul Harrison
 *
 */
public class LoginRequest {
	@JsonProperty("username") public String username;
    @JsonProperty("password") public String password;

    /**
     * Create login request
     * @param username username
     * @param password password
     * @author Paul Harrison
     */
    public LoginRequest(
    		@JsonProperty("username") String username,
    		@JsonProperty("password") String password) {
        
        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot have spaces");
        }

        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot have spaces");
        }
        
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
