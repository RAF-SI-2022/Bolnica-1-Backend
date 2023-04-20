package raf.bolnica1.infirmary.integration.admission.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter.HospitalRoomFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.HospitalizationCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.integration.admission.AdmissionIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.services.AdmissionService;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.HospitalizationService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.List;

public class AdmissionIntegrationHospitalizationSteps extends AdmissionIntegrationTestConfig {

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

    /// SERVICES
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private HospitalRoomService hospitalRoomService;
    @Autowired
    private HospitalizationService hospitalizationService;

    /// REPOSITORIES
    @Autowired
    private HospitalizationRepository hospitalizationRepository;
    @Autowired
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;

    /// MAPPERS
    @Autowired
    private HospitalizationMapper hospitalizationMapper;



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
    private HospitalizationDto hospitalizationDto;
    private Integer previousOccupancy;


    @When("otvorimo hospitalizaciju za pacijenta")
    public void otvorimo_hospitalizaciju_za_pacijent() {

        try{

            /// postavljen token
            String token=jwtTokenGetter.getDrMedSpec();
            tokenSetter.setToken(token);


            /// napravljena soba i uput
            Prescription prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);
            HospitalRoom hospitalRoom=hospitalRoomGenerator.getHospitalRoomWithDBSave(hospitalRoomRepository);
            previousOccupancy=hospitalRoom.getOccupancy();


            HospitalizationCreateDto hospitalizationCreateDto=hospitalizationCreateDtoGenerator.getHospitalizationCreateDto(
                    hospitalRoom.getId(), prescription.getId());

            hospitalizationDto=admissionService.createHospitalization(hospitalizationCreateDto,"Bearer "+token);

            Assertions.assertTrue(classJsonComparator.compareCommonFields(hospitalizationCreateDto,hospitalizationDto));

        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("hospitalizacija tog pacijenta se nalazi u bazi i uput i termin su obelezeni kao zakazani i zauzeto je mesto u sobi")
    public void hospitalizacija_tog_pacijenta_se_nalazi_u_bazi_i_uput_i_termin_su_obelezeni_kao_zakazani_i_zauzeto_je_mesto_u_sobi() {
        try{

            /// da li se nalazi u bazi
            List<HospitalizationDto> list=hospitalizationMapper.toDto(hospitalizationRepository.findAll());
            boolean flag=false;
            for(HospitalizationDto hd:list)
                if(classJsonComparator.compareCommonFields(hd,hospitalizationDto))
                    flag=true;

            Assertions.assertTrue(flag);


            Prescription prescription=prescriptionRepository.findPrescriptionById(hospitalizationDto.getPrescriptionId());
            Assertions.assertTrue(prescription.getStatus().equals(PrescriptionStatus.REALIZOVAN));

            ScheduledAppointment scheduledAppointment=scheduledAppointmentRepository.findScheduledAppointmentByPrescriptionId(
                    prescription.getId());
            if(scheduledAppointment!=null)
                Assertions.assertTrue(scheduledAppointment.getAdmissionStatus().equals(AdmissionStatus.REALIZOVAN));

            HospitalRoom hospitalRoom=hospitalRoomRepository.findHospitalRoomById(hospitalizationDto.getHospitalRoomId());
            Assertions.assertTrue(hospitalRoom.getOccupancy()==previousOccupancy+1);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
