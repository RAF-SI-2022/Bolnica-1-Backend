package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.hospitalRoom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevHospitalRoomCreateDtoGenerator {
    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;


    public static DevHospitalRoomCreateDtoGenerator getInstance(){
        return new DevHospitalRoomCreateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(), DevRandomTime.getInstance());
    }

    public HospitalRoomCreateDto getHospitalRoomCreateDto(){

        HospitalRoomCreateDto ret=new HospitalRoomCreateDto();

        ret.setIdDepartment(devRandomLong.getLong(20L));
        ret.setRoomNumber(devRandomLong.getLong(10L).intValue());
        ret.setName(devRandomNames.getFromRandom());
        ret.setCapacity(devRandomLong.getLong(10L).intValue());
        ret.setDescription(devRandomString.getString(10));

        return ret;
    }
}
