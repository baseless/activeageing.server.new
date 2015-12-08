package se.hkr.activeageing.server.resources;
import se.hkr.activeageing.server.boundary.AbstractEngine;
import se.hkr.activeageing.server.entities.Accounts;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by base on 2015-12-07.
 */
@Stateless
@Path("accountsRaw")
public class AccountRawResources extends AbstractEngine<Accounts> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    public AccountRawResources() {
        super(Accounts.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Accounts entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Accounts entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Accounts find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Accounts> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Accounts> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
}
