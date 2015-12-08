package se.hkr.activeageing.server.resources;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import se.hkr.activeageing.server.boundary.ManagerEngine;
import se.hkr.activeageing.server.boundary.ManufacturerEngine;
import se.hkr.activeageing.server.boundary.ProductEngine;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Manufacturers;
import se.hkr.activeageing.server.entities.Products;
import se.hkr.activeageing.server.viewmodels.ManagerViewModel;
import se.hkr.activeageing.server.viewmodels.ManufacturerViewModel;
import se.hkr.activeageing.server.viewmodels.ProductViewModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

/**
 * Manufacturer resources class.
 * Created by Daniel Ryhle & Emil Eider & Magnus Kanfjäll on 2015-11-10.
 */
@Path("manufacturers")
@Stateless
@Api(value = "Manufacturers")
public class ManufacturerResources {

    @Inject
    @DefaultLogger
    private Logger logger;

    @Inject
    ManufacturerEngine manufacturerEngine;

    @Inject
    ManagerEngine managerEngine;

    @Inject
    ProductEngine productEngine;

    @Inject
    protected ResponseHelper<Manufacturers> response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available manufacturers.",
            notes = "List all available manufacturers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned manufacturers."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response all() {
        Optional<Collection<Manufacturers>> manufacturers = manufacturerEngine.all();
        if(manufacturers.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Manufacturers>>(manufacturers.get()) {});
        }
       return response.getFailed("Could not retrieve manufacturers list");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a manufacturer based on id.",
            notes = "Returns a manufacturer based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned manufacturer."),
            @ApiResponse(code = 404, message = "Manufacturer not found.") })
    public Response get(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int id) {
        Optional<Manufacturers> code = manufacturerEngine.get(id);
        if(code.isPresent()) {
            return response.getOk(code.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes a manufacturer based on id.",
            notes = "Deletes a manufacturer based on id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted manufacturer."),
            @ApiResponse(code = 400, message = "Delete failed.") })
    public Response delete(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int id) {
        boolean requestSucceeded = manufacturerEngine.remove(id);
        if(requestSucceeded) {
            return response.deleteOk("Manufacturer with id '" + id + "' removed successfully");
        }
        return response.deleteFailed("Failed to delete manufacturer");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds a manufacturer.",
            notes = "Adds a manufacturer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added manufacturer."),
            @ApiResponse(code = 400, message = "Adding failed.") })
    public Response add(
            @ApiParam(value = "ManufacturerViewModel", required = true)
            ManufacturerViewModel item) {
        int resultId = manufacturerEngine.add(item);
        if(resultId != 0) {
            return response.postOk(resultId);
        }
        return response.postFailed("Failed to add manufacturer");
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edit a manufacturer.",
            notes = "Edit a manufacturer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully edited manufacturer."),
            @ApiResponse(code = 400, message = "Editing of manufacturer failed.") })
    public Response edit(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int id, ManufacturerViewModel item) {
        boolean requestSucceeded = manufacturerEngine.edit(id, item);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify manufacturer");
    }

    //--------------------------- PRODUCT RESOURCES---------------------------------------

    @GET
    @Path("{id}/products")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "List products of a manufacturer.",
            notes = "List products of a manufacturer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned products."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response products_all(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId) {
        Optional<Collection<Products>> products = productEngine.all(manufacturerId);
        if(products.isPresent()) {
            return response. getOk(new GenericEntity<Collection<Products>>(products.get()) {
            });
        }
        return response.getFailed("Could not retrieve manufacturers list");
    }

    @GET
    @Path("{id}/products/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get a product of a manufacturer.",
            notes = "Get a product of a manufacturer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned product."),
            @ApiResponse(code = 404, message = "Return failed.") })
    public Response products_get(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId,
            @ApiParam(value = "the id of the product.", required = true)
            @PathParam("productId") int productId) {
        Optional<Products> product = productEngine.get(productId, manufacturerId);
        if(product.isPresent()) {
            return response.getOkCommon(product.get());
        }
        return response.getNotFound();
    }

    @DELETE
    @Path("{id}/products/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete a product of a manufacturer.",
            notes = "Delete a product of a manufacturer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted product."),
            @ApiResponse(code = 400, message = "Delete failed.") })
    public Response products_delete(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId,
            @ApiParam(value = "the id of the product.", required = true)
            @PathParam("productId") int productId) {
        boolean requestSucceeded = productEngine.remove(productId, manufacturerId);
        if(requestSucceeded) {
            return response.deleteOk("Product with id '" + productId + "' removed successfully");
        }
        return response.deleteFailed("Failed to delete product");
    }

    @POST
    @Path("{id}/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add a product to a manufacturer.",
            notes = "Add a product to a manufacturer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added product."),
            @ApiResponse(code = 400, message = "Adding product failed.") })
    public Response products_add(
            @ApiParam(value = "ProductViewModel.", required = true)
            ProductViewModel item,
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId) {
        int resultId = productEngine.add(item, manufacturerId);
        if(resultId != 0) {
            return response.postOk(resultId);
        }
        return response.postFailed("Failed to add product");
    }

    @PUT
    @Path("{id}/products/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Edit a product of a manufacturer.",
            notes = "Edit a product of a manufacturer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully edited product."),
            @ApiResponse(code = 400, message = "Editing product failed.") })
    public Response products_edit(
            @ApiParam(value = "ProductViewModel.", required = true)
            ProductViewModel item,
            @ApiParam(value = "the id of the product.", required = true)
            @PathParam("productId") int productId,
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId) {
        boolean requestSucceeded = productEngine.edit(productId, item, manufacturerId);
        if(requestSucceeded) {
            return response.putOk();
        }
        return response.putFailed("Failed to modify product");
    }

    // ------------------------ MANAGER RESOURCES-------------------------------

    @GET
    @Path("{id}/managers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available manufacturer managers.",
            notes = "List all available manufacturer managers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned manufacturers."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response managers_all(@PathParam("id")int accountId) {
        Optional<Collection<Accounts>> accounts = managerEngine.all(accountId);
        if(accounts.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Accounts>>(accounts.get()) {});
        }
        return response.getFailed("Could not retrieve manufacturer managers list");
    }

    @DELETE
    @Path("{id}/managers/{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Remove an account as manager of a manufacturer.",
            notes = "This method removes the account associated with id in url from the manufacturer with the corresponding id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted manager-manufacturer relationship."),
            @ApiResponse(code = 400, message = "Failed to delete manager-manufacturer relationship.") })
    public Response managers_delete(
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId,
            @ApiParam(value = "the id of the manager (account).", required = true)
            @PathParam("accountId") int accountId) {
        boolean requestSucceeded = managerEngine.remove(accountId, manufacturerId);
        if(requestSucceeded) {
            return response.deleteOk("Manager (account) with id '" + accountId + "' removed successfully");
        }
        return response.deleteFailed("Failed to remove manager relationship with manufacturer");
    }

    @POST
    @Path("{id}/managers")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds an account as manager of a manufacturer.",
            notes = "This method adds an account to the manufacturer with the corresponding id.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added manager."),
            @ApiResponse(code = 400, message = "Failed to add manager.") })
    public Response managers_add(
            ManagerViewModel managerViewModel,
            @ApiParam(value = "the id of the manufacturer.", required = true)
            @PathParam("id") int manufacturerId) {
        boolean requestSucceeded = managerEngine.addManager(managerViewModel, manufacturerId);
        if(requestSucceeded) {
            return response.postOk("Manager (account) added to manufacturer with id '" + manufacturerId + "' successfully");
        }
        return response.postFailed("Failed to add manager relationship with manufacturer");
    }
}
