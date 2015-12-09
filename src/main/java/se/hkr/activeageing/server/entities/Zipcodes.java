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
@Table(name = "zipcodes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zipcodes.findAll", query = "SELECT z FROM Zipcodes z"),
    @NamedQuery(name = "Zipcodes.findById", query = "SELECT z FROM Zipcodes z WHERE z.id = :id"),
    @NamedQuery(name = "Zipcodes.findByCode", query = "SELECT z FROM Zipcodes z WHERE z.code = :code"),
    @NamedQuery(name = "Zipcodes.findByUpdated", query = "SELECT z FROM Zipcodes z WHERE z.updated = :updated"),
    @NamedQuery(name = "Zipcodes.findByCreated", query = "SELECT z FROM Zipcodes z WHERE z.created = :created"),
    @NamedQuery(name = "Zipcodes.findByDeleted", query = "SELECT z FROM Zipcodes z WHERE z.deleted = :deleted")})
public class Zipcodes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "code")
    private String code;
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
    @ManyToMany(mappedBy = "zipcodesCollection")
    private Collection<Transporters> transportersCollection;
    @OneToMany(mappedBy = "zipCodesid")
    private Collection<Accounts> accountsCollection;

    public Zipcodes() {
    }

    public Zipcodes(Integer id) {
        this.id = id;
    }

    public Zipcodes(Integer id, String code, Date updated, Date created, boolean deleted) {
        this.id = id;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @XmlTransient
    public Collection<Transporters> getTransportersCollection() {
        return transportersCollection;
    }

    public void setTransportersCollection(Collection<Transporters> transportersCollection) {
        this.transportersCollection = transportersCollection;
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
        if (!(object instanceof Zipcodes)) {
            return false;
        }
        Zipcodes other = (Zipcodes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Zipcodes[ id=" + id + " ]";
    }
    
}
