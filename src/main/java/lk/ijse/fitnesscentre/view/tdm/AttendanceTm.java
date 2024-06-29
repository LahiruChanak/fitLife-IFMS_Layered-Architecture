package lk.ijse.fitnesscentre.view.tdm;

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

public class AttendanceTm {
    private String attendanceId;
    private String memberName;
    private LocalDate date;
    private LocalTime time;
    private String memberId;
}