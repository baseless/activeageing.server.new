package se.hkr.activeageing.server.viewmodels;

import java.util.Date;

/**
 * Order viewModel class.
 * Created by Daniel Ryhle & Emil Eider & Andreas Svensson on 2015-11-10.
 */
public class OrderViewModel {
    private String status;
    private Date delivered;
    private String sensors;

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

    public String getSensors() {
        return sensors;
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }
}
