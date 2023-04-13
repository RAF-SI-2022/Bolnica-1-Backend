package raf.bolnica1.infirmary.integration.hospitalRoom.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.integration.hospitalRoom.HospitalRoomIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.HospitalRoomMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class HospitalRoomIntegrationCrudSteps extends HospitalRoomIntegrationTestConfig {


    /// GENERATORS
    @Autowired
    private HospitalRoomCreateDtoGenerator hospitalRoomCreateDtoGenerator;

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
    private HospitalRoomDto hospitalRoomDto;


    @When("bolnicka soba napravljena")
    public void bolnicka_soba_napravljena() {

        try{
            hospitalRoomDto=hospitalRoomService.createHospitalRoom(hospitalRoomCreateDtoGenerator.getHospitalRoomCreateDto());
            assertTrue(hospitalRoomDto.getOccupancy()==0);
        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("bolnicka soba se nalazi u bazi bolnickih soba")
    public void bolnicka_soba_se_nalazi_u_bazi_bolnickih_soba() {

        try{

            List<HospitalRoomDto> hospitalRoomDtos=hospitalRoomMapper.toDto(hospitalRoomRepository.findAll());
            boolean flag=false;
            for(HospitalRoomDto dto:hospitalRoomDtos)
                if(classJsonComparator.compareCommonFields(dto,hospitalRoomDto))
                    flag=true;

            assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }

    }



    @When("ista ta bolnicka soba obrisana")
    public void ista_ta_bolnicka_soba_obrisana() {
        try{
            hospitalRoomService.deleteHospitalRoom(hospitalRoomDto.getId());
        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("te bolnicke sobe nema u bazi bolnickih soba")
    public void te_bolnicke_sobe_nema_u_bazi_bolnickih_soba() {
        try{
            List<HospitalRoomDto> hospitalRoomDtos=hospitalRoomMapper.toDto(hospitalRoomRepository.findAll());
            boolean flag=false;
            for(HospitalRoomDto dto:hospitalRoomDtos)
                if(classJsonComparator.compareCommonFields(dto,hospitalRoomDto))
                    flag=true;

            assertTrue(!flag);
        }catch (Exception e){
            Assertions.fail(e);
        }

    }



    @Then("pretraga po njenom ID pronalazi tu sobu")
    public void pretraga_po_njenom_id_pronalazi_tu_sobu() {
        try{
            HospitalRoomDto dto=hospitalRoomService.getHospitalRoomById(hospitalRoomDto.getId());
            assertTrue(classJsonComparator.compareCommonFields(dto,hospitalRoomDto));
        }catch (Exception e){
            Assertions.fail(e);
        }

    }

}
