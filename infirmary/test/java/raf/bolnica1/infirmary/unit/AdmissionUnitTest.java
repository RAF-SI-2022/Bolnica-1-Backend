package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.ScheduledAppointmentGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.HospitalizationCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.filter.PrescriptionFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.filter.PrescriptionFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.ScheduledAppointmentCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter.ScheduledAppointmentFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter.ScheduledAppointmentFilterGenerator;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.mapper.ScheduledAppointmentMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;
import raf.bolnica1.infirmary.services.AdmissionService;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.impl.AdmissionServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AdmissionUnitTest {

    private HospitalizationCreateDtoGenerator hospitalizationCreateDtoGenerator=HospitalizationCreateDtoGenerator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private HospitalRoomGenerator hospitalRoomGenerator=HospitalRoomGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private ScheduledAppointmentCreateDtoGenerator scheduledAppointmentCreateDtoGenerator=ScheduledAppointmentCreateDtoGenerator.getInstance();
    private ScheduledAppointmentGenerator scheduledAppointmentGenerator=ScheduledAppointmentGenerator.getInstance();
    private ScheduledAppointmentFilterGenerator scheduledAppointmentFilterGenerator=ScheduledAppointmentFilterGenerator.getInstance();
    private PrescriptionFilterGenerator prescriptionFilterGenerator=PrescriptionFilterGenerator.getInstance();



    /// MOCK
    private PrescriptionMapper prescriptionMapper;
    private ScheduledAppointmentMapper scheduledAppointmentMapper;
    private HospitalizationMapper hospitalizationMapper;
    private AuthenticationUtils authenticationUtils;
    private RestTemplate patientRestTemplate;


    private PrescriptionRepository prescriptionRepository;
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private HospitalRoomRepository hospitalRoomRepository;
    private HospitalizationRepository hospitalizationRepository;


    private AdmissionService admissionService;



    @BeforeEach
    public void prepare(){
        prescriptionMapper=new PrescriptionMapper();
        authenticationUtils=mock(AuthenticationUtils.class);
        patientRestTemplate=mock(RestTemplate.class);
        scheduledAppointmentMapper=new ScheduledAppointmentMapper(authenticationUtils);
        hospitalizationMapper=new HospitalizationMapper(authenticationUtils,patientRestTemplate);
        prescriptionRepository=mock(PrescriptionRepository.class);
        scheduledAppointmentRepository=mock(ScheduledAppointmentRepository.class);
        hospitalRoomRepository=mock(HospitalRoomRepository.class);
        hospitalizationRepository=mock(HospitalizationRepository.class);
        admissionService=new AdmissionServiceImpl(prescriptionMapper,scheduledAppointmentMapper,hospitalizationMapper,
                prescriptionRepository,scheduledAppointmentRepository,hospitalRoomRepository,hospitalizationRepository);
    }


    @Test
    public void createHospitalizationTest(){

        given(authenticationUtils.getLbzFromAuthentication()).willReturn("mojLbz");


        String authorization="Bearer mojToken";
        long prescriptionId=4;
        long hospitalRoomId=3;
        long hospitalizationId=5;
        int prevOccupancy;

        Prescription prescription=prescriptionGenerator.getPrescription();
        prescription.setId(prescriptionId);
        HospitalRoom hospitalRoom=hospitalRoomGenerator.generateHospitalRoom();
        hospitalRoom.setId(hospitalRoomId);
        prevOccupancy=hospitalRoom.getOccupancy();
        HospitalizationCreateDto hospitalizationCreateDto=hospitalizationCreateDtoGenerator.getHospitalizationCreateDto(
                hospitalRoom.getId(),prescription.getId());

        given(hospitalRoomRepository.findHospitalRoomById(hospitalRoomId)).willReturn(hospitalRoom);
        given(prescriptionRepository.findPrescriptionById(prescriptionId)).willReturn(prescription);
        PatientDto patientDto=new PatientDto();
        patientDto.setName("ime");
        patientDto.setSurname("prezime");
        patientDto.setJmbg("jmbg");
        given(patientRestTemplate.exchange(eq("/find_patient/"+prescription.getLbp()),any(),any(), eq(PatientDto.class) ))
                .willReturn(new ResponseEntity<>(patientDto, HttpStatus.OK));

        Hospitalization hospitalization=hospitalizationMapper.toEntity(hospitalizationCreateDto,hospitalRoomRepository,
                prescriptionRepository,authorization);
        hospitalization.setId(hospitalizationId);
        given(hospitalizationRepository.save(any())).willReturn(hospitalization);

        given(scheduledAppointmentRepository.findScheduledAppointmentByPrescriptionId(prescription.getId())).willReturn(null);

        given(hospitalRoomRepository.findHospitalRoomById(hospitalRoomId)).willReturn(hospitalRoom);

        given(prescriptionRepository.findPrescriptionById(prescriptionId)).willReturn(prescription);


        HospitalizationDto result=admissionService.createHospitalization(hospitalizationCreateDto,authorization);


        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,hospitalizationCreateDto));
        Assertions.assertTrue(hospitalRoom.getOccupancy()==prevOccupancy+1);
        Assertions.assertTrue(prescription.getStatus()== PrescriptionStatus.REALIZOVAN);
        Assertions.assertTrue(result.getLbzRegister().equals("mojLbz"));
    }


    @Test
    public void createScheduledAppointmentTest(){

        given(authenticationUtils.getLbzFromAuthentication()).willReturn("mojLbz");

        long prescriptionId=4;
        long scheduledAppointmentId=3;

        Prescription prescription=prescriptionGenerator.getPrescription();
        prescription.setId(prescriptionId);

        ScheduledAppointmentCreateDto scheduledAppointmentCreateDto=scheduledAppointmentCreateDtoGenerator
                .getScheduledAppointmentCreateDto(prescriptionId);

        given(prescriptionRepository.findPrescriptionById(prescriptionId)).willReturn(prescription);

        ScheduledAppointment scheduledAppointment=scheduledAppointmentMapper.toEntity(scheduledAppointmentCreateDto,
                prescriptionRepository);
        scheduledAppointment.setId(scheduledAppointmentId);
        given(scheduledAppointmentRepository.save(any())).willReturn(scheduledAppointment);

        ScheduledAppointmentDto result=admissionService.createScheduledAppointment(scheduledAppointmentCreateDto);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,scheduledAppointmentCreateDto));
        Assertions.assertTrue(result.getLbzScheduler().equals("mojLbz"));
        Assertions.assertTrue(result.getId().equals(scheduledAppointmentId));
        Assertions.assertTrue(result.getAdmissionStatus().equals(AdmissionStatus.ZAKAZAN));
    }

    @Test
    public void getScheduledAppointmentsWithFilterTest(){

        long prescriptionId=4;
        long scheduledAppointmentCount=10;

        Prescription prescription=prescriptionGenerator.getPrescription();
        prescription.setId(prescriptionId);

        List<ScheduledAppointment> scheduledAppointments=new ArrayList<>();
        for(int i=0;i<scheduledAppointmentCount;i++){
            scheduledAppointments.add(scheduledAppointmentGenerator.getScheduledAppointment(prescription));
            scheduledAppointments.get(i).setId((long)i);
        }

        ScheduledAppointmentFilter f=scheduledAppointmentFilterGenerator.getRandomFilter();

        Page<ScheduledAppointment> page=new PageImpl<>(scheduledAppointments);

        Date nd=null;
        if(f.getEndDate()!=null)nd=new Date(f.getEndDate().getTime()+24*60*60*1000);
        given(scheduledAppointmentRepository.findScheduledAppointmentWithFilter(any(),eq(f.getLbp()),eq(f.getDepartmentId()),
                eq(f.getStartDate()),eq( nd ), eq(f.getAdmissionStatus()) ))
                .willReturn(page);

        Page<ScheduledAppointmentDto> result=admissionService.getScheduledAppointmentsWithFilter(f.getLbp(),
                f.getDepartmentId(),f.getStartDate(),f.getEndDate(),f.getAdmissionStatus(),0,(int)scheduledAppointmentCount);


        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),scheduledAppointments));
        for(int i=0;i<scheduledAppointmentCount;i++){
            Assertions.assertTrue(result.getContent().get(i).getPrescriptionId().equals(scheduledAppointments
                    .get(i).getPrescription().getId() ));
        }

    }

    @Test
    public void getScheduledAppointmentByPrescriptionIdTest(){

        long prescriptionId=4;
        long scheduledAppointmentId=2;

        Prescription prescription=prescriptionGenerator.getPrescription();
        prescription.setId(prescriptionId);

        ScheduledAppointment scheduledAppointment= scheduledAppointmentGenerator.getScheduledAppointment(prescription);
        scheduledAppointment.setId(scheduledAppointmentId);

        given(scheduledAppointmentRepository.findScheduledAppointmentByPrescriptionId(prescriptionId)).willReturn(
                scheduledAppointment);

        ScheduledAppointmentDto result=admissionService.getScheduledAppointmentByPrescriptionId(prescriptionId);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,scheduledAppointment));

    }

    @Test
    public void getPrescriptionsWithFilterTest(){

        int prescriptionCount=10;

        List<Prescription> prescriptions=new ArrayList<>();
        for(int i=0;i<prescriptionCount;i++){
            prescriptions.add(prescriptionGenerator.getPrescription());
            prescriptions.get(i).setId((long)i);
        }

        Page<Prescription> page= new PageImpl<>(prescriptions);

        PrescriptionFilter f=prescriptionFilterGenerator.getRandomFilter();
        given(prescriptionRepository.findPrescriptionWithFilter(any(), eq(f.getLbp()),eq(f.getDepartmentId()),
                eq(f.getPrescriptionStatus()) )).willReturn(page);

        Page<PrescriptionDto> result=admissionService.getPrescriptionsWithFilter(f.getLbp(),f.getDepartmentId(),
                f.getPrescriptionStatus(),0,prescriptionCount);

        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),prescriptions));
    }

    @Test
    public void setScheduledAppointmentStatusTest(){

        int scheduledAppointmentId=3;

        ScheduledAppointment scheduledAppointment=scheduledAppointmentGenerator.getScheduledAppointment(null);
        scheduledAppointment.setId((long)scheduledAppointmentId);

        given(scheduledAppointmentRepository.findScheduledAppointmentById((long)scheduledAppointmentId))
                .willReturn(scheduledAppointment);
        given(scheduledAppointmentRepository.save(scheduledAppointment)).willReturn(scheduledAppointment);

        admissionService.setScheduledAppointmentStatus((long)scheduledAppointmentId,AdmissionStatus.REALIZOVAN);

        ArgumentCaptor<ScheduledAppointment> arg=ArgumentCaptor.forClass(ScheduledAppointment.class);
        verify(scheduledAppointmentRepository).save(arg.capture());

        Assertions.assertTrue(arg.getValue().getAdmissionStatus()==AdmissionStatus.REALIZOVAN);
    }


}
