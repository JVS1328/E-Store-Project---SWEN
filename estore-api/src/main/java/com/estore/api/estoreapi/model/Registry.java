package com.estore.api.estoreapi.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.persistence.BasicPersistence;
import com.estore.api.estoreapi.persistence.RegistryDAO;

@Component
public class Registry extends BasicPersistence<User> implements RegistryDAO  {

    private static final Logger LOG = Logger.getLogger(Inventory.class.getName());

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public Registry(@Value("${registry.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(User.class);
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    public boolean save() throws IOException {
        ArrayList<User> users = getUsers();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), users.toArray());
        return true;
    }

    /**
     * Brings the user list
     * @return User list
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<User>(this.data.values());
    };

    /**
     * Gets a single user by id
     * @param id The id from which the corresponding user is found
     * @return The user
     */
    public User getUser(int id) {
        return this.data.get(id);
    }

    /**
     * Returns a given user's cart
     * @return The product list
     */
    public ArrayList<Product> getCartProducts(int id) {
        return new ArrayList<Product>(this.data.get(id).getCart().getItems());
    }

    /**
     * Searches users in the registry
     * @return list of users
     * @param username of the user
     */
    public ArrayList<User> findUsers(String username) {
        ArrayList<User> searchResult = new ArrayList<>(); 

        // First, convert the registry data (user list) into a stream, so that it can
        // be then filtered based on the lambda that specifies that each user within
        // the list should contain the given username.  For each member of the list
        // that is returned by the lambda expression, the member is added to the 
        // searchResult list, which is returned at the end of the function.
        this.data.values().stream()
        .filter(user -> user.getName().contains(username))
        .forEach(searchResult::add);

        return searchResult;
    };

    /**
     * Creates a user account.
     * @param user The user to add.
     * @throws IOException
     */
    public User createUser(User user) throws IOException {
        int id = nextId();
        user.id = id;
        this.data.put(id, user);
        save();
        return user;
    }

    /**
     * Get user by username
     * @param username username
     * @return the user matching
     * @throws IOException
     * @author Paul Harrison
     */
    public User getUserByUsername(String username) throws IOException {
    	 ArrayList<User> searchResult = new ArrayList<>(); 

         // First, convert the registry data (user list) into a stream, so that it can
         // be then filtered based on the lambda that specifies that each user within
         // the list should contain the given username.  For each member of the list
         // that is returned by the lambda expression, the member is added to the 
         // searchResult list, which is returned at the end of the function.
         this.data.values().stream()
         .filter(user -> user.getName().equals(username))
         .forEach(searchResult::add);

         if (searchResult.size() > 0) {
        	 return searchResult.get(0);
         } else {
        	 return null;
         }
    }


}