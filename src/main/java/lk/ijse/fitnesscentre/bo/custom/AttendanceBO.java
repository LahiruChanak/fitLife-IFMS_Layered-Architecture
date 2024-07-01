package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dto.AttendanceDTO;
import lk.ijse.fitnesscentre.entity.Attendance;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceBO extends SuperBO {

    String currentAttendanceId() throws SQLException;

    Attendance searchByAttendanceId(String attendanceId) throws SQLException;

    boolean addAttendance(AttendanceDTO dto) throws SQLException;

    List<AttendanceDTO> getAllAttendances() throws SQLException;

}
