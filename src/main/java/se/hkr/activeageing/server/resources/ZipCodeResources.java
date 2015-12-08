package se.hkr.activeageing.server.resources;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import se.hkr.activeageing.server.boundary.ZipCodeEngine;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.entities.Zipcodes;
import se.hkr.activeageing.server.viewmodels.ZipCodeViewModel;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

/**
 * ZipCodeResources
 * Created by Daniel Ryhle & Andreas Svensson on 2015-11-09.
 */
@Path("zipcodes")
@Stateless
@Api(value = "Zipcodes")
public class ZipCodeResources {

    @Inject
    @DefaultLogger
    private Logger logger;

    @Inject
    ZipCodeEngine zipCodeEngine;

    @Inject
    protected ResponseHelper<Zipcodes> response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available zipcodes.",
            notes = "List all available zipcodes.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned zipcodes."),
            @ApiResponse(code = 400, message = "Failed to return zipcodes.") })
    public Response all() {
        Optional<Collection<Zipcodes>> codes = zipCodeEngine.all();
        if(codes.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Zipcodes>>(codes.get()) {});
        }
        return response.getFailed("Could not retrieve zipCode list");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a zipcode based on id.",
            notes = "Returns a zipcode based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned zipcode."),
            @ApiResponse(code = 404, message = "Zipcode not found.") })
    public Response get(
            @ApiParam(value = "the id of the zipcode.", required = true)
            @PathParam("id") int id) {
        Optional<Zipcodes> code = zipCodeEngine.get(id);
        if(code.isPresent()) {
            return response.getOk(code.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes a zipcode based on id.",
            notes = "Deletes a zipcode based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted zipcode."),
            @ApiResponse(code = 400, message = "Failed to delete zipcode.") })
    public Response delete(
            @ApiParam(value = "the id of the zipcode.", required = true)
            @PathParam("id") int id) {
        boolean requestSucceeded = zipCodeEngine.remove(id);
        if(requestSucceeded) {
            return response.deleteOk("ZipCode with id '" + id + "' removed successfully");
        }
        return response.deleteFailed("Failed to delete ZipCode");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds a zipcode.",
            notes = "Adds a zipcode.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added zipcode."),
            @ApiResponse(code = 400, message = "Failed to add zipcode.") })
    public Response add(
            @ApiParam(value = "ZipCodeViewModel", required = true)
            ZipCodeViewModel item) {
        int resultId = zipCodeEngine.add(item);
        if(resultId != 0) {
            return response.postOk();
        }
        return response.postFailed("Failed to add ZipCode");
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edit a zipcode.",
            notes = "Edit a zipcode.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully modified zipcode."),
            @ApiResponse(code = 400, message = "Failed to modify zipcode.") })
    public Response edit(
            @ApiParam(value = "the id of the zipcode.", required = true)
            @PathParam("id") int id,ZipCodeViewModel item) {
        boolean requestSucceeded = zipCodeEngine.edit(id, item);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify ZipCode");
    }
}
