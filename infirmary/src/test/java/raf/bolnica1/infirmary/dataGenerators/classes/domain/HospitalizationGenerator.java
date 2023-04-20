package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dataGenerators.primitives.util.PatientGetter;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;

@Component
@AllArgsConstructor
public class HospitalizationGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final PatientGetter patientGetter;


    private final HospitalRoomGenerator hospitalRoomGenerator;
    private final PrescriptionGenerator prescriptionGenerator;


    public static HospitalizationGenerator getInstance(){
        return new HospitalizationGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),PatientGetter.getInstance(),
                HospitalRoomGenerator.getInstance(),PrescriptionGenerator.getInstance());
    }



    public Hospitalization getHospitalization(HospitalRoom hospitalRoom, Prescription prescription){

        //if(hospitalRoom==null || prescription==null)
          //  throw new RuntimeException();

        Hospitalization ret=new Hospitalization();

       /*ret.setJmbg(randomJMBG.getFromRandom());
        ret.setSurname(randomSurnames.getFromRandom());
        ret.setName(randomNames.getFromRandom());*/
        ret.setHospitalRoom(hospitalRoom);
        ret.setPrescription(prescription);
        ret.setNote(randomString.getString(20));
        ret.setLbzDoctor(randomString.getString(10));
        ret.setLbzRegister(randomString.getString(10));
        ret.setPatientAdmission(randomTimestamp.getFromRandom());
        ret.setDischargeDateAndTime(randomTimestamp.getFromRandom());

        /// popuni ime i prezime i jmbg
        String lbp= prescription.getLbp();
        PatientDto patientDto=patientGetter.getPatientByLbp(lbp).get(0);
        ret.setJmbg(patientDto.getJmbg());
        ret.setName(patientDto.getName());
        ret.setSurname(patientDto.getSurname());

        return ret;
    }

    public Hospitalization getHospitalizationWithDBSave(HospitalizationRepository hospitalizationRepository,
                                                        HospitalRoomRepository hospitalRoomRepository,
                                                        PrescriptionRepository prescriptionRepository,
                                                        HospitalRoom hospitalRoom, Prescription prescription){

        if(hospitalRoom==null)hospitalRoom=hospitalRoomGenerator.getHospitalRoomWithDBSave(hospitalRoomRepository);
        if(prescription==null)prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);

        return hospitalizationRepository.save(getHospitalization(hospitalRoom,prescription));
    }

}
