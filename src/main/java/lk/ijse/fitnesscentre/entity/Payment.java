package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Payment {
    private String paymentId;
    private String paymentMethod;
    private Double membershipFee;
    private Date date;
    private Time time;
    private String membershipId;
}
