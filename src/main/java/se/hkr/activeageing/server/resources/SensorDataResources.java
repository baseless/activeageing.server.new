package se.hkr.activeageing.server.resources;

import io.swagger.annotations.*;
import se.hkr.activeageing.server.boundary.SensorDataEngine;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Orders;
import se.hkr.activeageing.server.entities.Sensordata;
import se.hkr.activeageing.server.entities.SensordataReportvalues;
import se.hkr.activeageing.server.viewmodels.SensorDataViewModel;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Path("sensordata")
@Stateless
@Api(value = "SensorData")
public class SensorDataResources {

    @Inject
    SensorDataEngine sensorEngine;

    @Inject
    protected ResponseHelper<Accounts> response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available manufacturers.",
            notes = "List all available manufacturers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned manufacturers."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response all() {
        return Response.ok().entity(new GenericEntity<Collection<Sensordata>>(sensorEngine.findAll()) {}).build();
    }

    @GET
    @Path("{id}/events")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available events from sensorData.",
            notes = "Returns all available events from sensorData.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response events_All(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id) {
        Optional<Collection<SensordataReportvalues>> orders = sensorEngine.allEvents(id);
        if(orders.isPresent()) {
            return response.getOk(new GenericEntity<Collection<SensordataReportvalues>>(orders.get()) {});
        }
        return response.getFailed("Could not retrieve events list");
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds sensor data.",
            notes = "Adds sensor data.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added sensor data."),
            @ApiResponse(code = 400, message = "Adding failed.") })
    public Response add(
            @ApiParam(value = "SensorDataViewModel", required = true)
            SensorDataViewModel item) {
            boolean result = sensorEngine.insert(item);
            if(result) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
    }
}
