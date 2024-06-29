package lk.ijse.fitnesscentre.dao.custom;

import javafx.scene.chart.BarChart;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {

    void monthlyFeeChart(BarChart<String, Number> barChart) throws SQLException;

}
