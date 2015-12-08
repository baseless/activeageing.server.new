package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Manufacturers;
import se.hkr.activeageing.server.viewmodels.ManufacturerViewModel;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Manufacturer engine class.
 * Created by Daniel Ryhle & Emil Eider & Magnus Kanfjäll on 2015-11-10.
 */
@Stateless
public class ManufacturerEngine extends AbstractEngine<Manufacturers> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public ManufacturerEngine() { super(Manufacturers.class); }

    public Optional<Collection<Manufacturers>> all() {
        return Optional.of(super.findAll());
    }

    public Optional<Manufacturers> get(int id) {
        Optional<Manufacturers> result = Optional.empty();
        try {
            result = Optional.of(super.find(id));
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int add(ManufacturerViewModel manufacturerViewModel) {
        int result = 0;
        try {
            Manufacturers manufacturer = new Manufacturers();
            Timestamp current = new Timestamp((new Date()).getTime());
            manufacturer.setCreated(current);
            manufacturer.setUpdated(current);
            manufacturer.setDeleted(false);

            if(!(manufacturerViewModel.getName() == null && manufacturer.getName() != null)) {
                manufacturer.setName(manufacturerViewModel.getName());
            }
            manufacturer.setLogoURL(manufacturerViewModel.getLogoUrl());
            manufacturer.setDescription(manufacturerViewModel.getDescription());

            em.persist(manufacturer);
            em.flush();
            result = manufacturer.getId();
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        try {
            Manufacturers manufacturer = super.find(id);
            super.remove(manufacturer);
            result = true;
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(int id, ManufacturerViewModel manufacturerViewModel) {
        boolean result = false;
        try {
            Manufacturers manufacturer = super.find(id);
            manufacturer.setUpdated(new Timestamp((new Date()).getTime()));
            if(!(manufacturerViewModel.getName() == null && manufacturer.getName() != null)) {
                manufacturer.setName(manufacturerViewModel.getName());
            }
            manufacturer.setLogoURL(manufacturerViewModel.getLogoUrl());
            manufacturer.setDescription(manufacturerViewModel.getDescription());

            super.edit(manufacturer);
            result = true;
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
