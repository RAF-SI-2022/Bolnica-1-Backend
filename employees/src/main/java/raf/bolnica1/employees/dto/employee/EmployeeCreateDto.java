package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Profession;
import raf.bolnica1.employees.domain.Title;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeCreateDto {
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
    private Title title;
    private Profession profession;
    private Long department;
}
