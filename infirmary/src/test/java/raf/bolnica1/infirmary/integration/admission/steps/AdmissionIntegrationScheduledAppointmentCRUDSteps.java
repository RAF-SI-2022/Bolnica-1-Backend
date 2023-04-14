package raf.bolnica1.infirmary.integration.admission.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter.HospitalRoomFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.HospitalizationCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.ScheduledAppointmentCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.integration.admission.AdmissionIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.mapper.ScheduledAppointmentMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.services.AdmissionService;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.HospitalizationService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.List;

public class AdmissionIntegrationScheduledAppointmentCRUDSteps extends AdmissionIntegrationTestConfig {
    /// GENERATORS
    @Autowired
    private HospitalizationCreateDtoGenerator hospitalizationCreateDtoGenerator;
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private HospitalRoomCreateDtoGenerator hospitalRoomCreateDtoGenerator;
    @Autowired
    private HospitalRoomFilterGenerator hospitalRoomFilterGenerator;
    @Autowired
    private HospitalizationFilterGenerator hospitalizationFilterGenerator;
    @Autowired
    private PrescriptionGenerator prescriptionGenerator;
    @Autowired
    private HospitalRoomGenerator hospitalRoomGenerator;
    @Autowired
    private HospitalizationGenerator hospitalizationGenerator;
    @Autowired
    private ScheduledAppointmentCreateDtoGenerator scheduledAppointmentCreateDtoGenerator;

    /// SERVICES
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private HospitalRoomService hospitalRoomService;
    @Autowired
    private HospitalizationService hospitalizationService;

    /// REPOSITORIES
    @Autowired
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    @Autowired
    private HospitalizationRepository hospitalizationRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;

    /// MAPPERS
    @Autowired
    private HospitalizationMapper hospitalizationMapper;
    @Autowired
    private ScheduledAppointmentMapper scheduledAppointmentMapper;


    /// UTILS
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private TokenSetter tokenSetter;
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private Prescription prescription;
    private ScheduledAppointmentDto scheduledAppointmentDto;


    @Given("imamo uput u bazi sacuvan")
    public void imamo_uput_u_bazi_sacuvan() {
        try{
            /// postavljen token
            String token=jwtTokenGetter.getDrMedSpec();
            tokenSetter.setToken(token);

            prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @When("termin zakazan za taj uput")
    public void termin_zakazan_za_taj_uput() {
        try{

            ScheduledAppointmentCreateDto scheduledAppointmentCreateDto=scheduledAppointmentCreateDtoGenerator
                    .getScheduledAppointmentCreateDto(prescription.getId());

            scheduledAppointmentDto=admissionService.createScheduledAppointment(scheduledAppointmentCreateDto);

            Assertions.assertTrue(classJsonComparator.compareCommonFields(scheduledAppointmentCreateDto,scheduledAppointmentDto));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("termin se nalazi u bazi")
    public void termin_se_nalazi_u_bazi() {
        try{

            List<ScheduledAppointmentDto> list=scheduledAppointmentMapper.toDto(scheduledAppointmentRepository.findAll());

            boolean flag=false;
            for(ScheduledAppointmentDto s:list)
                if(classJsonComparator.compareCommonFields(s,scheduledAppointmentDto))
                    flag=true;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("promenimo status termina")
    public void promenimo_status_termina() {
        try{

            /// postavljen token
            String token=jwtTokenGetter.getDrMedSpec();
            tokenSetter.setToken(token);

            prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);
            ScheduledAppointmentCreateDto scheduledAppointmentCreateDto=scheduledAppointmentCreateDtoGenerator
                    .getScheduledAppointmentCreateDto(prescription.getId());
            scheduledAppointmentDto=admissionService.createScheduledAppointment(scheduledAppointmentCreateDto);

            admissionService.setScheduledAppointmentStatus(scheduledAppointmentDto.getId(), AdmissionStatus.REALIZOVAN);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("u bazi se promenio status termina")
    public void u_bazi_se_promenio_status_termina() {
        try{
            ScheduledAppointment s= scheduledAppointmentRepository.findScheduledAppointmentById(scheduledAppointmentDto.getId());
            Assertions.assertTrue(s.getAdmissionStatus().equals(AdmissionStatus.REALIZOVAN));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
