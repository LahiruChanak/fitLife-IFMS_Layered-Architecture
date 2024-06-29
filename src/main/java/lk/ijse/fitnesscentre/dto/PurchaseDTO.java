package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseDTO {
    private String purchaseId;
    private Date purchaseDate;
    private Time purchaseTime;
    private double totalPrice;
    private String memberId;
}
