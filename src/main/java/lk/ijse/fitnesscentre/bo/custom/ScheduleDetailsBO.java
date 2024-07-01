package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.ScheduleDetailsDTO;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleDetailsBO extends SuperBO {

    List<ScheduleDetailsDTO> getAllScheduleDetails() throws SQLException;

    boolean addScheduleDetails(ScheduleDetailsDTO entity) throws SQLException;

    boolean updateScheduleDetails(ScheduleDetailsDTO entity) throws SQLException;

    boolean deleteScheduleDetails(String scheduleId,String memberId) throws SQLException;

}
