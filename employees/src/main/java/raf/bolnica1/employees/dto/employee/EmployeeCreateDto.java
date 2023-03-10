package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Profession;
import raf.bolnica1.employees.domain.Title;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeCreateDto {
    @NotEmpty
    private String lbz;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private Date dateOfBirth;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String jmbg;
    @NotEmpty
    private String address;
    @NotEmpty
    private String placeOfLiving;
    @NotEmpty
    private String phone;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{4,29}@ibis\\.rs$")
    private String email;
    @NotEmpty
    private Title title;
    @NotEmpty
    private Profession profession;
    @NotEmpty
    private Long department;
}
