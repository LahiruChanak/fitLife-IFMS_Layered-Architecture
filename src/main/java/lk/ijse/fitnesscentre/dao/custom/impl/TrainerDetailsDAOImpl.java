package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.TrainerDetailsDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.Trainer;
import lk.ijse.fitnesscentre.entity.TrainerDetails;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDetailsDAOImpl implements TrainerDetailsDAO {

    public boolean add(TrainerDetails entity) throws SQLException {
       return SQLUtil.execute("INSERT INTO trainerDetails VALUES(?,?)", entity.getScheduleId(), entity.getTrainerId());
    }

    public boolean update(TrainerDetails entity) throws SQLException {
        return SQLUtil.execute("UPDATE trainerDetails SET scheduleId = ? WHERE trainerId = ?", entity.getScheduleId(), entity.getTrainerId());
    }

    public boolean delete(String scheduleId, String trainerId) throws SQLException {
        return SQLUtil.execute("DELETE FROM trainerDetails WHERE scheduleId = ? AND trainerId = ?", scheduleId, trainerId);
    }


    //Not Implemented

    @Override
    public String currentId() throws SQLException { return ""; }

    @Override
    public TrainerDetails searchById(String id) throws SQLException { return null; }

    @Override
    public List<String> getIds() throws SQLException { return List.of(); }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

    @Override
    public List<TrainerDetails> getAll() throws SQLException { return List.of(); }

    @Override
    public boolean add(List<TrainerDetails> entity) throws SQLException { return false; }

}
