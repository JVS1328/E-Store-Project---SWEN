package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the interface for user-related object persistence.
 * 
 * @author Benson Haley, Paul Harrison
 */
public interface RegistryDAO {
    ArrayList<Product> getCartProducts(int id) throws IOException;
    ArrayList<User> getUsers() throws IOException;
    User getUser(int id) throws IOException;
    ArrayList<User> findUsers(String username) throws IOException;
    User getUserByUsername(String username) throws IOException;
    User createUser(User user) throws IOException;
}