package raf.bolnica1.infirmary.dto.hospitalization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class HospitalizationDto {

    private Long id;
    private String lbzDoctor;
    private Timestamp patientAdmission;
    private Long hospitalRoomId;
    private String lbzRegister;
    private Timestamp dischargeDateAndTime;
    private Long prescriptionId;
    private String name;
    private String surname;
    private String jmbg;
    private String note;
    private String lbp;

}
