package cucumber.patientservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.services.PatientService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTestsSteps extends PatientServiceTestsConfig {

    @Autowired
    PatientService patientService;

    @When("Kada se kreira novi raspored")
    public void kada_se_kreira_novi_raspored() {
        ScheduleExamCreateDto scheduleExamCreateDto = new ScheduleExamCreateDto();
        scheduleExamCreateDto.setLbp("P0002");
        scheduleExamCreateDto.setNote("test note");
        scheduleExamCreateDto.setDateAndTime(new Timestamp(System.currentTimeMillis()));
        scheduleExamCreateDto.setDoctorLbz("D0001");
        patientService.schedule(scheduleExamCreateDto);
    }
    @Then("Taj raspored je sacuvan u bazi")
    public void taj_raspored_je_sacuvan_u_bazi() {
        try {
            List<ScheduleExamDto> scheduleExamDtoList = patientService.findScheduledExaminations();
            ScheduleExamDto expected = null;
            for(ScheduleExamDto dto: scheduleExamDtoList) {
                if(Objects.equals(dto.getLbp(), "P0002")) {
                    expected = dto;
                    break;
                }
            }
            assertTrue(scheduleExamDtoList.contains(expected));
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

    @When("Kada se raspored sa ID {long} izbrise iz baze")
    public void kada_se_raspored_izbrise_iz_baze(Long id) {
        MessageDto messageDto = patientService.deleteScheduledExamination(id);
        try{
            assertNotNull(messageDto);
            assertEquals("Scheduled exam with id 1 deleted", messageDto.getMessage());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Raspored sa ID {long} ne postoji u bazi")
    public void taj_raspored_ne_postoji_u_bazi(Long id) {
        try {
            List<ScheduleExamDto> scheduleExamDtoList = patientService.findScheduledExaminations();
            ScheduleExamDto expected = null;
            for(ScheduleExamDto dto: scheduleExamDtoList) {
                if(Objects.equals(dto.getId(), id)) {
                    expected = dto;
                    break;
                }
            }
            assertNull(expected);
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

    @When("Kada se prosledi ID dolaska pacijenta {long} i novi status {string}")
    public void kada_se_prosledi_id_dolaska_pacijenta_i_novi_status(Long id, String status) {
        PatientArrival pa = PatientArrival.valueOf(status);
        MessageDto messageDto = patientService.updatePatientArrivalStatus(id, pa);
        try{
            assertNotNull(messageDto);
            assertEquals("Status pregleda promenjen u " + status, messageDto.getMessage());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Status dolaska pacijenta sa ID {long} ce biti {string} u bazi")
    public void status_dolaska_pacijenta_sa_id_ce_biti_u_bazi(Long id, String status) {
        try {
            PatientArrival pa = PatientArrival.valueOf(status);
            List<ScheduleExamDto> scheduleExamDtoList = patientService.findScheduledExaminations();
            ScheduleExamDto expected = null;
            for(ScheduleExamDto dto: scheduleExamDtoList) {
                if(Objects.equals(dto.getId(), id)) {
                    expected = dto;
                    break;
                }
            }
            assertNotNull(expected);
            assertSame(expected.getPatientArrival(), pa);
        } catch (Exception e){
            fail(e.getMessage());
        }
    }
}