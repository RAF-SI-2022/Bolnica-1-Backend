package raf.bolnica1.laboratory.integration.labExamination.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.labWorkOrder.LabWorkOrderFilterGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.result.ResultUpdateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.ScheduledLabExaminationCreate;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.ScheduledLabExaminationCreateGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.filter.ScheduledLabExaminationDtoFilter;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.filter.ScheduledLabExaminationDtoFilterGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomDate;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomLong;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.integration.labExamination.LabExaminationIntegrationTestConfig;
import raf.bolnica1.laboratory.mappers.ScheduledLabExaminationMapper;
import raf.bolnica1.laboratory.repository.*;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.LabExaminationsService;
import raf.bolnica1.laboratory.services.lab.LabResultService;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.util.ExtractPageContentFromPageJson;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LabExaminationSteps extends LabExaminationIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private ScheduledLabExaminationDtoFilterGenerator scheduledLabExaminationDtoFilterGenerator;
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private RandomDate randomDate;
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;
    @Autowired
    private LabWorkOrderFilterGenerator labWorkOrderFilterGenerator;
    @Autowired
    private RandomString randomString;
    @Autowired
    private ResultUpdateDtoGenerator resultUpdateDtoGenerator;
    @Autowired
    private ScheduledLabExaminationCreateGenerator scheduledLabExaminationCreateGenerator;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private LabWorkOrderRepository labWorkOrderRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @Autowired
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;

    /// MAPPERS
    @Autowired
    private ScheduledLabExaminationMapper scheduledLabExaminationMapper;

    /// SERVICES
    @Autowired
    private LabResultService labResultService;
    @Autowired
    private LabWorkOrdersService labWorkOrdersService;
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;
    @Autowired
    private LabExaminationsService labExaminationsService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private TokenSetter tokenSetter;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("prescriptionRestTemplate")
    private RestTemplate prescriptionRestTemplate;
    @Autowired
    private ExtractPageContentFromPageJson extractPageContentFromPageJson;
    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Autowired
    @Qualifier("employeeRestTemplate")
    private RestTemplate employeeRestTemplate;

    /// CLASS DATA
    private List<ScheduledLabExaminationDto> scheduledLabExaminationDtos;
    private Long departmentId;


    private Long getDepartmentIdByLbz(){
        String lbz = authenticationUtils.getLbzFromAuthentication();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtTokenGetter.getDrMedSpec());
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Long> departmentId = employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class);
        return departmentId.getBody();
    }

    @Before
    public void prepare(){
        tokenSetter.setToken(jwtTokenGetter.getDrMedSpec());
        departmentId=getDepartmentIdByLbz();
    }


    @When("kreiranih {int} laboratorijskih pregleda")
    public void kreiranih_laboratorijskih_pregleda(Integer labExamCount) {
        try {

            scheduledLabExaminationDtos=new ArrayList<>();
            for(int i=0;i<labExamCount;i++){
                ScheduledLabExaminationCreate pom=scheduledLabExaminationCreateGenerator.getScheduledLabExamination();
                scheduledLabExaminationDtos.add(labExaminationsService.createScheduledExamination(pom.getLbp(),
                        pom.getScheduledDate(),pom.getNote(),"Bearer "+jwtTokenGetter.getDrMedSpec()));
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,scheduledLabExaminationDtos.get(i)));

                Assertions.assertTrue(scheduledLabExaminationDtos.get(i).getExaminationStatus()== ExaminationStatus.ZAKAZANO);
                Assertions.assertTrue(scheduledLabExaminationDtos.get(i).getDepartmentId().equals(departmentId));
                Assertions.assertTrue(scheduledLabExaminationDtos.get(i).getLbz().equals(authenticationUtils.getLbzFromAuthentication()));
            }

        }catch (Exception e){
           Assertions.fail(e);
        }
    }
    @Then("on se nalazi u bazi")
    public void on_se_nalazi_u_bazi() {
        try {

            Optional<ScheduledLabExamination> pom=scheduledLabExaminationRepository.findById(scheduledLabExaminationDtos.get(0).getId());
            Assertions.assertTrue(pom.isPresent());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom.get(),scheduledLabExaminationDtos.get(0)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @When("promenjen status laboratorijskog pregleda")
    public void promenjen_status_laboratorijskog_pregleda() {
        try {
            labExaminationsService.changeExaminationStatus(scheduledLabExaminationDtos.get(0).getId(),
                    ExaminationStatus.OTKAZANO);
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("status pregleda je promenjen i u bazi")
    public void status_pregleda_je_promenjen_i_u_bazi() {
        try {
            Assertions.assertTrue(scheduledLabExaminationRepository.findById(scheduledLabExaminationDtos.get(0).getId())
                    .get().getExaminationStatus().equals(ExaminationStatus.OTKAZANO));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("pronalazenje za odredjeni datum daje tacne rezultate")
    public void pronalazenje_za_odredjeni_datum_daje_tacne_rezultate() {
        try {
            Date queryDate=randomDate.getFromRandom();

            List<ScheduledLabExamination> scheduledLabExaminations=scheduledLabExaminationRepository.findAll();
            List<ScheduledLabExamination> result=new ArrayList<>();
            for(ScheduledLabExamination sle:scheduledLabExaminations){
                if(sle.getDepartmentId().equals(departmentId) && sle.getScheduledDate().getTime()==queryDate.getTime())
                    result.add(sle);
            }

            List<ScheduledLabExaminationDto>queryResult=labExaminationsService.listScheduledExaminationsByDay(queryDate,
                    "Bearer "+jwtTokenGetter.getDrMedSpec());

            List<ScheduledLabExamination>pom1=new ArrayList<>(result);
            List<ScheduledLabExaminationDto>pom2=new ArrayList<>(queryResult);
            pom1.sort(Comparator.comparing(ScheduledLabExamination::getId));
            pom2.sort(Comparator.comparing(ScheduledLabExaminationDto::getId));

            Assertions.assertTrue(pom1.size()==pom2.size());
            for(int i=0;i<pom1.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));


        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("pronalazenje daje tacne rezultate")
    public void pronalazenje_daje_tacne_rezultate() {
        try {

            List<ScheduledLabExamination> scheduledLabExaminations=scheduledLabExaminationRepository.findAll();
            List<ScheduledLabExamination> result=new ArrayList<>();
            for(ScheduledLabExamination sle:scheduledLabExaminations){
                if(sle.getDepartmentId().equals(departmentId))
                    result.add(sle);
            }

            List<ScheduledLabExaminationDto>queryResult=labExaminationsService.listScheduledExaminations(
                    "Bearer "+jwtTokenGetter.getDrMedSpec());

            List<ScheduledLabExamination>pom1=new ArrayList<>(result);
            List<ScheduledLabExaminationDto>pom2=new ArrayList<>(queryResult);
            pom1.sort(Comparator.comparing(ScheduledLabExamination::getId));
            pom2.sort(Comparator.comparing(ScheduledLabExaminationDto::getId));

            Assertions.assertTrue(pom1.size()==pom2.size());
            for(int i=0;i<pom1.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }




    private List<ScheduledLabExaminationDtoFilter> filters;
    @When("kreiranih {int} filtera za laboratorijski pregled")
    public void kreiranih_filtera_za_laboratorijski_pregled(Integer filterCount) {
        try {

            filters=new ArrayList<>();
            for(int i=0;i<filterCount;i++) {
                filters.add(scheduledLabExaminationDtoFilterGenerator.getRandomFilter());
                filters.get(i).setDepartmentId(getDepartmentIdByLbz());
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("pronalazenje za odredjeni lbp i raspon datuma daje tacne rezultate")
    public void pronalazenje_za_odredjeni_lbp_i_raspon_datuma_daje_tacne_rezultate() {
        try {

            List<ScheduledLabExaminationDto>list=scheduledLabExaminationMapper.toDto(scheduledLabExaminationRepository.findAll());

            for(int i=0;i<filters.size();i++){
                ScheduledLabExaminationDtoFilter f=filters.get(i);
                List<ScheduledLabExaminationDto>result=f.applyFilterToList(list);
                List<ScheduledLabExaminationDto> queried=labExaminationsService.listScheduledExaminationsByLbpAndDate(
                        f.getLbp(),f.getStartDate(),f.getEndDate(),"Bearer "+jwtTokenGetter.getDrMedSpec(),
                        0,1000000000).getContent();

                List<ScheduledLabExaminationDto>pom1=new ArrayList<>(result);
                List<ScheduledLabExaminationDto>pom2=new ArrayList<>(queried);
                pom1.sort(Comparator.comparing(ScheduledLabExaminationDto::getId));
                pom2.sort(Comparator.comparing(ScheduledLabExaminationDto::getId));

                System.out.println(f);
                Assertions.assertTrue(classJsonComparator.compareListCommonFields(pom1,pom2));

            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
