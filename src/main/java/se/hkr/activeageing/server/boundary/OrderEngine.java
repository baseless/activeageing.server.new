package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Orders;
import se.hkr.activeageing.server.viewmodels.OrderViewModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Order engine
 * Created by Daniel Ryhle on 2015-11-10.
 */
@Stateless
public class OrderEngine extends AbstractEngine<Orders> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public OrderEngine() { super(Orders.class); }


    public Optional<Collection<Orders>> all() {
        return Optional.of(super.findAll());
    }

    public Optional<Collection<Orders>> all(int accountId) {
        Optional<Collection<Orders>> result = Optional.empty();
        try {
            Collection<Orders> orders = em.createNamedQuery("Orders.findByAccountId", Orders.class)
                    .setParameter("accountId", accountId)
                    .getResultList();
            result = Optional.of(orders);
            logger.info("Got and responded containing Order list for id '" + accountId + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<Orders> get(int id, int accountId) {
        Optional<Orders> result = Optional.empty();
        try {
            Accounts account = em.find(Accounts.class, accountId);
            Orders order = em.createNamedQuery("Orders.findByIdAndAccount", Orders.class)
                    .setParameter("id", id)
                    .setParameter("account", account)
                    .getSingleResult();
            result = Optional.of(order);
            logger.info("Got and responded containing order with id '" + order.getId() + "'");
        } catch(RuntimeException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int add(OrderViewModel orderViewModel, int accountId) {
        int result = 0;
        try {
            Accounts account = em.find(Accounts.class, accountId);
            Orders order = new Orders();
            Timestamp current = new Timestamp((new Date()).getTime());
            order.setCreated(current);
            order.setUpdated(current);
            order.setDeleted(false);
            if(orderViewModel.getDelivered() != null) {
                order.setDelivered(orderViewModel.getDelivered());
            }
            order.setSensors(orderViewModel.getSensors());
            order.setStatus(orderViewModel.getStatus());
            order.setAccountsId(account);
            em.persist(order);
            em.flush();
            result = order.getId();
            logger.info("Added new order, id: '" + result + "'");
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id, int accountId) {
        boolean result = false;
        try {
            Orders order = super.find(id);
            if(order != null && order.getAccountsId().getId() == accountId) {
                    super.remove(order);
                    result = true;
            }
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(int id, OrderViewModel orderViewModel, int accountId) {
        boolean result = false;
        try {
            Orders order = super.find(id);
            if(order != null && order.getAccountsId().getId() == accountId) {
                order.setUpdated(new Timestamp((new Date()).getTime()));
                if(orderViewModel.getDelivered() != null) {
                    order.setDelivered(orderViewModel.getDelivered());
                }
                order.setSensors(orderViewModel.getSensors());
                order.setStatus(orderViewModel.getStatus());
                super.edit(order);
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
