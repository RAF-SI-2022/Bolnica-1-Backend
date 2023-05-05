package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.prescription;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevPrescriptionReceiveDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomTimestamp devRandomTimestamp;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomLBP devRandomLBP;



    public static DevPrescriptionReceiveDtoGenerator getInstance(){
        return new DevPrescriptionReceiveDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomTimestamp.getInstance(), DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomLBP.getInstance());
    }

    public PrescriptionReceiveDto getPrescriptionReceiveDto(){

        PrescriptionReceiveDto ret=new PrescriptionReceiveDto();

        ret.setType(PrescriptionType.values()[ devRandomLong.getLong(new Long(PrescriptionType.values().length) ).intValue() ]);
        ret.setDoctorLbz(devRandomString.getString(10));
        ret.setDepartmentToId(devRandomLong.getLong(10L));
        ret.setDepartmentFromId(devRandomLong.getLong(10L));
        ret.setLbp(devRandomLBP.getFromRandom());
        ret.setCreationDateTime(devRandomTimestamp.getFromRandom());
        ret.setStatus( PrescriptionStatus.values()[ devRandomLong.getLong(new Long(PrescriptionStatus.values().length)).intValue() ] );
        ret.setReferralDiagnosis(devRandomString.getString(10));
        ret.setReferralReason(devRandomString.getString(10));

        return ret;
    }


}
