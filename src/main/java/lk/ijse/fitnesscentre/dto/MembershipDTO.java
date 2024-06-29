package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MembershipDTO {
    private String membershipId;
    private String membershipType;
    private String description;
    private double membershipFee;

    public MembershipDTO(double membershipFee) {
        this.membershipFee = membershipFee;
    }

}
