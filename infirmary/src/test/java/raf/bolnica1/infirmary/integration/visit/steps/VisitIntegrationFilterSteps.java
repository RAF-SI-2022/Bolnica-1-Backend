package raf.bolnica1.infirmary.integration.visit.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.VisitCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter.VisitFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.integration.visit.VisitIntegrationTestConfig;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter.VisitFilter;
import raf.bolnica1.infirmary.mapper.VisitMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.VisitRepository;
import raf.bolnica1.infirmary.services.VisitService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisitIntegrationFilterSteps extends VisitIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private VisitCreateDtoGenerator visitCreateDtoGenerator;
    @Autowired
    private HospitalizationGenerator hospitalizationGenerator;
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private VisitFilterGenerator visitFilterGenerator;


    /// SERVICES
    @Autowired
    private VisitService visitService;


    /// REPOSITORIES
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;
    @Autowired
    private HospitalizationRepository hospitalizationRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private VisitRepository visitRepository;


    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private TokenSetter tokenSetter;


    /// MAPPERS
    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private ObjectMapper objectMapper;


    /// ATTRIBUTES
    private List<VisitFilter> filters=new ArrayList<>();
    private List<VisitDto> results=new ArrayList<>();


    @Given("izabrali smo {int} filtera {int} poseta zabelezeno")
    public void izabrali_smo_filtera_poseta_zabelezeno(Integer filterCount, Integer visitCount) {


        /// priprema filtera
        for(int i=0;i<filterCount;i++)
            filters.add(visitFilterGenerator.getRandomFilter());

        results=visitMapper.toDto(visitRepository.findAll());


        /// priprema podataka
        String token=jwtTokenGetter.getDefaultToken();
        String lbz=tokenSetter.setToken(token);

        int hospitalCount=3;
        List<Hospitalization> hospitalizations=new ArrayList<>();
        for(int i=0;i<hospitalCount;i++)
            hospitalizations.add(hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                    hospitalRoomRepository,prescriptionRepository,null,null));


        for(int i=0;i<visitCount;i++){
            Long hospitalizationId=hospitalizations.get(randomLong.getLong(new Long(hospitalizations.size()) ).intValue()).getId();
            VisitCreateDto dto=visitCreateDtoGenerator.getVisitCreateDto(hospitalizationId);
            VisitDto realDto=visitService.createVisit(dto);

            assertNotNull(realDto);
            assertTrue(classJsonComparator.compareCommonFields(realDto,dto));
            results.add(realDto);
        }

    }
    @Then("filterisanje po parametrima daje zeljene rezultate")
    public void filterisanje_po_parametrima_daje_zeljene_rezultate() {

        try {
            for(int i=0;i<filters.size();i++){
                VisitFilter f=filters.get(i);
                List<VisitDto> pom=f.applyFilterToList(results,hospitalizationRepository);
                List<VisitDto> visitDtos=visitService.getVisitsWithFilter(f.getDepartmentId(),f.getHospitalRoomId(),
                        f.getHospitalizationId(),f.getStartDate(),f.getEndDate(),0,1000000000).getContent();
                List<VisitDto> mutableVisitDtos=new ArrayList<>(visitDtos);
                List<VisitDto> mutableResults=new ArrayList<>(pom);
                mutableVisitDtos.sort( (o1, o2) -> o1.getId().compareTo(o2.getId()));
                mutableResults.sort( (o1, o2) -> o1.getId().compareTo(o2.getId()));


                System.out.println(f.toString());
                assertTrue(classJsonComparator.compareListCommonFields(mutableResults,mutableVisitDtos));
            }
        }
        catch (Exception e){
            Assertions.fail(e.toString());
        }

    }


}
