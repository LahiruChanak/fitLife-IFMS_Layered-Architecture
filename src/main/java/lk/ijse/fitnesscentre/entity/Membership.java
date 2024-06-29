package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Membership {
    private String membershipId;
    private String membershipType;
    private String description;
    private double membershipFee;

    public Membership(double membershipFee) {
        this.membershipFee = membershipFee;
    }

}
