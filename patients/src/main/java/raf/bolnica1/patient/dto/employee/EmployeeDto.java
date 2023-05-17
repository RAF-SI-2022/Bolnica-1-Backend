package raf.bolnica1.patient.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.Profession;
import raf.bolnica1.patient.domain.constants.Title;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto implements Serializable {
    private Long id;
    private String lbz;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String gender;
    private String jmbg;
    private String address;
    private String placeOfLiving;
    private String phone;
    private String email;
    private String username;
    private boolean deleted;
    private Title title;
    private Profession profession;
    private DepartmentDto department;
}
