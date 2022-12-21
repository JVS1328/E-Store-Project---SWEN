package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {
    private User mockUser;

    @BeforeEach
    public void setup() throws IOException {
        mockUser = new User(10, "Charles Tamzarian");
    }

    @Test
    public void getNameTest() {
        assertEquals(mockUser.getName(), "Charles Tamzarian");
    }
    
    @Test
    public void getIdTest() {
        assertEquals(mockUser.getId(), 10);
    }
    
    @Test
    public void isAdmin() {
    	assertFalse(mockUser.isAdmin());
    	mockUser.makeAdmin();
    	assertTrue(mockUser.isAdmin());
    }

    @Test
    public void equalsTest() {
        User user1 = new User(1, "name1");
        User user2 = new User(2, "name2");
        User user3 = new User(1, "name2");
        User user4 = new User(2, "name1");
        assert(user1.equals(user1));
        assert(!user1.equals(user2));
        assert(!user1.equals(user3));
        assert(!user2.equals(user4));
    }
}