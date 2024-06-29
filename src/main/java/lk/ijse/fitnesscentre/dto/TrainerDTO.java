package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TrainerDTO {
    private String trainerId;
    private String trainerName;
    private String trainerAddress;
    private String trainerContact;
    private String trainerExperience;

    public TrainerDTO(String trainerName) {
        this.trainerName = trainerName;
    }

}
