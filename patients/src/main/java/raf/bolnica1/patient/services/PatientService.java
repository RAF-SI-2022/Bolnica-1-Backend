package raf.bolnica1.patient.services;


import org.springframework.stereotype.Service;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.dto.MedicalRecordDto;
import raf.bolnica1.patient.dto.PatientDto;
import raf.bolnica1.patient.mapper.MedicalRecordMapper;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.GeneralMedicalDataRepository;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.SocialDataRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PatientService {


    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private SocialDataRepository socialDataRepository;

    public PatientService(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository, GeneralMedicalDataRepository generalMedicalDataRepository, SocialDataRepository socialDataRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.generalMedicalDataRepository = generalMedicalDataRepository;
        this.socialDataRepository = socialDataRepository;
    }

    //Registracija pacijenta
    public PatientDto registerPatient(PatientDto dto){
        Patient patient = PatientMapper.patientDtoToPatient(dto);
        patient.setLbp(UUID.randomUUID().toString());

        patient.setSocialData(socialDataRepository.save(patient.getSocialData()));

        patient = patientRepository.save(patient);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(patient);
        medicalRecord.setRegistrationDate(Date.valueOf(LocalDate.now()));

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        generalMedicalData = generalMedicalDataRepository.save(generalMedicalData);
        //nzm sta da dodajem kod generalMedicalData, pa sam ostavio sve prazno

        medicalRecord.setGeneralMedicalData(generalMedicalData);
        medicalRecordRepository.save(medicalRecord);

        return dto;
    }

    //Azuriranje podataka pacijenta
    public PatientDto updatePatient(PatientDto dto){
        Optional<Patient> patient = patientRepository.findById(dto.getId());
        if(patient.isPresent()){
            PatientMapper.compareAndSet(dto, patient.get());
            socialDataRepository.save(patient.get().getSocialData());
            patientRepository.save(patient.get());
            return dto;
        }
        return null;
    }

    //Brisanje pacijenta
    public boolean deletePatient(String lbp){
        Optional<Patient> patient = patientRepository.findByLbp(lbp);
        if(patient.isPresent()){
            patient.get().setDeleted(true);
            patientRepository.save(patient.get());
        }
        else
             return false;
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        if(!medicalRecords.isEmpty()){
            for(MedicalRecord mr: medicalRecords){
                if(mr.getPatient().getLbp().equals(lbp)){
                    mr.setDeleted(true);
                    medicalRecordRepository.save(mr);
                    return true;
                }
            }
        }
        return false;
    }


    public List<PatientDto> filterPatients(String lbp, String jmbg, String name, String surname){
        List<Patient> patients = new ArrayList<>();
        Optional<List<Patient>> tempList;
        Optional<Patient> tempPatientLbp;
        Optional<Patient> tempPatientJmbg;

        if(!name.equals("")){
            tempList = patientRepository.findByName(name);
            if(tempList.isPresent())
                patients = tempList.get();
        }
        if(!surname.equals("")){
            if(patients.isEmpty()) {
                tempList = patientRepository.findBySurname(surname);
                if(tempList.isPresent())
                    patients = tempList.get();
            }
            else
                patients.removeIf(p -> !p.getSurname().equals(surname));
        }
        if(!lbp.equals("")){
            tempPatientLbp = patientRepository.findByLbp(lbp);
            if(tempPatientLbp.isPresent()) {
                if (patients.isEmpty())
                    patients.add(tempPatientLbp.get());
                else
                    patients.removeIf(p -> !p.getLbp().equals(tempPatientLbp.get().getLbp()));
            }
        }
        if(!jmbg.equals("")){
            tempPatientJmbg = patientRepository.findByJmbg(jmbg);
            if(tempPatientJmbg.isPresent()){
                if(patients.isEmpty())
                    patients.add(tempPatientJmbg.get());
                else
                    patients.removeIf(p -> !p.getLbp().equals(tempPatientJmbg.get().getLbp()));
            }
        }
        //izbacujemo sve pacijente koji su "obrisani"
        patients.removeIf(p -> p.isDeleted());

        return PatientMapper.allToDto(patients);
    }

    //Pretraga pacijenta
    public Object findPatient(Object object){
        return null;
    }


    //Pretraga pacijenta preko LBP-a
    public Object findPatientLBP(Object object){
        return null;
    }


    //Dobijanje istorije bolesti pacijenta
    public Object hisotryOfDeseasePatient(Object object){
        return null;
    }


    //Svi izvestaji
    public Object findReportPatient(Object object){
        return null;
    }


    //Svi kartoni
    //m22
    public List<MedicalRecordDto> findMedicalRecordByLbp(String lbp) {

        Optional<List<MedicalRecord>> list = medicalRecordRepository.findByPatientLbp(lbp);

        if(list.isPresent()){
            return MedicalRecordMapper.allToDto(list.get());
        } else {
            return null;
        }
    }


    //Krvne grupe
    public Object findDetailsPatient(Object object){
        return null;
    }

}
