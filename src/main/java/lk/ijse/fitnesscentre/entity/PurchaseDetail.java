package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseDetail {
    private String purchaseId;
    private String productId;
    private Date date;
    private Time time;
    private int qty;

    public PurchaseDetail(String productId, int qty) {
        this.productId = productId;
        this.qty = qty;
    }

}
