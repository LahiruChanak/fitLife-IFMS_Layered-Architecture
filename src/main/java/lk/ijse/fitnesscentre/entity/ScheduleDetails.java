package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ScheduleDetails {
    private String scheduleId;
    private String scheduleName;
    private String memberId;
    private String memberName;

    public ScheduleDetails(String scheduleId, String memberId) {
        this.scheduleId = scheduleId;
        this.memberId = memberId;
    }

}
