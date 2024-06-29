package lk.ijse.fitnesscentre.dto;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartDTO {
    private String productId;
    private String memberId;
    private String productName;
    private double unitPrice;
    private int qty;
    private int qtyOnHand;
    private double total;
    private JFXButton btnRemove;
}
