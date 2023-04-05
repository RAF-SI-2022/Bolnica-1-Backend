package raf.bolnica1.infirmary.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class PatientInformationDto {
    private String lbp;
    private String patientName;
    private String patientSurname;
    private String jmbg;
    private Date dateOfBirth;
    private Long hospitalRoomId;
    private Integer roomNumber;
    private Integer roomCapacity;
    private Timestamp patientAdmissionDate;
    private String referralDiagnosis;
    private String lbzDoctor;
}
