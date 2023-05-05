package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.hospitalization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevHospitalizationCreateDtoGenerator {


    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;
    private final DevRandomTimestamp devRandomTimestamp;


    public static DevHospitalizationCreateDtoGenerator getInstance(){
        return new DevHospitalizationCreateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(), DevRandomTime.getInstance(),
                DevRandomTimestamp.getInstance());
    }

    public HospitalizationCreateDto getHospitalizationCreateDto(Long hospitalRoomId,Long prescriptionId){

        HospitalizationCreateDto ret=new HospitalizationCreateDto();

        ret.setNote(devRandomString.getString(10));
        ret.setHospitalRoomId(hospitalRoomId);
        ret.setLbzDoctor(devRandomString.getString(10));
        ret.setPrescriptionId(prescriptionId);
        ret.setPatientAdmission(devRandomTimestamp.getFromRandom());
        ret.setDischargeDateAndTime(devRandomTimestamp.getFromRandom());

        return ret;
    }

}
