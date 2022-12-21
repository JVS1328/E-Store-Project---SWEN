package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Login response sent to user on login
 * @author Paul Harrison
 *
 */
public class LoginResponse {
	@JsonProperty("token") private String token;
    @JsonProperty("success") private boolean success;
    @JsonProperty("message") private String message;
    @JsonProperty("admin") private boolean admin;
    
    /**
     * Login response
     * @param token token
     * @param success true if login successful
     * @param message error message
     */
    public LoginResponse(
    		@JsonProperty("token") String token,
    		@JsonProperty("success") boolean success,
    		@JsonProperty("message") String message,
    		@JsonProperty("admin") boolean admin) {
        this.token = token;
        this.success = success;
        this.message = message;
        this.admin = admin;
    }

    public String getToken() {
        return this.token;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getAdmin() {
        return this.admin;
    }
}
