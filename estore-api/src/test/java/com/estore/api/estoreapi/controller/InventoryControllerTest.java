package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Inventory;
import com.estore.api.estoreapi.model.Product;

@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController mockInventoryController;
    private Inventory mockInventory;

    final String DEFAULT_PICTURE = "/assets/car.png";

    /**
     * Before each test, create a new InventoryController object and inject
     * a mock DAO.
     */
    @BeforeEach
    public void setupHeroController() {
        mockInventory = mock(Inventory.class);
        mockInventoryController = new InventoryController(mockInventory);
    }

    @Test
    public void getProductTestException() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        doThrow(new IOException()).when(mockInventory).getProduct(product.id); // Throw an IOException.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.getProduct(product.id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void getProductsTestException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockInventory).getProducts(); // Throw an IOException.

        // Invoke
        ResponseEntity<ArrayList<Product>> response = mockInventoryController.getProducts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void searchProductsTestException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockInventory).findProducts(null); // Throw an IOException.

        // Invoke
        ResponseEntity<ArrayList<Product>> response = mockInventoryController.searchProducts(null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void getProductTest() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockInventory.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void getProductTestNotFound() throws Exception {
        // Setup
        int id = 999;
        when(mockInventory.getProduct(id)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.getProduct(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getProductsTest() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(product);
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockInventory.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<ArrayList<Product>> response = mockInventoryController.getProducts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void createProductTest() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        when(mockInventory.createProduct(product)).thenReturn(product); // Simulate success.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void createProductTestFails() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        //ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.CONFLICT);
        when(mockInventory.createProduct(product)).thenReturn(null);; // Simulate failure.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void createProductTestException() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        doThrow(new IOException()).when(mockInventory).createProduct(product); // Throw an IOException.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void createProductTestDuplicate() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        ArrayList<Product> list = new ArrayList<Product>();
        list.add(product);
        when(mockInventory.findProducts(product.getName())).thenReturn(list); // Simulate duplicate item.
    
        // Invoke
        ResponseEntity<Product> response = mockInventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void updateProductTest() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        when(mockInventory.updateProduct(product)).thenReturn(product); // Simulate success.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void updateProductTestFails() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        when(mockInventory.updateProduct(product)).thenReturn(null); // Simulate failure.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void updateProductTestException() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        doThrow(new IOException()).when(mockInventory).updateProduct(product); // Throw an IOException.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void deleteProductTest() throws IOException {
        // Setup
        when(mockInventory.removeProduct(0)).thenReturn(true); // Simulate success.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.deleteProduct(0);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void deleteProductTestFails() throws IOException {
        // Setup
        when(mockInventory.removeProduct(0)).thenReturn(false); // Simulate failure.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.deleteProduct(0);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void deleteProductTestException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockInventory).removeProduct(0); // Throw an IOException.

        // Invoke
        ResponseEntity<Product> response = mockInventoryController.deleteProduct(0);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void searchProductsTest() throws IOException {
        // Setup
        Product product = new Product(1,"name", "description", 0.0f, DEFAULT_PICTURE);
        ArrayList<Product> list = new ArrayList<Product>();
        list.add(product);
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockInventory.findProducts(product.getName())).thenReturn(list);

        // Invoke
        ResponseEntity<ArrayList<Product>> response = mockInventoryController.searchProducts(product.getName());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(list, response.getBody());
    }
}