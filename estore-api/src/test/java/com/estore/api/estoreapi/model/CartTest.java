package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class CartTest {
	final String DEFAULT_PICTURE = "/assets/car.png";
    private Cart mockCart;
    private Product product = new Product(0, "name", "description", 0, DEFAULT_PICTURE);

    @BeforeEach
    public void setup() throws IOException {
        mockCart = new Cart();
        mockCart.addItem(product);
    }

    @Test
    public void getItemsTest() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(product);
        assertEquals(mockCart.getItems(), list);
    }
    
    @Test
    public void setItemsTest() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(product);
        mockCart.setItems(list);
        assertEquals(mockCart.getItems(), list);
    }

    @Test
    public void addItemTest() {
        mockCart.addItem(new Product(1, null, null, 0, DEFAULT_PICTURE));
        assert(mockCart.getItems().size() == 2);
    }

    @Test
    public void removeItemTest() {
        addItemTest();
        mockCart.removeItem(product);
        assert(mockCart.getItems().size() == 1);
    }
    
}