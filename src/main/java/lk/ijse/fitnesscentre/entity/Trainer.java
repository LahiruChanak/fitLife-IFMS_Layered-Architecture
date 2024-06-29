package lk.ijse.fitnesscentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Trainer {
    private String trainerId;
    private String trainerName;
    private String trainerAddress;
    private String trainerContact;
    private String trainerExperience;

    public Trainer(String trainerName) {
        this.trainerName = trainerName;
    }

}
