package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class OperationDto implements Serializable {

    private Long id;
    private Date operationDate;
    private Long hospitalId;
    private Long departmentId;
    private String description;

}
