package se.hkr.activeageing.server.viewmodels;

import se.hkr.activeageing.server.entities.Accounts;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Daniel Ryhle & Emil Ejder on 2015-11-13.
 */

@XmlRootElement
public class AuthenticationResponseViewModel {
    private int id;

    private Accounts.AccountRole role;

    public Accounts.AccountRole getRole() {
        return role;
    }

    public void setRole(Accounts.AccountRole role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
