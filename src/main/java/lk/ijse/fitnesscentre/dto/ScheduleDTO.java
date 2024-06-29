package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ScheduleDTO {
    private String scheduleId;
    private String scheduleName;
    private String description;

    public ScheduleDTO(String scheduleName) {
        this.scheduleName = scheduleName;
    }
}
