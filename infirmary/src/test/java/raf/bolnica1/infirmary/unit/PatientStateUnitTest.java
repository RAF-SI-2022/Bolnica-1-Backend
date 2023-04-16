package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PatientStateGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.PrescriptionGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.PatientStateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.filter.PatientStateFilter;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.filter.PatientStateFilterGenerator;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.mapper.PatientStateMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PatientStateRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;
import raf.bolnica1.infirmary.services.PatientStateService;
import raf.bolnica1.infirmary.services.impl.PatientStateServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PatientStateUnitTest {

    private PatientStateDtoGenerator patientStateDtoGenerator=PatientStateDtoGenerator.getInstance();
    private HospitalizationGenerator hospitalizationGenerator=HospitalizationGenerator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private PatientStateGenerator patientStateGenerator=PatientStateGenerator.getInstance();
    private PatientStateFilterGenerator patientStateFilterGenerator=PatientStateFilterGenerator.getInstance();




    /// MOCK
    private PatientStateMapper patientStateMapper;
    private AuthenticationUtils authenticationUtils;
    private PatientStateRepository patientStateRepository;
    private HospitalizationRepository hospitalizationRepository;


    private PatientStateService patientStateService;


    @BeforeEach
    public void prepare(){
        authenticationUtils=mock(AuthenticationUtils.class);
        patientStateMapper=new PatientStateMapper(authenticationUtils);
        patientStateRepository=mock(PatientStateRepository.class);
        hospitalizationRepository=mock(HospitalizationRepository.class);
        patientStateService=new PatientStateServiceImpl(patientStateMapper,patientStateRepository,hospitalizationRepository);
    }


    @Test
    public void createPatientStateTest(){

        long hospitalizationId=4L;
        long patientStateId=2L;
        String mojLbz="moj_lbz";

        PatientStateCreateDto patientStateCreateDto=patientStateDtoGenerator.getPatientStateCreateDto(hospitalizationId);
        Hospitalization hospitalization= hospitalizationGenerator.getHospitalization(null,
                prescriptionGenerator.getPrescription());
        hospitalization.setId(hospitalizationId);

        given(hospitalizationRepository.findHospitalizationById(hospitalizationId)).willReturn(hospitalization);
        given(authenticationUtils.getLbzFromAuthentication()).willReturn(mojLbz);
        PatientState patientState= patientStateMapper.toEntity(patientStateCreateDto,hospitalizationRepository);
        patientState.setId(patientStateId);
        given(patientStateRepository.save(any())).willReturn(patientState);

        PatientStateDto result=patientStateService.createPatientState(patientStateCreateDto);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,patientStateCreateDto));
        Assertions.assertTrue(result.getId().equals(patientStateId));
        Assertions.assertTrue(result.getLbz().equals(mojLbz));

    }


    @Test
    public void getPatientStateByDateTest(){

        long hospitalizationId=4L;
        long patientStateCount=10;


        Hospitalization hospitalization= hospitalizationGenerator.getHospitalization(null,
                prescriptionGenerator.getPrescription());
        hospitalization.setId(hospitalizationId);

        List<PatientState> patientStateList=new ArrayList<>();
        for(int i=0;i<patientStateCount;i++) {
            patientStateList.add(patientStateGenerator.getPatientState(hospitalization));
            patientStateList.get(i).setId(new Long(i) );
        }

        PatientStateFilter f=patientStateFilterGenerator.getRandomFilter();

        Page<PatientState> page=new PageImpl<>(patientStateList);
        given(patientStateRepository.findPatientStatesByDate(any(),eq(f.getHospitalizationId()),
                eq(f.getStartDate()),eq(f.getEndDate()) )).willReturn(page);

        Page<PatientStateDto> result=patientStateService.getPatientStateByDate(f.getHospitalizationId(),f.getStartDate(),
                f.getEndDate(),0,(int) patientStateCount );


        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),patientStateList));
        for(int i=0;i<patientStateCount;i++){
            Assertions.assertTrue(result.getContent().get(i).getHospitalizationId().equals(
                    patientStateList.get(i).getHospitalization().getId()
            ));
        }

    }


}
