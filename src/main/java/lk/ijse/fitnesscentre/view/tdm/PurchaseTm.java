package lk.ijse.fitnesscentre.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseTm {
    private String purchaseId;
    private Date purchaseDate;
    private Time purchaseTime;
    private double totalPrice;
    private String memberId;
}
