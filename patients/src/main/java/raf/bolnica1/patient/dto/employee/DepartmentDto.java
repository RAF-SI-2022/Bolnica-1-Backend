package raf.bolnica1.patient.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto implements Serializable {
    private Long id;
    private String pbo;
    private String name;
    private String hospitalName;
}
