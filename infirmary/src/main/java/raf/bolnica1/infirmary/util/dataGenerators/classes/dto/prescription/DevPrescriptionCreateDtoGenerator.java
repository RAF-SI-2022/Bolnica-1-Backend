package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.prescription;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;
import raf.bolnica1.infirmary.dto.externalLabService.PrescriptionCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DevPrescriptionCreateDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomTimestamp devRandomTimestamp;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomLBP devRandomLBP;


    public static DevPrescriptionCreateDtoGenerator getInstance(){
        return new DevPrescriptionCreateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomTimestamp.getInstance(), DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomLBP.getInstance());
    }

    public PrescriptionCreateDto getPrescriptionCreateDto(){

        PrescriptionCreateDto ret=new PrescriptionCreateDto();

        ret.setType(PrescriptionType.values()[ devRandomLong.getLong(new Long(PrescriptionType.values().length) ).intValue() ]);
        ret.setDoctorLbz(devRandomString.getString(10));
        ret.setDepartmentToId(devRandomLong.getLong(10L));
        ret.setDepartmentFromId(devRandomLong.getLong(10L));
        ret.setLbp(devRandomLBP.getFromRandom());
        ret.setCreationDateTime(devRandomTimestamp.getFromRandom());
        ///ret.setStatus( PrescriptionStatus.values()[ randomLong.getLong(new Long(PrescriptionStatus.values().length)).intValue() ] );
        ret.setStatus(PrescriptionStatus.NEREALIZOVAN);
        ret.setComment(devRandomString.getString(10));
        ret.setPrescriptionAnalysisDtos(new ArrayList<>());

        return ret;
    }


}
