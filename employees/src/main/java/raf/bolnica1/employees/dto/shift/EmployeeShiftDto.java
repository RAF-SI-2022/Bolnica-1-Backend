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
public class EmployeeShiftDto {
    private long shiftId;
    private String lbz;
    private Date startDate;
    private Date endDate;
}
