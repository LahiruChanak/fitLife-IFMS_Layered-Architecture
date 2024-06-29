package lk.ijse.fitnesscentre.view.tdm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartTm {
    private String productId;
    private String productName;
    private double unitPrice;
    private int qty;
    private double total;
    private JFXButton btnRemove;
}
