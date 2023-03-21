package raf.bolnica1.patient.runner;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.general.PatientDto;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.GeneralMedicalDataRepository;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.SocialDataRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

//@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private PatientRepository patientRepository;
    private SocialDataRepository socialDataRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;

    private PatientMapper patientMapper;


    @Override
    public void run(String... args) throws Exception {
        Patient patient1 = createEntity();
        Patient patient2 = createEntity();
        Patient patient3 = createEntity();

        patient2.setLbp("testLBP");
        patient2.setName("Igor");
        patient2.setSurname("Igorovic");
        patient2.setJmbg("10987654321");
        patient2.setEmail("pat2@mail.com");

        patient3.setLbp(UUID.randomUUID().toString());
        patient3.setSurname("Jovanovic");
        patient3.setJmbg("10896745123");
        patient3.setEmail("pat3@mail.com");

        patient1.setSocialData(socialDataRepository.save(patient1.getSocialData()));
        patient2.setSocialData(socialDataRepository.save(patient2.getSocialData()));
        patient3.setSocialData(socialDataRepository.save(patient3.getSocialData()));


        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
        patient3 = patientRepository.save(patient3);

        MedicalRecord md1 = new MedicalRecord();
        md1.setRegistrationDate(Date.valueOf(LocalDate.now()));
        md1.setPatient(patient1);

        GeneralMedicalData gmd1 = new GeneralMedicalData();
        gmd1.setBloodType("A");
        gmd1.setRH("+");
        gmd1 = generalMedicalDataRepository.save(gmd1);

        md1.setGeneralMedicalData(gmd1);
        medicalRecordRepository.save(md1);

        MedicalRecord md2 = new MedicalRecord();
        md2.setRegistrationDate(Date.valueOf("1999-01-01"));
        md2.setPatient(patient2);

        GeneralMedicalData gmd2 = new GeneralMedicalData();
        gmd2.setBloodType("B");
        gmd2.setRH("+");
        gmd2 = generalMedicalDataRepository.save(gmd2);

        md2.setGeneralMedicalData(gmd2);
        medicalRecordRepository.save(md2);

        MedicalRecord md3 = new MedicalRecord();
        md3.setRegistrationDate(Date.valueOf("2007-05-08"));
        md3.setPatient(patient3);

        GeneralMedicalData gmd3 = new GeneralMedicalData();
        gmd3.setBloodType("A");
        gmd3.setRH("-");
        gmd3 = generalMedicalDataRepository.save(gmd3);

        md3.setGeneralMedicalData(gmd3);
        medicalRecordRepository.save(md3);
    }

    private Patient createEntity(){

        PatientDto dto = new PatientDto();
        dto.setLbp(UUID.randomUUID().toString());
        dto.setName("Petar");
        dto.setSurname("Petrovic");
        dto.setCitizenship(CountryCode.SRB);
        dto.setEmail("p4c1j3nt@mail.com");
        dto.setBirthPlace("Beograd");
        dto.setJmbg("11111112345");
        dto.setGender(Gender.MUSKO);
        dto.setDateOfBirth(Date.valueOf("2011-11-11"));
        dto.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        dto.setExpertiseDegree(ExpertiseDegree.BEZ_OSNOVNOG);
        dto.setGuardianJmbg("20030612345");
        dto.setMaritalStatus(MaritalStatus.SAMAC_SAMICA);
        dto.setParentName("Marko");
        dto.setNumOfChildren(0);
        dto.setGuardianNameAndSurname("Marko Markovic");
        dto.setPhone("0630744261");
        dto.setProfession("");
        dto.setPlaceOfLiving("Beograd");

        return patientMapper.patientDtoToPatient(dto);
    }
}
