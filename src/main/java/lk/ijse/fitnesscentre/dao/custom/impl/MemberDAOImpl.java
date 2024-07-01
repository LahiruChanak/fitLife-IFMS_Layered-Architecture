package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.MemberDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {

    @Override
    public boolean add(Member entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?)", entity.getMemberId(), entity.getMemberName(), entity.getMemberContact(), entity.getDateOfBirth(), entity.getGender(), entity.getEmail(), entity.getMembershipId(), entity.getStartDate(), entity.getEndDate());
    }

    @Override
    public boolean update(Member entity) throws SQLException {
        return SQLUtil.execute("UPDATE member SET memberName = ?, memberContact = ?, dateOfBirth = ?, gender = ?, email = ?, membershipId = ?, startDate = ?, endDate = ? WHERE memberId = ?",
                entity.getMemberName(), entity.getMemberContact(), entity.getDateOfBirth(), entity.getGender(), entity.getEmail(), entity.getMembershipId(), entity.getStartDate(), entity.getEndDate(), entity.getMemberId());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM member WHERE id = ?", id);
    }

    @Override
    public Member searchById(String id) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM member WHERE memberId = ?", id);

        Member member = null;

        if (resultSet.next()) {
            String member_id = resultSet.getString(1);
            String memberName = resultSet.getString(2);
            String memberContact = resultSet.getString(3);
            Date dateOfBirth = resultSet.getDate(4);
            String gender = resultSet.getString(5);
            String email = resultSet.getString(6);
            String membershipId = resultSet.getString(7);
            Date startDate = resultSet.getDate(8);
            Date endDate = resultSet.getDate(9);

            member = new Member(member_id, memberName, memberContact, dateOfBirth, gender, email, membershipId, startDate, endDate);
        }
        return member;
    }

    @Override
    public Member searchByContact(String contact) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM member WHERE memberContact = ?", contact);

        Member member = null;

        if (resultSet.next()) {
            String member_id = resultSet.getString(1);
            String memberName = resultSet.getString(2);
            String memberContact = resultSet.getString(3);
            Date dateOfBirth = resultSet.getDate(4);
            String gender = resultSet.getString(5);
            String email = resultSet.getString(6);
            String membershipId = resultSet.getString(7);
            Date startDate = resultSet.getDate(8);
            Date endDate = resultSet.getDate(9);

            member = new Member(member_id,memberName,memberContact,dateOfBirth,gender,email, membershipId, startDate, endDate);
        }
        return member;
    }

    @Override
    public List<Member> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM member");

        List<Member> memberList = new ArrayList<>();

        while (resultSet.next()) {
            String memberId = resultSet.getString(1);
            String memberName = resultSet.getString(2);
            String memberContact = resultSet.getString(3);
            Date dateOfBirth = resultSet.getDate(4);
            String gender = resultSet.getString(5);
            String email = resultSet.getString(6);
            String membershipId = resultSet.getString(7);
            Date startDate = resultSet.getDate(8);
            Date endDate = resultSet.getDate(9);

            Member member = new Member(memberId, memberName, memberContact, dateOfBirth, gender, email, membershipId, startDate, endDate);
            memberList.add(member);
        }
        return memberList;
    }

    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT memberId FROM member ORDER BY memberId");

        List<String> idList = new ArrayList<>();

        while(resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public int getCount() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS member_count FROM member");

        int memberCount = 0;

        if (resultSet.next()) {
            memberCount = resultSet.getInt("member_count");
        }
        return memberCount;
    }

    @Override
    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT memberId FROM member ORDER BY memberId desc LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }


    //Not Implemented

    @Override
    public boolean add(List<Member> entity) throws SQLException { return false; }

    @Override
    public double getTotal() throws SQLException { return 0; }

}