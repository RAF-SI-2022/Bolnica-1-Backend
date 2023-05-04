package raf.bolnica1.patient.services.impl.additionalTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.patient.cucumber.validation.ClassJsonComparator;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.SocialData;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.domain.prescription.LabPrescription;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.create.LabResultDto;
import raf.bolnica1.patient.dto.create.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDoneDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionDoneLabDto;
import raf.bolnica1.patient.mapper.PrescriptionMapper;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.repository.LabResultsRepository;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.PrescriptionRepository;
import raf.bolnica1.patient.services.PrescriptionService;
import raf.bolnica1.patient.services.impl.PrescriptionServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceUnitTest {

    private JmsTemplate jmsTemplate;
    private String destinationSendLab;
    private String destinationDeleteLab;
    private String destinationUpdateLab;
    private String destinationSendInfirmary;
    private MessageHelper messageHelper;
    private PrescriptionMapper prescriptionMapper;
    private RestTemplate labRestTemplate;
    private PrescriptionRepository prescriptionRepository;
    private PatientRepository patientRepository;
    private LabResultsRepository labResultsRepository;
    private MedicalRecordRepository medicalRecordRepository;

    private PrescriptionService prescriptionService;

    private ClassJsonComparator classJsonComparator=new ClassJsonComparator(new ObjectMapper());


    private List<Prescription> prescriptions;
    private List<MedicalRecord> medicalRecords;
    private List<Patient> patients;



    @BeforeEach
    private void prepare(){

        generateData();

        jmsTemplate=mock(JmsTemplate.class);
        destinationSendLab="destinationSendLab";
        destinationDeleteLab="destinationDeleteLab";
        destinationUpdateLab="destinationUpdateLab";
        destinationSendInfirmary="destinationSendInfirmary";
        messageHelper=mock(MessageHelper.class);
        patientRepository=mock(PatientRepository.class);
        labResultsRepository=mock(LabResultsRepository.class);
        medicalRecordRepository=mock(MedicalRecordRepository.class);
        prescriptionMapper=new PrescriptionMapper(labResultsRepository,medicalRecordRepository,patientRepository);
        labRestTemplate=mock(RestTemplate.class);
        prescriptionRepository=mock(PrescriptionRepository.class);
        prescriptionService=new PrescriptionServiceImpl(jmsTemplate,messageHelper,destinationSendLab,destinationDeleteLab,
                destinationUpdateLab,destinationSendInfirmary,prescriptionMapper,labRestTemplate,prescriptionRepository,
                patientRepository,labResultsRepository);
    }


    @Test
    public void getAllDonePrescriptionsForPatientTest() throws JsonProcessingException {

        String lbp="mojLBP";
        Date startDate=new Date(System.currentTimeMillis());
        Date endDate=new Date(System.currentTimeMillis());

        List<Prescription> list=new ArrayList<>();
        list.add(prescriptions.get(0));
        list.add(prescriptions.get(1));

        Page<Prescription> page=new PageImpl<>(list);

        given(prescriptionRepository.findPrescriptionByPatientAndDate(any(),eq(lbp),eq(startDate),eq(endDate)))
                .willReturn(page);

        List<String> analysis1=new ArrayList<>();
        analysis1.add("analysis1");
        given(labResultsRepository.findAnalysisForPrescription(1L)).willReturn(analysis1);
        List<String> analysis2=new ArrayList<>();
        analysis2.add("analysis2");
        given(labResultsRepository.findAnalysisForPrescription(2L)).willReturn(analysis2);

        LabResults results1=new LabResults();
        results1.setResult("result1");
        results1.setUnitOfMeasure("unit1");
        results1.setUpperLimit(1.0);
        results1.setLowerLimit(-1.0);
        results1.setParameterName("param1");
        List<LabResults> resultsList1=new ArrayList<>();
        resultsList1.add(results1);
        given(labResultsRepository.findResultsForPrescription(1L,"analysis1")).willReturn(resultsList1);

        LabResults results2=new LabResults();
        results2.setResult("result2");
        results2.setUnitOfMeasure("unit2");
        results2.setUpperLimit(2.0);
        results2.setLowerLimit(-2.0);
        results2.setParameterName("param2");
        List<LabResults> resultsList2=new ArrayList<>();
        resultsList2.add(results2);
        given(labResultsRepository.findResultsForPrescription(2L,"analysis2")).willReturn(resultsList2);


        Page<PrescriptionDoneDto> ret=prescriptionService.getAllDonePrescriptionsForPatient(lbp,startDate,endDate,0,1000);

        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(ret.getContent()));

        ret.getContent().get(0).setId(1L);
        ret.getContent().get(1).setId(2L);
        Assertions.assertTrue(classJsonComparator.compareListCommonFields(ret.getContent(),list));

        Assertions.assertTrue(classJsonComparator.compareCommonFields(results1,
                ((PrescriptionDoneLabDto)ret.getContent().get(0)).getParameters().get(0).getParameters().get(0) ));
        Assertions.assertTrue(classJsonComparator.compareCommonFields(results2,
                ((PrescriptionDoneLabDto)ret.getContent().get(1)).getParameters().get(0).getParameters().get(0) ));

        Assertions.assertEquals(((PrescriptionDoneLabDto) ret.getContent().get(0)).getParameters().get(0).getAnalysisName(), analysis1.get(0));
        Assertions.assertEquals(((PrescriptionDoneLabDto) ret.getContent().get(1)).getParameters().get(0).getAnalysisName(), analysis2.get(0));

        Assertions.assertEquals(ret.getContent().get(0).getPrescriptionStatus(), PrescriptionStatus.REALIZOVAN);
        Assertions.assertEquals(ret.getContent().get(1).getPrescriptionStatus(), PrescriptionStatus.REALIZOVAN);

        Assertions.assertEquals(ret.getContent().get(0).getLbp(), patients.get(0).getLbp());
        Assertions.assertEquals(ret.getContent().get(1).getLbp(), patients.get(1).getLbp());
    }


    @Test
    public void createPrescriptionTest(){

        PrescriptionCreateDto prescriptionCreateDto=new PrescriptionCreateDto();
        prescriptionCreateDto.setDoctorLbz("lbz");
        prescriptionCreateDto.setDepartmentToId(1L);
        prescriptionCreateDto.setDepartmentFromId(2L);
        prescriptionCreateDto.setDate(new Date(System.currentTimeMillis()));
        prescriptionCreateDto.setLbp(patients.get(0).getLbp());
        prescriptionCreateDto.setComment("comment");
        prescriptionCreateDto.setType("LABORATORIJA");
        List<LabResultDto>labResultDtoList=new ArrayList<>();
        LabResultDto labResultDto=new LabResultDto();
        labResultDto.setResult("result");
        labResultDto.setUnitOfMeasure("unit");
        labResultDto.setUpperLimit(1.0);
        labResultDto.setParameterName("param");
        labResultDto.setLowerLimit(-1.0);
        labResultDto.setAnalysisName("analysis");
        labResultDtoList.add(labResultDto);
        prescriptionCreateDto.setLabResultDtoList(labResultDtoList);

        given(patientRepository.findByLbp(patients.get(0).getLbp())).willReturn(Optional.ofNullable(patients.get(0)));
        given(medicalRecordRepository.findByPatient(patients.get(0))).willReturn(Optional.ofNullable(medicalRecords.get(0)));
        Prescription pom=prescriptionMapper.toEntity(prescriptionCreateDto);
        given(prescriptionRepository.save(any())).willReturn(pom);

        prescriptionService.createPrescription(prescriptionCreateDto);

        ArgumentCaptor<Prescription>prescriptionArgumentCaptor=ArgumentCaptor.forClass(Prescription.class);
        verify(prescriptionRepository).save(prescriptionArgumentCaptor.capture());

        ArgumentCaptor<LabResults> labResultsArgumentCaptor=ArgumentCaptor.forClass(LabResults.class);
        verify(labResultsRepository).save(labResultsArgumentCaptor.capture());

        Assertions.assertTrue(classJsonComparator.compareCommonFields(labResultsArgumentCaptor.getValue(),
                prescriptionCreateDto.getLabResultDtoList().get(0)));

        Assertions.assertTrue(classJsonComparator.compareCommonFields(prescriptionArgumentCaptor.getValue(),
                prescriptionCreateDto));

    }



    /// generate data

    public void generateData(){

        prescriptions=new ArrayList<>();
        medicalRecords=new ArrayList<>();
        patients=new ArrayList<>();

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

        LabPrescription prescription1 = new LabPrescription();
        prescription1.setDoctorLbz("Dr. Smith");
        prescription1.setDepartmentFromId(1L);
        prescription1.setDepartmentToId(2L);
        prescription1.setDate(Date.valueOf("2023-05-07"));
        prescription1.setMedicalRecord(medicalRecord1);
        prescription1.setComment("comment1");
        prescription1.setId(1L);

        LabPrescription prescription2 = new LabPrescription();
        prescription2.setDoctorLbz("Dr. Johnson");
        prescription2.setDepartmentFromId(3L);
        prescription2.setDepartmentToId(4L);
        prescription2.setDate(Date.valueOf("2023-05-08"));
        prescription2.setMedicalRecord(medicalRecord2);
        prescription2.setComment("comment2");
        prescription2.setId(2L);

        LabPrescription prescription3 = new LabPrescription();
        prescription3.setDoctorLbz("Dr. Lee");
        prescription3.setDepartmentFromId(2L);
        prescription3.setDepartmentToId(3L);
        prescription3.setDate(Date.valueOf("2023-05-09"));
        prescription3.setMedicalRecord(medicalRecord3);
        prescription3.setComment("comment3");
        prescription3.setId(3L);



        prescriptions.add(prescription1);
        prescriptions.add(prescription2);
        prescriptions.add(prescription3);

        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);
        medicalRecords.add(medicalRecord3);

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

    }

}
