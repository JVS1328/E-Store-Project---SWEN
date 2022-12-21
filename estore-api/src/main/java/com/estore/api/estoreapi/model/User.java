package com.estore.api.estoreapi.model;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.Identified;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User object.
 * 
 * @author John Skorcik, Paul Harrison
 */
public class User extends Identified {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    //Testing stuff?
    static final String STRING_FORMAT = "User [id=%d, username=%s]";
    
    @JsonProperty("admin") private boolean admin;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("cart") private Cart cart;

    /**
     * Create a User with a given ID and user.
     * @param id	ID of the User
     * @param name	Name of the user
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(
    		@JsonProperty("id") int id,
    		@JsonProperty("username") String username) {
        this.id = id;
        this.username = username;
        this.admin = false;
        
        // each user has just one shopping cart that will be stored with the user
        this.cart = new Cart();
    }
    
    public User(
    		String username,
    		String password) {
        this.username = username;
        this.password = password;
        
        // each user has just one shopping cart that will be stored with the user
        this.cart = new Cart();
    }

    /**
     * Retrieves the name of the product
     * @return The name of the user
     */
    public String getName() {return username;}

    /**
     * Retrieves the user's cart.
     * @return the Cart of the user.
     */
    public Cart getCart() {return cart;}

    /**
     * Checks if user is administrator
     * @return true if is administrator
     */
    public boolean isAdmin() {return admin;}
    
    /**
     * Makes a user administrator
     */
    public void makeAdmin() {admin = true;}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,username);
    }


    @Override
    public boolean equals(Object o) {
        User that = (User)o;
        return (this.id == that.id) && (this.username == that.username);
    }

    /**
     * Check user password
     * @param password2 password to check against user password
     * @return true if password matches
     * @author Paul Harrison
     */
	public boolean checkPassword(String password2) {
		return password2.equals(password);
	}

}
