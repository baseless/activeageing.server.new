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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author base
 */
@Entity
@Table(name = "sensordata_reportvalues")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensordataReportvalues.findAll", query = "SELECT s FROM SensordataReportvalues s"),
    @NamedQuery(name = "SensordataReportvalues.findById", query = "SELECT s FROM SensordataReportvalues s WHERE s.id = :id"),
    @NamedQuery(name = "SensordataReportvalues.findByValue", query = "SELECT s FROM SensordataReportvalues s WHERE s.value = :value"),
    @NamedQuery(name = "SensordataReportvalues.findByDataUnitTypeuuid", query = "SELECT s FROM SensordataReportvalues s WHERE s.dataUnitTypeuuid = :dataUnitTypeuuid"),
    @NamedQuery(name = "SensordataReportvalues.findByDataUnitTypehref", query = "SELECT s FROM SensordataReportvalues s WHERE s.dataUnitTypehref = :dataUnitTypehref"),
    @NamedQuery(name = "SensordataReportvalues.findByGatewayTimestamp", query = "SELECT s FROM SensordataReportvalues s WHERE s.gatewayTimestamp = :gatewayTimestamp"),
    @NamedQuery(name = "SensordataReportvalues.findBySensorUuid", query = "SELECT s FROM SensordataReportvalues s WHERE s.sensorUuid = :sensorUuid"),
    @NamedQuery(name = "SensordataReportvalues.findBySensorHref", query = "SELECT s FROM SensordataReportvalues s WHERE s.sensorHref = :sensorHref")})
public class SensordataReportvalues implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 500)
    @Column(name = "value")
    private String value;
    @Size(max = 500)
    @Column(name = "dataUnitType_uuid")
    private String dataUnitTypeuuid;
    @Size(max = 500)
    @Column(name = "dataUnitType_href")
    private String dataUnitTypehref;
    @Column(name = "gatewayTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gatewayTimestamp;
    @Size(max = 500)
    @Column(name = "sensor_uuid")
    private String sensorUuid;
    @Size(max = 500)
    @Column(name = "sensor_href")
    private String sensorHref;
    @JoinColumn(name = "sensordata_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sensordata sensordataId;

    public SensordataReportvalues() {
    }

    public SensordataReportvalues(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataUnitTypeuuid() {
        return dataUnitTypeuuid;
    }

    public void setDataUnitTypeuuid(String dataUnitTypeuuid) {
        this.dataUnitTypeuuid = dataUnitTypeuuid;
    }

    public String getDataUnitTypehref() {
        return dataUnitTypehref;
    }

    public void setDataUnitTypehref(String dataUnitTypehref) {
        this.dataUnitTypehref = dataUnitTypehref;
    }

    public Date getGatewayTimestamp() {
        return gatewayTimestamp;
    }

    public void setGatewayTimestamp(Date gatewayTimestamp) {
        this.gatewayTimestamp = gatewayTimestamp;
    }

    public String getSensorUuid() {
        return sensorUuid;
    }

    public void setSensorUuid(String sensorUuid) {
        this.sensorUuid = sensorUuid;
    }

    public String getSensorHref() {
        return sensorHref;
    }

    public void setSensorHref(String sensorHref) {
        this.sensorHref = sensorHref;
    }

    public Sensordata getSensordataId() {
        return sensordataId;
    }

    public void setSensordataId(Sensordata sensordataId) {
        this.sensordataId = sensordataId;
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
        if (!(object instanceof SensordataReportvalues)) {
            return false;
        }
        SensordataReportvalues other = (SensordataReportvalues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SensordataReportvalues[ id=" + id + " ]";
    }
    
}
