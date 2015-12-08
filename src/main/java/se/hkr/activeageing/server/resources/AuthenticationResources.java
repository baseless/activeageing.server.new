package se.hkr.activeageing.server.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import se.hkr.activeageing.server.boundary.AccountEngine;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.ResponseHelper;
import se.hkr.activeageing.server.viewmodels.AccountAuthenticateViewModel;
import se.hkr.activeageing.server.viewmodels.AuthenticationResponseViewModel;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by Daniel Ryhle & Emil Ejder on 2015-11-09.
 */

@Path("authentication")
@Api(value = "Authentication")
public class AuthenticationResources {

    @Inject
    @DefaultLogger
    private Logger logger;

    @Inject
    AccountEngine accountEngine;

    @Inject
    protected ResponseHelper<AuthenticationResponseViewModel> response;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Authenticates an account based on username or email and password.",
            notes = "Returns authentication data.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned authentication data."),
            @ApiResponse(code = 401, message = "Authentication failed.") })
    public Response authenticate(AccountAuthenticateViewModel accViewModel) {
        Optional<AuthenticationResponseViewModel> result = accountEngine.authenticate(accViewModel);
        if(result.isPresent()) {
            return response.getOkCommon(result.get());
        }
        return response.postFailed("authentication failed");
    }

}
