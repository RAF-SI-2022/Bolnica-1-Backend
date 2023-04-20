package raf.bolnica1.infirmary.integration.patientState.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.PatientStateDtoGenerator;
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

import java.util.List;

public class PatientStateIntegrationCreateSteps extends PatientStateIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PatientStateDtoGenerator patientStateDtoGenerator;
    @Autowired
    private HospitalizationGenerator hospitalizationGenerator;

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

    /// CLASS DATA
    private PatientStateDto patientStateDto;


    @When("stanje zabelezeno")
    public void stanje_zabelezeno() {

        try{

            String token=jwtTokenGetter.getDefaultToken();
            String lbz=tokenSetter.setToken(token);

            Hospitalization hospitalization=hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                    hospitalRoomRepository,prescriptionRepository,null,null);
            PatientStateCreateDto patientStateCreateDto=patientStateDtoGenerator.getPatientStateCreateDto(hospitalization.getId());
            patientStateDto=patientStateService.createPatientState(patientStateCreateDto);
            Assertions.assertNotNull(patientStateDto);
            Assertions.assertTrue(lbz.equals(patientStateDto.getLbz()));
            Assertions.assertTrue(classJsonComparator.compareCommonFields(patientStateCreateDto,patientStateDto));
        }
        catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("stanje se nalazi u bazi")
    public void stanje_se_nalazi_u_bazi() {

        try{
            List<PatientStateDto> patientStateDtos=patientStateMapper.toDto(patientStateRepository.findAll());
            boolean flag=false;
            for(PatientStateDto patientStateDto1:patientStateDtos)
                if(classJsonComparator.compareCommonFields(patientStateDto1,patientStateDto))
                    flag=true;
            Assertions.assertTrue(flag);
        }
        catch(Exception e){
            Assertions.fail(e);
        }

    }

}
