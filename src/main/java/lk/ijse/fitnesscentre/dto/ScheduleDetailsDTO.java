package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ScheduleDetailsDTO {
    private String scheduleId;
    private String scheduleName;
    private String memberId;
    private String memberName;

    public ScheduleDetailsDTO(String scheduleId, String memberId) {
        this.scheduleId = scheduleId;
        this.memberId = memberId;
    }
}
