package raf.bolnica1.infirmary.integration.dischargeList.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.dischargeList.DischargeListDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter.HospitalRoomFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.integration.dischargeList.DischargeListIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.DischargeListService;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.HospitalizationService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.List;


public class DischargeListIntegrationSteps extends DischargeListIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private DischargeListDtoGenerator dischargeListDtoGenerator;
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
    private HospitalRoomService hospitalRoomService;
    @Autowired
    private HospitalizationService hospitalizationService;
    @Autowired
    private DischargeListService dischargeListService;

    /// REPOSITORIES
    @Autowired
    private DischargeListRepository dischargeListRepository;
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
    private DischargeListMapper dischargeListMapper;



    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private DischargeListDto dischargeListDto;



    @When("napravili smo otpusnu listu i smestili smo je u bazu")
    public void napravili_smo_otpusnu_listu_i_smestili_smo_je_u_bazu() {
        try{

            Hospitalization hospitalization=hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                    hospitalRoomRepository,prescriptionRepository,null,null);

            dischargeListDto=dischargeListDtoGenerator.getDischargeListDto(hospitalization.getId());

            CreateDischargeListDto createDischargeListDto=dischargeListMapper.toDto(dischargeListDto);
            DischargeListDto pom=dischargeListService.createDischargeList(createDischargeListDto);

            dischargeListDto.setId(pom.getId());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,dischargeListDto));
            dischargeListDto=pom;

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("ona se nalazi u bazi otpusnih listi")
    public void ona_se_nalazi_u_bazi_otpusnih_listi() {
        try{

            List<DischargeListDto> list=dischargeListMapper.toDto(dischargeListRepository.findAll());

            boolean flag=false;
            for(DischargeListDto dto:list)
                if(classJsonComparator.compareCommonFields(dischargeListDto,dto))
                    flag=true;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("pretraga po njenom ID hospitalizacije je pronalazi")
    public void pretraga_po_njenom_id_hospitalizacije_je_pronalazi() {

        try{
            DischargeListDto dto=dischargeListService.getDischargeListWithFilter(dischargeListDto.getHospitalizationId(),
                    null,null,null,0,1000000000).getContent().get(0);
            Assertions.assertTrue(classJsonComparator.compareCommonFields(dischargeListDto,dto));
        }catch (Exception e){
            Assertions.fail(e);
        }

    }

}
