package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Manufacturers;
import se.hkr.activeageing.server.entities.Products;
import se.hkr.activeageing.server.viewmodels.ProductViewModel;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Product engine
 * Created by Emil Ejder & Magnus Kanfjäll on 2015-11-11.
 */
@Stateless
public class ProductEngine extends AbstractEngine<Products> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public ProductEngine() { super(Products.class); }

    public Optional<Collection<Products>> all() {
        return Optional.of(em.createNamedQuery("Products.findAll").getResultList());
    }

    public Optional<Collection<Products>> all(int manufacturerId) {
        Optional<Collection<Products>> result = Optional.empty();
        try {
            Collection<Products> productList = em.createNamedQuery("Products.findByManufacturer", Products.class)
                    .setParameter("manufacturerId", manufacturerId)
                    .getResultList();
            result = Optional.of(productList);
            logger.info("Got and responded containing product list for id '" + manufacturerId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<Products> get(int id) {
        Optional<Products> result = Optional.empty();
        try {
            result = Optional.of(super.find(id));
            logger.info("Got and responded containing product with id '" + id + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }

        return result;
    }

    public Optional<Products> get(int id, int manufacturerId) {
        Optional<Products> result = Optional.empty();
        try {
            Manufacturers manufacturer = em.find(Manufacturers.class, manufacturerId);
            Products product = em.createNamedQuery("Products.findByIdAndManufacturer", Products.class)
                    .setParameter("id", id)
                    .setParameter("manufacturer", manufacturer)
                    .getSingleResult();
            result = Optional.of(product);
            logger.info("Got and responded containing product with id '" + product.getId() + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int add(ProductViewModel productViewModel, int manufacturerId) {
        int result = 0;
        try {
            Manufacturers manufacturer = em.find(Manufacturers.class, manufacturerId);
            Products product = new Products();
            Timestamp current = new Timestamp((new Date()).getTime());
            product.setCreated(current);
            product.setUpdated(current);
            product.setDeleted(false);
            product.setTags(productViewModel.getTags());
            product.setImageUrl(productViewModel.getImageUrl());
            product.setManufacturersId(manufacturer);

            if(!(productViewModel.getName() == null && product.getName() !=null)) {
                product.setName(productViewModel.getName());
            }
            product.setDescription(productViewModel.getDescription());
            product.setPrice(productViewModel.getPrice());

            em.persist(product);
            em.flush();
            result = product.getId();
            logger.info("Added new product, id: '" + result + "'");
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id, int manufacturerId) {
        boolean result = false;
        try {
            Products product = super.find(id);
            if(product.getManufacturersId().getId() == manufacturerId) {
                super.remove(product);
                result = true;
            }
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(int id, ProductViewModel productViewModel, int manufacturerId) {
        boolean result = false;
        try {
            Products product = super.find(id);
            if(product.getManufacturersId().getId() == manufacturerId) {
                product.setUpdated(new Timestamp((new Date()).getTime()));
                if(!(productViewModel.getName() == null && product.getName() !=null)) {
                    product.setName(productViewModel.getName());
                }
                product.setTags(productViewModel.getTags());
                product.setImageUrl(productViewModel.getImageUrl());
                product.setDescription(productViewModel.getDescription());
                product.setPrice(productViewModel.getPrice());
                super.edit(product);
                result = true;
            }
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
