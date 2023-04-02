package raf.bolnica1.infirmary.dto.dischargeListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HospitalizationDto {

    private Timestamp patientAdmission;
    private Timestamp dischargeDateAndTime;

}
