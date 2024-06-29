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

//    public List<String> getIds() throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT trainerId FROM trainer");
//
//        List<String> typeList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            typeList.add(resultSet.getString("trainerId"));
//        }
//        return typeList;
//    }

//    public List<String> getIds() throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT scheduleId FROM schedule");
//
//        List<String> typeList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            typeList.add(resultSet.getString("scheduleId"));
//        }
//        return typeList;
//    }

//    public Schedule searchByScheduleId(String scheduleId) throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT scheduleName FROM schedule WHERE scheduleId = ?", scheduleId);
//
//        Schedule schedule = null;
//
//        if (resultSet.next()) {
//            String name = resultSet.getString("scheduleName");
//
//            schedule = new Schedule(name);
//        }
//        return schedule;
//    }

//    public Trainer searchById(String trainerId) throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT trainerName FROM trainer WHERE trainerId = ?", trainerId);
//
//        Trainer trainer = null;
//
//        if (resultSet.next()) {
//            String name = resultSet.getString("trainerName");
//
//            trainer = new Trainer(name);
//        }
//        return trainer;
//    }

    public List<TrainerDetails> getAll() throws SQLException {
        String sql = "SELECT s.scheduleId, s.scheduleName, t.trainerId, t.trainerName FROM trainerDetails td JOIN trainer t ON td.trainerId = t.trainerId JOIN schedule s ON td.scheduleId = s.scheduleId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TrainerDetails> tdList = new ArrayList<>();
        while (resultSet.next()) {
            String scheduleId = resultSet.getString(1);
            String scheduleName = resultSet.getString(2);
            String trainerId = resultSet.getString(3);
            String trainerName = resultSet.getString(4);

            TrainerDetails td = new TrainerDetails(scheduleId, scheduleName, trainerId, trainerName);
            tdList.add(td);
        }
        return tdList;
    }

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
    public boolean add(List<TrainerDetails> entity) throws SQLException { return false; }

}
