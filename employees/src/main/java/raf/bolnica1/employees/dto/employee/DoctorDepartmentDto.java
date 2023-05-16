package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDepartmentDto implements Serializable {

    private String name;
    private String surname;
    private String lbz;
    private Long departmentId;

}
