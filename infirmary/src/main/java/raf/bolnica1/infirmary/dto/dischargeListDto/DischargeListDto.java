package raf.bolnica1.infirmary.dto.dischargeListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.DischargeList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DischargeListDto {

    private HospitalizationDto hospitalizationDto;
    private PrescriptionDto prescriptionDto;
    private DischargeList dischargeList;
    private DoctorDto doctorDto;
    private ChiefDto chiefDto;
}
