package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import raf.bolnica1.patient.domain.MedicalRecord;

import javax.persistence.ManyToOne;
import java.sql.Date;

@Getter
@Setter
public class OperationDto {

    private Date operationDate;
    private Long hospitalId;
    private Long departmentId;
    private String description;

}
