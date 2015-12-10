package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Manufacturers;
import se.hkr.activeageing.server.entities.Transporters;
import se.hkr.activeageing.server.viewmodels.ManagerViewModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Daniel & Emil on 2015-11-13.
 */
@Stateless
public class TransportManagerEngine extends AbstractEngine<Accounts> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public TransportManagerEngine() {
        super(Accounts.class);
    }

    public Optional<Collection<Accounts>> all(int transporterId) {
        Optional<Collection<Accounts>> result = Optional.empty();
        try {
            Collection<Accounts> accounts = em.createNamedQuery("Accounts.findByTransporterId", Accounts.class).setParameter("transporterId", transporterId).getResultList();
            result = Optional.of(accounts);
            logger.info("Got and responded containing account list for id '" + transporterId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id, int transporterId) {
        boolean result = false;
        try {
            Transporters transporter = em.find(Transporters.class, transporterId);
            Accounts account = super.find(id);
            account.getTransportersCollection().remove(transporter);
            super.edit(account);
            result = true;
            logger.info("Removed account with id '" + account.getId() + "' as manager for id '" + transporterId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean addManager(ManagerViewModel managerViewModel, int transporterId) {
        boolean result = false;
        try {
            Transporters transporter = em.find(Transporters.class, transporterId);
            Accounts account = super.find(managerViewModel.getId());
            account.getTransportersCollection().add(transporter);
            super.edit(account);
            result = true;
            logger.info("Added account with id '" + account.getId() + "' as manager for id '" + transporterId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
