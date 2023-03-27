package raf.bolnica1.infirmary.services;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InfirmaryService {
    private HospitalRoomRepository hospitalRoomRepository;
    private DischargeListRepository dischargeListRepository;
    private PrescriptionRepository prescriptionRepository;
    private HospitalizationRepository hospitalizationRepository;




    public InfirmaryService(HospitalRoomRepository hospitalRoomRepository,DischargeListRepository dischargeListRepository,PrescriptionRepository prescriptionRepository,HospitalizationRepository hospitalizationRepository) {
        this.hospitalRoomRepository = hospitalRoomRepository;
        this.dischargeListRepository = dischargeListRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.hospitalizationRepository = hospitalizationRepository;
    }

    public Optional<List<HospitalRoom>> findHospitalRooms(Long  idDepartment){
        //Dohvatanje svih bolnickih soba sa datim id-jem departmana
        Optional<List<HospitalRoom>> rooms;
        rooms = hospitalRoomRepository.findAllByIdDepartment(idDepartment);

        if(!rooms.isPresent()){
            return rooms;
        }
        return null;
    }

    public void createDischargeList(Long  idDepartment,String lbp,String followingDiagnosis,String anamnesis,String analysis,String courseOfDisease,String summary,String therapy){
        //Dekrementiramo kapacitet sobe
        hospitalRoomRepository.decrementCapasity(idDepartment);
        //Pronalazimo uput preko lbp-a
        Prescription prescription = prescriptionRepository.findByLbp(lbp);
        //Pronalazimo hospitalizaciju preko uputa
        Hospitalization hospitalization = hospitalizationRepository.findByPrescription(prescription);

        //Kreiramo objekat otpusne liste i setujemo podatke
        DischargeList dischargeList = new DischargeList();
        dischargeList.setAnamnesis(anamnesis);
        dischargeList.setAnalysis(analysis);
        dischargeList.setFollowingDiagnosis(followingDiagnosis);
        dischargeList.setCourseOfDisease(courseOfDisease);
        dischargeList.setSummary(summary);
        dischargeList.setTherapy(therapy);
        dischargeList.setHospitalization(hospitalization);

        //Ovo dobijas iz tokena
        //dischargeList.setLbzPrescribing();

        //Moras da posaljes upit na employees
        //dischargeList.setLbzDepartment();

        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        dischargeList.setCreation(ts);

        //Sejvujemo otpusnu listu
        dischargeListRepository.save(dischargeList);

    }

    public void pacientAdmission(Long  idDepartment,String lbp,String lbzDoctor,String referralDiagnosis,String note,Long idPrescription){
        //Inkrementiramo kapacitet sobe
        hospitalRoomRepository.incrementCapasity(idDepartment);

        //Pronalazimo sobu sa id-jem odeljenja
        Optional<HospitalRoom> room = hospitalRoomRepository.findByIdDepartment(idDepartment);

        //Pronalazimo uput sa id-jem uputa
        Optional<Prescription> prescription = prescriptionRepository.findById(idPrescription);

        //Setujemo status uputa na realizovan
        prescriptionRepository.updatePrescriptionStatus(PrescriptionStatus.REALIZOVAN,idPrescription);

        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setDischargeDateAndTime(null);
        hospitalization.setNote(note);
        hospitalization.setLbzDoctor(lbzDoctor);
        hospitalization.setPrescription(prescription.get());
        hospitalization.setHospitalRoom(room.get());

        //Ovo dobijas iz tokena
        //hospitalization.setLbzRegister();

        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        hospitalization.setPatientAdmission(ts);

        hospitalizationRepository.save(hospitalization);

    }
}

