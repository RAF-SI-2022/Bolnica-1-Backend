package raf.bolnica1.employees.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeUpdateDto {
    private String oldPassword;
    private String newPassword;
    private String phone;
}
