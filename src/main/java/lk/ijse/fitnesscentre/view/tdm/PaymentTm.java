package lk.ijse.fitnesscentre.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentTm {
    private String paymentId;
    private String paymentMethod;
    private Double membershipFee;
    private Date date;
    private Time time;
    private String membershipId;
    private String memberId;
}
