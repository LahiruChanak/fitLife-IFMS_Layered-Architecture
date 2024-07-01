package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.TrainerBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.TrainerDAO;
import lk.ijse.fitnesscentre.dto.TrainerDTO;
import lk.ijse.fitnesscentre.entity.Trainer;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerBOImpl implements TrainerBO {

    TrainerDAO trainerDAO = (TrainerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TRAINER);

    @Override
    public boolean addTrainer(TrainerDTO dto) throws SQLException {
        return trainerDAO.add(new Trainer(dto.getTrainerId(),
                dto.getTrainerName(),
                dto.getTrainerAddress(),
                dto.getTrainerContact(),
                dto.getTrainerExperience())
        );
    }

    @Override
    public boolean updateTrainer(TrainerDTO dto) throws SQLException {
        return trainerDAO.update(new Trainer(dto.getTrainerId(),
                dto.getTrainerName(),
                dto.getTrainerAddress(),
                dto.getTrainerContact(),
                dto.getTrainerExperience())
        );
    }

    @Override
    public boolean deleteTrainer(String trainerId) throws SQLException {
        return trainerDAO.delete(trainerId);
    }

    @Override
    public Trainer searchByTrainerId(String trainerId) throws SQLException {
        return trainerDAO.searchById(trainerId);
    }

    @Override
    public List<TrainerDTO> getAllTrainers() throws SQLException {

        List<TrainerDTO> allTrainers = new ArrayList<>();
        List<Trainer> all = trainerDAO.getAll();

        for (Trainer t : all) {
            allTrainers.add(new TrainerDTO(t.getTrainerId(),
                    t.getTrainerName(),
                    t.getTrainerAddress(),
                    t.getTrainerContact(),
                    t.getTrainerExperience())
            );
        }
        return allTrainers;
    }

    @Override
    public int getTrainerCount() throws SQLException {
        return trainerDAO.getCount();
    }

    @Override
    public String currentTrainerId() throws SQLException {
        return trainerDAO.currentId();
    }

    @Override
    public List<String> getTrainerIds() throws SQLException {
        return trainerDAO.getIds();
    }

}
