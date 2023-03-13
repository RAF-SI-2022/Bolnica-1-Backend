package raf.bolnica1.patient.services;

import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.dto.*;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import raf.bolnica1.patient.domain.*;

import raf.bolnica1.patient.mapper.ExaminationHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalRecordMapper;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.*;


import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.List;


public interface PatientService {


    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private MedicalHistoryRepository medicalHistoryRepository;
    private ExaminationHistoryRepository examinationHistoryRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private SocialDataRepository socialDataRepository;
    private AllergyDataRepository allergyDataRepository;
    private VaccinationDataRepository vaccinationDataRepository;

    public PatientService(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository, GeneralMedicalDataRepository generalMedicalDataRepository, SocialDataRepository socialDataRepository,MedicalHistoryRepository medicalHistoryRepository,ExaminationHistoryRepository examinationHistoryRepository, AllergyDataRepository allergyDataRepository, VaccinationDataRepository vaccinationDataRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.generalMedicalDataRepository = generalMedicalDataRepository;
        this.socialDataRepository = socialDataRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.examinationHistoryRepository = examinationHistoryRepository;
        this.allergyDataRepository = allergyDataRepository;
        this.vaccinationDataRepository = vaccinationDataRepository;
    }


    //Registracija pacijenta
    public PatientDto registerPatient(PatientDto dto);

    //Azuriranje podataka pacijenta
    public PatientDto updatePatient(PatientDto dto);

    //Brisanje pacijenta
    public boolean deletePatient(String lbp);

    public List<PatientDto> filterPatients(String lbp, String jmbg, String name, String surname);

    //Pretraga pacijenta
    public Object findPatient(Object object);

    //Pretraga pacijenta preko LBP-a
    public Patient findPatientLBP(String lbp);

    //Dobijanje istorije bolesti pacijenta
    public List<PatientDtoDesease> hisotryOfDeseasePatient(String  lbp, Long mkb10);

    //Svi izvestaji
    //Dohvatanje izvestaja pregleda preko lbp-a pacijenta i preko konkretnog datuma
    public List<PatientDtoReport>  findReportPatientByCurrDate(String lbp, Date currDate);

    //Dohvatanje izvestaja pregleda preko lbp-a pacijenta i preko raspona datuma od-do
    public List<PatientDtoReport> findReportPatientByFromAndToDate(String lbp,Date fromDate,Date toDate);

    //Svi kartoni

    //m22
    public List<MedicalRecordDto> findMedicalRecordByLbp(String lbp);

    //Krvne grupe
    public Object findDetailsPatient(Object object);

    //Dohvatanje GeneralMedicalData po LBP(GMD,vaccines,allergies)
    public GeneralMedicalDataDto findGeneralMedicalDataByLbp(String lbp);

    public List<MedicalRecordDto> findMedicalRecordByLbp(String lbp) {

        Optional<List<MedicalRecord>> list = medicalRecordRepository.findByPatientLbp(lbp);

        if(list.isPresent()){
            return MedicalRecordMapper.allToDto(list.get());
        } else {
            return null;
        }
    }


    //Krvne grupe
    public PatientDetailsDto findPatientDetails(String lbp){

        Optional<Patient> patient = patientRepository.findByLbp(lbp);
        Optional<List<MedicalRecord>> medicalRecordList = medicalRecordRepository.findByPatientLbp(patient.get().getLbp());

        List<GeneralMedicalData> generalMedicalDataList = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordList.get()){
            generalMedicalDataList.add(medicalRecord.getGeneralMedicalData());
        }


        List<AllergyData> allergyDataList = new ArrayList<>();
        for (GeneralMedicalData generalMedicalData : generalMedicalDataList) {
            allergyDataList.addAll(allergyDataRepository.findAllByGeneralMedicalDataId(generalMedicalData.getId()));
        }

        List<Allergy> allergyList = new ArrayList<>();
        for(AllergyData allergyData : allergyDataList){
            allergyList.add(allergyData.getAllergy());
        }


        List<VaccinationData> vaccinationDataList = new ArrayList<>();
        for (GeneralMedicalData generalMedicalData : generalMedicalDataList) {
            vaccinationDataList.addAll( vaccinationDataRepository.findAllByGeneralMedicalDataId(generalMedicalData.getId()));
        }

        List<Vaccination> vaccinationList = new ArrayList<>();
        for(VaccinationData vaccinationData : vaccinationDataList){
            vaccinationList.add(vaccinationData.getVaccination());
        }

        PatientDetailsDto patientDetailsDto = new PatientDetailsDto();
        patientDetailsDto.setAllergies(allergyList);
        patientDetailsDto.setVaccinations(vaccinationList);
        patientDetailsDto.setRH(generalMedicalDataList.get(0).getRH());
        patientDetailsDto.setBloodType(generalMedicalDataList.get(0).getBloodType());

        return patientDetailsDto;
    }

}
