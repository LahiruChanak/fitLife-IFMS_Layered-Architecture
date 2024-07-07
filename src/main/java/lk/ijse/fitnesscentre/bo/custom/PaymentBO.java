package lk.ijse.fitnesscentre.bo.custom;

import javafx.scene.chart.BarChart;
import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.PaymentDTO;
import lk.ijse.fitnesscentre.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {

    boolean addPayment(PaymentDTO dto) throws SQLException;

    PaymentDTO searchByPaymentId(String paymentId) throws SQLException;

    List<PaymentDTO> getAllPayment() throws SQLException;

    double getTotalPayment() throws SQLException;

    String currentPaymentId() throws SQLException;

    void monthlyFeeChart(BarChart<String, Number> barChart) throws SQLException;
}
