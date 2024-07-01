package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.ScheduleDetailsDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsDAOImpl implements ScheduleDetailsDAO {

//    public List<String> getIds() throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT scheduleId FROM schedule");
//
//        List<String> typeList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            typeList.addMember(resultSet.getString("scheduleId"));
//        }
//        return typeList;
//    }

//    public List<String> getIds() throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT memberId FROM member");
//
//        List<String> typeList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            typeList.addMember(resultSet.getString("memberId"));
//        }
//        return typeList;
//    }

//    public Schedule searchById(String scheduleId) throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT scheduleName FROM schedule WHERE scheduleId = ?", scheduleId);
//
//        Schedule schedule = null;
//
//        if (resultSet.next()) {
//            String name = resultSet.getString("scheduleName");
//
//            schedule = new Schedule(name);
//        }
//        return schedule;
//    }

//    public Member searchById(String memberId) throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT memberName FROM member WHERE memberId = ?", memberId);
//
//        Member member = null;
//
//        if (resultSet.next()) {
//            String name = resultSet.getString("memberName");
//
//            member = new Member(name);
//        }
//        return member;
//    }

    public List<ScheduleDetails> getAll() throws SQLException {
        String sql = "SELECT s.scheduleId, s.scheduleName, m.memberId, m.memberName FROM scheduleDetails sd JOIN member m ON sd.memberId = m.memberId JOIN schedule s ON sd.scheduleId = s.scheduleId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<ScheduleDetails> scheduleDetailsList = new ArrayList<>();
        while (resultSet.next()) {
            String scheduleId = resultSet.getString(1);
            String scheduleName = resultSet.getString(2);
            String memberId = resultSet.getString(3);
            String memberName = resultSet.getString(4);

            ScheduleDetails scheduleDetails = new ScheduleDetails(scheduleId, scheduleName, memberId, memberName);
            scheduleDetailsList.add(scheduleDetails);
        }
        return scheduleDetailsList;
    }

    public boolean add(ScheduleDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO scheduleDetails VALUES(?,?)", entity.getScheduleId(), entity.getMemberId());
    }

    public boolean update(ScheduleDetails entity) throws SQLException {
        return SQLUtil.execute("UPDATE scheduleDetails SET scheduleId = ? WHERE memberId = ?", entity.getScheduleId(), entity.getMemberId());
    }

    public boolean delete(String scheduleId,String memberId) throws SQLException {
        return SQLUtil.execute("DELETE FROM scheduleDetails WHERE scheduleId = ? AND memberId = ?", scheduleId, memberId);
    }


    //Not Implemented

    @Override
    public List<String> getIds() throws SQLException { return null; }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

    @Override
    public String currentId() throws SQLException { return null; }

    @Override
    public ScheduleDetails searchById(String id) throws SQLException { return null; }

    @Override
    public boolean add(List<ScheduleDetails> entity) throws SQLException { return false; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

}
