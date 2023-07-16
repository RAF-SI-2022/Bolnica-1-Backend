package raf.bolnica1.laboratory.dto.lab.prescription;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionCreateDto implements Serializable {

    private String lbp;
    private String doctorLbz;
    private Long departmentFromId;
    private Long departmentToId;
    private Date date;
    private String comment;
    private List<LabResultDto> labResultDtoList;
    private String type;
    private boolean covid = false;

}
