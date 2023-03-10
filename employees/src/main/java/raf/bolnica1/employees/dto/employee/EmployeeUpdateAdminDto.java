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
public class EmployeeUpdateAdminDto {
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
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{4,29}$")
    /// Username can start with letter only, can have numbers, and length must be between 5 and 30
    private String username;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_.-]{5,30}$")
    /// Password can have letters, numbers and special characters like _ . - length should be between 5 and 30
    private String password;
    @NotEmpty
    private boolean deleted;
    @NotEmpty
    private Title title;
    @NotEmpty
    private Profession profession;
    @NotEmpty
    private Department department;
}
