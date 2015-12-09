/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.hkr.activeageing.server.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daniel Ryhle
 */
@Entity
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.findById", query = "SELECT a FROM Accounts a WHERE a.id = :id"),
    @NamedQuery(name = "Accounts.findByUserName", query = "SELECT a FROM Accounts a WHERE a.userName = :userName"),
    @NamedQuery(name = "Accounts.findByUpdated", query = "SELECT a FROM Accounts a WHERE a.updated = :updated"),
    @NamedQuery(name = "Accounts.findByCreated", query = "SELECT a FROM Accounts a WHERE a.created = :created"),
    @NamedQuery(name = "Accounts.findByDeleted", query = "SELECT a FROM Accounts a WHERE a.deleted = :deleted"),
    @NamedQuery(name = "Accounts.findByFirstName", query = "SELECT a FROM Accounts a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Accounts.findByLastName", query = "SELECT a FROM Accounts a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Accounts.findByEmail", query = "SELECT a FROM Accounts a WHERE a.email = :email"),
    @NamedQuery(name = "Accounts.findByPassword", query = "SELECT a FROM Accounts a WHERE a.password = :password"),
    @NamedQuery(name = "Accounts.findByPhoneNumber", query = "SELECT a FROM Accounts a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Accounts.findByStreetName", query = "SELECT a FROM Accounts a WHERE a.streetName = :streetName"),
    @NamedQuery(name = "Accounts.findByStreetNumber", query = "SELECT a FROM Accounts a WHERE a.streetNumber = :streetNumber"),
    @NamedQuery(name = "Accounts.findByCity", query = "SELECT a FROM Accounts a WHERE a.city = :city"),
    @NamedQuery(name = "Accounts.findByRole", query = "SELECT a FROM Accounts a WHERE a.role = :role"),
    @NamedQuery(name = "Accounts.findByTags", query = "SELECT a FROM Accounts a WHERE a.tags = :tags")})

public class Accounts implements Serializable {

    public enum AccountRole { GUEST, CUSTOMER, USER, ADMIN, NOTSET }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "userName")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
    @Size(max = 200)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 200)
    @Column(name = "lastName")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "email")
    private String email;
    @Size(max = 200)
    @Column(name = "password")
    private String password;
    @Size(max = 200)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Size(max = 200)
    @Column(name = "streetName")
    private String streetName;
    @Size(max = 200)
    @Column(name = "streetNumber")
    private String streetNumber;
    @Size(max = 200)
    @Column(name = "city")
    private String city;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AccountRole role = AccountRole.CUSTOMER;
    @Size(max = 2000)
    @Column(name = "tags")
    private String tags;
    @JoinTable(name = "accounts_manages_manufacturers", joinColumns = {
        @JoinColumn(name = "accounts_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "manufacturers_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Manufacturers> manufacturersCollection;
    @JoinTable(name = "accounts_manages_transporters", joinColumns = {
        @JoinColumn(name = "accounts_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "transporters_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Transporters> transportersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountsId")
    private Collection<Orders> ordersCollection;
    @JoinColumn(name = "zipCodes_id", referencedColumnName = "id")
    @ManyToOne
    private Zipcodes zipCodesid;

    public Accounts() {
    }

    public Accounts(Integer id) {
        this.id = id;
    }

    public Accounts(Integer id, String userName, Date updated, Date created, boolean deleted, String email) {
        this.id = id;
        this.userName = userName;
        this.updated = updated;
        this.created = created;
        this.deleted = deleted;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @XmlTransient
    public Collection<Manufacturers> getManufacturersCollection() {
        return manufacturersCollection;
    }

    public void setManufacturersCollection(Collection<Manufacturers> manufacturersCollection) {
        this.manufacturersCollection = manufacturersCollection;
    }

    @XmlTransient
    public Collection<Transporters> getTransportersCollection() {
        return transportersCollection;
    }

    public void setTransportersCollection(Collection<Transporters> transportersCollection) {
        this.transportersCollection = transportersCollection;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    public Zipcodes getZipCodesid() {
        return zipCodesid;
    }

    public void setZipCodesid(Zipcodes zipCodesid) {
        this.zipCodesid = zipCodesid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Accounts[ id=" + id + " ]";
    }
    
}
