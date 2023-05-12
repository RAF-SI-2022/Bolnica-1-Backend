package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;

import java.sql.Timestamp;

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


    public static HospitalizationCreateDtoGenerator getInstance(){
        return new HospitalizationCreateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),RandomTime.getInstance(),
                RandomTimestamp.getInstance());
    }

    public HospitalizationCreateDto getHospitalizationCreateDto(Long hospitalRoomId,Long prescriptionId){

        HospitalizationCreateDto ret=new HospitalizationCreateDto();

        ret.setNote(randomString.getString(10));
        ret.setHospitalRoomId(hospitalRoomId);
        ret.setLbzDoctor(randomString.getString(10));
        ret.setPrescriptionId(prescriptionId);
        ret.setPatientAdmission(new Timestamp(1000*(randomTimestamp.getFromRandom().getTime()/1000)));
        ret.setDischargeDateAndTime(new Timestamp(1000*(randomTimestamp.getFromRandom().getTime()/1000)));

        return ret;
    }

}
