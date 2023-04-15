package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.AnamnesisDto;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;

@Component
@AllArgsConstructor
public class HospitalRoomCreateDtoGenerator {
    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;


    public static HospitalRoomCreateDtoGenerator getInstance(){
        return new HospitalRoomCreateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),RandomTime.getInstance());
    }

    public HospitalRoomCreateDto getHospitalRoomCreateDto(){

        HospitalRoomCreateDto ret=new HospitalRoomCreateDto();

        ret.setIdDepartment(randomLong.getLong(20L));
        ret.setRoomNumber(randomLong.getLong(10L).intValue());
        ret.setName(randomNames.getFromRandom());
        ret.setCapacity(randomLong.getLong(10L).intValue());
        ret.setDescription(randomString.getString(10));

        return ret;
    }
}
