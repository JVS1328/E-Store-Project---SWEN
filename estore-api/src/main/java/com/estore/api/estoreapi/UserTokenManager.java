package com.estore.api.estoreapi;

import java.util.HashMap;
import java.util.UUID;

import com.estore.api.estoreapi.model.User;

/**
 * Manages user sessions
 * @author Paul Harrison
 *
 */
public class UserTokenManager {
	static HashMap<UUID, User> tokenmap = new HashMap<UUID, User>();
	
	/**
	 * Registers a session token for a user
	 * @param user user that is logging in
	 * @return session token for user
	 */
	public static String loginUser(User user) {
		UUID uuid = UUID.randomUUID();
		tokenmap.put(uuid, user);
		return uuid.toString();
	}
	
	/**
	 * Gets user by token
	 * @param token user token
	 * @return the user
	 */
	public static User getUserFromToken(String token) {
		UUID uuid = UUID.fromString(token);
		
		if (tokenmap.containsKey(uuid)) {
			User u = tokenmap.get(uuid);
			return u;
		} else {
			return null;
		}
	}
	
	/**
	 * Logs user out by token
	 * @param token user token
	 */
	public static void logoutUser(String token) {
		tokenmap.remove(UUID.fromString(token));
	}
}
