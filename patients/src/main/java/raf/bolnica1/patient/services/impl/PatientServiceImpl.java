package raf.bolnica1.patient.services.impl;


import org.springframework.stereotype.Service;

import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;

import raf.bolnica1.patient.domain.*;

import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.PatientService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PatientServiceImpl implements PatientService {

/**
    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private MedicalHistoryRepository medicalHistoryRepository;
    private ExaminationHistoryRepository examinationHistoryRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private SocialDataRepository socialDataRepository;
    private VaccinationDataRepository vaccinationDataRepository;
    private AllergyDataRepository allergyDataRepository;
    private OperationRepository operationRepository;


    private GeneralMedicalDataMapper generalMedicalDataMapper;
    private OperationMapper operationMapper;
    private MedicalHistoryMapper medicalHistoryMapper;
    private ExaminationHistoryMapper examinationHistoryMapper;
    private MedicalRecordMapper medicalRecordMapper;
    private PatientMapper patientMapper;


    public PatientServiceImpl(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository,
                              GeneralMedicalDataRepository generalMedicalDataRepository, SocialDataRepository socialDataRepository,
                              MedicalHistoryRepository medicalHistoryRepository, ExaminationHistoryRepository examinationHistoryRepository,
                              VaccinationDataRepository vaccinationDataRepository,AllergyDataRepository allergyDataRepository,
                              GeneralMedicalDataMapper generalMedicalDataMapper, OperationRepository operationRepository,
                              OperationMapper operationMapper, MedicalHistoryMapper medicalHistoryMapper,
                              ExaminationHistoryMapper examinationHistoryMapper,MedicalRecordMapper medicalRecordMapper,
                              PatientMapper patientMapper
    ) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.generalMedicalDataRepository = generalMedicalDataRepository;
        this.socialDataRepository = socialDataRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.examinationHistoryRepository = examinationHistoryRepository;
        this.vaccinationDataRepository=vaccinationDataRepository;
        this.allergyDataRepository=allergyDataRepository;
        this.generalMedicalDataMapper=generalMedicalDataMapper;
        this.operationRepository=operationRepository;
        this.operationMapper=operationMapper;
        this.medicalHistoryMapper=medicalHistoryMapper;
        this.examinationHistoryMapper=examinationHistoryMapper;
        this.medicalRecordMapper=medicalRecordMapper;
        this.patientMapper=patientMapper;
    }




    ///TODO: pretraga medicalHystory nije dobra, DiagnosisCode_Id(id polje generisano u bazi) nije isto sto i mkb10 vrv
    //Dobijanje istorije bolesti pacijenta
    public List<PatientDtoDesease> hisotryOfDeseasePatient(String  lbp, Long mkb10){
        //Dohvatanje konkretnog pacijenta preko lbp-a
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException());

        //Dohvatanje kartona konkretnog pacijenta
        MedicalRecord medical =  medicalRecordRepository.findByLbp(patient).orElseThrow(() -> new RuntimeException());

        //Dohvatanje bolesti preko karotna i preko mkb10 (dijagnoza)
        List<MedicalHistory> history = medicalHistoryRepository.findByMedicalRecord_IdAndDiagnosisCode_Id(medical.get().getId(), Long.valueOf(mkb10));

        //Provera da li postoji bolest ako postoji onda mapiramo na dto koji vracamo na front, ako ne postoji onda vracamo null
        if(history.isPresent()){
            return PatientDeseaseMapper.allToDto(history.get());
        }

        return null;
    }



    //Svi izvestaji
    //Dohvatanje izvestaja pregleda preko lbp-a pacijenta i preko konkretnog datuma
    public List<PatientDtoReport>  findReportPatientByCurrDate(String lbp, Date currDate){
        //Dohvatanje konkretnog pacijenta preko lbp-a
        Optional<Patient> patient;
        patient = patientRepository.findByLbp(lbp);

        //Dohvatanje kartona tog pacijenta
        Optional<MedicalRecord> medical;
        medical =  medicalRecordRepository.findByLbp(patient.get().getLbp());

        //Dohvatanje izvestaja pregleda preko kartona konkretnog pacijenta i preko konkretnog datuma
        Optional<List<ExaminationHistory>> examination;
        examination = examinationHistoryRepository.findByMedicalRecord_IdAndExamDateEquals(medical.get().getId(),currDate);

        //Provera da li postoje izvestaji pregleda, ako postoje mapiramo na dto koji vracamo na front ako ne postoji onda vracamo null
        if(examination.isPresent()){
            return PatientReportMapper.allToDto(examination.get());
        }


        return null;
    }


    //Dohvatanje izvestaja pregleda preko lbp-a pacijenta i preko raspona datuma od-do
    public List<PatientDtoReport> findReportPatientByFromAndToDate(String lbp,Date fromDate,Date toDate){
        //Dohvatanje konkretnog pacijenta preko lbp-a
        Optional<Patient> patient;
        patient = patientRepository.findByLbp(lbp);

        //Dohvatanje kartona tog pacijenta
        Optional<MedicalRecord> medical;
        medical =  medicalRecordRepository.findByLbp(patient.get().getLbp());

        //Dohvatanje izvestaja pregleda preko kartona konkretnog pacijenta i preko raspona datuma od-do
        Optional<List<ExaminationHistory>> examination;
        examination = examinationHistoryRepository.findByMedicalRecord_IdAndExamDateGreaterThanAndExamDateLessThan(medical.get().getId(),fromDate,toDate);

        //Provera da li postoje izvestaji pregleda, ako postoje mapiramo na dto koji vracamo na front ako ne postoji onda vracamo null
        if(examination.isPresent()){
            return PatientReportMapper.allToDto(examination.get());
        }

        return null;
    }


 */

}
