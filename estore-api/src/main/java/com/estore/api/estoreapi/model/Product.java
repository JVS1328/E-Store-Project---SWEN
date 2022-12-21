package com.estore.api.estoreapi.model;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.Identified;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product object.
 * 
 * @author John Skorcik, Paul Harrison
 */
public class Product extends Identified {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    //Testing stuff?
    static final String STRING_FORMAT = "Product [id=%d, name=%s, price=%f]";
    
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("price") private float price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("picture") private String picture;

    //@JsonProperty("bidEnd") public int bidEnd = 0;
    //@JsonProperty("bidUser") public String bidUser = "";

    /**
     * Create a product with a given ID, name, and Price.
     * @param id			product ID
     * @param name			product name
     * @param description	product description (HTML)
     * @param price			product price ($X.XX)
     * @param picture       product picture
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Product(
    		@JsonProperty("id") int id,
    		@JsonProperty("name") String name,
    		@JsonProperty("description") String description,
    		@JsonProperty("price") float price,
    		@JsonProperty("picture") String picture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picture = picture;
    }
    
    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /** Standard description getter. */
    public String getDescription() { return this.description; }
    /** Standard description setter. */
    public void setDescription(String description) { this.description = description; }

    /** Standard price getter. */
    public float getPrice() { return this.price; }
    /** Standard price setter. */
    public void setPrice(float price) { this.price = price; }

    /** Standard quantity getter. */
    public int getQuantity() { return this.quantity; }
    /** Standard quantity setter. */
    public void setQuantity(int quantity) {
    	if (quantity > 0)
    		this.quantity = quantity;
    	else
    		this.quantity = 0;
    }

    /** Standard picture getter. */
    public String getPicture() { return this.picture; }
    /** Standard quantity setter. */
    public void setPicture(String picture) { this.picture = picture; }

    public void setId(int id) { this.id = id; }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price);
    }

    @Override
    public boolean equals(Object o) {
        Product that = (Product) o;
        return (this.id == that.id) && (this.name.equals(that.name)) && (this.price == that.price);
    }
}