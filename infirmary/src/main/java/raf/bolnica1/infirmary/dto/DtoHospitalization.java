package raf.bolnica1.infirmary.dto;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Prescription;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
public class DtoHospitalization {
    private Long id;
    private String lbzDoctor;
    private Timestamp patientAdmission;
    private Long hospitalRoomId;
    private String lbzRegister;
    private Timestamp dischargeDateAndTime;
    private Long prescriptionId;
    private String note;
}
