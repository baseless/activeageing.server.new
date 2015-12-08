package se.hkr.activeageing.server.resources;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import se.hkr.activeageing.server.boundary.TransportManagerEngine;
import se.hkr.activeageing.server.boundary.TransporterEngine;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Transporters;
import se.hkr.activeageing.server.viewmodels.ManagerViewModel;
import se.hkr.activeageing.server.viewmodels.TransporterViewModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by base on 2015-11-10.
 */
@Path("transporters")
@Api(value = "Transporters")
public class TransporterResources {

    @Inject
    @DefaultLogger
    private Logger logger;

    @Inject
    TransporterEngine transporterEngine;

    @Inject
    TransportManagerEngine transportManagerEngine;

    @Inject
    private ResponseHelper<Transporters> response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available transporters.",
            notes = "List all available transporters.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned transporters."),
            @ApiResponse(code = 400, message = "Failed to return transporters.") })
    public Response all() {
        Optional<Collection<Transporters>> transporters = transporterEngine.all();
        if(transporters.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Transporters>>(transporters.get()) {});
        }
        return response.getFailed("Could not retrieve transporters list");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a transporter based on id.",
            notes = "Returns a transporter based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned transporter."),
            @ApiResponse(code = 404, message = "Transporter not found.") })
    public Response get(
            @ApiParam(value = "the id of the transporter.", required = true)
            @PathParam("id") int id) {
        Optional<Transporters> code = transporterEngine.get(id);
        if(code.isPresent()) {
            return response.getOk(code.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes a transporter based on id.",
            notes = "Deletes a transporter based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted transporter."),
            @ApiResponse(code = 400, message = "Failed to delete transporter.") })
    public Response delete(
            @ApiParam(value = "the id of the transporter.", required = true)
            @PathParam("id") int id) {
        boolean requestSucceeded = transporterEngine.remove(id);
        if(requestSucceeded) {
            return response.deleteOk("Transporter with id '" + id + "' removed successfully");
        }
        return response.deleteFailed("Failed to delete transporter");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds a transporter.",
            notes = "Adds a transporter.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added transporter."),
            @ApiResponse(code = 400, message = "Failed to add transporter.") })
    public Response add(
            @ApiParam(value = "TransporterViewModel", required = true)
            TransporterViewModel item) {
        int resultId = transporterEngine.add(item);
        if(resultId != 0) {
            return response.postOk(resultId);
        }
        return response.postFailed("Failed to add transporter");
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edit a transporter.",
            notes = "Edit a transporter.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully modified transporter."),
            @ApiResponse(code = 400, message = "Failed to modify transporter.") })
    public Response edit(
            @ApiParam(value = "the id of the transporter.", required = true)
            @PathParam("id") int id, TransporterViewModel item) {
        boolean requestSucceeded = transporterEngine.edit(id, item);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify transporter");
    }

    // ------------------------ MANAGER RESOURCES-------------------------------

    @GET
    @Path("{id}/managers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available managers for selected transporter.",
            notes = "Returns a list containing all manager account for the transporter.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned transporter managers."),
            @ApiResponse(code = 400, message = "Failed to rertrieve manager list.") })
    public Response managers_all(@PathParam("id")int accountId) {
        Optional<Collection<Accounts>> accounts = transportManagerEngine.all(accountId);
        if(accounts.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Accounts>>(accounts.get()) {});
        }
        return response.getFailed("Could not retrieve transporter manager list");
    }

    @DELETE
    @Path("{id}/managers/{accountId}") // DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Remove an account as manager of a transporters.",
            notes = "This method removes the account associated with id in url from the transporter with the corresponding id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted manager-transporter relationship."),
            @ApiResponse(code = 400, message = "Failed to delete manager-transporter relationship.") })
    public Response managers_delete(
            @ApiParam(value = "the id of the transporter.", required = true)
            @PathParam("id") int transporterId,
            @ApiParam(value = "the id of the manager (account).", required = true)
            @PathParam("accountId") int accountId) {
        boolean requestSucceeded = transportManagerEngine.remove(accountId, transporterId);
        if(requestSucceeded) {
            return response.deleteOk("Manager (account) with id '" + accountId + "' removed successfully");
        }
        return response.deleteFailed("Failed to remove manager relationship with transporter");
    }

    @POST
    @Path("{id}/managers") // CHANGE
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds an account as manager of a transporter.",
            notes = "This method adds an account to the transporter with the corresponding id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added manager."),
            @ApiResponse(code = 400, message = "Failed to add manager.") })
    public Response managers_add(
            ManagerViewModel managerViewModel,
            @ApiParam(value = "the id of the transporter.", required = true)
            @PathParam("id") int transporterId) {
        boolean requestSucceeded = transportManagerEngine.addManager(managerViewModel, transporterId);
        if(requestSucceeded) {
            return response.postOk("Manager (account) added to transporter with id '" + transporterId + "' successfully");
        }
        return response.postFailed("Failed to add manager relationship with transporter");
    }
}
