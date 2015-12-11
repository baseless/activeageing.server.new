package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Orderitems;
import se.hkr.activeageing.server.entities.Orders;
import se.hkr.activeageing.server.entities.Products;
import se.hkr.activeageing.server.viewmodels.OrderItemViewModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * OrderItem engine class
 * Created by Daniel Ryhle on 2015-11-11.
 */
@Stateless
public class OrderItemEngine extends AbstractEngine<Orderitems> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public OrderItemEngine() {
        super(Orderitems.class);
    }

    public Optional<Collection<Orderitems>> all(int orderId) {
        Optional<Collection<Orderitems>> result = Optional.empty();
        try {
            Collection<Orderitems> ordersItems = em.createNamedQuery("Orderitems.findByOrderId", Orderitems.class).setParameter("orderId", orderId).getResultList();
            result = Optional.of(ordersItems);
            logger.info("Got and responded containing Order list for id '" + orderId + "'");
        } catch(RuntimeException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public Optional<Orderitems> get(int id, int parentId, int accountId) {
        Optional<Orderitems> result = Optional.empty();
        try {
            Orderitems orderItem = em.createNamedQuery("Orderitems.findByIdAndOrderId", Orderitems.class)
                    .setParameter("id", id)
                    .setParameter("orderId", parentId)
                    .getSingleResult();
            result = Optional.of(orderItem);
            logger.info("Got and responded containing order with id '" + orderItem.getId() + "'");
        } catch(RuntimeException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public int add(OrderItemViewModel orderItemViewModel, int parentId, int accountId) {
        int result = 0;
        try {
            Orders order = em.find(Orders.class, parentId);
            if(order.getAccountsId().getId() == accountId) {
                Orderitems orderItem = new Orderitems();
                Timestamp current = new Timestamp((new Date()).getTime());
                orderItem.setCreated(current);
                orderItem.setUpdated(current);
                orderItem.setDeleted(false);
                orderItem.setOrdersId(order);
                orderItem.setDelivered(orderItemViewModel.getDelivered());
                if(orderItemViewModel.getProductId() != 0) {
                    Products product = em.find(Products.class, orderItemViewModel.getProductId());
                    orderItem.setProductsId(product);
                }

                em.persist(orderItem);
                em.flush();
                result = order.getId();
            }

            logger.info("Added new order, id: '" + result + "'");
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id, int parentId, int accountId) {
        boolean result = false;
        try {
            Orderitems orderItem = super.find(id);
            Orders order = orderItem.getOrdersId();
            if (order.getId() == parentId && order.getAccountsId().getId() == accountId) {
                super.remove(orderItem);
                result = true;
            }
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(OrderItemViewModel orderItemViewModel, int itemId, int parentId, int accountId) {
        boolean result = false;
        try {
            Orderitems orderItem = super.find(itemId);
            Orders order = orderItem.getOrdersId();
            if (order.getId() == parentId && order.getAccountsId().getId() == accountId) {
                order.setUpdated(new Timestamp((new Date()).getTime()));
                orderItem.setDelivered(orderItemViewModel.getDelivered());
                if(orderItemViewModel.getProductId() != 0) {
                    Products product = em.find(Products.class, orderItemViewModel.getProductId());
                    orderItem.setProductsId(product);
                }
                super.edit(orderItem);
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
