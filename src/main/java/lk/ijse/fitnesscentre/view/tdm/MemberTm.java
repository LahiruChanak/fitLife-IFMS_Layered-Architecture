package lk.ijse.fitnesscentre.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MemberTm {
    private String memberId;
    private String memberName;
    private String memberContact;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String membershipId;
    private Date startDate;
    private Date endDate;
}
