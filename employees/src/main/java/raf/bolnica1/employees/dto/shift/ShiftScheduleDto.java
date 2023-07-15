package raf.bolnica1.employees.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftScheduleDto {
    private Long id;
    private int shift;
    private String lbz;
    private String doctor;
   private Date date;
}
