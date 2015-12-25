package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.core.utility.PasswordHasher;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Manufacturers;
import se.hkr.activeageing.server.entities.Transporters;
import se.hkr.activeageing.server.viewmodels.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Account engine class.
 * Created by Daniel Ryhle on 2015-11-09.
 */
@Stateless
public class AccountEngine extends AbstractEngine<Accounts> {

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public AccountEngine() { super(Accounts.class); }

    public Optional<Collection<Accounts>> all() {
        return Optional.of(super.findAll());
    }

    public Optional<Accounts> get(int id) {
        Optional<Accounts> result = Optional.empty();
        try {
            result = Optional.of(super.find(id));
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int add(AccountAddViewModel accountAddViewModel) {
        int result = 0;
        try {
            Accounts account = new Accounts();
            account.setRole(Accounts.AccountRole.CUSTOMER);
            Timestamp current = new Timestamp((new Date()).getTime());
            account.setCreated(current);
            account.setUpdated(current);
            account.setDeleted(false);
            account.setUserName(accountAddViewModel.getUserName());
            account.setFirstName(accountAddViewModel.getFirstName());
            account.setLastName(accountAddViewModel.getLastName());
            account.setEmail(accountAddViewModel.getEmail());
            account.setPassword(PasswordHasher.Hash256(accountAddViewModel.getPassword())); //hashed!
            account.setPhoneNumber(accountAddViewModel.getPhoneNumber());
            account.setStreetName(accountAddViewModel.getStreetName());
            account.setStreetNumber(accountAddViewModel.getStreetNumber());
            account.setCity(accountAddViewModel.getCity());
            account.setTags(accountAddViewModel.getTags());

            em.persist(account);
            em.flush();
            result = account.getId();
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean remove(int id) {
        boolean result = false;
        try {
            Accounts account = super.find(id);
            super.remove(account);
            result = true;
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean edit(int id, AccountEditViewModel accountEditViewModel) {
        boolean result = false;
        try {
            Accounts account = super.find(id);
            if(!accountEditViewModel.getRole().equals(Accounts.AccountRole.NOTSET)) {
                account.setRole(accountEditViewModel.getRole());
            }
            account.setUpdated(new Timestamp((new Date()).getTime()));
            account.setFirstName(accountEditViewModel.getFirstName());
            account.setLastName(accountEditViewModel.getLastName());
            if(accountEditViewModel.getEmail() != null && accountEditViewModel.getEmail().length() > 0) {
                account.setEmail(accountEditViewModel.getEmail());
            }
            account.setPhoneNumber(accountEditViewModel.getPhoneNumber());
            account.setStreetName(accountEditViewModel.getStreetName());
            account.setStreetNumber(accountEditViewModel.getStreetNumber());
            account.setCity(accountEditViewModel.getCity());
            account.setTags(accountEditViewModel.getTags());

            super.edit(account);
            result = true;
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public boolean changePassword(int id, AccountPasswordViewModel pswdViewModel) {
        boolean result = false;
        try {
            Accounts account = super.find(id);
            if(account.getPassword().equals(pswdViewModel.getOldPassword()) && pswdViewModel.getNewPassword() != null && pswdViewModel.getNewPassword().length() > 0) {
                account.setPassword(pswdViewModel.getNewPassword());
                super.edit(account);
                result = true;
            }
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<AuthenticationResponseViewModel> authenticate(AccountAuthenticateViewModel authViewModel) {
        Optional<AuthenticationResponseViewModel> result = Optional.empty();
        if (authViewModel.credentialsAreValid()) {
            Accounts account = null;
            try {
                if (authViewModel.userNameIsSet()) {
                    account = em.createNamedQuery("Accounts.findByUserName", Accounts.class)
                            .setParameter("userName", authViewModel.getUserName())
                            .getSingleResult();
                } else if (authViewModel.emailIsSet()) {
                    account = em.createNamedQuery("Accounts.findByEmail", Accounts.class)
                            .setParameter("email", authViewModel.getEmail())
                            .getSingleResult();
                }

                if (account.getPassword().equals(authViewModel.getPassword())) {
                    logger.info("authentication for user '" + account.getUserName() + "' (" + account.getId() + ") succeeded");
                    AuthenticationResponseViewModel authenticationResponseViewModel = new AuthenticationResponseViewModel();
                    authenticationResponseViewModel.setId(account.getId());
                    authenticationResponseViewModel.setRole(account.getRole());
                    result = Optional.of(authenticationResponseViewModel);
                }
            }catch(Exception e) {
                logger.warn(e.getMessage());
            }
        }
        return result;
    }

    public int emailExists(String emailAddress) {
        int result;
        try {
            Accounts account = em.createNamedQuery("Accounts.findByEmail", Accounts.class)
                    .setParameter("email", emailAddress)
                    .getSingleResult();
            result = 2;
        } catch(Exception e) {
            result = 1;
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int userNameExists(String userName) {
        int result;
        try {
            Accounts account = em.createNamedQuery("Accounts.findByUserName", Accounts.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
            result = 2;
        } catch(Exception e) {
            result = 1;
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<Collection<Manufacturers>> getAllManufacturers(int id) {
        Optional<Collection<Manufacturers>> result = Optional.empty();
        try {
            Collection<Manufacturers> manufacturers = em.createNamedQuery("Manufacturers.findByAccountId",Manufacturers.class).setParameter("accountId",id).getResultList();
            result = Optional.of(manufacturers);
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<Collection<Transporters>> getAllTransporters(int id) {
        Optional<Collection<Transporters>> result = Optional.empty();
        try {
            Collection<Transporters> transporters = em.createNamedQuery("Transporters.findByAccountId",Transporters.class).setParameter("accountId",id).getResultList();
            result = Optional.of(transporters);
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
