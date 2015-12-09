package se.hkr.activeageing.server.resources;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import se.hkr.activeageing.server.boundary.ProductEngine;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.entities.Products;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Mattias on 2015-12-09.
 */

@Path("products")
@Stateless
@Api(value = "Products")
public class ProductResources {

    @Inject
    @DefaultLogger
    private Logger logger;

    @Inject
    ProductEngine productEngine;

    @Inject
    protected ResponseHelper<Products> response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all available products.",
            notes = "List all available products.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned products."),
            @ApiResponse(code = 400, message = "Return failed.") })
    public Response all() {
        Optional<Collection<Products>> products = productEngine.all();
        if(products.isPresent()) {
            return response.getOk(new GenericEntity<Collection<Products>>(products.get()) {});
        }
        return response.getFailed("Could not retrieve products list");
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get a product.",
            notes = "Get a product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned product."),
            @ApiResponse(code = 404, message = "Return failed.") })
    public Response products_get(
            @ApiParam(value = "the id of the product.", required = true)
            @PathParam("id") int productId) {
        Optional<Products> product = productEngine.get(productId);
        if(product.isPresent()) {
            return response.getOkCommon(product.get());
        }
        return response.getNotFound();
    }


}
