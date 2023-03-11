package raf.bolnica1.patient.dto;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
@Getter
@Setter
public class PatientDtoReport {
        private String lbp;
        private Date currDate;
        private Date fromDate;
        private Date toDate;
    }

