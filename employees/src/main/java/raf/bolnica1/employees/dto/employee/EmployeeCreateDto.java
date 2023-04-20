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
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeCreateDto {
    @NotEmpty(message = "LBZ ne sme biti prazan.")
    private String lbz;
    @NotEmpty(message = "Ime ne sme biti prazno.")
    private String name;
    @NotEmpty(message = "Prezime ne sme biti prazno.")
    private String surname;
    @NotNull(message = "Datum rođenja ne sme biti prazan.")
    private Date dateOfBirth;
    @NotEmpty(message = "Pol ne sme biti prazan.")
    private String gender;
    @NotEmpty(message = "JMBG ne sme biti prazan.")
    private String jmbg;
    @NotEmpty(message = "Adresa ne sme biti prazna.")
    private String address;
    @NotEmpty(message = "Mesto stanovanja ne sme biti prazno.")
    private String placeOfLiving;
    @NotEmpty(message = "Broj telefona ne sme biti prazan.")
    private String phone;
    @NotEmpty(message = "Email ne sme biti prazan.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,63}$", message = "Email adresa nije u ispravnom formatu.")
    private String email;
    private Title title;
    private Profession profession;
    @NotEmpty(message = "PBO odeljenje ne sme biti prazno.")
    private String departmentPbo;
    @NotEmpty(message = "Dozvole ne smeju biti prazne.")
    private List<String> permissions;
}
