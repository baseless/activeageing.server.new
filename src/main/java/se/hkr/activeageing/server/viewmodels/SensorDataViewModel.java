package se.hkr.activeageing.server.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * Created by base on 2015-12-08.
 */
@XmlRootElement
public class SensorDataViewModel {
    private int alarmDefinition;
    private int alarmState;
    private int alarmTimeActive;
    private int batteryState;
    private String batteryVoltage;
    private String created;
    private String gatewayTimestamp;
    private int missed;
    private int missedAvg;
    private int missedState;
    private int nodeEventTimeOut;
    private int repeater;
    private IdHrefPair reportEventType;
    private String reportTime;
    private Collection<SensorReportValue> reportValues;
    private UuidHrefPair sender;
    private int timeSinceConnection;
    private int timeSinceConnectionState;
    private int version;

    public int getAlarmDefinition() {
        return alarmDefinition;
    }

    public void setAlarmDefinition(int alarmDefinition) {
        this.alarmDefinition = alarmDefinition;
    }

    public int getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(int alarmState) {
        this.alarmState = alarmState;
    }

    public int getAlarmTimeActive() {
        return alarmTimeActive;
    }

    public void setAlarmTimeActive(int alarmTimeActive) {
        this.alarmTimeActive = alarmTimeActive;
    }

    public int getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(int batteryState) {
        this.batteryState = batteryState;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getGatewayTimestamp() {
        return gatewayTimestamp;
    }

    public void setGatewayTimestamp(String gatewayTimestamp) {
        this.gatewayTimestamp = gatewayTimestamp;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public int getMissedAvg() {
        return missedAvg;
    }

    public void setMissedAvg(int missedAvg) {
        this.missedAvg = missedAvg;
    }

    public int getMissedState() {
        return missedState;
    }

    public void setMissedState(int missedState) {
        this.missedState = missedState;
    }

    public int getNodeEventTimeOut() {
        return nodeEventTimeOut;
    }

    public void setNodeEventTimeOut(int nodeEventTimeOut) {
        this.nodeEventTimeOut = nodeEventTimeOut;
    }

    public int getRepeater() {
        return repeater;
    }

    public void setRepeater(int repeater) {
        this.repeater = repeater;
    }

    public IdHrefPair getReportEventType() {
        return reportEventType;
    }

    public void setReportEventType(IdHrefPair reportEventType) {
        this.reportEventType = reportEventType;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public UuidHrefPair getSender() {
        return sender;
    }

    public void setSender(UuidHrefPair sender) {
        this.sender = sender;
    }

    public int getTimeSinceConnection() {
        return timeSinceConnection;
    }

    public void setTimeSinceConnection(int timeSinceConnection) {
        this.timeSinceConnection = timeSinceConnection;
    }

    public int getTimeSinceConnectionState() {
        return timeSinceConnectionState;
    }

    public void setTimeSinceConnectionState(int timeSinceConnectionState) {
        this.timeSinceConnectionState = timeSinceConnectionState;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<SensorReportValue> getReportValues() {
        return reportValues;
    }

    public void setReportValues(Collection<SensorReportValue> reportValues) {
        this.reportValues = reportValues;
    }
}
