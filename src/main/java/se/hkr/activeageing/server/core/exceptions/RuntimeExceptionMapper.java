package se.hkr.activeageing.server.core.exceptions;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Used to gracefully handle occurred runtime exceptions.
 * Created by Daniel Ryhle on 2015-10-17.
 */
@Provider
 public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Inject
    @DefaultLogger
    private Logger logger;

    @Override
    public Response toResponse(RuntimeException e) {
        logger.error(e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("X-Reason", e.getMessage()).build();
    }
}
