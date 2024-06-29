package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.TrainerDAO;
import lk.ijse.fitnesscentre.entity.Trainer;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAOImpl implements TrainerDAO {

    @Override
    public boolean add(Trainer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO trainer VALUES(?,?,?,?,?)", entity.getTrainerId(), entity.getTrainerName(), entity.getTrainerAddress(), entity.getTrainerContact(), entity.getTrainerExperience());
    }

    @Override
    public boolean update(Trainer entity) throws SQLException {
        return SQLUtil.execute("UPDATE trainer SET trainerName = ?, trainerAddress = ?, trainerContact = ?, trainerExperience = ? WHERE trainerId = ?", entity.getTrainerName(), entity.getTrainerAddress(), entity.getTrainerContact(), entity.getTrainerExperience(), entity.getTrainerId());
    }

    @Override
    public boolean delete(String trainerId) throws SQLException {
        return SQLUtil.execute("DELETE FROM trainer WHERE trainerId = ?", trainerId);
    }

    @Override
    public Trainer searchById(String trainerId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM trainer WHERE trainerId = ?", trainerId);

        Trainer trainer = null;

        if (resultSet.next()) {
            String trainer_id = resultSet.getString(1);
            String trainerName = resultSet.getString(2);
            String trainerAddress = resultSet.getString(3);
            String trainerContact = resultSet.getString(4);
            String trainerExperience = resultSet.getString(5);

            trainer = new Trainer(trainer_id,trainerName,trainerAddress,trainerContact,trainerExperience);
        }
        return trainer;
    }

    @Override
    public List<Trainer> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM trainer");

        List<Trainer> trainerList = new ArrayList<>();
        while (resultSet.next()) {
            String trainer_id = resultSet.getString(1);
            String trainerName = resultSet.getString(2);
            String trainerAddress = resultSet.getString(3);
            String trainerContact = resultSet.getString(4);
            String trainerExperience = resultSet.getString(5);

            Trainer trainer = new Trainer(trainer_id,trainerName,trainerAddress,trainerContact,trainerExperience);
            trainerList.add(trainer);
        }
        return trainerList;
    }

    @Override
    public int getCount() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS trainer_count FROM trainer");

        int trainerCount = 0;

        if (resultSet.next()) {
            trainerCount = resultSet.getInt("trainer_count");
        }
        return trainerCount;
    }

    @Override
    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT trainerId FROM trainer ORDER BY trainerId DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT trainerId FROM trainer");

        List<String> typeList = new ArrayList<>();

        while (resultSet.next()) {
            typeList.add(resultSet.getString("trainerId"));
        }
        return typeList;
    }


    //Not Implemented

    @Override
    public double getTotal() throws SQLException { return 0; }

    @Override
    public boolean add(List<Trainer> entity) throws SQLException { return false; }

}
