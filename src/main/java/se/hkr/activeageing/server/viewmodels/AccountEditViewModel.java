package se.hkr.activeageing.server.viewmodels;

import se.hkr.activeageing.server.entities.Accounts;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Account addViewModel
 * Created by Andreas Svensson & Daniel Ryhle on 2015-11-109.
 */
@XmlRootElement
public class AccountEditViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String streetName;
    private String streetNumber;
    private String city;
    private int zipCodeId;
    private String tags;
    private Accounts.AccountRole role = Accounts.AccountRole.NOTSET;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCodeId() {
        return zipCodeId;
    }

    public void setZipCodeId(int zipCodeId) {
        this.zipCodeId = zipCodeId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Accounts.AccountRole getRole() {
        return role;
    }

    public void setRole(Accounts.AccountRole role) {
        this.role = role;
    }
}
