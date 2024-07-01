package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dto.MembershipDTO;
import lk.ijse.fitnesscentre.entity.Membership;

import java.sql.SQLException;
import java.util.List;

public interface MembershipBO extends SuperBO {

    boolean addMembership(MembershipDTO dto) throws SQLException;

    boolean updateMembership(MembershipDTO dto) throws SQLException;

    boolean deleteMembership(String membershipId) throws SQLException;

    Membership searchByMembershipId(String membershipId) throws SQLException;

    List<MembershipDTO> getAllMemberships() throws SQLException;

    List<String> getMembershipIds() throws SQLException;

    String currentMembershipId() throws SQLException;

    Membership getMembershipFee(String membershipId) throws SQLException;

    String getEndDate(String membershipId) throws SQLException;

}
