package se.hkr.activeageing.server.core.filters;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.*;

/**
 * Global filter used for authentication and authorization of client requests.
 * Simple BASIC authentication which verifies account against database.
 * Created by Daniel Ryhle on 2015-10-24.
 */
@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject @DefaultLogger
    private Logger logger;

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        MultivaluedMap<String, String> headers = context.getHeaders();
        UriInfo uri = context.getUriInfo();
        logger.debug("New request -> clientAddress:'" + servletRequest.getRemoteAddr() + "', urlRequested:' " +  uri.getAbsolutePath() + "'");
        Map.Entry<String, String> auth = getUserNameAndPassword(headers);
        if(auth != null) {
            logger.debug("Basic authentication included, username:'" + auth.getKey() + "', password:'" + auth.getValue() + "'");
        }

        //context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build());
    }

    private Map.Entry<String,String> getUserNameAndPassword(MultivaluedMap<String, String> headers) {

        List<String> authHeaders = headers.get("authorization");
        if(authHeaders != null && authHeaders.size() > 0) {
            String authParam = authHeaders.get(0);
            final String[] authEncoded = authParam.split(" ");
            if(authEncoded.length == 2) {
                try {
                    String authDecoded = new String(Base64.getDecoder().decode(authEncoded[1]));
                    final StringTokenizer token = new StringTokenizer(authDecoded, ":");
                    final String username = token.nextToken();
                    final String password = token.nextToken();
                    return new AbstractMap.SimpleEntry<>(username, password);
                } catch (Exception e) {
                    logger.error("Error decoding username and password ( " + e.getMessage() + ")");
                }
            }
        }
        return null;
    }
}
