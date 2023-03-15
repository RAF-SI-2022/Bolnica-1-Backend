package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.employees.domain.Profession;
import raf.bolnica1.employees.domain.Title;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

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
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{4,29}@ibis\\.rs$")
    private String email;
    private Title title;
    private Profession profession;
    @NotEmpty
    private String departmentPbo;
    @NotEmpty
    private List<String> permissions;
}
