package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.TrainerDetailsDTO;
import lk.ijse.fitnesscentre.entity.TrainerDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TrainerDetailsBO extends SuperBO {

    List<TrainerDetailsDTO> getAllTrainerDetails() throws SQLException;

    boolean addTrainerDetails(TrainerDetailsDTO dto) throws SQLException;

    boolean updateTrainerDetails(TrainerDetailsDTO dto) throws SQLException;

    boolean deleteTrainerDetails(String scheduleId, String trainerId) throws SQLException;

}
