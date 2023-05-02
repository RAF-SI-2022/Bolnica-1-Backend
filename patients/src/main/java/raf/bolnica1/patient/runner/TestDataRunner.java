package raf.bolnica1.patient.runner;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.domain.prescription.LabPrescription;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

//@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private PatientMapper patientMapper;

    private PatientRepository patientRepository;
    private SocialDataRepository socialDataRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private AllergyRepository allergyRepository;
    private VaccinationRepository vaccinationRepository;
    private DiagnosisCodeRepository diagnosisCodeRepository;
    private PrescriptionRepository prescriptionRepository;
    private LabResultsRepository labResultsRepository;
    private AllergyDataRepository allergyDataRepository;
    private final VaccinationDataRepository vaccinationDataRepository;
    private final AnamnesisRepository anamnesisRepository;
    private final OperationRepository operationRepository;
    private final ScheduleExamRepository scheduleExamRepository;
    private final ExaminationHistoryRepository examinationHistoryRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    private void clearAllRepositories(){
        patientRepository.deleteAll();
        socialDataRepository.deleteAll();
        medicalRecordRepository.deleteAll();
        generalMedicalDataRepository.deleteAll();
        allergyRepository.deleteAll();
        vaccinationRepository.deleteAll();
        diagnosisCodeRepository.deleteAll();
        prescriptionRepository.deleteAll();
        labResultsRepository.deleteAll();
        allergyDataRepository.deleteAll();
        vaccinationDataRepository.deleteAll();
        anamnesisRepository.deleteAll();
        operationRepository.deleteAll();
        scheduleExamRepository.deleteAll();
        examinationHistoryRepository.deleteAll();
        medicalHistoryRepository.deleteAll();
    }


    @Override
    public void run(String... args) throws Exception {

        ///clearAllRepositories();

        System.out.println("USAO U RUNNER");

        Allergy a1 = new Allergy();
        a1.setName("Mleko");
        allergyRepository.save(a1);

        Allergy a2 = new Allergy();
        a2.setName("Jaja");
        allergyRepository.save(a2);

        Allergy a3 = new Allergy();
        a3.setName("Orašasti plodovi");
        allergyRepository.save(a3);

        Allergy a4 = new Allergy();
        a4.setName("Plodovi mora");
        allergyRepository.save(a4);

        Allergy a5 = new Allergy();
        a5.setName("Pšenica");
        allergyRepository.save(a5);

        Allergy a6 = new Allergy();
        a6.setName("Soja");
        allergyRepository.save(a6);

        Allergy a7 = new Allergy();
        a7.setName("Riba");
        allergyRepository.save(a7);

        Allergy a8 = new Allergy();
        a8.setName("Penicilin");
        allergyRepository.save(a8);

        Allergy a9 = new Allergy();
        a9.setName("Cefalosporin");
        allergyRepository.save(a9);

        Allergy a10 = new Allergy();
        a10.setName("Tetraciklin");
        allergyRepository.save(a10);

        Allergy a11 = new Allergy();
        a11.setName("Karbamazepin");
        allergyRepository.save(a11);

        Allergy a12 = new Allergy();
        a12.setName("Ibuprofen");
        allergyRepository.save(a12);

        Vaccination v1 = new Vaccination();
        v1.setName("PRIORIX");
        v1.setType(VaccinationType.VIRUS);
        v1.setDescription("Vakcina protiv morbila (malih boginja)");
        v1.setManufacturer("GlaxoSmithKline Biologicals S.A.,Belgija");
        vaccinationRepository.save(v1);

        Vaccination v2 = new Vaccination();
        v2.setName("HIBERIX");
        v2.setType(VaccinationType.BACTERIA);
        v2.setDescription("Kapsulirani antigen hemofilus influence tip B");
        v2.setManufacturer("GlaxoSmithKline Biologicals S.A.,Belgija");
        vaccinationRepository.save(v2);

        Vaccination v3 = new Vaccination();
        v3.setName("INFLUVAC");
        v3.setType(VaccinationType.VIRUS);
        v3.setDescription("Virusne vakcine protiv influence (grip)");
        v3.setManufacturer("Abbott Biologicals B.V., Holandija");
        vaccinationRepository.save(v3);

        Vaccination v4 = new Vaccination();
        v4.setName("SYNFLORIX");
        v4.setType(VaccinationType.BACTERIA);
        v4.setDescription("Vakcine protiv pneumokoka");
        v4.setManufacturer("GlaxoSmithKline Biologicals S.A.,Belgija");
        vaccinationRepository.save(v4);

        Vaccination v5 = new Vaccination();
        v5.setName("BCG VAKCINA");
        v5.setType(VaccinationType.BACTERIA);
        v5.setDescription("Vakcine protiv tuberkuloze");
        v5.setManufacturer("Institut za virusologiju, vakcine i serume \"Torlak\", Republika Srbija");
        vaccinationRepository.save(v5);

        DiagnosisCode d1 = new DiagnosisCode();
        d1.setCode("A15.3");
        d1.setDescription("Tuberkuloza pluća, potvrđena neoznačenim metodama");
        d1.setLatinDescription("Tuberculosis pulmonum, methodis non specificatis confirmata");
        diagnosisCodeRepository.save(d1);

        DiagnosisCode d2 = new DiagnosisCode();
        d2.setCode("D50");
        d2.setDescription("Anemija uzrokovana nedostatkom gvožđa");
        d2.setLatinDescription("Anaemia sideropenica");
        diagnosisCodeRepository.save(d2);

        DiagnosisCode d3 = new DiagnosisCode();
        d3.setCode("I10");
        d3.setDescription("Povišen krvni pritisak, nepoznatog porekla");
        d3.setLatinDescription("Hypertensio arterialis essentialis (primaria)");
        diagnosisCodeRepository.save(d3);

        DiagnosisCode d4 = new DiagnosisCode();
        d4.setCode("I35.0");
        d4.setDescription("Suženje aortnog zaliska");
        d4.setLatinDescription("Stenosis valvulae aortae non rheumatica");
        diagnosisCodeRepository.save(d4);

        DiagnosisCode d5 = new DiagnosisCode();
        d5.setCode("J11");
        d5.setDescription("Grip, virus nedokazan");
        d5.setLatinDescription("Influenza, virus non identificatum");
        diagnosisCodeRepository.save(d5);

        DiagnosisCode d6 = new DiagnosisCode();
        d6.setCode("J12.9");
        d6.setDescription("Zapaljenje pluća uzrokovano virusom, neoznačeno");
        d6.setLatinDescription("Pneumonia viralis, non specificata");
        diagnosisCodeRepository.save(d6);

        DiagnosisCode d7 = new DiagnosisCode();
        d7.setCode("K35");
        d7.setDescription("Akutno zapaljenje slepog creva");
        d7.setLatinDescription("Appendicitis acuta");
        diagnosisCodeRepository.save(d7);

        DiagnosisCode d8 = new DiagnosisCode();
        d8.setCode("K70.3");
        d8.setDescription("Ciroza jetre uzrokovana alkoholom");
        d8.setLatinDescription("Cirrhosis hepatis alcoholica");
        diagnosisCodeRepository.save(d8);

        DiagnosisCode d9 = new DiagnosisCode();
        d9.setCode("K71.0");
        d9.setDescription("Toksička bolest jetre zbog zastoja žuči");
        d9.setLatinDescription("Morbus hepatis toxicus cholestaticus");
        diagnosisCodeRepository.save(d9);

        DiagnosisCode d10 = new DiagnosisCode();
        d9.setCode("N20.0");
        d9.setDescription("Kamen u bubregu");
        d9.setLatinDescription("Calculus renis");
        diagnosisCodeRepository.save(d10);

        // Patients
        Patient patient1 = new Patient();
        patient1.setJmbg("0101998100001");
        patient1.setLbp("P0002");
        patient1.setName("John");
        patient1.setParentName("Doe");
        patient1.setSurname("Smith");
        patient1.setGender(Gender.MUSKO);
        patient1.setDateOfBirth(Date.valueOf("1998-01-02"));
        patient1.setBirthPlace("Belgrade");
        patient1.setPlaceOfLiving("Novi Sad");
        patient1.setResidenceCountry(CountryCode.SRB);
        patient1.setCitizenship(CountryCode.SRB);
        patient1.setPhone("+38111111111");
        patient1.setEmail("john.smith@example.com");
        patient1.setGuardianJmbg("0101970100001");
        patient1.setGuardianNameAndSurname("Jane Doe");
        SocialData socialData1 = new SocialData();
        socialData1.setMaritalStatus(MaritalStatus.U_BRAKU);
        socialData1.setNumOfChildren(0);
        socialData1.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData1.setProfession("Software engineer");
        socialData1.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        patient1.setSocialData(socialData1);

        Patient patient2 = new Patient();
        patient2.setJmbg("0202999000001");
        patient2.setLbp("P0003");
        patient2.setName("Jane");
        patient2.setParentName("Smith");
        patient2.setSurname("Doe");
        patient2.setGender(Gender.ZENSKO);
        patient2.setDateOfBirth(Date.valueOf("1998-02-03"));
        patient2.setBirthPlace("Novi Sad");
        patient2.setPlaceOfLiving("Belgrade");
        patient2.setResidenceCountry(CountryCode.SRB);
        patient2.setCitizenship(CountryCode.SRB);
        patient2.setPhone("+38122222222");
        patient2.setEmail("jane.doe@example.com");
        patient2.setGuardianJmbg("0101970100001");
        patient2.setGuardianNameAndSurname("Jane Doe");
        SocialData socialData2 = new SocialData();
        socialData2.setMaritalStatus(MaritalStatus.RAZVEDENI);
        socialData2.setNumOfChildren(1);
        socialData2.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData2.setProfession("Lawyer");
        socialData2.setFamilyStatus(FamilyStatus.RAZVEDENI);
        patient2.setSocialData(socialData2);

        Patient patient3 = new Patient();
        patient3.setJmbg("0303998000001");
        patient3.setLbp("P0004");
        patient3.setName("Adam");
        patient3.setParentName("Johnson");
        patient3.setSurname("Lee");
        patient3.setGender(Gender.MUSKO);
        patient3.setDateOfBirth(Date.valueOf("1998-03-04"));
        patient3.setBirthPlace("New York");
        patient3.setPlaceOfLiving("Los Angeles");
        patient3.setResidenceCountry(CountryCode.USA);
        patient3.setCitizenship(CountryCode.USA);
        patient3.setPhone("+14155555555");
        patient3.setEmail("adam.lee@example.com");
        patient3.setGuardianJmbg("0203942000001");
        patient3.setGuardianNameAndSurname("Sarah Johnson");
        SocialData socialData3 = new SocialData();
        socialData3.setMaritalStatus(MaritalStatus.U_BRAKU);
        socialData3.setNumOfChildren(0);
        socialData3.setExpertiseDegree(ExpertiseDegree.SREDNJE);
        socialData3.setProfession("Doctor");
        socialData3.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        patient3.setSocialData(socialData3);

        Patient patient4 = new Patient();
        patient4.setJmbg("0404997000001");
        patient4.setLbp("P0005");
        patient4.setName("Emily");
        patient4.setParentName("Brown");
        patient4.setSurname("Davis");
        patient4.setGender(Gender.ZENSKO);
        patient4.setDateOfBirth(Date.valueOf("1997-3-4"));
        patient4.setBirthPlace("London");
        patient4.setPlaceOfLiving("Manchester");
        patient4.setResidenceCountry(CountryCode.GBR);
        patient4.setCitizenship(CountryCode.GBR);
        patient4.setPhone("+442071234567");
        patient4.setEmail("emily.davis@example.com");
        patient4.setGuardianJmbg("0304928000001");
        patient4.setGuardianNameAndSurname("Oliver Brown");
        SocialData socialData4 = new SocialData();
        socialData4.setMaritalStatus(MaritalStatus.UDOVAC_UDOVICA);
        socialData4.setNumOfChildren(2);
        socialData4.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        socialData4.setProfession("Teacher");
        socialData4.setFamilyStatus(FamilyStatus.JEDAN_RODITELJ);
        patient4.setSocialData(socialData4);

        Patient patient5 = new Patient();
        patient5.setJmbg("0505996000001");
        patient5.setLbp("P0006");
        patient5.setName("David");
        patient5.setParentName("Garcia");
        patient5.setSurname("Martinez");
        patient5.setGender(Gender.MUSKO);
        patient5.setDateOfBirth(Date.valueOf("1996-4-5"));
        patient5.setBirthPlace("Madrid");
        patient5.setPlaceOfLiving("Barcelona");
        patient5.setResidenceCountry(CountryCode.ESP);
        patient5.setCitizenship(CountryCode.ESP);
        patient5.setPhone("+34911234567");
        patient5.setEmail("david.martinez@example.com");
        patient5.setGuardianJmbg("0405853000001");
        patient5.setGuardianNameAndSurname("Maria Garcia");
        SocialData socialData5 = new SocialData();
        socialData5.setMaritalStatus(MaritalStatus.RAZVEDENI);
        socialData5.setNumOfChildren(1);
        socialData5.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData5.setProfession("Architect");
        socialData5.setFamilyStatus(FamilyStatus.JEDAN_RODITELJ);
        patient5.setSocialData(socialData5);


        //DODATO DA BI TEST ZA MEDICALRECORDSERVIS MOGAO DA RADI
        Patient patient6 = new Patient();
        patient6.setJmbg("0607995000001");
        patient6.setLbp("P0007");
        patient6.setName("Michael");
        patient6.setParentName("John");
        patient6.setSurname("Johnson");
        patient6.setGender(Gender.MUSKO);
        patient6.setDateOfBirth(Date.valueOf("1989-5-4"));
        patient6.setBirthPlace("Toronto");
        patient6.setPlaceOfLiving("Canada");
        patient6.setResidenceCountry(CountryCode.CAN);
        patient6.setCitizenship(CountryCode.CAN);
        patient6.setPhone("+35498214756");
        patient6.setEmail("michael.johnson@example.com");
        patient6.setGuardianJmbg("0485363054001");
        patient6.setGuardianNameAndSurname("Maria Garcia");
        SocialData socialData6 = new SocialData();
        socialData6.setMaritalStatus(MaritalStatus.RAZVEDENI);
        socialData6.setNumOfChildren(2);
        socialData6.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData6.setProfession("Architect");
        socialData6.setFamilyStatus(FamilyStatus.JEDAN_RODITELJ);
        patient6.setSocialData(socialData6);



        socialDataRepository.saveAll(Arrays.asList(socialData1, socialData2, socialData3, socialData4, socialData5,socialData6));
        patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3, patient4, patient5,patient6));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setPatient(patient1);
        medicalRecord1.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord1.setDeleted(false);
        GeneralMedicalData generalMedicalData1 = new GeneralMedicalData();
        generalMedicalData1.setBloodType("A");
        generalMedicalData1.setRH("+");
        medicalRecord1.setGeneralMedicalData(generalMedicalData1);

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setPatient(patient2);
        medicalRecord2.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord2.setDeleted(false);
        GeneralMedicalData generalMedicalData2 = new GeneralMedicalData();
        generalMedicalData2.setBloodType("B");
        generalMedicalData2.setRH("-");
        medicalRecord2.setGeneralMedicalData(generalMedicalData2);

        MedicalRecord medicalRecord3 = new MedicalRecord();
        medicalRecord3.setPatient(patient3);
        medicalRecord3.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord3.setDeleted(false);
        GeneralMedicalData generalMedicalData3 = new GeneralMedicalData();
        generalMedicalData3.setBloodType("0");
        generalMedicalData3.setRH("+");
        medicalRecord3.setGeneralMedicalData(generalMedicalData3);

        MedicalRecord medicalRecord4 = new MedicalRecord();
        medicalRecord4.setPatient(patient4);
        medicalRecord4.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord4.setDeleted(false);
        GeneralMedicalData generalMedicalData4 = new GeneralMedicalData();
        generalMedicalData4.setBloodType("AB");
        generalMedicalData4.setRH("-");
        medicalRecord4.setGeneralMedicalData(generalMedicalData4);

        MedicalRecord medicalRecord5 = new MedicalRecord();
        medicalRecord5.setPatient(patient5);
        medicalRecord5.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord5.setDeleted(false);
        GeneralMedicalData generalMedicalData5 = new GeneralMedicalData();
        generalMedicalData5.setBloodType("A");
        generalMedicalData5.setRH("-");
        medicalRecord5.setGeneralMedicalData(generalMedicalData5);

        generalMedicalDataRepository.saveAll(Arrays.asList(generalMedicalData1, generalMedicalData2, generalMedicalData3, generalMedicalData4, generalMedicalData5));
        medicalRecordRepository.saveAll(Arrays.asList(medicalRecord1, medicalRecord2, medicalRecord3, medicalRecord4, medicalRecord5));

        AllergyData allergyData1 = new AllergyData();
        allergyData1.setGeneralMedicalData(generalMedicalData1);
        allergyData1.setAllergy(a1);

        AllergyData allergyData2 = new AllergyData();
        allergyData2.setGeneralMedicalData(generalMedicalData2);
        allergyData2.setAllergy(a2);

        AllergyData allergyData3 = new AllergyData();
        allergyData3.setGeneralMedicalData(generalMedicalData3);
        allergyData3.setAllergy(a3);

        AllergyData allergyData4 = new AllergyData();
        allergyData4.setGeneralMedicalData(generalMedicalData4);
        allergyData4.setAllergy(a4);

        AllergyData allergyData5 = new AllergyData();
        allergyData5.setGeneralMedicalData(generalMedicalData5);
        allergyData5.setAllergy(a5);

        allergyDataRepository.saveAll(Arrays.asList(allergyData1, allergyData2, allergyData3, allergyData4, allergyData5));

        VaccinationData vaccinationData1 = new VaccinationData();
        vaccinationData1.setVaccinationDate(Date.valueOf("2022-03-04"));
        vaccinationData1.setDeleted(false);
        vaccinationData1.setGeneralMedicalData(generalMedicalData1);
        vaccinationData1.setVaccination(v1);

        VaccinationData vaccinationData2 = new VaccinationData();
        vaccinationData2.setVaccinationDate(Date.valueOf("2022-04-04"));
        vaccinationData2.setDeleted(false);
        vaccinationData2.setGeneralMedicalData(generalMedicalData1);
        vaccinationData2.setVaccination(v2);

        VaccinationData vaccinationData3 = new VaccinationData();
        vaccinationData3.setVaccinationDate(Date.valueOf("2022-05-04"));
        vaccinationData3.setDeleted(false);
        vaccinationData3.setGeneralMedicalData(generalMedicalData1);
        vaccinationData3.setVaccination(v3);

        VaccinationData vaccinationData4 = new VaccinationData();
        vaccinationData4.setVaccinationDate(Date.valueOf("2022-06-04"));
        vaccinationData4.setDeleted(false);
        vaccinationData4.setGeneralMedicalData(generalMedicalData1);
        vaccinationData4.setVaccination(v4);

        VaccinationData vaccinationData5 = new VaccinationData();
        vaccinationData5.setVaccinationDate(Date.valueOf("2022-07-04"));
        vaccinationData5.setDeleted(false);
        vaccinationData5.setGeneralMedicalData(generalMedicalData1);
        vaccinationData5.setVaccination(v5);

        vaccinationDataRepository.saveAll(Arrays.asList(vaccinationData1, vaccinationData2, vaccinationData3, vaccinationData4, vaccinationData5));

        Anamnesis anamnesis1 = new Anamnesis();
        anamnesis1.setMainProblems("Headache and nausea");
        anamnesis1.setCurrDisease("Flu");
        anamnesis1.setPersonalAnamnesis("No significant past medical history");
        anamnesis1.setFamilyAnamnesis("No significant family medical history");
        anamnesis1.setPatientOpinion("I think it's just a mild case of flu");

        Anamnesis anamnesis2 = new Anamnesis();
        anamnesis2.setMainProblems("Chest pain and shortness of breath");
        anamnesis2.setCurrDisease("Possible heart attack");
        anamnesis2.setPersonalAnamnesis("History of hypertension and hyperlipidemia");
        anamnesis2.setFamilyAnamnesis("Father had a heart attack at age 60");
        anamnesis2.setPatientOpinion("I'm really scared it might be a heart attack");

        Anamnesis anamnesis3 = new Anamnesis();
        anamnesis3.setMainProblems("Abdominal pain and diarrhea");
        anamnesis3.setCurrDisease("Gastroenteritis");
        anamnesis3.setPersonalAnamnesis("No significant past medical history");
        anamnesis3.setFamilyAnamnesis("No significant family medical history");
        anamnesis3.setPatientOpinion("I think I ate something bad yesterday");

        Anamnesis anamnesis4 = new Anamnesis();
        anamnesis4.setMainProblems("Joint pain and stiffness");
        anamnesis4.setCurrDisease("Rheumatoid arthritis");
        anamnesis4.setPersonalAnamnesis("No significant past medical history");
        anamnesis4.setFamilyAnamnesis("Mother has a history of rheumatoid arthritis");
        anamnesis4.setPatientOpinion("I've been feeling this way for a while now");

        Anamnesis anamnesis5 = new Anamnesis();
        anamnesis5.setMainProblems("Fever and sore throat");
        anamnesis5.setCurrDisease("Pharyngitis");
        anamnesis5.setPersonalAnamnesis("No significant past medical history");
        anamnesis5.setFamilyAnamnesis("No significant family medical history");
        anamnesis5.setPatientOpinion("I think I might have caught a cold");

        anamnesisRepository.saveAll(Arrays.asList(anamnesis1, anamnesis2, anamnesis3, anamnesis4, anamnesis5));

        Operation operation1 = new Operation();
        operation1.setOperationDate(Date.valueOf("2023-08-12"));
        operation1.setHospitalId(1L);
        operation1.setDepartmentId(1L);
        operation1.setDescription("Appendectomy");
        operation1.setMedicalRecord(medicalRecord1);

        Operation operation2 = new Operation();
        operation2.setOperationDate(Date.valueOf("2023-02-01"));
        operation2.setHospitalId(2L);
        operation2.setDepartmentId(3L);
        operation2.setDescription("Cholecystectomy");
        operation2.setMedicalRecord(medicalRecord2);

        Operation operation3 = new Operation();
        operation3.setOperationDate(Date.valueOf("2023-09-11"));
        operation3.setHospitalId(1L);
        operation3.setDepartmentId(2L);
        operation3.setDescription("Hernia repair");
        operation3.setMedicalRecord(medicalRecord3);

        Operation operation4 = new Operation();
        operation4.setOperationDate(Date.valueOf("2023-05-08"));
        operation4.setHospitalId(3L);
        operation4.setDepartmentId(1L);
        operation4.setDescription("Total hip replacement");
        operation4.setMedicalRecord(medicalRecord4);

        Operation operation5 = new Operation();
        operation5.setOperationDate(Date.valueOf("2023-05-06"));
        operation5.setHospitalId(2L);
        operation5.setDepartmentId(4L);
        operation5.setDescription("Coronary artery bypass grafting");
        operation5.setMedicalRecord(medicalRecord5);

        operationRepository.saveAll(Arrays.asList(operation1, operation2, operation3, operation4, operation5));

        LabPrescription prescription1 = new LabPrescription();
        prescription1.setDoctorLbz("Dr. Smith");
        prescription1.setDepartmentFromId(1L);
        prescription1.setDepartmentToId(2L);
        prescription1.setDate(Date.valueOf("2023-05-07"));
        prescription1.setMedicalRecord(medicalRecord1);

        LabPrescription prescription2 = new LabPrescription();
        prescription2.setDoctorLbz("Dr. Johnson");
        prescription2.setDepartmentFromId(3L);
        prescription2.setDepartmentToId(4L);
        prescription2.setDate(Date.valueOf("2023-05-08"));
        prescription2.setMedicalRecord(medicalRecord2);

        LabPrescription prescription3 = new LabPrescription();
        prescription3.setDoctorLbz("Dr. Lee");
        prescription3.setDepartmentFromId(2L);
        prescription3.setDepartmentToId(3L);
        prescription3.setDate(Date.valueOf("2023-05-09"));
        prescription3.setMedicalRecord(medicalRecord3);

        LabPrescription prescription4 = new LabPrescription();
        prescription4.setDoctorLbz("Dr. Kim");
        prescription4.setDepartmentFromId(4L);
        prescription4.setDepartmentToId(1L);
        prescription4.setDate(Date.valueOf("2023-05-11"));
        prescription4.setMedicalRecord(medicalRecord4);

        LabPrescription prescription5 = new LabPrescription();
        prescription5.setDoctorLbz("Dr. Garcia");
        prescription5.setDepartmentFromId(1L);
        prescription5.setDepartmentToId(4L);
        prescription5.setDate(Date.valueOf("2023-05-10"));
        prescription5.setMedicalRecord(medicalRecord5);

        prescriptionRepository.saveAll(Arrays.asList(prescription1, prescription2, prescription3, prescription4, prescription5));

        LabResults labResults1 = new LabResults();
        labResults1.setResult("5.2");
        labResults1.setParameterName("Glukoza");
        labResults1.setUnitOfMeasure("mmol/L");
        labResults1.setLowerLimit(3.9);
        labResults1.setUpperLimit(6.1);
        labResults1.setAnalysisName("Glukoza");
        labResults1.setLabPrescription(prescription1);

        LabResults labResults2 = new LabResults();
        labResults2.setResult("4.8");
        labResults2.setParameterName("Holesterol");
        labResults2.setUnitOfMeasure("mmol/L");
        labResults2.setLowerLimit(0.0);
        labResults2.setUpperLimit(5.2);
        labResults2.setAnalysisName("Holesterol");
        labResults2.setLabPrescription(prescription1);

        LabResults labResults3 = new LabResults();
        labResults3.setResult("1.6");
        labResults3.setParameterName("Trigliceridi");
        labResults3.setUnitOfMeasure("mmol/L");
        labResults3.setLowerLimit(0.0);
        labResults3.setUpperLimit(1.7);
        labResults3.setAnalysisName("Trigliceridi");
        labResults3.setLabPrescription(prescription2);

        LabResults labResults4 = new LabResults();
        labResults4.setResult("5.4");
        labResults4.setParameterName("Urea");
        labResults4.setUnitOfMeasure("mmol/L");
        labResults4.setLowerLimit(2.1);
        labResults4.setUpperLimit(7.1);
        labResults4.setAnalysisName("Urea");
        labResults4.setLabPrescription(prescription2);

        LabResults labResults5 = new LabResults();
        labResults5.setResult("89.0");
        labResults5.setParameterName("Kreatinin");
        labResults5.setUnitOfMeasure("umol/L");
        labResults5.setLowerLimit(53D);
        labResults5.setUpperLimit(97D);
        labResults5.setAnalysisName("Kreatinin");
        labResults5.setLabPrescription(prescription3);

        LabResults labResults6 = new LabResults();
        labResults6.setResult("5.0");
        labResults6.setParameterName("Bilirubin");
        labResults6.setUnitOfMeasure("umol/L");
        labResults6.setLowerLimit(5.1);
        labResults6.setUpperLimit(20.5);
        labResults6.setAnalysisName("Bilirubin");
        labResults6.setLabPrescription(prescription3);

        LabResults labResults7 = new LabResults();
        labResults7.setResult("12.0");
        labResults7.setParameterName("Alanin aminotransferaza");
        labResults7.setUnitOfMeasure("U/L");
        labResults7.setLowerLimit(10D);
        labResults7.setUpperLimit(40D);
        labResults7.setAnalysisName("Alanin aminotransferaza");
        labResults7.setLabPrescription(prescription3);

        LabResults labResults8 = new LabResults();
        labResults8.setResult("20.0");
        labResults8.setParameterName("Aspartat aminotransferaza");
        labResults8.setUnitOfMeasure("U/L");
        labResults8.setLowerLimit(10D);
        labResults8.setUpperLimit(40D);
        labResults8.setAnalysisName("Aspartat aminotransferaza");
        labResults8.setLabPrescription(prescription4);

        LabResults labResults9 = new LabResults();
        labResults9.setResult("150.0");
        labResults9.setParameterName("Kreatin kinaza");
        labResults9.setUnitOfMeasure("U/L");
        labResults9.setLowerLimit(24D);
        labResults9.setUpperLimit(170D);
        labResults9.setAnalysisName("Kreatin kinaza");
        labResults9.setLabPrescription(prescription4);

        LabResults labResults10 = new LabResults();
        labResults10.setResult("1.2");
        labResults10.setParameterName("Tireostimulirajući hormon");
        labResults10.setUnitOfMeasure("mIU/L");
        labResults10.setLowerLimit(0.4);
        labResults10.setUpperLimit(4.0);
        labResults10.setAnalysisName("Tireostimulirajući hormon");
        labResults10.setLabPrescription(prescription5);

        labResultsRepository.saveAll(Arrays.asList(labResults1, labResults2, labResults3, labResults4, labResults5, labResults6, labResults7, labResults8, labResults9, labResults10));

        ScheduleExam scheduleExam1 = new ScheduleExam();
        scheduleExam1.setDateAndTime(Timestamp.valueOf("2023-04-05 09:00:00"));
        scheduleExam1.setArrivalStatus(PatientArrival.ZAKAZANO);
        scheduleExam1.setNote("Regular check-up");
        scheduleExam1.setDoctorLbz("E0005");
        scheduleExam1.setLbz("E0001");
        scheduleExam1.setPatient(patient1);

        ScheduleExam scheduleExam2 = new ScheduleExam();
        scheduleExam2.setDateAndTime(Timestamp.valueOf("2023-04-05 11:00:00"));
        scheduleExam2.setArrivalStatus(PatientArrival.CEKA);
        scheduleExam2.setNote("Fever and cough symptoms");
        scheduleExam2.setDoctorLbz("E0004");
        scheduleExam2.setLbz("E0002");
        scheduleExam2.setPatient(patient2);

        ScheduleExam scheduleExam3 = new ScheduleExam();
        scheduleExam3.setDateAndTime(Timestamp.valueOf("2023-04-06 14:30:00"));
        scheduleExam3.setArrivalStatus(PatientArrival.OTKAZANO);
        scheduleExam3.setNote("Post-operative check-up");
        scheduleExam3.setDoctorLbz("E0003");
        scheduleExam3.setLbz("E0004");
        scheduleExam3.setPatient(patient3);

        ScheduleExam scheduleExam4 = new ScheduleExam();
        scheduleExam4.setDateAndTime(Timestamp.valueOf("2023-04-07 10:15:00"));
        scheduleExam4.setArrivalStatus(PatientArrival.ZAVRSENO);
        scheduleExam4.setNote("Lab results review");
        scheduleExam4.setDoctorLbz("E0002");
        scheduleExam4.setLbz("E0005");
        scheduleExam4.setPatient(patient4);

        ScheduleExam scheduleExam5 = new ScheduleExam();
        scheduleExam5.setDateAndTime(Timestamp.valueOf("2023-04-08 16:00:00"));
        scheduleExam5.setArrivalStatus(PatientArrival.TRENUTNO);
        scheduleExam5.setNote("Headache and dizziness");
        scheduleExam5.setDoctorLbz("E0001");
        scheduleExam5.setLbz("E0006");
        scheduleExam5.setPatient(patient5);

        scheduleExamRepository.saveAll(Arrays.asList(scheduleExam1, scheduleExam2, scheduleExam3, scheduleExam4, scheduleExam5));

        ExaminationHistory examinationHistory1 = new ExaminationHistory();
        examinationHistory1.setExamDate(Date.valueOf("2023-02-04"));
        examinationHistory1.setLbz("lbz01");
        examinationHistory1.setConfidential(true);
        examinationHistory1.setObjectiveFinding("Objective finding 1");
        examinationHistory1.setAdvice("Advice 1");
        examinationHistory1.setTherapy("Therapy 1");
        examinationHistory1.setDiagnosisCode(d1);
        examinationHistory1.setAnamnesis(anamnesis1);
        examinationHistory1.setMedicalRecord(medicalRecord1);

        ExaminationHistory examinationHistory2 = new ExaminationHistory();
        examinationHistory2.setExamDate(Date.valueOf("2023-02-05"));
        examinationHistory2.setLbz("lbz02");
        examinationHistory2.setConfidential(false);
        examinationHistory2.setObjectiveFinding("Objective finding 2");
        examinationHistory2.setAdvice("Advice 2");
        examinationHistory2.setTherapy("Therapy 2");
        examinationHistory2.setDiagnosisCode(d2);
        examinationHistory2.setAnamnesis(anamnesis2);
        examinationHistory2.setMedicalRecord(medicalRecord2);

        ExaminationHistory examinationHistory3 = new ExaminationHistory();
        examinationHistory3.setExamDate(Date.valueOf("2023-02-06"));
        examinationHistory3.setLbz("lbz03");
        examinationHistory3.setConfidential(true);
        examinationHistory3.setObjectiveFinding("Objective finding 3");
        examinationHistory3.setAdvice("Advice 3");
        examinationHistory3.setTherapy("Therapy 3");
        examinationHistory3.setDiagnosisCode(d3);
        examinationHistory3.setAnamnesis(anamnesis3);
        examinationHistory3.setMedicalRecord(medicalRecord3);

        ExaminationHistory examinationHistory4 = new ExaminationHistory();
        examinationHistory4.setExamDate(Date.valueOf("2023-02-07"));
        examinationHistory4.setLbz("lbz04");
        examinationHistory4.setConfidential(false);
        examinationHistory4.setObjectiveFinding("Objective finding 4");
        examinationHistory4.setAdvice("Advice 4");
        examinationHistory4.setTherapy("Therapy 4");
        examinationHistory4.setDiagnosisCode(d4);
        examinationHistory4.setAnamnesis(anamnesis4);
        examinationHistory4.setMedicalRecord(medicalRecord4);

        ExaminationHistory examinationHistory5 = new ExaminationHistory();
        examinationHistory5.setExamDate(Date.valueOf("2023-02-08"));
        examinationHistory5.setLbz("lbz05");
        examinationHistory5.setConfidential(true);
        examinationHistory5.setObjectiveFinding("Objective finding 5");
        examinationHistory5.setAdvice("Advice 5");
        examinationHistory5.setTherapy("Therapy 5");
        examinationHistory5.setDiagnosisCode(d5);
        examinationHistory5.setAnamnesis(anamnesis5);
        examinationHistory5.setMedicalRecord(medicalRecord5);

        ExaminationHistory examinationHistory6 = new ExaminationHistory();
        examinationHistory6.setExamDate(Date.valueOf("2023-02-09"));
        examinationHistory6.setLbz("lbz06");
        examinationHistory6.setConfidential(false);
        examinationHistory6.setObjectiveFinding("Objective finding 6");
        examinationHistory6.setAdvice("Advice 6");
        examinationHistory6.setTherapy("Therapy 6");
        examinationHistory6.setDiagnosisCode(d6);
        examinationHistory6.setAnamnesis(anamnesis2);
        examinationHistory6.setMedicalRecord(medicalRecord5);

        ExaminationHistory examinationHistory7 = new ExaminationHistory();
        examinationHistory7.setExamDate(Date.valueOf("2023-02-10"));
        examinationHistory7.setLbz("lbz7");
        examinationHistory7.setConfidential(false);
        examinationHistory7.setObjectiveFinding("Objective finding 7");
        examinationHistory7.setAdvice("Advice 7");
        examinationHistory7.setTherapy("Therapy 7");
        examinationHistory7.setDiagnosisCode(d7);
        examinationHistory7.setAnamnesis(anamnesis5);
        examinationHistory7.setMedicalRecord(medicalRecord2);

        ExaminationHistory examinationHistory8 = new ExaminationHistory();
        examinationHistory8.setExamDate(Date.valueOf("2023-02-11"));
        examinationHistory8.setLbz("lbz8");
        examinationHistory8.setConfidential(true);
        examinationHistory8.setObjectiveFinding("Objective finding 8");
        examinationHistory8.setAdvice("Advice 8");
        examinationHistory8.setTherapy("Therapy 8");
        examinationHistory8.setDiagnosisCode(d8);
        examinationHistory8.setAnamnesis(anamnesis4);
        examinationHistory8.setMedicalRecord(medicalRecord2);

        ExaminationHistory examinationHistory9 = new ExaminationHistory();
        examinationHistory9.setExamDate(Date.valueOf("2023-02-12"));
        examinationHistory9.setLbz("lbz9");
        examinationHistory9.setConfidential(false);
        examinationHistory9.setObjectiveFinding("Objective finding 9");
        examinationHistory9.setAdvice("Advice 9");
        examinationHistory9.setTherapy("Therapy 9");
        examinationHistory9.setDiagnosisCode(d9);
        examinationHistory9.setAnamnesis(anamnesis5);
        examinationHistory9.setMedicalRecord(medicalRecord3);

        ExaminationHistory examinationHistory10 = new ExaminationHistory();
        examinationHistory10.setExamDate(Date.valueOf("2023-03-03"));
        examinationHistory10.setLbz("lbz10");
        examinationHistory10.setConfidential(true);
        examinationHistory10.setObjectiveFinding("Objective finding 10");
        examinationHistory10.setAdvice("Advice 10");
        examinationHistory10.setTherapy("Therapy 10");
        examinationHistory10.setDiagnosisCode(d10);
        examinationHistory10.setAnamnesis(anamnesis3);
        examinationHistory10.setMedicalRecord(medicalRecord3);

        examinationHistoryRepository.saveAll(Arrays.asList(examinationHistory1, examinationHistory2, examinationHistory3, examinationHistory4, examinationHistory5, examinationHistory6, examinationHistory7, examinationHistory8, examinationHistory9, examinationHistory10));

        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setStartDate(Date.valueOf("2010-01-01"));
        medicalHistory1.setEndDate(Date.valueOf("2010-02-01"));
        medicalHistory1.setConfidential(false);
        medicalHistory1.setTreatmentResult(TreatmentResult.OPORAVLJEN);
        medicalHistory1.setCurrStateDesc("Current state description 1");
        medicalHistory1.setValidFrom(Date.valueOf("2010-01-01"));
        medicalHistory1.setValidTo(Date.valueOf("2010-12-31"));
        medicalHistory1.setValid(true);
        medicalHistory1.setMedicalRecord(medicalRecord1);
        medicalHistory1.setDiagnosisCode(d1);

        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setStartDate(Date.valueOf("2012-01-01"));
        medicalHistory2.setEndDate(Date.valueOf("2012-02-01"));
        medicalHistory2.setConfidential(false);
        medicalHistory2.setTreatmentResult(TreatmentResult.STALNE_POSLEDICE);
        medicalHistory2.setCurrStateDesc("Current state description 2");
        medicalHistory2.setValidFrom(Date.valueOf("2012-01-01"));
        medicalHistory2.setValidTo(Date.valueOf("2012-12-31"));
        medicalHistory2.setValid(true);
        medicalHistory2.setMedicalRecord(medicalRecord1);
        medicalHistory2.setDiagnosisCode(d2);

        MedicalHistory medicalHistory3 = new MedicalHistory();
        medicalHistory3.setStartDate(Date.valueOf("2015-01-01"));
        medicalHistory3.setEndDate(Date.valueOf("2015-02-01"));
        medicalHistory3.setConfidential(false);
        medicalHistory3.setTreatmentResult(TreatmentResult.U_TOKU);
        medicalHistory3.setCurrStateDesc("Current state description 3");
        medicalHistory3.setValidFrom(Date.valueOf("2015-01-01"));
        medicalHistory3.setValidTo(Date.valueOf("2015-12-31"));
        medicalHistory3.setValid(true);
        medicalHistory3.setMedicalRecord(medicalRecord2);
        medicalHistory3.setDiagnosisCode(d3);

        MedicalHistory medicalHistory4 = new MedicalHistory();
        medicalHistory4.setStartDate(Date.valueOf("2017-01-01"));
        medicalHistory4.setEndDate(Date.valueOf("2017-02-01"));
        medicalHistory4.setConfidential(false);
        medicalHistory4.setTreatmentResult(TreatmentResult.PREMINUO);
        medicalHistory4.setCurrStateDesc("Current state description 4");
        medicalHistory4.setValidFrom(Date.valueOf("2017-01-01"));
        medicalHistory4.setValidTo(Date.valueOf("2017-12-31"));
        medicalHistory4.setValid(true);
        medicalHistory4.setMedicalRecord(medicalRecord2);
        medicalHistory4.setDiagnosisCode(d4);

        MedicalHistory medicalHistory5 = new MedicalHistory();
        medicalHistory5.setStartDate(Date.valueOf("2020-01-01"));
        medicalHistory5.setEndDate(Date.valueOf("2020-02-01"));
        medicalHistory5.setConfidential(false);
        medicalHistory5.setTreatmentResult(TreatmentResult.U_TOKU);
        medicalHistory5.setCurrStateDesc("Current state description 5");
        medicalHistory5.setValidFrom(Date.valueOf("2020-01-01"));
        medicalHistory5.setValidTo(Date.valueOf("2020-12-31"));
        medicalHistory5.setValid(true);
        medicalHistory5.setMedicalRecord(medicalRecord3);
        medicalHistory5.setDiagnosisCode(d5);

        medicalHistoryRepository.saveAll(Arrays.asList(medicalHistory1, medicalHistory2, medicalHistory3, medicalHistory4, medicalHistory5));

    }
}
