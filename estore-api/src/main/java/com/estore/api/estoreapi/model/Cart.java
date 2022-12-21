package com.estore.api.estoreapi.model;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Cart object.
 * 
 * @author John Skorcik, Paul Harrison
 */
public class Cart {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    private static final String STRING_FORMAT = "Cart";

    @JsonProperty("items") private ArrayList<Product> items;

    /**
     * Create a cart.
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Cart() {
        this.items = new ArrayList<Product>();
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setItems(ArrayList<Product> items) {
    	this.items = items;
    }

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public ArrayList<Product> getItems() {
    	return this.items;
    }

    /**
     * Add item to cart
     * @param product	the product to add to the cart
     */
    public void addItem(Product product) {
    	this.items.add(product);
    }

    /**
     * Remove item from cart
     * @param product	the product to remove from the cart
     */
    public void removeItem(Product product) {
    	this.items.remove(product);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return STRING_FORMAT;
    }
}
