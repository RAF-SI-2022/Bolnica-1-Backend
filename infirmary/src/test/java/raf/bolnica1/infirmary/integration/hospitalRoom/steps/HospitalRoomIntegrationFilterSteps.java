package raf.bolnica1.infirmary.integration.hospitalRoom.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter.HospitalRoomFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter.HospitalRoomFilterGenerator;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.integration.hospitalRoom.HospitalRoomIntegrationTestConfig;
import org.junit.jupiter.api.Assertions;
import raf.bolnica1.infirmary.mapper.HospitalRoomMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

public class HospitalRoomIntegrationFilterSteps extends HospitalRoomIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private HospitalRoomCreateDtoGenerator hospitalRoomCreateDtoGenerator;
    @Autowired
    private HospitalRoomFilterGenerator hospitalRoomFilterGenerator;

    /// SERVICES
    @Autowired
    private HospitalRoomService hospitalRoomService;

    /// REPOSITORIES
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;

    /// MAPPERS
    @Autowired
    private HospitalRoomMapper hospitalRoomMapper;


    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;


    /// CLASS DATA
    private List<HospitalRoomFilter> hospitalRoomFilters;
    private List<HospitalRoomDto> result;


    @When("{int} bolnickih soba napravljeno i {int} ID departmana izabrano")
    public void bolnickih_soba_napravljeno_i_id_departmana_izabrano(Integer hospitalRoomCount, Integer filterCount) {

        try{

            result=hospitalRoomMapper.toDto(hospitalRoomRepository.findAll());

            hospitalRoomFilters=new ArrayList<>();
            for(int i=0;i<filterCount;i++)
                hospitalRoomFilters.add(hospitalRoomFilterGenerator.getRandomFilter());

            for(int i=0;i<hospitalRoomCount;i++){
                HospitalRoomDto hospitalRoomDto=hospitalRoomService.createHospitalRoom(hospitalRoomCreateDtoGenerator.getHospitalRoomCreateDto());
                result.add(hospitalRoomDto);
            }

        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("filtriranje po svakom od izabranih ID departmana daje korektne bolnicke sobe")
    public void filtriranje_po_svakom_od_izabranih_id_departmana_daje_korektne_bolnicke_sobe() {

        try{

            for(int i=0;i< hospitalRoomFilters.size();i++){
                HospitalRoomFilter f=hospitalRoomFilters.get(i);
                List<HospitalRoomDto> pom=f.applyFilterToList(result);
                List<HospitalRoomDto> hospitalRoomDtos=hospitalRoomService.getHospitalRoomsByDepartmentId(f.getDepartmentId(),
                        0,1000000000).getContent();
                List<HospitalRoomDto> p1=new ArrayList<>(hospitalRoomDtos);
                List<HospitalRoomDto> p2=new ArrayList<>(pom);
                p1.sort((o1,o2)->o1.getId().compareTo(o2.getId()) );
                p2.sort((o1,o2)->o1.getId().compareTo(o2.getId()) );

                System.out.println(f.getDepartmentId());
                Assertions.assertTrue(classJsonComparator.compareListCommonFields(p1,p2));
            }

        }catch (Exception e){
            Assertions.fail(e);
        }

    }



}
