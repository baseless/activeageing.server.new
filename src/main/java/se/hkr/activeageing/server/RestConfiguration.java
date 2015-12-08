package se.hkr.activeageing.server;

import io.swagger.jaxrs.config.BeanConfig;
import se.hkr.activeageing.server.core.filters.AuthorizationFilter;
import se.hkr.activeageing.server.core.filters.CORSRequestFilter;
import se.hkr.activeageing.server.core.filters.CORSResponseFilter;
import se.hkr.activeageing.server.resources.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * The main application class
 * Created by Daniel Ryhle on 2015-10-24.
 */
@ApplicationPath("resources")
public class RestConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();

        resources.add(AccountRawResources.class);
        resources.add(AccountResources.class);
        resources.add(ManufacturerResources.class);
        //resources.add(TransporterResources.class);
        resources.add(AuthenticationResources.class);
        resources.add(ZipCodeResources.class);
        resources.add(AuthorizationFilter.class);
        resources.add(CORSRequestFilter.class);
        resources.add(CORSResponseFilter.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return resources;
    }

    public RestConfiguration() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        ////beanConfig.setSchemes(new String[]{"http"});
        ////beanConfig.setHost("localhost:8080");
        ////beanConfig.setBasePath("/activeageing/resources");
        beanConfig.setSchemes(new String[]{"https"});
        beanConfig.setHost("activeageing.se");
        beanConfig.setBasePath("/resources");
        beanConfig.setResourcePackage("se.hkr.activeageing.server");
        beanConfig.setScan(true);
        beanConfig.setTitle("ActiveAgeing - API");
    }
}
