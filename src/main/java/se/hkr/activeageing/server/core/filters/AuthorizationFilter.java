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
import java.net.URI;
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

    private static String USER = "aa";
    private static String PSWD = "abc123";
    private static String SENSOR_USER = "tsi!push";
    private static String SENSOR_PSWD = "zaerohjuyo4AiBeesh2eemu8ohyeet5e";

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        MultivaluedMap<String, String> headers = context.getHeaders();
        UriInfo uri = context.getUriInfo();
        Map.Entry<String, String> auth = getUserNameAndPassword(headers);
        logger.debug("clientAddress:'" + servletRequest.getRemoteAddr() + "', urlRequested:' " +  uri.getAbsolutePath() + " [" + uri.getPath() +  "]', username + '" + auth.getKey() + "', password '" + auth.getValue() + "'");
        if(!authenticate(uri, auth.getKey(), auth.getValue())) {
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build());
        }
    }

    private boolean authenticate(UriInfo uri, String userName, String password) {
        String path = uri.getPath();
        logger.debug("STARTED, CHECKING " + path);
        if(path.equalsIgnoreCase("authentication")) {
            logger.debug("AUTHENTICATION METHOD");
            return true; //this path is unprotected, just to authenticate users.
        }
        if(path.toLowerCase().startsWith("sensordata")) {
            logger.debug("GOT TO SENSORDATAA METHOD");
            if((userName.toLowerCase().equals(SENSOR_USER) && password.equals(SENSOR_PSWD)) || (userName.toLowerCase().equals(USER) && password.equals(PSWD))) {
                return true;
            }
            return false;
        }
        if((userName.toLowerCase().equals(USER) && password.equals(PSWD)) || 1 == 1) { //here will go the main auth code. Always true atm
            return true;
        }
        logger.debug(uri.getPath());
        return false;
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
        return new AbstractMap.SimpleEntry<>("", "");
    }
}
