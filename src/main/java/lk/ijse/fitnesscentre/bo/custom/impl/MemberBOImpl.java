package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.MemberBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.MemberDAO;
import lk.ijse.fitnesscentre.dto.MemberDTO;
import lk.ijse.fitnesscentre.entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberBOImpl implements MemberBO {

    MemberDAO memberDAO = (MemberDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.MEMBER);

    @Override
    public boolean addMember(MemberDTO dto) throws SQLException {
        return memberDAO.add(new Member(dto.getMemberId(),
                dto.getMemberName(),
                dto.getMemberContact(),
                dto.getDateOfBirth(),
                dto.getGender(),
                dto.getEmail(),
                dto.getMembershipId(),
                dto.getStartDate(),
                dto.getEndDate())
        );

    }

    @Override
    public boolean updateMember(MemberDTO dto) throws SQLException {
        return memberDAO.update(new Member(dto.getMemberId(),
                dto.getMemberName(),
                dto.getMemberContact(),
                dto.getDateOfBirth(),
                dto.getGender(),
                dto.getEmail(),
                dto.getMembershipId(),
                dto.getStartDate(),
                dto.getEndDate())
        );
    }

    @Override
    public boolean deleteMember(String memberId) throws SQLException {
        return memberDAO.delete(memberId);
    }

    @Override
    public MemberDTO searchByMemberId(String id) throws SQLException {
        Member m = memberDAO.searchById(id);
        return new MemberDTO(m.getMemberId(),
                m.getMemberName(),
                m.getMemberContact(),
                m.getDateOfBirth(),
                m.getGender(),
                m.getEmail(),
                m.getMembershipId(),
                m.getStartDate(),
                m.getEndDate()
        );
    }

    @Override
    public MemberDTO searchByContact(String contact) throws SQLException {
        Member m = memberDAO.searchByContact(contact);
        return new MemberDTO(m.getMemberId(),
                m.getMemberName(),
                m.getMemberContact(),
                m.getDateOfBirth(),
                m.getGender(),
                m.getEmail(),
                m.getMembershipId(),
                m.getStartDate(),
                m.getEndDate()
        );
    }

    @Override
    public List<MemberDTO> getAllMember() throws SQLException {

        List<MemberDTO> allMembers = new ArrayList<>();
        List<Member> all = memberDAO.getAll();

        for (Member m : all) {
            allMembers.add(new MemberDTO(m.getMemberId(),
                    m.getMemberName(),
                    m.getMemberContact(),
                    m.getDateOfBirth(),
                    m.getGender(),
                    m.getEmail(),
                    m.getMembershipId(),
                    m.getStartDate(),
                    m.getEndDate())
            );
        }
        return allMembers;
    }

    @Override
    public List<String> getMemberIds() throws SQLException {
        return memberDAO.getIds();
    }

    @Override
    public int getMemberCount() throws SQLException {
        return memberDAO.getCount();
    }

    @Override
    public String currentMemberId() throws SQLException {
        return memberDAO.currentId();
    }

}