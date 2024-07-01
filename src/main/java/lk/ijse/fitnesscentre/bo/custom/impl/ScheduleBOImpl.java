package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.ScheduleBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.ScheduleDAO;
import lk.ijse.fitnesscentre.dto.ScheduleDTO;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleBOImpl implements ScheduleBO {

    ScheduleDAO scheduleDAO = (ScheduleDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SCHEDULE);

    @Override
    public boolean addSchedule(ScheduleDTO dto) throws SQLException {
        return scheduleDAO.add(new Schedule(dto.getScheduleId(), dto.getScheduleName(), dto.getDescription()));
    }

    @Override
    public boolean updateSchedule(ScheduleDTO dto) throws SQLException {
        return scheduleDAO.update(new Schedule(dto.getScheduleId(), dto.getScheduleName(), dto.getDescription()));
    }

    @Override
    public boolean deleteSchedule(String scheduleId) throws SQLException {
        return scheduleDAO.delete(scheduleId);
    }

    @Override
    public Schedule searchByScheduleId(String scheduleId) throws SQLException {
        return scheduleDAO.searchById(scheduleId);
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() throws SQLException {

        List<ScheduleDTO> allSchedules = new ArrayList<>();
        List<Schedule> all = scheduleDAO.getAll();

        for (Schedule s : all) {
            allSchedules.add(new ScheduleDTO(s.getScheduleId(), s.getScheduleName(), s.getDescription()));
        }
        return allSchedules;
    }

    @Override
    public String currentScheduleId() throws SQLException {
        return scheduleDAO.currentId();
    }

    @Override
    public List<String> getScheduleIds() throws SQLException {
        return scheduleDAO.getIds();
    }

}
