package se.hkr.activeageing.server.viewmodels;

import java.sql.Timestamp;

/**
 * OrderItem viewModel class.
 * Created by Daniel Ryhle & Emil Eider & Andreas Svensson on 2015-11-10.
 */
public class OrderItemViewModel {
    private Timestamp delivered;
    private int productId;

    public Timestamp getDelivered() {
        return delivered;
    }

    public void setDelivered(Timestamp delivered) {
        this.delivered = delivered;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
