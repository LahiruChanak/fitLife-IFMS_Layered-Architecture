package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TrainerDetails {
    private String scheduleId;
    private String scheduleName;
    private String trainerId;
    private String trainerName;

    public TrainerDetails(String scheduleId, String trainerId) {
        this.scheduleId = scheduleId;
        this.trainerId = trainerId;
    }
}
