package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.ScheduleDTO;
import lk.ijse.fitnesscentre.entity.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleBO extends SuperBO {

    boolean addSchedule(ScheduleDTO dto) throws SQLException;

    boolean updateSchedule(ScheduleDTO dto) throws SQLException;

    boolean deleteSchedule(String scheduleId) throws SQLException;

    Schedule searchByScheduleId(String scheduleId) throws SQLException;

    List<ScheduleDTO> getAllSchedules() throws SQLException;

    String currentScheduleId() throws SQLException;

    List<String> getScheduleIds() throws SQLException;

}
