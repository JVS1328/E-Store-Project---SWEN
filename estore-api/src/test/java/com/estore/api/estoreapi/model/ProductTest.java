package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ProductTest {
    private Product mockProduct;
    final String DEFAULT_PICTURE = "/assets/car.png";

    @BeforeEach
    public void setup() throws IOException {
        mockProduct = new Product(0, "Tomacco", "Tomacco is a produce made by Homer by accidentally mixing tobacco seeds and tomato seeds and then using plutonium rods to help the plants grow.", 3.99f, DEFAULT_PICTURE);
    }

    @Test
    public void getNameTest() {
        assertEquals(mockProduct.getName(), "Tomacco");
    }
    
    @Test
    public void getDescriptionTest() {
        assertEquals(mockProduct.getDescription(),  "Tomacco is a produce made by Homer by accidentally mixing tobacco seeds and tomato seeds and then using plutonium rods to help the plants grow.");
    }
    
    @Test
    public void getPriceTest() {
        assertEquals(mockProduct.getPrice(), 3.99f);
    }
    
    @Test
    public void getIdTest() {
        assertEquals(mockProduct.getId(), 0);
    }
    
    @Test
    public void setNameTest() {
    	mockProduct.setName("Gummy Bears");
        assertEquals(mockProduct.getName(), "Gummy Bears");
    }
    
    @Test
    public void setDescriptionTest() {
    	mockProduct.setDescription("Not with gummy bears you won't!");
        assertEquals(mockProduct.getDescription(), "Not with gummy bears you won't!");
    }
    
    @Test
    public void setPriceTest() {
    	mockProduct.setPrice(200f);
        assertEquals(mockProduct.getPrice(), 200f);
    }
    
    @Test
    public void setIdTest() {
    	mockProduct.setId(89);
        assertEquals(mockProduct.getId(), 89);
    }

    @Test
    public void getQuantityTest() {
        var result = mockProduct.getQuantity();
        assertEquals(0, result);
    }

    @Test
    public void equalsTest() {
        Product product1 = mockProduct;
        Product product2 = new Product(0, null, null, 0, DEFAULT_PICTURE);
        assert(product1.equals(product1));
        assert(!product1.equals(product2));
        product2.setName(mockProduct.getName());
        assert(!product1.equals(product2));
        product2.setDescription(mockProduct.getDescription());
        assert(!product1.equals(product2));
        product2.setPrice(mockProduct.getPrice());
        product2.setName("unequal");
        assert(!product1.equals(product2));
        product2.setPicture(mockProduct.getPicture());
        assert(!product1.equals(product2));
    }
}