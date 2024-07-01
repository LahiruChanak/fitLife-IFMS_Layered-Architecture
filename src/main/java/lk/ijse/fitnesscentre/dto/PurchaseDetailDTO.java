package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseDetailDTO {
    private String purchaseId;
    private String productId;
    private Date date;
    private Time time;
    private int qty;
}
