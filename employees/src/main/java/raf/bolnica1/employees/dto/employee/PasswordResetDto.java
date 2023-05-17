package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetDto implements Serializable {
    @NotEmpty(message = "Stara lozinka ne sme biti prazna.")
    private String oldPassword;
    @NotEmpty(message = "Nova lozinka ne sme biti prazna.")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]{5,30}$", message = "Nova lozinka može da sadrži slova, brojeve i specijalne karaktere [_,.,-] i mora da bude dužine između 5 i 30 karaktera.")
    private String newPassword;
}
