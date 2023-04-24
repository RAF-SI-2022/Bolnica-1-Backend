package raf.bolnica1.laboratory.integration.analysisParameter.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.integration.analysisParameter.AnalysisParameterIntegrationTestConfig;
import raf.bolnica1.laboratory.mappers.AnalysisParameterMapper;
import raf.bolnica1.laboratory.mappers.ParameterMapper;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.ParameterRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.AnalysisParameterService;
import raf.bolnica1.laboratory.services.LabAnalysisService;
import raf.bolnica1.laboratory.services.ParameterService;
import raf.bolnica1.laboratory.util.ExtractPageContentFromPageJson;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnalysisParameterSteps extends AnalysisParameterIntegrationTestConfig {

    /// MAPPERS
    @Autowired
    private ParameterMapper parameterMapper;
    @Autowired
    private AnalysisParameterMapper analysisParameterMapper;

    /// SERVICES
    @Autowired
    private AnalysisParameterService analysisParameterService;
    @Autowired
    private LabAnalysisService labAnalysisService;
    @Autowired
    private ParameterService parameterService;

    /// REPOSITORIES
    @Autowired
    private ParameterRepository parameterRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;

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


    /// CLASS DATA
    private List<ParameterDto> parameterDtoList;
    private LabAnalysisDto labAnalysisDto;
    private List<AnalysisParameterDto>analysisParameterDtos;



    @When("kreirano {int} AnalysisParameter")
    public void kreirano_analysis_parameter(Integer apCount) {
        try{
            labAnalysisDto=labAnalysisService.getLabAnalysis(1L);
            List<ParameterDto>list=parameterMapper.toDto(parameterRepository.findAll());

            apCount=Math.min(apCount,list.size());
            parameterDtoList=new ArrayList<>();
            for(int i=0;i<apCount;i++)
                parameterDtoList.add(list.get(i));


            analysisParameterDtos=new ArrayList<>();
            for(int i=0;i<parameterDtoList.size();i++){
                AnalysisParameterDto pom=new AnalysisParameterDto();
                pom.setParameter(parameterDtoList.get(i));
                pom.setLabAnalysis(labAnalysisDto);
                AnalysisParameterDto pom2=analysisParameterService.createAnalysisParameter(pom);
                pom.setId(pom2.getId());
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom2,pom));
                analysisParameterDtos.add(pom2);
            }


        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("nalazi se u bazi")
    public void nalazi_se_u_bazi() {
        try{

            List<AnalysisParameterDto> list=analysisParameterMapper.toDto(analysisParameterRepository.findAll());

            for(AnalysisParameterDto p1:analysisParameterDtos){
                boolean flag=false;
                for(AnalysisParameterDto p2:list)
                    if(classJsonComparator.compareCommonFields(p1,p2))
                        flag=true;
                Assertions.assertTrue(flag);
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @Then("azuriranje menja i u bazi")
    public void azuriranje_menja_i_u_bazi() {
        try{

            AnalysisParameterDto pom=analysisParameterService.updateAnalysisParameter(analysisParameterDtos.get(0));
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,analysisParameterDtos.get(0)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @When("obrisan taj AnalysisParameter")
    public void obrisan_taj_analysis_parameter() {
        try{

            analysisParameterService.deleteAnalysisParameter(analysisParameterDtos.get(0).getId());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("ne nalazi se u bazi")
    public void ne_nalazi_se_u_bazi() {
        try{

            List<AnalysisParameterDto> list=analysisParameterMapper.toDto(analysisParameterRepository.findAll());

            boolean flag=true;
            for(AnalysisParameterDto p2:list)
                if(classJsonComparator.compareCommonFields(analysisParameterDtos.get(0),p2))
                    flag=false;
            Assertions.assertTrue(flag);


        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @Then("dohvatanje po njegovom ID daje njega")
    public void dohvatanje_po_njegovom_id_daje_njega() {
        try{

            Assertions.assertTrue(classJsonComparator.compareCommonFields(analysisParameterDtos.get(0),
                    analysisParameterService.getAnalysisParameter(analysisParameterDtos.get(0).getId())));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @Then("dohvatanje Parameter po Analysis je ispravno")
    public void dohvatanje_parameter_po_analysis_je_ispravno() {
        try{

            List<AnalysisParameterDto> list=analysisParameterMapper.toDto(analysisParameterRepository.findAll());
            List<ParameterDto>result=new ArrayList<>();
            for(AnalysisParameterDto apd:list)
                if(apd.getLabAnalysis().getId().equals(analysisParameterDtos.get(0).getLabAnalysis().getId()))
                    result.add(apd.getParameter());

            List<ParameterDto>queried=analysisParameterService.getParametersByAnalysisId(analysisParameterDtos.get(0)
                    .getLabAnalysis().getId(),0,1000000000).getContent();

            List<ParameterDto>pom1=new ArrayList<>(result);
            List<ParameterDto>pom2=new ArrayList<>(queried);
            pom1.sort(Comparator.comparing(ParameterDto::getId));
            pom2.sort(Comparator.comparing(ParameterDto::getId));

            Assertions.assertTrue(pom1.size()==pom2.size());
            for(int i=0;i<pom1.size();i++) {
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i), pom2.get(i)));
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }


}
