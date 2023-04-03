package raf.bolnica1.patient.runner;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.CountryCode;
import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.domain.constants.VaccinationType;
import raf.bolnica1.patient.domain.prescription.LabPrescription;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.*;

import java.sql.Date;

//@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private PatientRepository patientRepository;
    private SocialDataRepository socialDataRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private AllergyRepository allergyRepository;
    private VaccinationRepository vaccinationRepository;

    private DiagnosisCodeRepository diagnosisCodeRepository;

    private PatientMapper patientMapper;

    private PrescriptionRepository prescriptionRepository;
    private LabResultsRepository labResultsRepository;

    @Override
    public void run(String... args) throws Exception {
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

        Patient patient = new Patient();
        patient.setName("Peter");
        patient.setSurname("Parker");
        patient.setDeleted(false);
        patient.setCitizenship(CountryCode.ITA);
        patient.setBirthPlace("USA");
        patient.setDateAndTimeOfDeath(null);
        patient.setGender(Gender.MUSKO);
        patient.setEmail("peter.parker@gmail.com");
        patient.setSocialData(null);
        patient.setDateOfBirth(Date.valueOf("1999-09-09"));
        patient.setJmbg("1234567");
        patient.setPhone("1234567");
        patient.setParentName("John");
        patient.setLbp("P0001");
        patient.setResidenceCountry(CountryCode.ITA);
        patientRepository.save(patient);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setGeneralMedicalData(null);
        medicalRecord.setPatient(patient);
        medicalRecord.setDeleted(false);
        medicalRecord.setRegistrationDate(Date.valueOf("2008-08-08"));
        medicalRecordRepository.save(medicalRecord);

        Prescription prescription = new LabPrescription();
        prescription.setDate(Date.valueOf("2022-02-02"));
        prescription.setDepartmentFromId(1L);
        prescription.setDepartmentToId(2L);
        prescription.setDoctorLbz("E0003");
        prescription.setMedicalRecord(medicalRecord);
        prescriptionRepository.save(prescription);

        LabResults labResults = new LabResults();
        labResults.setResult("15");
        labResults.setLowerLimit(0.0);
        labResults.setUpperLimit(20.0);
        labResults.setAnalysisName("analiza1");
        labResults.setParameterName("parametar1");
        labResults.setUnitOfMeasure("mera");
        labResults.setLabPrescription((LabPrescription) prescription);
        labResultsRepository.save(labResults);

        LabResults labResults2 = new LabResults();
        labResults2.setResult("10");
        labResults2.setLowerLimit(0.0);
        labResults2.setUpperLimit(30.0);
        labResults2.setAnalysisName("analiza1");
        labResults2.setParameterName("parametar2");
        labResults2.setUnitOfMeasure("mera");
        labResults2.setLabPrescription((LabPrescription) prescription);
        labResultsRepository.save(labResults2);

        LabResults labResults3 = new LabResults();
        labResults3.setResult("10");
        labResults3.setLowerLimit(0.0);
        labResults3.setUpperLimit(30.0);
        labResults3.setAnalysisName("analiza2");
        labResults3.setParameterName("parametar2");
        labResults3.setUnitOfMeasure("mera");
        labResults3.setLabPrescription((LabPrescription) prescription);
        labResultsRepository.save(labResults3);


    }
}
