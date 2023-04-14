package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;

@Component
@AllArgsConstructor
public class HospitalizationCreateDtoGenerator {


    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;
    private final RandomTimestamp randomTimestamp;


    public HospitalizationCreateDto getHospitalizationCreateDto(Long hospitalRoomId,Long prescriptionId){

        HospitalizationCreateDto ret=new HospitalizationCreateDto();

        ret.setNote(randomString.getString(10));
        ret.setHospitalRoomId(hospitalRoomId);
        ret.setLbzDoctor(randomString.getString(10));
        ret.setPrescriptionId(prescriptionId);
        ret.setPatientAdmission(randomTimestamp.getFromRandom());
        ret.setDischargeDateAndTime(randomTimestamp.getFromRandom());

        return ret;
    }

}
