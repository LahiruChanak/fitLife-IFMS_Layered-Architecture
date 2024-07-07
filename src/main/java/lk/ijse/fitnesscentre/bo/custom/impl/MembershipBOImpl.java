package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.MembershipBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.MembershipDAO;
import lk.ijse.fitnesscentre.dto.MembershipDTO;
import lk.ijse.fitnesscentre.entity.Membership;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipBOImpl implements MembershipBO {

    MembershipDAO membershipDAO = (MembershipDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.MEMBERSHIP);

    @Override
    public boolean addMembership(MembershipDTO dto) throws SQLException {
        return membershipDAO.add(new Membership(dto.getMembershipId(), dto.getMembershipType(), dto.getDescription(), dto.getMembershipFee()));
    }

    @Override
    public boolean updateMembership(MembershipDTO dto) throws SQLException {
        return membershipDAO.update(new Membership(dto.getMembershipId(), dto.getMembershipType(), dto.getDescription(), dto.getMembershipFee()));
    }

    @Override
    public boolean deleteMembership(String membershipId) throws SQLException {
        return membershipDAO.delete(membershipId);
    }

    @Override
    public MembershipDTO searchByMembershipId(String membershipId) throws SQLException {
        Membership m = membershipDAO.searchById(membershipId);
        return new MembershipDTO(m.getMembershipId(), m.getMembershipType(), m.getDescription(), m.getMembershipFee());
    }

    @Override
    public List<MembershipDTO> getAllMemberships() throws SQLException {

        List<MembershipDTO> allMemberships = new ArrayList<>();
        List<Membership> all = membershipDAO.getAll();

        for (Membership m : all) {
            allMemberships.add(new MembershipDTO(m.getMembershipId(), m.getMembershipType(), m.getDescription(), m.getMembershipFee()));
        }
        return allMemberships;
    }

    @Override
    public List<String> getMembershipIds() throws SQLException {
        return membershipDAO.getIds();
    }

    @Override
    public String currentMembershipId() throws SQLException {
        return membershipDAO.currentId();
    }

    @Override
    public MembershipDTO getMembershipFee(String membershipId) throws SQLException {
        Membership m = membershipDAO.getFee(membershipId);
        return new MembershipDTO(m.getMembershipId(), m.getMembershipType(), m.getDescription(), m.getMembershipFee());
    }

    @Override
    public String getEndDate(String membershipId) throws SQLException {
        return membershipDAO.getEndDate(membershipId);
    }

}
