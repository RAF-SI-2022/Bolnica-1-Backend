package raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;

import java.sql.Timestamp;
import java.util.Random;

@Component
@AllArgsConstructor
public class PrescriptionReceiveDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public PrescriptionReceiveDto getPrescriptionReceiveDto(){

        PrescriptionReceiveDto ret=new PrescriptionReceiveDto();

        ret.setType(PrescriptionType.values()[ randomLong.getLong(new Long(PrescriptionType.values().length) ).intValue() ]);
        ret.setDoctorLbz(randomString.getString(10));
        ret.setDepartmentToId(randomLong.getLong(10L));
        ret.setDepartmentFromId(randomLong.getLong(10L));
        ret.setLbp(randomLBP.getFromRandom());
        ret.setCreationDateTime(randomTimestamp.getFromRandom());
        ret.setStatus( PrescriptionStatus.values()[ randomLong.getLong(new Long(PrescriptionStatus.values().length)).intValue() ] );
        ret.setReferralDiagnosis(randomString.getString(10));
        ret.setReferralReason(randomString.getString(10));

        return ret;
    }


}
