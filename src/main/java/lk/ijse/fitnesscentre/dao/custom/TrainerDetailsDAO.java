package lk.ijse.fitnesscentre.dao.custom;

import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.entity.TrainerDetails;

import java.sql.SQLException;

public interface TrainerDetailsDAO extends CrudDAO<TrainerDetails> {

    boolean delete(String scheduleId, String trainerId) throws SQLException;

}
