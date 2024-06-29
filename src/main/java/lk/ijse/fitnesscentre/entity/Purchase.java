package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Purchase {
    private String purchaseId;
    private Date purchaseDate;
    private Time purchaseTime;
    private double totalPrice;
    private String memberId;
}
