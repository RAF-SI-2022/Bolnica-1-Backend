package raf.bolnica1.laboratory.integration.parameter.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.parameter.ParameterDtoGenerator;
import raf.bolnica1.laboratory.domain.lab.Parameter;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.integration.parameter.ParameterIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.ParameterRepository;
import raf.bolnica1.laboratory.services.lab.ParameterService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.List;

public class ParameterCRUDSteps extends ParameterIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private ParameterDtoGenerator parameterDtoGenerator;

    /// REPOSITORIES
    @Autowired
    private ParameterRepository parameterRepository;

    /// SERVICES
    @Autowired
    private ParameterService parameterService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private ParameterDto parameterDto;
    private ParameterDto parameterDto2;



    @When("kreiramo parametar")
    public void kreiramo_parametar() {
        try {
            parameterDto=parameterDtoGenerator.getParameterDto();
            ParameterDto pom=parameterService.createParameter(parameterDto);
            parameterDto.setId(pom.getId());
            /*System.out.println(objectMapper.writeValueAsString(parameterDto));
            System.out.println(objectMapper.writeValueAsString(pom));*/
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,parameterDto));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("on se nalazi u bazi")
    public void on_se_nalazi_u_bazi() {
        try {

            List<Parameter> parameters=parameterRepository.findAll();

            boolean flag=false;
            for(Parameter p:parameters)
                if(classJsonComparator.compareCommonFields(p,parameterDto))
                    flag=true;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("azuriramo parametar")
    public void azuriramo_parametar() {
        try {

            parameterDto2=parameterDtoGenerator.getParameterDto();
            parameterDto2.setId(parameterDto.getId());
            ParameterDto pom=parameterService.updateParameter(parameterDto2);
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,parameterDto2));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("parametar je u bazi azuriran")
    public void parametar_je_u_bazi_azuriran() {
        try {

            Assertions.assertTrue(classJsonComparator.compareCommonFields(parameterDto2,
                    parameterService.getParameter(parameterDto2.getId())));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("obrisemo parametar")
    public void obrisemo_parametar() {
        try {

            parameterService.deleteParameter(parameterDto.getId());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("parametar se ne nalazi u bazi")
    public void parametar_se_ne_nalazi_u_bazi() {
        try {

            List<Parameter> parameters=parameterRepository.findAll();

            boolean flag=true;
            for(Parameter p:parameters)
                if(classJsonComparator.compareCommonFields(p,parameterDto))
                    flag=false;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("dobavljanje parametar nam daje odgovarajuci parametar")
    public void dobavljanje_parametar_nam_daje_odgovarajuci_parametar() {
        try {

            ParameterDto pom=parameterService.getParameter(parameterDto.getId());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,parameterDto));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
