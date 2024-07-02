package lk.ijse.fitnesscentre.dao.custom;

import javafx.scene.chart.PieChart;
import lk.ijse.fitnesscentre.dao.SuperDAO;
import lk.ijse.fitnesscentre.entity.PurchaseHistory;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.entity.TrainerDetails;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<PurchaseHistory> getPurchaseHistory() throws SQLException;

    List<ScheduleDetails> getScheduleDetails() throws SQLException;

    List<TrainerDetails> getTrainerDetails() throws SQLException;

    void productSales(PieChart pieChart) throws SQLException;

}
