package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.AttendanceDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.Attendance;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {

    @Override
    public String currentId() throws SQLException {
        String sql = "SELECT attendanceId FROM attendance ORDER BY attendanceId desc LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public Attendance searchById(String attendanceId) throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM attendance WHERE attendanceId = ?", attendanceId);

        Attendance attendance = null;

        if (rst.next()) {
            String attendance_id = rst.getString("attendanceId");
            String memberName = rst.getString("memberName");
            LocalDate date = rst.getDate("date").toLocalDate();
            LocalTime time = rst.getTime("time").toLocalTime();
            String memberId = rst.getString("memberId");

            attendance = new Attendance(attendance_id, memberName, date, time, memberId);
        }
        return attendance;
    }

    @Override
    public boolean add(Attendance entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO attendance VALUES(?,?,?,?,?)", entity.getAttendanceId(), entity.getMemberName(), entity.getDate(), entity.getTime(), entity.getMemberId());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM attendance WHERE id = ?", id);
    }

    @Override
    public List<Attendance> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM attendance");

        List<Attendance> attendanceList = new ArrayList<>();

        Attendance attendance = null;

        while (rst.next()) {
            String attendanceId = rst.getString("attendanceId");
            String memberName = rst.getString("memberName");
            LocalDate date = rst.getDate("date").toLocalDate();
            LocalTime time = rst.getTime("time").toLocalTime();
            String memberId = rst.getString("memberId");

            attendance = new Attendance(attendanceId, memberName, date, time, memberId);
            attendanceList.add(attendance);
        }
        return attendanceList;
    }


    //Not Implemented

    @Override
    public boolean add(List<Attendance> entity) throws SQLException { return false; }

    @Override
    public boolean update(Attendance entity) throws SQLException { return false; }

    @Override
    public List<String> getIds() throws SQLException { return List.of(); }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

}
