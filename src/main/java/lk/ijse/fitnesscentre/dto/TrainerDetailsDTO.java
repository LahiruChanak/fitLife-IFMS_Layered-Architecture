package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TrainerDetailsDTO {
    private String scheduleId;
    private String scheduleName;
    private String trainerId;
    private String trainerName;

    public TrainerDetailsDTO(String scheduleId, String trainerId) {
        this.scheduleId = scheduleId;
        this.trainerId = trainerId;
    }
}
