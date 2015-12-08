package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Transporters;
import se.hkr.activeageing.server.viewmodels.TransporterViewModel;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Transporter engine viewModel
 * Created by Emil Eider & Daniel Ryhle on 2015-11-10.
 */
public class TransporterEngine extends AbstractEngine<Transporters> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public TransporterEngine() { super(Transporters.class); }

    public Optional<Collection<Transporters>> all() {
        return Optional.of(super.findAll());
    }

    public Optional<Transporters> get(int id) {
        Optional<Transporters> result = Optional.empty();
        try {
            result = Optional.of(super.find(id));
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int add(TransporterViewModel transporterViewModel) {
        int result = 0;
        try {
            Transporters transporter = new Transporters();
            Timestamp current = new Timestamp((new Date()).getTime());
            transporter.setCreated(current);
            transporter.setUpdated(current);
            transporter.setDeleted(false);

            if(!(transporterViewModel.getName() == null && transporter.getName() != null)) {
                transporter.setName(transporterViewModel.getName());
            }
            transporter.setLogoURL(transporterViewModel.getLogoURL());

            em.persist(transporter);
            em.flush();
            result = transporter.getId();
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        try {
            Transporters transporter = super.find(id);
            super.remove(transporter);
            result = true;
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(int id, TransporterViewModel transporterViewModel) {
        boolean result = false;
        try {
            Transporters transporter = super.find(id);
            transporter.setUpdated(new Timestamp((new Date()).getTime()));
            if(!(transporterViewModel.getName() == null && transporter.getName() != null)) {
                transporter.setName(transporterViewModel.getName());
            }
            transporter.setLogoURL(transporterViewModel.getLogoURL());

            super.edit(transporter);
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
