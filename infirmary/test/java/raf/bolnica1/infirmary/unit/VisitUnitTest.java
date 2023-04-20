package raf.bolnica1.infirmary.unit;

import io.cucumber.java.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.VisitGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.VisitCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter.VisitFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter.VisitFilterGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.mapper.VisitMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.VisitRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;
import raf.bolnica1.infirmary.services.VisitService;
import raf.bolnica1.infirmary.services.impl.VisitServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class VisitUnitTest {

    private VisitCreateDtoGenerator visitCreateDtoGenerator=VisitCreateDtoGenerator.getInstance();
    private RandomLong randomLong=RandomLong.getInstance();
    private HospitalizationGenerator hospitalizationGenerator=HospitalizationGenerator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private VisitGenerator visitGenerator=VisitGenerator.getInstance();
    private VisitFilterGenerator visitFilterGenerator=VisitFilterGenerator.getInstance();


    /// MOCKING
    private VisitMapper visitMapper;
    private VisitRepository visitRepository;
    private HospitalizationRepository hospitalizationRepository;
    private AuthenticationUtils authenticationUtils;
    private VisitService visitService;


    @BeforeEach
    public void prepare(){
        authenticationUtils=mock(AuthenticationUtils.class);
        visitMapper=new VisitMapper(authenticationUtils);
        visitRepository=mock(VisitRepository.class);
        hospitalizationRepository=mock(HospitalizationRepository.class);
        visitService=new VisitServiceImpl(visitMapper,visitRepository,hospitalizationRepository);
    }


    @Test
    public void createVisitTest(){


        VisitCreateDto visitCreateDto=visitCreateDtoGenerator.getVisitCreateDto(4L);
        Hospitalization hospitalization= hospitalizationGenerator.getHospitalization(null,
                prescriptionGenerator.getPrescription());
        hospitalization.setId(4L);

        given(authenticationUtils.getLbzFromAuthentication()).willReturn("mojLbz");
        given(hospitalizationRepository.findHospitalizationById(4L)).willReturn(hospitalization);
        Visit visit=visitMapper.toEntity(visitCreateDto,hospitalizationRepository);
        visit.setId(2L);
        given(visitRepository.save(any())).willReturn(visit);

        VisitDto visitDto=visitService.createVisit(visitCreateDto);

        Assertions.assertTrue(visitDto.getId()==2l);
        Assertions.assertTrue(visitDto.getLbzRegister().equals("mojLbz"));
        Assertions.assertTrue(classJsonComparator.compareCommonFields(visitCreateDto,visitDto));
    }


    @Test
    public void getVisitsWithFilterTest(){

        Hospitalization hospitalization= hospitalizationGenerator.getHospitalization(null,
                prescriptionGenerator.getPrescription());
        hospitalization.setId(4L);

        int visitCount=10;

        List<Visit> visits=new ArrayList<>();
        for(int i=0;i<visitCount;i++) {
            visits.add(visitGenerator.getVisit(hospitalization));
            visits.get(i).setId(new Long(i));
        }
        PageImpl<Visit> page=new PageImpl<>(visits);
        VisitFilter f=visitFilterGenerator.getRandomFilter();

        Date nd=null;
        if(f.getEndDate()!=null)nd=new Date(f.getEndDate().getTime()+24*60*60*1000);
        given(visitRepository.findVisitsWithFilter(any(),eq(f.getDepartmentId()),eq(f.getHospitalRoomId()),
                eq(f.getHospitalizationId()),eq(f.getStartDate()), eq(nd) ) ).willReturn(page);

        Page<VisitDto> result=visitService.getVisitsWithFilter(f.getDepartmentId(),f.getHospitalRoomId(),
                f.getHospitalizationId(),f.getStartDate(),f.getEndDate(),0,10);

        Assertions.assertTrue(classJsonComparator.compareListCommonFields(visits,result.getContent()));
        for(int i=0;i<visitCount;i++){
            Assertions.assertTrue(visits.get(i).getHospitalization().getId().equals(result.getContent().get(i)
                    .getHospitalizationId()));
        }
    }

}
