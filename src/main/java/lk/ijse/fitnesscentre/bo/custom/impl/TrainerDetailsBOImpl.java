package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.TrainerDetailsBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.QueryDAO;
import lk.ijse.fitnesscentre.dao.custom.TrainerDetailsDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.dto.TrainerDetailsDTO;
import lk.ijse.fitnesscentre.entity.TrainerDetails;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDetailsBOImpl implements TrainerDetailsBO {

    TrainerDetailsDAO trainerDetailsDAO = (TrainerDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TRAINER_DETAILS);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);

    @Override
    public List<TrainerDetailsDTO> getAllTrainerDetails() throws SQLException {

        List<TrainerDetailsDTO> allTrainerDetails = new ArrayList<>();
        List<TrainerDetails> all = queryDAO.getTrainerDetails();

        for (TrainerDetails td : all) {
            allTrainerDetails.add(new TrainerDetailsDTO(td.getScheduleId(), td.getScheduleName(), td.getTrainerId(), td.getTrainerName()));
        }
        return allTrainerDetails;
    }

    @Override
    public boolean addTrainerDetails(TrainerDetailsDTO dto) throws SQLException {
       return trainerDetailsDAO.add(new TrainerDetails(dto.getScheduleId(), dto.getTrainerId()));
    }

    @Override
    public boolean updateTrainerDetails(TrainerDetailsDTO dto) throws SQLException {
        return trainerDetailsDAO.update(new TrainerDetails(dto.getScheduleId(), dto.getTrainerId()));
    }

    @Override
    public boolean deleteTrainerDetails(String scheduleId, String trainerId) throws SQLException {
        return trainerDetailsDAO.delete(scheduleId, trainerId);
    }

}
