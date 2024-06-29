package lk.ijse.fitnesscentre.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ScheduleDetailsTm {
    private String scheduleId;
    private String scheduleName;
    private String memberId;
    private String memberName;
}
