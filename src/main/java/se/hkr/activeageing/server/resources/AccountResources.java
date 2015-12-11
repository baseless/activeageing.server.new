package se.hkr.activeageing.server.resources;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import se.hkr.activeageing.server.boundary.AccountEngine;
import se.hkr.activeageing.server.boundary.OrderEngine;
import se.hkr.activeageing.server.boundary.OrderItemEngine;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.entities.*;
import se.hkr.activeageing.server.viewmodels.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

/**
 * Account resources.
 * Created by Daniel Ryhle on 2015-11-09.
 */
@Path("accounts")
@Stateless
@Api(value = "Accounts")
public class AccountResources {

    //@Context
    //ResourceContext rc;

    @Inject
    @DefaultLogger
    private Logger logger;

    @Inject
    AccountEngine accountEngine;

    @Inject
    private OrderEngine orderEngine;

    @Inject
    private OrderItemEngine orderItemEngine;

    @Inject
    protected ResponseHelper<Accounts> response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available accounts.",
            notes = "List all available accounts.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned accounts."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response all() {
        Optional<Collection<Accounts>> accounts = accountEngine.all();
        if(accounts.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Accounts>>(accounts.get()) {});
        }
        return response.getFailed("Could not retrieve account list");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns an account based on id.",
            notes = "Returns an account based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned account."),
            @ApiResponse(code = 404, message = "Account not found.") })
    public Response get(
            @ApiParam(value = "the id of the account.", required = true)
            @PathParam("id") int id) {
        Optional<Accounts> code = accountEngine.get(id);
        if(code.isPresent()) {
            return response.getOk(code.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes an account based on id.",
            notes = "Deletes an account based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted account."),
            @ApiResponse(code = 400, message = "Delete failed.") })
    public Response delete(
            @ApiParam(value = "the id of the account.", required = true)
            @PathParam("id") int id) {
        boolean requestSucceeded = accountEngine.remove(id);
        if(requestSucceeded) {
            return response.deleteOk("ZipCode with id '" + id + "' removed successfully");
        }
        return response.deleteFailed("Failed to modify account");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds an account.",
            notes = "Adds an account.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created account."),
            @ApiResponse(code = 400, message = "Create failed.") })
    public Response add(
            @ApiParam(value = "AccountAddViewModel", required = true)
            AccountAddViewModel item) {
        int resultId = accountEngine.add(item);
        if(resultId != 0) {
            return response.postOk("id",String.valueOf(resultId));
        }
        return response.postFailed("Failed to add account");
    }

    @PUT
    @Path("{id}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Change password on an account.",
            notes = "Change password on an account")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully changed account password."),
            @ApiResponse(code = 400, message = "Password change failed.") })
    public Response changePassword(
            @ApiParam(value = "id of the account.", required = true)
            @PathParam("id") int id,
            @ApiParam(value = "AccountPasswordViewModel", required = true)
            AccountPasswordViewModel pswdViewModel) {
        boolean requestSucceeded = accountEngine.changePassword(id, pswdViewModel);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Could not change password (old password invalid?)");
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edit an account.",
            notes = "Edit an account.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Edit succeeded."),
            @ApiResponse(code = 400, message = "Edit failed.") })
    public Response edit(
            @ApiParam(value = "the id of the account.", required = true)
            @PathParam("id") int id, AccountEditViewModel item) {
        boolean requestSucceeded = accountEngine.edit(id, item);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify account");


    }

    @POST
    @Path("/check/email")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Check if an email is in use in the database.",
            notes = "Returns {'result':'taken'} if in use. Otherwise returns {'result':'free'}.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succeeded to check if email is in use."),
            @ApiResponse(code = 400, message = "Failed to verify if email is in use.") })
    public Response emailExists(
            AccountEmailViewModel emailViewModel) {
        int result = accountEngine.emailExists(emailViewModel.getEmail());
        switch(result) {
            case 1: return response.postOk("result", "free");
            case 2: return response.postOk("result", "taken");
            default: return response.postFailed("Error occurred while verifying if email exists");
        }
    }

    @POST
    @Path("/check/username")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Check if an username is in use in the database.",
            notes = "Returns {'result':'taken'} if in use. Otherwise returns {'result':'free'}.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succeeded to check if username is in use."),
            @ApiResponse(code = 400, message = "Failed to verify if username is in use.") })
    public Response userNameExists(
            AccountUserViewModel userViewModel) {
        int result = accountEngine.userNameExists(userViewModel.getUserName());
        switch(result) {
            case 1: return response.postOk("result", "free");
            case 2: return response.postOk("result", "taken");
            default: return response.postFailed("Error occurred while verifying if username exists");
        }
    }

    //----------------------------- ORDERS SUBRESOURCES --------------------------------

    @GET
    @Path("{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available orders from user.",
            notes = "Returns all available orders from user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response order_All(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id) {
        Optional<Collection<Orders>> orders = orderEngine.all(id);
        if(orders.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Orders>>(orders.get()) {});
        }
        return response.getFailed("Could not retrieve order list");
    }

    @GET
    @Path("{id}/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns an order.",
            notes = "Returns an order.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Returns failed.") })
    public Response order_Get(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId) {
        Optional<Orders> orderResult = orderEngine.get(orderId, id);
        if(orderResult.isPresent()) {
            return response.getOkCommon(orderResult.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}/orders/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes an order.",
            notes = "Deletes an order.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Delete succeeded."),
            @ApiResponse(code = 400, message = "Delete failed.") })
    public Response order_Delete(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId) {
        boolean requestSucceeded = orderEngine.remove(orderId, id);
        if(requestSucceeded) {
            return response.deleteOk("order with id '" + id + "' removed successfully");
        }
        return response.deleteFailed("Failed to modify order");
    }

    @POST
    @Path("{id}/orders")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds an order.",
            notes = "Adds an order.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succeeded in adding order."),
            @ApiResponse(code = 400, message = "Failed to add order.") })
    public Response order_Add(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id) {
        int createdId = orderEngine.add(new OrderViewModel(), id);
        if(createdId != 0) {
            return response.postOk("id", String.valueOf(createdId));
        }
        return response.postFailed("Failed to add order");
    }

    @PUT
    @Path("{id}/orders/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edits an order.",
            notes = "Edits an order.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succeeded in editing order."),
            @ApiResponse(code = 400, message = "Editing order failed.") })
    public Response order_Edit(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id,
            @ApiParam(value = "OrderViewModel.", required = true)
            OrderViewModel item,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId) {
        boolean requestSucceeded = orderEngine.edit(orderId, item, id);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify order");
    }

    //----------------------------- ORDERITEMS SUBRESOURCES --------------------------------

    @GET
    @Path("{id}/orders/{orderId}/items")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns an order items list",
            notes = "Returns an order items list.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned successfully."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response allOrderItemsForOrder(@ApiParam(value = "the id of the order you like to view items for.", required = true) @PathParam("orderId") int orderId) {
        Optional<Collection<Orderitems>> orders = orderItemEngine.all(orderId);
        if(orders.isPresent()) {
            return Response.ok().entity(new GenericEntity<Collection<Orderitems>>(orders.get()) {}).build();
        }
        else {
            return response.getFailed("Failed to retrieve items for order");
        }
    }

    @GET
    @Path("{id}/orders/{orderId}/items/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a specified item in a orderlist.",
            notes = "Returns a specified item in a orderlist..")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return successed."),
            @ApiResponse(code = 404, message = "Not found.") })
    public Response orders_orderitems_Get(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int accountId,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId,
            @ApiParam(value = "the id of the item.", required = true)
            @PathParam("itemId") int itemId) {
        Optional<Orderitems> orderResult = orderItemEngine.get(itemId, orderId, accountId);
        if(orderResult.isPresent()) {
            return response.getOkCommon(orderResult.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}/orders/{orderId}/items/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes a specified item in a orderlist.",
            notes = "Deletes a specified item in a orderlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item deleted successfully."),
            @ApiResponse(code = 400, message = "Delete of item failed.") })
    public Response orders_orderitems_Delete(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int accountId,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId,
            @ApiParam(value = "the id of the item.", required = true)
            @PathParam("itemId") int itemId) {
        boolean requestSucceeded = orderItemEngine.remove(itemId, orderId, accountId);
        if(requestSucceeded) {
            return response.deleteOk("order with id '" + itemId + "' removed successfully");
        }
        return response.deleteFailed("Failed to modify order");
    }

    @POST
    @Path("{id}/orders/{orderId}/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds an item in a orderlist.",
            notes = "Adds an item in a orderlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item added successfully."),
            @ApiResponse(code = 400, message = "Failed in adding item.") })
    public Response orders_orderitems_Add(
            @ApiParam(value = "OrderItemViewModel.", required = true)
            OrderItemViewModel orderItemViewModel,
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int accountId,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId) {
        int createdId = orderItemEngine.add(orderItemViewModel, orderId, accountId);
        if(createdId != 0) {
            return response.postOk("id", String.valueOf(createdId));
        }
        return response.postFailed("Failed to add order");
    }

    @PUT
    @Path("{id}/orders/{orderId}/items/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edits an item in a orderlist.",
            notes = "Edits an item in a orderlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item edited successfully."),
            @ApiResponse(code = 400, message = "Failed in editing item.") })
    public Response orders_orderitems_Edit(
            @ApiParam(value = "OrderItemViewModel.", required = true)
            OrderItemViewModel orderItemViewModel,
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int accountId,
            @ApiParam(value = "the id of the order.", required = true)
            @PathParam("orderId") int orderId,
            @ApiParam(value = "the id of the item.", required = true)
            @PathParam("itemId") int itemId) {
        boolean requestSucceeded = orderItemEngine.edit(orderItemViewModel, itemId, orderId, accountId);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify order");
    }

    //-------------------------- MANUFACTURERS SUBRESOURCES -------------------------
    @GET
    @Path("{id}/manages/manufacturers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all manufacturers linked to the specified id.",
            notes = "Returns all manufacturers linked to the specified id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Returns failed.") })
    public Response manufacturers_get(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id) {
        Optional<Collection<Manufacturers>> manufacturersResult = accountEngine.getAllManufacturers(id);
        if(manufacturersResult.isPresent()) {
            return response.getOkCommon(new GenericEntity<Collection<Manufacturers>>(manufacturersResult.get()){});
        }
        return response.getFailed();
    }

    //-------------------------- TRANSPORTERS SUBRESOURCES -------------------------
    @GET
    @Path("{id}/manages/transporters")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all transporters linked to the specified id.",
            notes = "Returns all transporters linked to the specified id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return succeeded."),
            @ApiResponse(code = 400, message = "Returns failed.") })
    public Response transporters_getAll(
            @ApiParam(value = "the id of the user.", required = true)
            @PathParam("id") int id) {
        Optional<Collection<Transporters>> transportsResult = accountEngine.getAllTransporters(id);
        if(transportsResult.isPresent()) {
            return response.getOkCommon(new GenericEntity<Collection<Transporters>>(transportsResult.get()){});
        }
        return response.getFailed();
    }
}
