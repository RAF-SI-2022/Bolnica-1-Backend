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
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.ScheduledAppointmentCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter.ScheduledAppointmentFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter.ScheduledAppointmentFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
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

import java.util.ArrayList;
import java.util.List;

public class AdmissionIntegrationScheduledAppointmentFilterSteps extends AdmissionIntegrationTestConfig {

    /// GENERATORS
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


    @When("zatrazimo termin po ID uputa")
    public void zatrazimo_termin_po_id_uputa() {
        try{

            /// postavljen token
            String token=jwtTokenGetter.getDrMedSpec();
            tokenSetter.setToken(token);

            prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);
            ScheduledAppointmentCreateDto scheduledAppointmentCreateDto=scheduledAppointmentCreateDtoGenerator
                    .getScheduledAppointmentCreateDto(prescription.getId());
            scheduledAppointmentDto=admissionService.createScheduledAppointment(scheduledAppointmentCreateDto);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("dobili smo odgovarajuci termin sa tim ID uputa")
    public void dobili_smo_odgovarajuci_termin_sa_tim_id_uputa() {
        try{

            ScheduledAppointmentDto s=admissionService.getScheduledAppointmentByPrescriptionId(prescription.getId());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(s,scheduledAppointmentDto));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    /// CLAS DATA
    private List<ScheduledAppointmentDto> result;
    private List<ScheduledAppointmentFilter> filters;

    @When("ubacimo {int} termina i probamo {int} filtera")
    public void ubacimo_termina_i_probamo_filtera(Integer scheduledAppointmentCount, Integer filterCount) {
        try{
            /// postavljen token
            String token=jwtTokenGetter.getDrMedSpec();
            tokenSetter.setToken(token);

            filters=new ArrayList<>();
            for(int i=0;i<filterCount;i++)
                filters.add(scheduledAppointmentFilterGenerator.getRandomFilter());

            result=scheduledAppointmentMapper.toDto(scheduledAppointmentRepository.findAll());

            for(int i=0;i<scheduledAppointmentCount;i++){
                prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);
                ScheduledAppointmentCreateDto scheduledAppointmentCreateDto=scheduledAppointmentCreateDtoGenerator
                        .getScheduledAppointmentCreateDto(prescription.getId());
                scheduledAppointmentDto=admissionService.createScheduledAppointment(scheduledAppointmentCreateDto);
                Assertions.assertTrue(classJsonComparator.compareCommonFields(scheduledAppointmentDto,scheduledAppointmentCreateDto));
                result.add(scheduledAppointmentDto);
            }


        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("dobicemo tacan rezultat za svaki filter termina")
    public void dobicemo_tacan_rezultat_za_svaki_filter_termina() {
        try{

            for(int i=0;i< filters.size();i++){
                ScheduledAppointmentFilter f=filters.get(i);
                List<ScheduledAppointmentDto>pom=f.applyFilterToList(result,prescriptionRepository);
                List<ScheduledAppointmentDto>queried=admissionService.getScheduledAppointmentsWithFilter(f.getLbp(),
                        f.getDepartmentId(),f.getStartDate(),f.getEndDate(),f.getAdmissionStatus(),0,1000000000)
                        .getContent();
                List<ScheduledAppointmentDto> p1=new ArrayList<>(queried);
                List<ScheduledAppointmentDto> p2=new ArrayList<>(pom);
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
