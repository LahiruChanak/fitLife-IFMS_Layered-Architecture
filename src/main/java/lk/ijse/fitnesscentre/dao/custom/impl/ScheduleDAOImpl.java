package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.ScheduleDAO;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAOImpl implements ScheduleDAO {

    public boolean add(Schedule entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO schedule VALUES(?,?,?)", entity.getScheduleId(), entity.getScheduleName(), entity.getDescription());
    }

    public boolean update(Schedule entity) throws SQLException {
        return SQLUtil.execute("UPDATE member SET scheduleName = ?, description = ? WHERE scheduleId = ?", entity.getScheduleName(), entity.getDescription(), entity.getScheduleId());
    }

    public boolean delete(String scheduleId) throws SQLException {
        return SQLUtil.execute("DELETE FROM schedule WHERE scheduleId = ?", scheduleId);
    }

    public Schedule searchById(String scheduleId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM schedule WHERE scheduleId = ?", scheduleId);

        Schedule schedule = null;

        if (resultSet.next()) {
            String schedule_id = resultSet.getString(1);
            String scheduleName = resultSet.getString(2);
            String description = resultSet.getString(3);

            schedule = new Schedule(schedule_id, scheduleName, description);
        }
        return schedule;
    }

    public List<Schedule> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM schedule");

        List<Schedule> scheduleList = new ArrayList<>();
        while (resultSet.next()) {
            String scheduleId = resultSet.getString(1);
            String scheduleName = resultSet.getString(2);
            String description = resultSet.getString(3);

            Schedule schedule = new Schedule(scheduleId, scheduleName, description);
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT scheduleId FROM schedule ORDER BY scheduleId DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT scheduleId FROM schedule");

        List<String> typeList = new ArrayList<>();

        while (resultSet.next()) {
            typeList.add(resultSet.getString("scheduleId"));
        }
        return typeList;
    }


    //Not Implemented

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

    @Override
    public boolean add(List<Schedule> entities) throws SQLException { return false; }

}
