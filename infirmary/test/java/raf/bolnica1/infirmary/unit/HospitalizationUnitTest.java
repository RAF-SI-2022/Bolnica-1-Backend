package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter.HospitalizationFilterGenerator;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;
import raf.bolnica1.infirmary.services.HospitalizationService;
import raf.bolnica1.infirmary.services.impl.HospitalizationServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class HospitalizationUnitTest {

    private HospitalizationGenerator hospitalizationGenerator=HospitalizationGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private HospitalRoomGenerator hospitalRoomGenerator=HospitalRoomGenerator.getInstance();
    private HospitalizationFilterGenerator hospitalizationFilterGenerator=HospitalizationFilterGenerator.getInstance();


    /// MOCK
    private HospitalizationMapper hospitalizationMapper;
    private HospitalizationRepository hospitalizationRepository;
    private HospitalizationService hospitalizationService;
    private AuthenticationUtils authenticationUtils;
    private RestTemplate patientRestTemplate;


    @BeforeEach
    public void prepare(){
        authenticationUtils=mock(AuthenticationUtils.class);
        patientRestTemplate=mock(RestTemplate.class);
        hospitalizationMapper=new HospitalizationMapper(authenticationUtils,patientRestTemplate);
        hospitalizationRepository=mock(HospitalizationRepository.class);
        hospitalizationService=new HospitalizationServiceImpl(hospitalizationMapper,hospitalizationRepository);
    }

    private List<Hospitalization> generateHospitalizations(int hospitalizationCount){
        List<Hospitalization> ret=new ArrayList<>();
        for(int i=0;i<hospitalizationCount;i++){
            Prescription prescription= prescriptionGenerator.getPrescription();
            prescription.setId((long)i);
            HospitalRoom hospitalRoom=hospitalRoomGenerator.generateHospitalRoom();
            hospitalRoom.setId((long)i);
            ret.add(hospitalizationGenerator.getHospitalization(hospitalRoom,prescription));
            ret.get(i).setId((long)i);
        }
        return ret;
    }

    @Test
    public void getHospitalizationsByDepartmentIdTest(){

        int hospitalizationCount=10;
        int departmentId=3;

        List<Hospitalization> hospitalizations=generateHospitalizations(hospitalizationCount);
        Page<Hospitalization> page= new PageImpl<>(hospitalizations);

        given(hospitalizationRepository.findHospitalizationsByDepartmentId(any(),eq((long)departmentId) ))
                .willReturn(page);

        Page<HospitalizationDto> result=hospitalizationService.getHospitalizationsByDepartmentId((long)departmentId,
                0,hospitalizationCount);

        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),hospitalizations));
        for(int i=0;i<hospitalizationCount;i++) {
            Assertions.assertTrue(result.getContent().get(i).getHospitalRoomId().equals(hospitalizations.get(i).getHospitalRoom().getId()));
            Assertions.assertTrue(result.getContent().get(i).getPrescriptionId().equals(hospitalizations.get(i).getPrescription().getId()));
        }
    }


    @Test
    public void getHospitalizationsByHospitalRoomId(){

        int hospitalizationCount=10;
        int hospitalRoomId=3;

        List<Hospitalization> hospitalizations=generateHospitalizations(hospitalizationCount);
        Page<Hospitalization> page= new PageImpl<>(hospitalizations);

        given(hospitalizationRepository.findHospitalizationsByHospitalRoomId(any(),eq((long)hospitalRoomId) ))
                .willReturn(page);

        Page<HospitalizationDto> result=hospitalizationService.getHospitalizationsByHospitalRoomId((long)hospitalRoomId,
                0,hospitalizationCount);

        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),hospitalizations));
        for(int i=0;i<hospitalizationCount;i++) {
            Assertions.assertTrue(result.getContent().get(i).getHospitalRoomId().equals(hospitalizations.get(i).getHospitalRoom().getId()));
            Assertions.assertTrue(result.getContent().get(i).getPrescriptionId().equals(hospitalizations.get(i).getPrescription().getId()));
        }

    }


    @Test
    public void getHospitalizationsWithFilterTest(){

        int hospitalizationCount=10;

        List<Hospitalization> hospitalizations=generateHospitalizations(hospitalizationCount);
        Page<Hospitalization> page= new PageImpl<>(hospitalizations);

        HospitalizationFilter f=hospitalizationFilterGenerator.getRandomFilter();

        Date nd=null;
        if(f.getEndDate()!=null)nd=new Date(f.getEndDate().getTime()+24*60*60*1000);
        given(hospitalizationRepository.findHospitalizationsWithFilter(any(),eq(f.getName()),eq(f.getSurname()),
                eq(f.getJmbg()), eq(f.getDepartmentId()), eq(f.getHospitalRoomId()), eq(f.getLbp()),
                eq(f.getStartDate()), eq(nd) ))
                .willReturn(page);

        Page<HospitalizationDto> result=hospitalizationService.getHospitalizationsWithFilter(f.getName(),f.getSurname(),
                f.getJmbg(),f.getDepartmentId(),f.getHospitalRoomId(),f.getLbp(),f.getStartDate(),f.getEndDate(),
                0,hospitalizationCount);

        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),hospitalizations));
        for(int i=0;i<hospitalizationCount;i++) {
            Assertions.assertTrue(result.getContent().get(i).getHospitalRoomId().equals(hospitalizations.get(i).getHospitalRoom().getId()));
            Assertions.assertTrue(result.getContent().get(i).getPrescriptionId().equals(hospitalizations.get(i).getPrescription().getId()));
        }

    }


}
