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
import javax.persistence.ManyToOne;
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
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByIdAndAccount", query = "SELECT o FROM Orders o WHERE o.id = :id and o.accountsId = :account"),
    @NamedQuery(name = "Orders.findByUpdated", query = "SELECT o FROM Orders o WHERE o.updated = :updated"),
    @NamedQuery(name = "Orders.findByCreated", query = "SELECT o FROM Orders o WHERE o.created = :created"),
    @NamedQuery(name = "Orders.findByDeleted", query = "SELECT o FROM Orders o WHERE o.deleted = :deleted"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByDelivered", query = "SELECT o FROM Orders o WHERE o.delivered = :delivered"),
    @NamedQuery(name = "Orders.findByAccountId", query = "SELECT o FROM Orders o WHERE o.accountsId = :accountId")
})
public class Orders implements Serializable {

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
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @Column(name = "delivered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delivered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordersId")
    private Collection<Orderitems> orderitemsCollection;
    @JoinColumn(name = "accounts_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accounts accountsId;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Orders(Integer id, Date updated, Date created, boolean deleted) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDelivered() {
        return delivered;
    }

    public void setDelivered(Date delivered) {
        this.delivered = delivered;
    }

    @XmlTransient
    public Collection<Orderitems> getOrderitemsCollection() {
        return orderitemsCollection;
    }

    public void setOrderitemsCollection(Collection<Orderitems> orderitemsCollection) {
        this.orderitemsCollection = orderitemsCollection;
    }

    @XmlTransient
    public Accounts getAccountsId() {
        return accountsId;
    }

    public void setAccountsId(Accounts accountsId) {
        this.accountsId = accountsId;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aa.entities.Orders[ id=" + id + " ]";
    }
    
}
