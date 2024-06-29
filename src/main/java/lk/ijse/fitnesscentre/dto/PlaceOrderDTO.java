package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PlaceOrderDTO {
    private PurchaseDTO purchase;
    private List<PurchaseDetail> pdList;
}
