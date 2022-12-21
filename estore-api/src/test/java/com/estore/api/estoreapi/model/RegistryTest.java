package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Tag("Model-tier")
public class RegistryTest {
    private ObjectMapper mockObjectMapper;
    private Registry mockRegistry;
    private User tests[] = new User[2];

    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        tests[0] = new User(0, "username");
        tests[1] = new User(1, "USERNAME");

        when(
            mockObjectMapper.readValue(new File("testRegistry.json"), mockObjectMapper.constructType(User.class.arrayType()))
        ).thenReturn(tests);

        mockRegistry = new Registry("testRegistry.json", mockObjectMapper);
    }

    @Test
    public void getUsersTest() throws IOException {
        // Invoke
        ArrayList<User> result = mockRegistry.getUsers();
        
        // Analyse
        ArrayList<User> expected = new ArrayList<User>();
        expected.add(new User(0, "username"));
        expected.add(new User(1, "USERNAME"));
        assertEquals(expected, result);
    }

    @Test
    public void getUserTest() {
        // Setup
        int id = 0;
        User user = new User(0, "username");

        // Invoke
        User result = mockRegistry.getUser(id);

        // Analyze
        assertEquals(user, result);
    }

    @Test
    public void findUsersTest() {
        // Setup
        String username = "username";
        User user = new User(0, username);

        // Invoke
        ArrayList<User> result = mockRegistry.findUsers(username);

        // Analyze
        assert(user.equals(result.get(0)));
    }

    @Test
    public void createUserTest() throws IOException {
        // Setup
        User user = new User(0, "username");

        // Invoke
        User result = mockRegistry.createUser(user);

        // Analyze
        assertEquals(user, result);
    }

    @Test
    public void getUserByUsernameTest() throws IOException {
        // Setup
        User user = new User(0, "username");

        // Invoke
        User result = mockRegistry.getUserByUsername("username");

        // Analyze
        assertEquals(user, result);
    }

    @Test
    public void getUserByUsernameNullTest() throws IOException {
        // Invoke
        User result = mockRegistry.getUserByUsername(null);

        // Analyze
        assertEquals(null, result);
    }

    @Test
    public void getCartProductsTest() throws IOException {
        // Setup
        User user = new User(0, "username");
        Cart cart = user.getCart();

        // Invoke
        ArrayList<Product> result = mockRegistry.getCartProducts(0);

        // Analyze
        assertEquals(result, cart.getItems());
    }
}