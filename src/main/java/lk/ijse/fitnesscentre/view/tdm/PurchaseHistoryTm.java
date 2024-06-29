package lk.ijse.fitnesscentre.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseHistoryTm {
    private String purchaseId;
    private String productId;
    private String productName;
    private double unitPrice;
    private String memberId;
    private String memberName;
    private Date date;
    private Time time;
    private int qty;
    private double total;
}
