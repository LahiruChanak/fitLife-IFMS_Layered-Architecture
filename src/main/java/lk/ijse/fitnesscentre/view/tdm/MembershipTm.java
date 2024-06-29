package lk.ijse.fitnesscentre.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MembershipTm {
    private String membershipId;
    private String membershipType;
    private String description;
    private double membershipFee;
}
