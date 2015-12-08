package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Manufacturers;
import se.hkr.activeageing.server.entities.Orders;
import se.hkr.activeageing.server.viewmodels.ManagerViewModel;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Daniel Ryhle & Emil Ejder on 2015-11-13.
 */
@Stateless
public class ManagerEngine extends AbstractEngine<Accounts> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public ManagerEngine() {
        super(Accounts.class);
    }

    public Optional<Collection<Accounts>> all(int manufacturerId) {
        Optional<Collection<Accounts>> result = Optional.empty();
        try {
            Manufacturers manufacturer = em.find(Manufacturers.class, manufacturerId);
            result = Optional.of(manufacturer.getAccountsCollection());
            logger.info("Got and responded containing account list for id '" + manufacturerId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean addManager(ManagerViewModel managerViewModel, int manufacturerId) {
        boolean result = false;
        try {
            Manufacturers manufacturer = em.find(Manufacturers.class, manufacturerId);
            Accounts account = super.find(managerViewModel.getId());
            account.getManufacturersCollection().add(manufacturer);
            super.edit(account);
            result = true;
            logger.info("Added account with id '" + account.getId() + "' as manager for id '" + manufacturerId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id, int manufacturerId) {
        boolean result = false;
        try {
            Manufacturers manufacturer = em.find(Manufacturers.class, manufacturerId);
            Accounts account = super.find(id);
            account.getManufacturersCollection().remove(manufacturer);
            super.edit(account);
            result = true;
            logger.info("Removed account with id '" + account.getId() + "' as manager for id '" + manufacturerId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
