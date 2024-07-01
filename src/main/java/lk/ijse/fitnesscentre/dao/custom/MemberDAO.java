package lk.ijse.fitnesscentre.dao.custom;

import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.entity.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO extends CrudDAO<Member> {

    Member searchByContact(String contact) throws SQLException;


}
