package raf.bolnica1.patient.dto.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamForPatientDto {

    private String lbp;
    private Timestamp examDate;
    private String doctorLbz;
    private String note;

}
