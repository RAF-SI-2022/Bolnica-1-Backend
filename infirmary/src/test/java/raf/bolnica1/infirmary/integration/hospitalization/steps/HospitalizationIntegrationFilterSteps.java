package raf.bolnica1.infirmary.integration.hospitalization.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter.HospitalRoomFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.integration.hospitalization.HospitalizationIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.HospitalRoomMapper;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.HospitalizationService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HospitalizationIntegrationFilterSteps extends HospitalizationIntegrationTestConfig {

    /// GENERATORS
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

    /// REPOSITORIES
    @Autowired
    private HospitalizationRepository hospitalizationRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;

    /// MAPPERS
    @Autowired
    private HospitalizationMapper hospitalizationMapper;



    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private List<HospitalizationDto> result;
    private List<HospitalizationFilter> filters;



    @When("imamo {int} hospitalizacija i {int} filteri su: idDep: {int} , idHr: {int} , name: {int} , surname: {int} , jmbg: {int} , lbp: {int} , startDate: {int} , endDate: {int}")
    public void imamo_hospitalizacija_i_filteri_su_id_dep_id_hr_name_surname_jmbg_lbp_start_date_end_date(
            Integer hospitalizationCount,Integer filterCount, Integer depIdFlag, Integer hrIdFlag, Integer nameFlag, Integer surnameFlag,
            Integer jmbgFlag, Integer lbpFlag, Integer startDateFlag, Integer endDateFlag) {
        try {

            filters = new ArrayList<>();
            for (int i = 0; i < filterCount; i++) {
                HospitalizationFilter f = hospitalizationFilterGenerator.getRandomFilter();
                if (depIdFlag == 0) f.setDepartmentId(null);
                if (hrIdFlag == 0) f.setHospitalRoomId(null);
                if (nameFlag == 0) f.setName(null);
                if (surnameFlag == 0) f.setSurname(null);
                if (jmbgFlag == 0) f.setJmbg(null);
                if (lbpFlag == 0) f.setLbp(null);
                if (startDateFlag == 0) f.setStartDate(null);
                if (endDateFlag == 0) f.setEndDate(null);
                filters.add(f);
            }

            result = hospitalizationMapper.toDto(hospitalizationRepository.findAll());

            int prescriptionCount = 10;
            int hospitalRoomCount = 10;
            List<Prescription> prescriptions = new ArrayList<>();
            List<HospitalRoom> hospitalRooms = new ArrayList<>();
            for (int i = 0; i < prescriptionCount; i++)
                prescriptions.add(prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository));
            for (int i = 0; i < hospitalRoomCount; i++)
                hospitalRooms.add(hospitalRoomGenerator.getHospitalRoomWithDBSave(hospitalRoomRepository));

            for (int i = 0; i < hospitalizationCount; i++) {
                int prescriptionInd = randomLong.getLong(Long.parseLong(String.valueOf(prescriptionCount))).intValue();
                int hospitalRoomInd = randomLong.getLong(Long.parseLong(String.valueOf(hospitalRoomCount))).intValue();
                Hospitalization hospitalization = hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                        hospitalRoomRepository, prescriptionRepository, hospitalRooms.get(hospitalRoomInd),
                        prescriptions.get(prescriptionInd));

                result.add(hospitalizationMapper.toDto(hospitalization));
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("isfiltrirane hospitalizacije su tacne")
    public void isfiltrirane_hospitalizacije_su_tacne() {

        try{

            for(int i=0;i<filters.size();i++){
                HospitalizationFilter f=filters.get(i);

                List<HospitalizationDto> pom=f.applyFilterToList(result,hospitalizationRepository);
                List<HospitalizationDto> hospitalizationDtos=hospitalizationService.getHospitalizationsWithFilter(f.getName(),
                        f.getSurname(),f.getJmbg(),f.getDepartmentId(),f.getHospitalRoomId(),f.getLbp(),f.getStartDate(),
                        f.getEndDate(),0,1000000000).getContent();

                List<HospitalizationDto> mutableVisitDtos=new ArrayList<>(hospitalizationDtos);
                List<HospitalizationDto> mutableResults=new ArrayList<>(pom);
                mutableVisitDtos.sort( (o1, o2) -> o1.getId().compareTo(o2.getId()));
                mutableResults.sort( (o1, o2) -> o1.getId().compareTo(o2.getId()));


                System.out.println(f.toString());
                assertTrue(classJsonComparator.compareListCommonFields(mutableResults,mutableVisitDtos));
            }

        }catch (Exception e){
            Assertions.fail(e);
        }

    }


}
