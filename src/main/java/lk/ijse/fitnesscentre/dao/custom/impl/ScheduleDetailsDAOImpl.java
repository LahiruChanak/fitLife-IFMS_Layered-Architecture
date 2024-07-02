package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.ScheduleDetailsDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsDAOImpl implements ScheduleDetailsDAO {

    public boolean add(ScheduleDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO scheduleDetails VALUES(?,?)", entity.getScheduleId(), entity.getMemberId());
    }

    public boolean update(ScheduleDetails entity) throws SQLException {
        return SQLUtil.execute("UPDATE scheduleDetails SET scheduleId = ? WHERE memberId = ?", entity.getScheduleId(), entity.getMemberId());
    }

    public boolean delete(String scheduleId,String memberId) throws SQLException {
        return SQLUtil.execute("DELETE FROM scheduleDetails WHERE scheduleId = ? AND memberId = ?", scheduleId, memberId);
    }


    //Not Implemented

    @Override
    public List<String> getIds() throws SQLException { return null; }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

    @Override
    public String currentId() throws SQLException { return null; }

    @Override
    public ScheduleDetails searchById(String id) throws SQLException { return null; }

    @Override
    public boolean add(List<ScheduleDetails> entity) throws SQLException { return false; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

    @Override
    public List<ScheduleDetails> getAll() throws SQLException { return List.of(); }

}
