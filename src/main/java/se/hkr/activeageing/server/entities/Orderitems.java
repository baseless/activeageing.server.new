/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.hkr.activeageing.server.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author base
 */
@Entity
@Table(name = "orderitems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderitems.findAll", query = "SELECT o FROM Orderitems o"),
    @NamedQuery(name = "Orderitems.findById", query = "SELECT o FROM Orderitems o WHERE o.id = :id"),
    @NamedQuery(name = "Orderitems.findByOrderId", query = "SELECT o FROM Orderitems o WHERE o.ordersId = :orderId"),
    @NamedQuery(name = "Orderitems.findByIdAndOrderId", query = "SELECT o FROM Orderitems o WHERE o.ordersId = :orderId AND o.id = :id"),
    @NamedQuery(name = "Orderitems.findByDelivered", query = "SELECT o FROM Orderitems o WHERE o.delivered = :delivered"),
    @NamedQuery(name = "Orderitems.findByUpdated", query = "SELECT o FROM Orderitems o WHERE o.updated = :updated"),
    @NamedQuery(name = "Orderitems.findByCreated", query = "SELECT o FROM Orderitems o WHERE o.created = :created"),
    @NamedQuery(name = "Orderitems.findByDeleted", query = "SELECT o FROM Orderitems o WHERE o.deleted = :deleted")})
public class Orderitems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delivered;
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
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders ordersId;
    @JoinColumn(name = "products_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productsId;

    public Orderitems() {
    }

    public Orderitems(Integer id) {
        this.id = id;
    }

    public Orderitems(Integer id, Date delivered, Date updated, Date created, boolean deleted) {
        this.id = id;
        this.delivered = delivered;
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

    public Date getDelivered() {
        return delivered;
    }

    public void setDelivered(Date delivered) {
        this.delivered = delivered;
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

    public Orders getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Orders ordersId) {
        this.ordersId = ordersId;
    }

    public Products getProductsId() {
        return productsId;
    }

    public void setProductsId(Products productsId) {
        this.productsId = productsId;
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
        if (!(object instanceof Orderitems)) {
            return false;
        }
        Orderitems other = (Orderitems) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Orderitems[ id=" + id + " ]";
    }
    
}
