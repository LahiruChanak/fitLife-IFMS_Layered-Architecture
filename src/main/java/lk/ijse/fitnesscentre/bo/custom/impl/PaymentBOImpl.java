package lk.ijse.fitnesscentre.bo.custom.impl;

import javafx.scene.chart.BarChart;
import lk.ijse.fitnesscentre.bo.custom.PaymentBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.PaymentDAO;
import lk.ijse.fitnesscentre.dto.AttendanceDTO;
import lk.ijse.fitnesscentre.dto.PaymentDTO;
import lk.ijse.fitnesscentre.entity.Attendance;
import lk.ijse.fitnesscentre.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean addPayment(PaymentDTO dto) throws SQLException {
        return paymentDAO.add(new Payment(dto.getPaymentId(),
                dto.getPaymentMethod(),
                dto.getMembershipFee(),
                dto.getDate(),
                dto.getTime(),
                dto.getMembershipId(),
                dto.getMemberId())
        );
    }

    @Override
    public PaymentDTO searchByPaymentId(String paymentId) throws SQLException {
        Payment p = paymentDAO.searchById(paymentId);
        return new PaymentDTO(p.getPaymentId(),
                p.getPaymentMethod(),
                p.getMembershipFee(),
                p.getDate(),
                p.getTime(),
                p.getMembershipId(),
                p.getMemberId()
        );
    }

    @Override
    public List<PaymentDTO> getAllPayment() throws SQLException {

        List<PaymentDTO> allPayments = new ArrayList<>();
        List<Payment> all = paymentDAO.getAll();

        for (Payment p : all) {
            allPayments.add(new PaymentDTO(p.getPaymentId(),
                    p.getPaymentMethod(),
                    p.getMembershipFee(),
                    p.getDate(),
                    p.getTime(),
                    p.getMembershipId(),
                    p.getMemberId())
            );
        }
        return allPayments;
    }

    @Override
    public double getTotalPayment() throws SQLException {
        return paymentDAO.getTotal();
    }

    @Override
    public String currentPaymentId() throws SQLException {
        return paymentDAO.currentId();
    }

    @Override
    public void monthlyFeeChart(BarChart<String, Number> barChart) throws SQLException {
        paymentDAO.monthlyFeeChart(barChart);
    }

}
