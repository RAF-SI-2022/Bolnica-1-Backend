package raf.bolnica1.employees.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {
    private Long id;
    private String pbo;
    private String name;
    private String hospitalName;
}
