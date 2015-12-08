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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author base
 */
@Entity
@Table(name = "sensordata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sensordata.findAll", query = "SELECT s FROM Sensordata s"),
    @NamedQuery(name = "Sensordata.findById", query = "SELECT s FROM Sensordata s WHERE s.id = :id"),
    @NamedQuery(name = "Sensordata.findByUpdated", query = "SELECT s FROM Sensordata s WHERE s.updated = :updated"),
    @NamedQuery(name = "Sensordata.findByCreated", query = "SELECT s FROM Sensordata s WHERE s.created = :created"),
    @NamedQuery(name = "Sensordata.findByDeleted", query = "SELECT s FROM Sensordata s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "Sensordata.findByIdentifier", query = "SELECT s FROM Sensordata s WHERE s.identifier = :identifier"),
    @NamedQuery(name = "Sensordata.findBySensorType", query = "SELECT s FROM Sensordata s WHERE s.sensorType = :sensorType")})
public class Sensordata implements Serializable {

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
    @Column(name = "identifier")
    private String identifier;
    @Size(max = 50)
    @Column(name = "sensorType")
    private String sensorType;

    public Sensordata() {
    }

    public Sensordata(Integer id) {
        this.id = id;
    }

    public Sensordata(Integer id, Date updated, Date created, boolean deleted, String identifier) {
        this.id = id;
        this.updated = updated;
        this.created = created;
        this.deleted = deleted;
        this.identifier = identifier;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
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
        if (!(object instanceof Sensordata)) {
            return false;
        }
        Sensordata other = (Sensordata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aa.entities.Sensordata[ id=" + id + " ]";
    }
    
}
