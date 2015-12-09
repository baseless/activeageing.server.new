/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.hkr.activeageing.server.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author base
 */
@Entity
@Table(name = "transporters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transporters.findAll", query = "SELECT t FROM Transporters t"),
    @NamedQuery(name = "Transporters.findById", query = "SELECT t FROM Transporters t WHERE t.id = :id"),
    @NamedQuery(name = "Transporters.findByAccountId", query = "SELECT m FROM Transporters m INNER JOIN m.accountsCollection a WHERE a.id = :accountId"),
    @NamedQuery(name = "Transporters.findByUpdated", query = "SELECT t FROM Transporters t WHERE t.updated = :updated"),
    @NamedQuery(name = "Transporters.findByCreated", query = "SELECT t FROM Transporters t WHERE t.created = :created"),
    @NamedQuery(name = "Transporters.findByDeleted", query = "SELECT t FROM Transporters t WHERE t.deleted = :deleted"),
    @NamedQuery(name = "Transporters.findByName", query = "SELECT t FROM Transporters t WHERE t.name = :name"),
    @NamedQuery(name = "Transporters.findByLogoURL", query = "SELECT t FROM Transporters t WHERE t.logoURL = :logoURL")})
public class Transporters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "logoURL")
    private String logoURL;
    @ManyToMany(mappedBy = "transportersCollection")
    private Collection<Manufacturers> manufacturersCollection;
    @JoinTable(name = "transporters_delivers_to_zipcodes", joinColumns = {
        @JoinColumn(name = "transporters_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "zipcodes_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Zipcodes> zipcodesCollection;
    @ManyToMany(mappedBy = "transportersCollection")
    private Collection<Accounts> accountsCollection;

    public Transporters() {
    }

    public Transporters(Integer id) {
        this.id = id;
    }

    public Transporters(Integer id, Date updated, Date created, boolean deleted, String name) {
        this.id = id;
        this.updated = updated;
        this.created = created;
        this.deleted = deleted;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    @XmlTransient
    public Collection<Manufacturers> getManufacturersCollection() {
        return manufacturersCollection;
    }

    public void setManufacturersCollection(Collection<Manufacturers> manufacturersCollection) {
        this.manufacturersCollection = manufacturersCollection;
    }

    @XmlTransient
    public Collection<Zipcodes> getZipcodesCollection() {
        return zipcodesCollection;
    }

    public void setZipcodesCollection(Collection<Zipcodes> zipcodesCollection) {
        this.zipcodesCollection = zipcodesCollection;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
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
        if (!(object instanceof Transporters)) {
            return false;
        }
        Transporters other = (Transporters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transporters[ id=" + id + " ]";
    }
    
}
