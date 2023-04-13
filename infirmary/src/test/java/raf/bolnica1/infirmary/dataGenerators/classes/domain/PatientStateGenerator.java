package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PatientStateRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;


@Component
@AllArgsConstructor
public class PatientStateGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;


    private final HospitalizationGenerator hospitalizationGenerator;


    public PatientState getPatientState(Hospitalization hospitalization){

        if(hospitalization==null)
            throw new RuntimeException();

        PatientState ret=new PatientState();

        ret.setDescription(randomString.getString(20));
        ret.setLbz(randomString.getString(10));
        ret.setDateExamState(randomDate.getFromRandom());
        ret.setPulse(randomLong.getLong(100L).intValue());
        ret.setHospitalization(hospitalization);
        ret.setTemperature(randomDouble.getDouble(50.0).floatValue());
        ret.setTherapy(randomString.getString(20));
        ret.setTimeExamState(randomTime.getTimeFromRandom());
        ret.setSystolicPressure(randomLong.getLong(200L).intValue());
        ret.setDiastolicPressure(randomLong.getLong(200L).intValue());

        return ret;
    }

    public PatientState getPatientStateWithDBSave(PatientStateRepository patientStateRepository,
                                                  HospitalizationRepository hospitalizationRepository,
                                                  PrescriptionRepository prescriptionRepository,
                                                  HospitalRoomRepository hospitalRoomRepository,
                                                  Hospitalization hospitalization){

        if(hospitalization==null)hospitalization=hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                hospitalRoomRepository,prescriptionRepository,null,null);

        return patientStateRepository.save(getPatientState(hospitalization));
    }

}
