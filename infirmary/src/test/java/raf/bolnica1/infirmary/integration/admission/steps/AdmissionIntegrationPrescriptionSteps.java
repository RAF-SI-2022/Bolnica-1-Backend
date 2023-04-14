package raf.bolnica1.infirmary.integration.admission.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.filter.PrescriptionFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.filter.PrescriptionFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.ScheduledAppointmentCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter.ScheduledAppointmentFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.integration.admission.AdmissionIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.mapper.ScheduledAppointmentMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.services.AdmissionService;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.HospitalizationService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

public class AdmissionIntegrationPrescriptionSteps extends AdmissionIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PrescriptionFilterGenerator prescriptionFilterGenerator;
    @Autowired
    private ScheduledAppointmentFilterGenerator scheduledAppointmentFilterGenerator;
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
    private PrescriptionMapper prescriptionMapper;
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
    private List<PrescriptionDto> result;
    private List<PrescriptionFilter> filters;



    @When("ubacimo {int} uputa i probamo {int} filtera")
    public void ubacimo_uputa_i_probamo_filtera(Integer prescriptionCount, Integer filterCount) {
        try{

            filters=new ArrayList<>();
            for(int i=0;i<filterCount;i++)
                filters.add(prescriptionFilterGenerator.getRandomFilter());

            result=prescriptionMapper.toDto(prescriptionRepository.findAll());

            for(int i=0;i<prescriptionCount;i++)
                result.add(prescriptionMapper.toDto(prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("dobicema tacan rezultat za svaki filter uputa")
    public void dobicema_tacan_rezultat_za_svaki_filter_uputa() {
        try{

            for(int i=0;i< filters.size();i++){
                PrescriptionFilter f=filters.get(i);
                List<PrescriptionDto> pom=f.applyFilterToList(result);
                List<PrescriptionDto> queried=admissionService.getPrescriptionsWithFilter(f.getLbp(),
                        f.getDepartmentId(),f.getPrescriptionStatus(),0,1000000000).getContent();
                List<PrescriptionDto> p1=new ArrayList<>(queried);
                List<PrescriptionDto> p2=new ArrayList<>(pom);
                p1.sort((o1,o2)->o1.getId().compareTo(o2.getId()) );
                p2.sort((o1,o2)->o1.getId().compareTo(o2.getId()) );

                System.out.println(f);
                Assertions.assertTrue(classJsonComparator.compareListCommonFields(p1,p2));
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
