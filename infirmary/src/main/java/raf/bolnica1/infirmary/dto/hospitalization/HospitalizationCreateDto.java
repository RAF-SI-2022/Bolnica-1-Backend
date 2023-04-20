package raf.bolnica1.infirmary.dto.hospitalization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Prescription;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class HospitalizationCreateDto {

    private String lbzDoctor;
    private Timestamp patientAdmission;
    private Long hospitalRoomId;
    private Timestamp dischargeDateAndTime;
    private Long prescriptionId;
    private String note;

}
