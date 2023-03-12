package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetDto {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_.-]{5,30}$")
    /// Password can have letters, numbers and special characters like _ . - length should be between 5 and 30
    private String newPassword;
}
