package com.estore.api.estoreapi.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identified {
    @JsonProperty("id") public int id;

    /**
     * Retrieves the id of the object
     * @return The id of the object
     */
    public int getId() { return id; };
}