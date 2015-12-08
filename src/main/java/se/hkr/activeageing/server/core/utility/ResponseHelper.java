package se.hkr.activeageing.server.core.utility;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 * Helper class used to handle all resource responses in a single class.
 * Created by Daniel Ryhle on 2015-10-24.
 */
public class ResponseHelper<TEntity> {

    /**
     * Used for all collections that is to be marshalled
     * https://jax-rs-spec.java.net/nonav/2.0/apidocs/javax/ws/rs/core/GenericEntity.html
     */
    public final Response getOk(GenericEntity obj) { return Response.status(Response.Status.OK).entity(obj).build(); }
    public final Response getOk(String message) { return  Response.status(Response.Status.OK).header("X-Reason", message).build(); }
    public final Response getOk(TEntity obj) { return Response.status(Response.Status.OK).entity(obj).build(); }
    public final Response getOkCommon(Object obj) { return Response.status(Response.Status.OK).entity(obj).build(); }
    public final Response getOk(String param, String value) { return Response.status(Response.Status.OK).entity("{\"" + param +"\":\"" + value + "\"}").build(); }

    public final Response getFailed() { return badRequest(); }
    public final Response getFailed(String reason) { return badRequest(reason); }
    public final Response getNotFound() { return Response.status(Response.Status.NOT_FOUND).build(); }

    public final Response postOk() { return created(); }
    public final Response postOk(String message) { return  created(message); }
    public final Response postOk(int id) { return  postOk("id", String.valueOf(id)); }
    public final Response postOk(String title, String value) { return  Response.status(Response.Status.CREATED).entity("{\"" + title + "\":\"" + value + "\"}").build(); }

    public final Response postFailed() { return badRequest(); }
    public final Response postFailed(String reason) { return badRequest(reason); }
    public final Response postAuthenticationFailed(String reason) { return Response.status(Response.Status.UNAUTHORIZED).header("X-Reason", reason).build(); }

    public final Response putOk() { return created(); }
    public final Response putOk(String message) { return  created(message); }
    public final Response putFailed() { return badRequest(); }
    public final Response putFailed(String reason) { return badRequest(reason); }


    public final Response deleteOk() { return created(); }
    public final Response deleteOk(String message) { return  created(message); }
    public final Response deleteFailed() { return badRequest(); }
    public final Response deleteFailed(String reason) { return badRequest(reason); }


    public final Response created() { return Response.status(Response.Status.CREATED).build(); }
    public final Response created(String message) { return Response.status(Response.Status.CREATED).header("X-Reason", message).build(); }
    private final Response badRequest() { return Response.status(Response.Status.BAD_REQUEST).build(); }
    private final Response badRequest(String reason) { return Response.status(Response.Status.BAD_REQUEST).header("X-Reason", reason).build(); }
    public final Response unathorized() { return Response.status(Response.Status.UNAUTHORIZED).build(); }
}
