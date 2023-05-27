package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.mapper.HospitalRoomMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;

@Component
@AllArgsConstructor
public class HospitalRoomGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;


    public static HospitalRoomGenerator getInstance(){
        return new HospitalRoomGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance());
    }

    public HospitalRoom generateHospitalRoom(){

        HospitalRoom ret=new HospitalRoom();

        ret.setCapacity(randomLong.getLong(new Long(10)).intValue()+1);
        ret.setOccupancy(0);
        ret.setRoomNumber(randomLong.getLong(new Long(100) ).intValue() );
        ret.setName(randomNames.getFromRandom());
        ret.setDescription(randomString.getString(10 ));
        ret.setIdDepartment(randomLong.getLong(new Long(4) ));

        return ret;
    }


    public HospitalRoom getHospitalRoomWithDBSave(HospitalRoomRepository hospitalRoomRepository){
        return hospitalRoomRepository.save(generateHospitalRoom());
    }


}
