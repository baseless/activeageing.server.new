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
    @ApiOperation(value = "Returns all available sensorData.",
            notes = "List all available sensorData.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned sensorData."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response all() {
        return Response.ok().entity(new GenericEntity<Collection<Sensordata>>(sensorEngine.findAll()) {}).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns sensorData.",
            notes = "Returns sensorData.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response getSensorData(
            @ApiParam(value = "the id of the sensorData.", required = true)
            @PathParam("id") int id) {
        Optional<Sensordata> sensorData = sensorEngine.getSensorData(id);
        if(sensorData.isPresent()) {
            return response.getOkCommon(sensorData.get());
        }
        return response.getFailed("Could not retrieve events list");
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
            int result = sensorEngine.insert(item);
            if(result != 0) {
                return Response.status(Response.Status.CREATED).header("X-Location", "https://activeageing.se/resources/sensordata/" + result).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
    }

    @GET
    @Path("/bydate/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available sensor data from specified date to current date.",
            notes = "Returns all available sensor data from specified date to current date.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response sort_date(
            @ApiParam(value = "the specified date.", required = true)
            @PathParam("date") String date) {

        Optional<Collection<Sensordata>> sensordata = sensorEngine.sortByDate(date);

        if(sensordata.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Sensordata>>(sensordata.get()) {});
        }
        return response.getFailed("Could not retrieve sensor list");
    }

    @GET
    @Path("/bydate/{fromdate}/{todate}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available sensor data from specified date to current date.",
            notes = "Returns all available sensor data from specified date to current date.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response sort_date(
            @ApiParam(value = "from the specified date.", required = true)
            @PathParam("fromdate") String fromDate,
            @ApiParam(value = "to the specified date.", required = true)
            @PathParam("todate") String toDate) {

        Optional<Collection<Sensordata>> sensordata = sensorEngine.sortByDate(fromDate,toDate);

        if(sensordata.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Sensordata>>(sensordata.get()) {});
        }
        return response.getFailed("Could not retrieve sensor list");
    }
}
