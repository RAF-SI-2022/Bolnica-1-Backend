package raf.bolnica1.infirmary.integration.patientState.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PatientStateGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.PatientStateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.filter.PatientStateFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.filter.PatientStateFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.integration.patientState.PatientStateIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.PatientStateMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PatientStateRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.PatientStateService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

public class PatientStateIntegrationFilterSteps extends PatientStateIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PatientStateDtoGenerator patientStateDtoGenerator;
    @Autowired
    private HospitalizationGenerator hospitalizationGenerator;
    @Autowired
    private PatientStateFilterGenerator patientStateFilterGenerator;

    /// SERVICES
    @Autowired
    private PatientStateService patientStateService;

    /// REPOSITORIES
    @Autowired
    private HospitalizationRepository hospitalizationRepository;
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PatientStateRepository patientStateRepository;

    /// MAPPERS
    @Autowired
    private PatientStateMapper patientStateMapper;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private TokenSetter tokenSetter;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private List<PatientStateDto> result=new ArrayList<>();
    private List<PatientStateFilter> patientStateFilters=new ArrayList<>();


    @Given("ima {int} zabeleznih stanja za pacijenta i {int} zeljenih filtera")
    public void ima_zabeleznih_stanja_za_pacijenta_i_zeljenih_filtera(Integer stateCount, Integer filterCount) {

        String token=jwtTokenGetter.getDefaultToken();
        String lbz=tokenSetter.setToken(token);

        Hospitalization hospitalization= hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                hospitalRoomRepository,prescriptionRepository,null,null);

        for(int i=0;i<filterCount;i++)
            patientStateFilters.add(patientStateFilterGenerator.getRandomFilter());

        result=patientStateMapper.toDto(patientStateRepository.findAll());

        for(int i=0;i<stateCount;i++){
            PatientStateCreateDto patientStateCreateDto= patientStateDtoGenerator.getPatientStateCreateDto(hospitalization.getId());
            PatientStateDto pom=patientStateService.createPatientState(patientStateCreateDto);
            result.add(pom);
            Assertions.assertNotNull(pom);
            Assertions.assertTrue(classJsonComparator.compareCommonFields(patientStateCreateDto,pom));
        }


    }
    @Then("filtriranje filtrira korektno")
    public void filtriranje_filtrira_korektno() {

        try{

            for(int i=0;i<patientStateFilters.size();i++){

                PatientStateFilter f=patientStateFilters.get(i);
                List<PatientStateDto> pom=f.applyFilterToList(result);
                List<PatientStateDto> patientStateDtos=patientStateService.getPatientStateByDate(f.getHospitalizationId(),
                        f.getStartDate(),f.getEndDate(),0,1000000000).getContent();
                List<PatientStateDto> p1=new ArrayList<>(patientStateDtos);
                List<PatientStateDto> p2=new ArrayList<>(pom);
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
