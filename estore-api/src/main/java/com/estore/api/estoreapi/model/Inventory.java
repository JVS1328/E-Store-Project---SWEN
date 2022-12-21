package com.estore.api.estoreapi.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.persistence.BasicPersistence;
import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a inventory object.
 * @author Kevin Park, Hridiza Roy, Benson Haley
 */
@Component
public class Inventory extends BasicPersistence<Product> implements InventoryDAO {

    private static final Logger LOG = Logger.getLogger(Inventory.class.getName());
    
    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public Inventory(@Value("${inventory.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(Product.class);
    }

    /**
     * Saves the products from the map into the file as an array of JSON objects
     * 
     * @return true if the products were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    public boolean save() throws IOException {
        ArrayList<Product> products = getProducts();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),products.toArray());
        return true;
    }

    /**
     * Brings the inventory list
     * @return Inventory list
     */
    public ArrayList<Product> getProducts() throws IOException {
        return new ArrayList<Product>(this.data.values());
    };

    /** 
     * Gets a single product
     * @param id The id corresponding to the product to get.
     * @return The product that corresponds to the id. */
    public Product getProduct(int id) throws IOException {
        return this.data.get(id);
    }

    /**
     * User searches products in the inventory
     * @return list of products
     * @param name of the product
     */
    public ArrayList<Product> findProducts(String name) throws IOException {
        ArrayList<Product> searchResult = new ArrayList<>(); 

        // filter the inventory to find products that contain name and add them to result
        this.data.values().stream()
        .filter(product -> product.getName().contains(name))
        .forEach(searchResult::add);

        return searchResult;
    };

    /**
     * @param product
     * @author Hridiza
     * Adds a product to the inventory
     * @throws IOException
     */
    public Product createProduct(Product product) throws IOException {
        int id = nextId();
        product.setId(id);
        this.data.put(id, product);
        save();
        return product;
    }

    /**
     * Updates the product
     * @throws IOException
     */
    public Product updateProduct(Product info) throws IOException {
        if (data.containsKey(info.getId()) == false) { return null; }
        Product p = data.get(info.getId());
        if (info.getName() != null) { p.setName(info.getName()); }
        if (info.getDescription() != null) { p.setDescription(info.getDescription()); }
        if (info.getPrice() != 0) { p.setPrice(info.getPrice()); }
        if (info.getQuantity() != 0) { p.setQuantity(info.getQuantity()); }
        if (info.getPicture() != null) { p.setPicture(info.getPicture()); }
        save();
        return p;
    }

    /**
     * @param product
     * @author Hridiza and Benson
     * Removes the specified product from the inventory
     * @throws IOException
     */
    public boolean removeProduct(int id) throws IOException {
        var value = this.data.remove(id);
        save();
        return value != null;
    }

    @Override
    public String toString() {
        int size = this.data.size();
        return "Inventory, # of items = " + size;
    }

}
