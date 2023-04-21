package raf.bolnica1.employees.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospitalDepartmentDto {
    private Long hopsitalId;
    private String hospitalName;
    private Long departmentId;
}
