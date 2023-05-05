package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.DischargeListGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.dischargeList.DischargeListDtoGenerator;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.services.DischargeListService;
import raf.bolnica1.infirmary.services.impl.DischargeListServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class DischargeListUnitTest {

    private DischargeListDtoGenerator dischargeListDtoGenerator=DischargeListDtoGenerator.getInstance();
    private HospitalizationGenerator hospitalizationGenerator=HospitalizationGenerator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private DischargeListGenerator dischargeListGenerator=DischargeListGenerator.getInstance();


    /// MOCK
    private DischargeListMapper dischargeListMapper;
    private DischargeListRepository dischargeListRepository;
    private HospitalizationRepository hospitalizationRepository;
    private DischargeListService dischargeListService;


    @BeforeEach
    public void prepare(){
        dischargeListMapper=new DischargeListMapper();
        dischargeListRepository=mock(DischargeListRepository.class);
        hospitalizationRepository=mock(HospitalizationRepository.class);
        dischargeListService=new DischargeListServiceImpl(dischargeListMapper,dischargeListRepository,hospitalizationRepository);
    }


    @Test
    public void createDischargeListTest(){

        long hospitalizationId=4;
        long dischargeListId=2;

        Hospitalization hospitalization=hospitalizationGenerator.getHospitalization(null,
                prescriptionGenerator.getPrescription());
        hospitalization.setId(hospitalizationId);
        DischargeListDto dischargeListDto=dischargeListDtoGenerator.getDischargeListDto(hospitalization.getId());

        given(hospitalizationRepository.findHospitalizationById(hospitalizationId)).willReturn(hospitalization);
        DischargeList dischargeList=dischargeListMapper.toEntity(dischargeListDto,hospitalizationRepository);
        dischargeList.setId(dischargeListId);
        given(dischargeListRepository.save(any())).willReturn(dischargeList);


        CreateDischargeListDto createDischargeListDto=dischargeListMapper.toDto(dischargeListDto);
        DischargeListDto result=dischargeListService.createDischargeList(createDischargeListDto);

        dischargeListDto.setId(dischargeListId);
        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,dischargeListDto));
    }


    @Test
    public void getDischargeListByHospitalizationIdTest(){
        try {
            long hospitalizationId=4;
            long dischargeListId=2;

            Hospitalization hospitalization=hospitalizationGenerator.getHospitalization(null,
                    prescriptionGenerator.getPrescription());
            hospitalization.setId(hospitalizationId);
            DischargeList dischargeList=dischargeListGenerator.getDischargeList(hospitalization);
            dischargeList.setId(dischargeListId);
            List<DischargeList>lista=new ArrayList<>();
            lista.add(dischargeList);
            Page<DischargeList> page=new PageImpl<>(lista);

            given(dischargeListRepository.findDischargeListWithFilter(any(),
                    eq(hospitalizationId),any(),any(),any()  )).willReturn(page);

            DischargeListDto result=dischargeListService.getDischargeListWithFilter(hospitalizationId,
                    null,null,null,0,1000000000).getContent().get(0);

            Assertions.assertTrue(classJsonComparator.compareCommonFields(result,dischargeList));
            Assertions.assertTrue(result.getHospitalizationId().equals(dischargeList.getHospitalization().getId()));
        }
        catch (Exception e){
            Assertions.fail(e);
        }

    }


}
