package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.Title;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeUpdateAdminDto implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotNull
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
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,63}$")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{4,29}$")
    /// Username can start with letter only, can have numbers, and length must be between 5 and 30
    private String username;
    @Pattern(regexp = "^(?:[a-zA-Z0-9_.-]{5,30})?$")
    /// Password can have letters, numbers and special characters like _ . - length should be between 5 and 30
    private String password;
    private boolean deleted;
    private Title title;
    private Profession profession;
    @NotEmpty
    private String departmentPbo;
    @NotEmpty
    private List<String> permissions;
}
