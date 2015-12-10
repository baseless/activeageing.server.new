package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Zipcodes;
import se.hkr.activeageing.server.viewmodels.ZipCodeViewModel;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;


/**
 * ZipCode engine class.
 * Created by Daniel Ryhle & Andreas Svensson on 2015-11-09.
 */
@Stateless
public class ZipCodeEngine extends AbstractEngine<Zipcodes> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public ZipCodeEngine() {
        super(Zipcodes.class);
    }

    public Optional<Collection<Zipcodes>> all() {
        //return Optional.of(super.findAll());
        return Optional.of(em.createNamedQuery("Zipcodes.findAll").getResultList());
    }

    public Optional<Zipcodes> get(int id) {
        Optional<Zipcodes> result = Optional.empty();
        try {
            //result = Optional.of(super.find(id));
            Zipcodes zipcode = em.find(Zipcodes.class, id);
            result = Optional.of(zipcode);
            logger.info("Got and responded containing product with id '" + zipcode.getId() + "'");
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int add(ZipCodeViewModel viewModel) {
        int result = 0;
        try {
            Zipcodes zipCode = new Zipcodes();
            Timestamp current = new Timestamp((new Date()).getTime());
            zipCode.setCreated(current);
            zipCode.setUpdated(current);
            zipCode.setDeleted(false);
            zipCode.setCode(viewModel.getCode());

            em.persist(zipCode);
            em.flush();
            result = zipCode.getId();
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        try {
            Zipcodes zipCode = super.find(id);
            logger.info("Removing zipcode with id '" + zipCode.getId() + "'");
            super.remove(zipCode);
            result = true;
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(int id, ZipCodeViewModel viewModel) {
        boolean result = false;
        try {
            Zipcodes zipCode = super.find(id);
            zipCode.setUpdated(new Timestamp((new Date()).getTime()));
            zipCode.setCode(viewModel.getCode());
            super.edit(zipCode);
            result = true;
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
