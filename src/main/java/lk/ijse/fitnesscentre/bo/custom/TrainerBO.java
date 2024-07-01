package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dto.TrainerDTO;
import lk.ijse.fitnesscentre.entity.Trainer;

import java.sql.SQLException;
import java.util.List;

public interface TrainerBO extends SuperBO {

    boolean addTrainer(TrainerDTO dto) throws SQLException;

    boolean updateTrainer(TrainerDTO dto) throws SQLException;

    boolean deleteTrainer(String trainerId) throws SQLException;

    Trainer searchByTrainerId(String trainerId) throws SQLException;

    List<TrainerDTO> getAllTrainers() throws SQLException;

    int getTrainerCount() throws SQLException;

    String currentTrainerId() throws SQLException;

    List<String> getTrainerIds() throws SQLException;

}
