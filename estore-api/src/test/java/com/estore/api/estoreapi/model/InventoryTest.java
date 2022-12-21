package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

@Tag("Model-tier")
public class InventoryTest {
    private ObjectMapper mockObjectMapper;
    private Inventory mockInventory;
    private Product tests[] = new Product[2];
    final String DEFAULT_PICTURE = "/assets/car.png";

    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        tests[0] = new Product(0, "0", null, 0, DEFAULT_PICTURE);
        tests[1] = new Product(1, "1", null, 0, DEFAULT_PICTURE);

        when(
            mockObjectMapper.readValue(new File("testInventory.json"), mockObjectMapper.constructType(Product.class.arrayType()))
        ).thenReturn(tests);

        mockInventory = new Inventory("testInventory.json", mockObjectMapper);
    }

    @Test
    public void getProductsTest() throws IOException {
        // Invoke
        ArrayList<Product> result = mockInventory.getProducts();
        
        // Analyse
        ArrayList<Product> expected = new ArrayList<Product>();
        expected.add(new Product(0, "0", null, 0, DEFAULT_PICTURE));
        expected.add(new Product(1, "1", null, 0, DEFAULT_PICTURE));
        assertEquals(expected, result);
    }

    @Test
    public void getProductTest() throws IOException {
        // Setup
        int id = 0;
        Product product = new Product(0, "0", null, 0, DEFAULT_PICTURE);

        // Invoke
        Product result = mockInventory.getProduct(id);

        // Analyze
        assertEquals(product, result);
    }

    @Test
    public void findUsersTest() throws IOException {
        // Setup
        Product product = new Product(0, "0", null, 0, DEFAULT_PICTURE);

        // Invoke
        ArrayList<Product> result = mockInventory.findProducts(product.getName());

        // Analyze
        assert(product.equals(result.get(0)));
    }

    @Test
    public void createProductTest() throws IOException {
        // Setup
        Product product = new Product(0, "0", null, 0, DEFAULT_PICTURE);

        // Invoke
        Product result = mockInventory.createProduct(product);

        // Analyze
        assertEquals(product, result);
    }

    @Test
    public void updateProductTest() throws IOException {
        // Setup
        final int UPDATED = 99;
        Product product = new Product(0, null, null, 0, DEFAULT_PICTURE);
        Product updated = new Product(0, "updated", "updated", UPDATED, DEFAULT_PICTURE);
        Product another = new Product(0, null, null, 0, DEFAULT_PICTURE);
        updated.setQuantity(UPDATED);
        mockInventory.data = new TreeMap<Integer, Product>();
        mockInventory.data.put(0, product);

        // Invoke
        Product result = mockInventory.updateProduct(updated);
        result = mockInventory.updateProduct(another);
        result = mockInventory.updateProduct(product);
        
        
        // Analyze
        assertEquals(product, result);

    }

    @Test
    public void updateProductTestFails() throws IOException {
        // Setup
        Product product = new Product(99, null, null, 0, DEFAULT_PICTURE); // Intentionally nonexistent product.
        
        // Invoke
        Product result = mockInventory.updateProduct(product);

        // Analyze
        assertEquals(result, null);
    }

    @Test
    public void removeProductTest() throws IOException {
        // Setup
        Product product1 = new Product(0, "0", null, 0, DEFAULT_PICTURE);
        Product product2 = new Product(99, null, null, 0, DEFAULT_PICTURE);

        // Invoke
        boolean result1 = mockInventory.removeProduct(product1.getId());
        boolean result2 = mockInventory.removeProduct(product2.getId());

        // Analyze
        assert(result1);
        assert(!result2);
    }

}