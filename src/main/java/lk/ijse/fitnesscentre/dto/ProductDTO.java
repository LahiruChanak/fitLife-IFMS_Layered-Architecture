package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductDTO {
    private String productId;
    private String productName;
    private double unitPrice;
    private int qtyOnHand;
    private Date addedDate;
    private Time addedTime;
}
