package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Schedule {
    private String scheduleId;
    private String scheduleName;
    private String description;

}
