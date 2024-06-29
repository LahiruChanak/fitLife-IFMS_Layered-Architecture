package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AttendanceDTO {
    private String attendanceId;
    private String memberName;
    private Date date;
    private Time time;
    private String memberId;
}