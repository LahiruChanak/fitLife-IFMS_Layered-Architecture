package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MemberDTO {
    private String memberId;
    private String memberName;
    private String memberContact;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String membershipId;
    private Date startDate;
    private Date endDate;

    public MemberDTO(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public MemberDTO(String memberName) {
        this.memberName = memberName;
    }
}
