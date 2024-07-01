package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.MemberDTO;
import lk.ijse.fitnesscentre.entity.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberBO extends SuperBO {

    boolean addMember(MemberDTO dto) throws SQLException;

    boolean updateMember(MemberDTO dto) throws SQLException;

    boolean deleteMember(String id) throws SQLException;

    Member searchByMemberId(String id) throws SQLException;

    Member searchByContact(String contact) throws SQLException;

    List<MemberDTO> getAllMember() throws SQLException;

    List<String> getMemberIds() throws SQLException;

    int getMemberCount() throws SQLException;

    String currentMemberId() throws SQLException;

}
