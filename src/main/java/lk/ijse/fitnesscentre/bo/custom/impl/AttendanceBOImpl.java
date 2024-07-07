package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.AttendanceBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.AttendanceDAO;
import lk.ijse.fitnesscentre.dto.AttendanceDTO;
import lk.ijse.fitnesscentre.entity.Attendance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);

    @Override
    public String currentAttendanceId() throws SQLException {
        return attendanceDAO.currentId();
    }

    @Override
    public AttendanceDTO searchByAttendanceId(String attendanceId) throws SQLException {
        Attendance a = attendanceDAO.searchById(attendanceId);
        return new AttendanceDTO(a.getAttendanceId(), a.getMemberName(), a.getDate(), a.getTime(), a.getMemberId());
    }

    @Override
    public boolean addAttendance(AttendanceDTO dto) throws SQLException {
        return attendanceDAO.add(new Attendance(dto.getAttendanceId(),
                dto.getMemberName(),
                dto.getDate(),
                dto.getTime(),
                dto.getMemberId())
        );
    }

    public List<AttendanceDTO> getAllAttendances() throws SQLException {

        List<AttendanceDTO> allAttendances = new ArrayList<>();
        List<Attendance> all = attendanceDAO.getAll();

        for (Attendance a : all) {
            allAttendances.add(new AttendanceDTO(a.getAttendanceId(), a.getMemberName(), a.getDate(), a.getTime(), a.getMemberId()));
        }
        return allAttendances;
    }

}
