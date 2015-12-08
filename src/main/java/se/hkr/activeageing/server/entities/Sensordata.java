/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.hkr.activeageing.server.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Sensordata.findByAlarmDefinition", query = "SELECT s FROM Sensordata s WHERE s.alarmDefinition = :alarmDefinition"),
    @NamedQuery(name = "Sensordata.findByAlarmState", query = "SELECT s FROM Sensordata s WHERE s.alarmState = :alarmState"),
    @NamedQuery(name = "Sensordata.findByAlarmTimeActive", query = "SELECT s FROM Sensordata s WHERE s.alarmTimeActive = :alarmTimeActive"),
    @NamedQuery(name = "Sensordata.findByBatteryState", query = "SELECT s FROM Sensordata s WHERE s.batteryState = :batteryState"),
    @NamedQuery(name = "Sensordata.findByBatteryVoltage", query = "SELECT s FROM Sensordata s WHERE s.batteryVoltage = :batteryVoltage"),
    @NamedQuery(name = "Sensordata.findByCreated", query = "SELECT s FROM Sensordata s WHERE s.created = :created"),
    @NamedQuery(name = "Sensordata.findByTimeSinceConnection", query = "SELECT s FROM Sensordata s WHERE s.timeSinceConnection = :timeSinceConnection"),
    @NamedQuery(name = "Sensordata.findByGatewayTimestamp", query = "SELECT s FROM Sensordata s WHERE s.gatewayTimestamp = :gatewayTimestamp"),
    @NamedQuery(name = "Sensordata.findByMissed", query = "SELECT s FROM Sensordata s WHERE s.missed = :missed"),
    @NamedQuery(name = "Sensordata.findByMissedAvg", query = "SELECT s FROM Sensordata s WHERE s.missedAvg = :missedAvg"),
    @NamedQuery(name = "Sensordata.findByMissedState", query = "SELECT s FROM Sensordata s WHERE s.missedState = :missedState"),
    @NamedQuery(name = "Sensordata.findByNodeEventTimeOut", query = "SELECT s FROM Sensordata s WHERE s.nodeEventTimeOut = :nodeEventTimeOut"),
    @NamedQuery(name = "Sensordata.findByRepeater", query = "SELECT s FROM Sensordata s WHERE s.repeater = :repeater"),
    @NamedQuery(name = "Sensordata.findByReportEventTypeid", query = "SELECT s FROM Sensordata s WHERE s.reportEventTypeid = :reportEventTypeid"),
    @NamedQuery(name = "Sensordata.findByReportEventTypehref", query = "SELECT s FROM Sensordata s WHERE s.reportEventTypehref = :reportEventTypehref"),
    @NamedQuery(name = "Sensordata.findByReportTime", query = "SELECT s FROM Sensordata s WHERE s.reportTime = :reportTime"),
    @NamedQuery(name = "Sensordata.findBySenderUuid", query = "SELECT s FROM Sensordata s WHERE s.senderUuid = :senderUuid"),
    @NamedQuery(name = "Sensordata.findBySenderHref", query = "SELECT s FROM Sensordata s WHERE s.senderHref = :senderHref"),
    @NamedQuery(name = "Sensordata.findByTimeSinceConnectionState", query = "SELECT s FROM Sensordata s WHERE s.timeSinceConnectionState = :timeSinceConnectionState"),
    @NamedQuery(name = "Sensordata.findByVersion", query = "SELECT s FROM Sensordata s WHERE s.version = :version")})
public class Sensordata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "alarmDefinition")
    private Integer alarmDefinition;
    @Column(name = "alarmState")
    private Integer alarmState;
    @Column(name = "alarmTimeActive")
    private Integer alarmTimeActive;
    @Column(name = "batteryState")
    private Integer batteryState;
    @Column(name = "batteryVoltage")
    private String batteryVoltage;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "timeSinceConnection")
    private Integer timeSinceConnection;
    @Column(name = "gatewayTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gatewayTimestamp;
    @Column(name = "missed")
    private Integer missed;
    @Column(name = "missedAvg")
    private Integer missedAvg;
    @Column(name = "missedState")
    private Integer missedState;
    @Column(name = "nodeEventTimeOut")
    private Integer nodeEventTimeOut;
    @Column(name = "repeater")
    private Integer repeater;
    @Size(max = 500)
    @Column(name = "reportEventType_id")
    private String reportEventTypeid;
    @Size(max = 500)
    @Column(name = "reportEventType_href")
    private String reportEventTypehref;
    @Column(name = "reportTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;
    @Size(max = 500)
    @Column(name = "sender_uuid")
    private String senderUuid;
    @Size(max = 500)
    @Column(name = "sender_href")
    private String senderHref;
    @Column(name = "timeSinceConnectionState")
    private Integer timeSinceConnectionState;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensordataId")
    private Collection<SensordataReportvalues> sensordataReportvaluesCollection;

    public Sensordata() {
    }

    public Sensordata(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlarmDefinition() {
        return alarmDefinition;
    }

    public void setAlarmDefinition(Integer alarmDefinition) {
        this.alarmDefinition = alarmDefinition;
    }

    public Integer getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(Integer alarmState) {
        this.alarmState = alarmState;
    }

    public Integer getAlarmTimeActive() {
        return alarmTimeActive;
    }

    public void setAlarmTimeActive(Integer alarmTimeActive) {
        this.alarmTimeActive = alarmTimeActive;
    }

    public Integer getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(Integer batteryState) {
        this.batteryState = batteryState;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getTimeSinceConnection() {
        return timeSinceConnection;
    }

    public void setTimeSinceConnection(Integer timeSinceConnection) {
        this.timeSinceConnection = timeSinceConnection;
    }

    public Date getGatewayTimestamp() {
        return gatewayTimestamp;
    }

    public void setGatewayTimestamp(Date gatewayTimestamp) {
        this.gatewayTimestamp = gatewayTimestamp;
    }

    public Integer getMissed() {
        return missed;
    }

    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    public Integer getMissedAvg() {
        return missedAvg;
    }

    public void setMissedAvg(Integer missedAvg) {
        this.missedAvg = missedAvg;
    }

    public Integer getMissedState() {
        return missedState;
    }

    public void setMissedState(Integer missedState) {
        this.missedState = missedState;
    }

    public Integer getNodeEventTimeOut() {
        return nodeEventTimeOut;
    }

    public void setNodeEventTimeOut(Integer nodeEventTimeOut) {
        this.nodeEventTimeOut = nodeEventTimeOut;
    }

    public Integer getRepeater() {
        return repeater;
    }

    public void setRepeater(Integer repeater) {
        this.repeater = repeater;
    }

    public String getReportEventTypeid() {
        return reportEventTypeid;
    }

    public void setReportEventTypeid(String reportEventTypeid) {
        this.reportEventTypeid = reportEventTypeid;
    }

    public String getReportEventTypehref() {
        return reportEventTypehref;
    }

    public void setReportEventTypehref(String reportEventTypehref) {
        this.reportEventTypehref = reportEventTypehref;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(String senderUuid) {
        this.senderUuid = senderUuid;
    }

    public String getSenderHref() {
        return senderHref;
    }

    public void setSenderHref(String senderHref) {
        this.senderHref = senderHref;
    }

    public Integer getTimeSinceConnectionState() {
        return timeSinceConnectionState;
    }

    public void setTimeSinceConnectionState(Integer timeSinceConnectionState) {
        this.timeSinceConnectionState = timeSinceConnectionState;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @XmlTransient
    public Collection<SensordataReportvalues> getSensordataReportvaluesCollection() {
        return sensordataReportvaluesCollection;
    }

    public void setSensordataReportvaluesCollection(Collection<SensordataReportvalues> sensordataReportvaluesCollection) {
        this.sensordataReportvaluesCollection = sensordataReportvaluesCollection;
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
        return "entities.Sensordata[ id=" + id + " ]";
    }
    
}
