package lk.ijse.fitnesscentre.dao.custom;

import javafx.scene.chart.BarChart;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.entity.Membership;

import java.sql.SQLException;
import java.util.List;

public interface MembershipDAO extends CrudDAO<Membership> {

    Membership getFee(String membershipId) throws SQLException;

    String getEndDate(String membershipId) throws SQLException;

}
