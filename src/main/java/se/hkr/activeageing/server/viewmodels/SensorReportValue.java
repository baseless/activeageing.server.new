package se.hkr.activeageing.server.viewmodels;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by base on 2015-12-08.
 */
public class SensorReportValue {
    private String value;
    private UuidHrefPair dataUnitType;
    private Timestamp gatewayTimestamp;
    private UuidHrefPair sensor;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UuidHrefPair getDataUnitType() {
        return dataUnitType;
    }

    public void setDataUnitType(UuidHrefPair dataUnitType) {
        this.dataUnitType = dataUnitType;
    }

    public Date getGatewayTimestamp() {
        return gatewayTimestamp;
    }

    public void setGatewayTimestamp(Timestamp gatewayTimestamp) {
        this.gatewayTimestamp = gatewayTimestamp;
    }

    public UuidHrefPair getSensor() {
        return sensor;
    }

    public void setSensor(UuidHrefPair sensor) {
        this.sensor = sensor;
    }
}
