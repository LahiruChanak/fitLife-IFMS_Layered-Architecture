package lk.ijse.fitnesscentre.dao.custom;

import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;

import java.sql.SQLException;

public interface ScheduleDetailsDAO extends CrudDAO<ScheduleDetails> {

    boolean delete(String scheduleId,String memberId) throws SQLException;

}
