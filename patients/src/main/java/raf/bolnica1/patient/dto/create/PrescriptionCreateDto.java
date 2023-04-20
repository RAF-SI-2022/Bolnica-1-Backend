package raf.bolnica1.patient.dto.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.patient.domain.prescription.LabResults;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionCreateDto {

    private String lbp;
    private String doctorLbz;
    private Long departmentFromId;
    private Long departmentToId;
    private Date date;
    private String comment;
    private List<LabResultDto> labResultDtoList;
    private String type;

}
