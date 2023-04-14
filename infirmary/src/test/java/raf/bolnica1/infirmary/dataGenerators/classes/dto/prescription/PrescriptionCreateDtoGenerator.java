package raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;
import raf.bolnica1.infirmary.dto.externalLabService.PrescriptionCreateDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class PrescriptionCreateDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public PrescriptionCreateDto getPrescriptionCreateDto(){

        PrescriptionCreateDto ret=new PrescriptionCreateDto();

        ret.setType(PrescriptionType.values()[ randomLong.getLong(new Long(PrescriptionType.values().length) ).intValue() ]);
        ret.setDoctorLbz(randomString.getString(10));
        ret.setDepartmentToId(randomLong.getLong(10L));
        ret.setDepartmentFromId(randomLong.getLong(10L));
        ret.setLbp(randomLBP.getFromRandom());
        ret.setCreationDateTime(randomTimestamp.getFromRandom());
        ///ret.setStatus( PrescriptionStatus.values()[ randomLong.getLong(new Long(PrescriptionStatus.values().length)).intValue() ] );
        ret.setStatus(PrescriptionStatus.NEREALIZOVAN);
        ret.setComment(randomString.getString(10));
        ret.setPrescriptionAnalysisDtos(new ArrayList<>());

        return ret;
    }


}
