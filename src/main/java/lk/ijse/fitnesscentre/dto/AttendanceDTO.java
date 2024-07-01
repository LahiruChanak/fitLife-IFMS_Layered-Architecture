package lk.ijse.fitnesscentre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AttendanceDTO {
    private String attendanceId;
    private String memberName;
    private LocalDate date;
    private LocalTime time;
    private String memberId;
}