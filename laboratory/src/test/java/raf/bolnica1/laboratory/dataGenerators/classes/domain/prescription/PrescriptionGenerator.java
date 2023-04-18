package raf.bolnica1.laboratory.dataGenerators.classes.domain.prescription;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.dataGenerators.primitives.*;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;

@Component
@AllArgsConstructor
public class PrescriptionGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public static PrescriptionGenerator getInstance(){
        return new PrescriptionGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance());
    }

    public Prescription getPrescription(){

        Prescription ret=new Prescription();

        ret.setStatus(PrescriptionStatus.values()[randomLong.getLong(new Long(PrescriptionStatus.values().length) ).intValue()]);
        ret.setType(PrescriptionType.values()[randomLong.getLong(new Long(PrescriptionType.values().length) ).intValue()]);
        ret.setLbp(randomLBP.getFromRandom());
        ret.setCreationDateTime(randomTimestamp.getFromRandom());
        ret.setDepartmentToId(randomLong.getLong(4L));
        ret.setDoctorLbz(randomString.getString(10));
        ret.setComment(randomString.getString(10));
        ret.setDepartmentFromId(randomLong.getLong(4L));

        return ret;
    }


    public Prescription getPrescriptionWithDBSave(PrescriptionRepository prescriptionRepository){
        return prescriptionRepository.save(getPrescription());
    }


}
