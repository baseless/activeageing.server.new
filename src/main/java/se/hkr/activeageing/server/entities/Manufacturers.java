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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "manufacturers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manufacturers.findAll", query = "SELECT m FROM Manufacturers m"),
    @NamedQuery(name = "Manufacturers.findById", query = "SELECT m FROM Manufacturers m WHERE m.id = :id"),
    @NamedQuery(name = "Manufacturers.findByAccountId", query = "SELECT m FROM Manufacturers m INNER JOIN m.accountsCollection a WHERE a.id = :accountId"),
    @NamedQuery(name = "Manufacturers.findByName", query = "SELECT m FROM Manufacturers m WHERE m.name = :name"),
    @NamedQuery(name = "Manufacturers.findByUpdated", query = "SELECT m FROM Manufacturers m WHERE m.updated = :updated"),
    @NamedQuery(name = "Manufacturers.findByCreated", query = "SELECT m FROM Manufacturers m WHERE m.created = :created"),
    @NamedQuery(name = "Manufacturers.findByDeleted", query = "SELECT m FROM Manufacturers m WHERE m.deleted = :deleted"),
    @NamedQuery(name = "Manufacturers.findByLogoURL", query = "SELECT m FROM Manufacturers m WHERE m.logoURL = :logoURL")})
public class Manufacturers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
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
    @Column(name = "logoURL")
    private String logoURL;
    @Lob
    @Size(max = 16777215)
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "manufacturersCollection")
    private Collection<Accounts> accountsCollection;
    @JoinTable(name = "manufacturers_uses_transporters", joinColumns = {
        @JoinColumn(name = "manufacturers_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "transporters_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Transporters> transportersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturersId")
    private Collection<Products> productsCollection;

    public Manufacturers() {
    }

    public Manufacturers(Integer id) {
        this.id = id;
    }

    public Manufacturers(Integer id, String name, Date updated, Date created, boolean deleted) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.created = created;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @XmlTransient
    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
    }

    @XmlTransient
    public Collection<Transporters> getTransportersCollection() {
        return transportersCollection;
    }

    public void setTransportersCollection(Collection<Transporters> transportersCollection) {
        this.transportersCollection = transportersCollection;
    }

    @XmlTransient
    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
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
        if (!(object instanceof Manufacturers)) {
            return false;
        }
        Manufacturers other = (Manufacturers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Manufacturers[ id=" + id + " ]";
    }
    
}
