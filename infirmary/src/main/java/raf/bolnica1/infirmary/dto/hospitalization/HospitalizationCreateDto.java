package raf.bolnica1.infirmary.dto.hospitalization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Prescription;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class HospitalizationCreateDto implements Serializable {

    private String lbzDoctor;
    private Timestamp patientAdmission;
    private Long hospitalRoomId;
    private Long prescriptionId;
    private String note;

}
