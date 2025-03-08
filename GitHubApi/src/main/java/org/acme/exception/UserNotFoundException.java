package org.acme.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class UserNotFoundException extends WebApplicationException {

    public UserNotFoundException(String username) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(404, "User " + username + " not found"))
                .build());
    }

}
