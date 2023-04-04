package raf.bolnica1.patient.dto.prescription.lab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;

import java.sql.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionNewDto {
    private Long id;
    private String type;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private String doctorLbz;
    private String comment;
    private Date creationDate;
    private PrescriptionStatus status;
    private List<PrescriptionAnalysisDataDto> prescriptionAnalysisDataDtoList;
}