package raf.bolnica1.laboratory.unit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.lab.*;
import raf.bolnica1.laboratory.dto.lab.PatientDto;
import raf.bolnica1.laboratory.dto.prescription.*;
import raf.bolnica1.laboratory.mappers.PrescriptionMapper;
import raf.bolnica1.laboratory.mappers.PrescriptionRecieveMapper;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.PrescriptionRecieveService;
import raf.bolnica1.laboratory.services.impl.LabWorkOrdersServiceImpl;
import raf.bolnica1.laboratory.services.impl.PrescriptionRecieveServiceImpl;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.prescription.PrescriptionGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription.PrescriptionUpdateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionReceiveServiceTest {

    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator=PrescriptionCreateDtoGenerator.getInstance();
    private PrescriptionUpdateDtoGenerator prescriptionUpdateDtoGenerator=PrescriptionUpdateDtoGenerator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private RandomString randomString=RandomString.getInstance();


    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private PrescriptionRepository prescriptionRepository;
    private AnalysisParameterRepository analysisParameterRepository;
    private PrescriptionMapper prescriptionMapper;
    private PrescriptionRecieveMapper prescriptionrecieveMapper;
    private LabWorkOrdersService labWorkOrdersService;
    private LabWorkOrderRepository labWorkOrderRepository;

    private PrescriptionRecieveService prescriptionRecieveService;


    @BeforeEach
    public void prepare(){

        parameterAnalysisResultRepository=mock(ParameterAnalysisResultRepository.class);
        prescriptionRepository=mock(PrescriptionRepository.class);
        analysisParameterRepository=mock(AnalysisParameterRepository.class);
        labWorkOrderRepository=mock(LabWorkOrderRepository.class);
        prescriptionMapper=new PrescriptionMapper();
        prescriptionrecieveMapper=new PrescriptionRecieveMapper(labWorkOrderRepository,parameterAnalysisResultRepository,
                analysisParameterRepository);
        labWorkOrdersService=mock(LabWorkOrdersServiceImpl.class);
        prescriptionRecieveService=new PrescriptionRecieveServiceImpl(parameterAnalysisResultRepository,
                prescriptionRepository,analysisParameterRepository,prescriptionMapper,prescriptionrecieveMapper,
                labWorkOrdersService,labWorkOrderRepository);
    }


    private List<AnalysisParameter> getAnalysisParameters(int apCount){
        List<AnalysisParameter>aps=new ArrayList<>();
        for(int i=0;i<apCount;i++){
            AnalysisParameter ap=new AnalysisParameter();
            ap.setId((long)i);
            Parameter p=new Parameter();
            p.setParameterName(randomString.getString(5));
            p.setId((long)(i%5));
            LabAnalysis la=new LabAnalysis();
            la.setAnalysisName(randomString.getString(5));
            la.setId((long)i);
            ap.setParameter(p);
            ap.setLabAnalysis(la);
            aps.add(ap);
        }
        return aps;
    }
    private AnalysisParameter findByAnalysisIdAndParameterId(List<AnalysisParameter> list,Long analysisId,Long parameterId){
        for(AnalysisParameter ap:list)
            if(ap.getParameter().getId().equals(parameterId) && ap.getLabAnalysis().getId().equals(analysisId))
                return ap;
        return null;
    }

    @Test
    public void createPrescriptionTest(){

        /// generate prescriptions
        int apCount=20;
        List<AnalysisParameter>aps=getAnalysisParameters(apCount);

        given(analysisParameterRepository.findAll()).willReturn(aps);
        PrescriptionCreateDto prescriptionCreateDto= prescriptionCreateDtoGenerator.getPrescriptionCreateDto(analysisParameterRepository);

        System.out.println(prescriptionCreateDto.getPrescriptionAnalysisDtos().size()+"  AAA");

        Prescription p=prescriptionMapper.toEntity(prescriptionCreateDto);
        p.setId(3L);
        given(prescriptionRepository.save(any())).willReturn(p);

        LabWorkOrder lwo=new LabWorkOrder();
        given(labWorkOrdersService.createWorkOrder(p)).willReturn(lwo);


        int totalCount=0;
        for(PrescriptionAnalysisDto pad:prescriptionCreateDto.getPrescriptionAnalysisDtos()){
            Long analysisId=pad.getAnalysisId();
            for(Long paramId:pad.getParametersIds()){
                totalCount++;
                given(analysisParameterRepository.findAnalysisParameterByAnalysisIdAndParameterId(analysisId,paramId))
                        .willReturn(findByAnalysisIdAndParameterId(aps,analysisId,paramId));
            }
        }

        prescriptionRecieveService.createPrescription(prescriptionCreateDto);

        ArgumentCaptor<Prescription> prescriptionArgumentCaptor=ArgumentCaptor.forClass(Prescription.class);
        verify(prescriptionRepository).save(prescriptionArgumentCaptor.capture());
        Assertions.assertTrue(classJsonComparator.compareCommonFields(prescriptionArgumentCaptor.getValue(),
                prescriptionCreateDto));

        ArgumentCaptor<ParameterAnalysisResult>parCaptor=ArgumentCaptor.forClass(ParameterAnalysisResult.class);
        verify(parameterAnalysisResultRepository,times(totalCount)).save(parCaptor.capture());

        Assertions.assertTrue(parCaptor.getAllValues().size()==totalCount);
        for(ParameterAnalysisResult par:parCaptor.getAllValues()){
            Assertions.assertTrue(aps.contains(par.getAnalysisParameter()));
            Assertions.assertTrue(par.getLabWorkOrder().equals(lwo));
        }

    }


    @Test
    public void updatePrescriptionTest(){

        int apCount=20;
        List<AnalysisParameter>aps=getAnalysisParameters(apCount);

        Prescription p=prescriptionGenerator.getPrescription();
        p.setId(3L);
        p.setStatus(PrescriptionStatus.NEREALIZOVAN);
        given(prescriptionRepository.findById(p.getId())).willReturn(Optional.of(p));

        given(analysisParameterRepository.findAll()).willReturn(aps);
        List<Prescription>pomList=new ArrayList<>();
        pomList.add(p);
        given(prescriptionRepository.findAll()).willReturn(pomList);
        PrescriptionUpdateDto prescriptionUpdateDto=prescriptionUpdateDtoGenerator.getPrescriptionUpdateDto(prescriptionRepository,
                analysisParameterRepository);


        LabWorkOrder lwo=new LabWorkOrder();
        lwo.setStatus(OrderStatus.NEOBRADJEN);
        given(labWorkOrderRepository.findByPrescription(p.getId())).willReturn(Optional.of(lwo));


        int totalCount=0;
        for(PrescriptionAnalysisDto pad:prescriptionUpdateDto.getPrescriptionAnalysisDtos()){
            Long analysisId=pad.getAnalysisId();
            for(Long paramId:pad.getParametersIds()){
                totalCount++;
                given(analysisParameterRepository.findAnalysisParameterByAnalysisIdAndParameterId(analysisId,paramId))
                        .willReturn(findByAnalysisIdAndParameterId(aps,analysisId,paramId));
            }
        }

        prescriptionRecieveService.updatePrescription(prescriptionUpdateDto);

        ArgumentCaptor<Prescription> prescriptionArgumentCaptor=ArgumentCaptor.forClass(Prescription.class);
        verify(prescriptionRepository).save(prescriptionArgumentCaptor.capture());
        Assertions.assertTrue(classJsonComparator.compareCommonFields(prescriptionArgumentCaptor.getValue(),
                prescriptionUpdateDto));

        ArgumentCaptor<ParameterAnalysisResult>parCaptor=ArgumentCaptor.forClass(ParameterAnalysisResult.class);
        verify(parameterAnalysisResultRepository,times(totalCount)).save(parCaptor.capture());

        Assertions.assertTrue(parCaptor.getAllValues().size()==totalCount);
        for(ParameterAnalysisResult par:parCaptor.getAllValues()){
            Assertions.assertTrue(aps.contains(par.getAnalysisParameter()));
            Assertions.assertTrue(par.getLabWorkOrder().equals(lwo));
        }

    }


    @Test
    public void deletePrescriptionTest(){

        Prescription p=prescriptionGenerator.getPrescription();
        p.setId(3L);
        given(prescriptionRepository.findById(p.getId())).willReturn(Optional.of(p));

        LabWorkOrder lwo=new LabWorkOrder();
        given(labWorkOrderRepository.findByPrescription(p.getId())).willReturn(Optional.of(lwo));

        prescriptionRecieveService.deletePrescription(p.getId(),p.getDoctorLbz());

        verify(labWorkOrdersService).deleteWorkOrder(lwo);
        verify(prescriptionRepository).delete(p);
    }


    @Test
    public void findPrescriptionsForPatientTest(){

        int prescriptionCount=10;
        List<Prescription> prescriptionList=new ArrayList<>();
        for(int i=0;i<prescriptionCount;i++) {
            prescriptionList.add(prescriptionGenerator.getPrescription());
            prescriptionList.get(i).setId((long)i);
        }
        Page<Prescription> page=new PageImpl<>(prescriptionList);

        String lbp="mojLbp";
        String lbz="mojLbz";

        given(prescriptionRepository.findPrescriptionsByLbpAndDoctorLbz(any(),eq(lbp),eq(lbz),eq(PrescriptionStatus.NEREALIZOVAN) ))
                .willReturn(page);

        Page<PrescriptionDto> ret=prescriptionRecieveService.findPrescriptionsForPatient(lbp,lbz,0,1000000000);

        List<Prescription>pom1=new ArrayList<Prescription>(prescriptionList);
        List<PrescriptionDto>pom2=new ArrayList<PrescriptionDto>(ret.getContent());
        pom1.sort(Comparator.comparing(Prescription::getId));
        pom2.sort(Comparator.comparing(PrescriptionDto::getId));

        for(int i=0;i<prescriptionCount;i++)
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));

    }


    @Test
    public void findPrescriptionTest(){

        Prescription p=prescriptionGenerator.getPrescription();
        p.setId(3L);
        p.setStatus(PrescriptionStatus.NEREALIZOVAN);
        p.setType(PrescriptionType.LABORATORIJA);
        given(prescriptionRepository.findPrescriptionById(p.getId())).willReturn(p);

        LabWorkOrder lwo=new LabWorkOrder();
        lwo.setStatus(OrderStatus.NEOBRADJEN);
        lwo.setId(2L);
        given(labWorkOrderRepository.findByPrescription(p.getId())).willReturn(Optional.of(lwo));

        int apCount=20;
        List<AnalysisParameter>aps=getAnalysisParameters(apCount);
        List<ParameterAnalysisResult>par=new ArrayList<>();
        for(AnalysisParameter ap:aps) {
            ParameterAnalysisResult pom=new ParameterAnalysisResult();
            pom.setLabWorkOrder(lwo);
            pom.setAnalysisParameter(ap);
            par.add(pom);
        }
        given(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(lwo.getId()))
                .willReturn(par);

        PrescriptionDoneDto ret=prescriptionRecieveService.findPrescription(p.getId());

        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret,p));

        for(PrescriptionAnalysisNameDto pand:ret.getParameters()){
            String analysisName=pand.getAnalysisName();
            for(ParameterDto pd:pand.getParameters()){
                String parameterName=pd.getParameterName();

                boolean flag=false;
                for(ParameterAnalysisResult pa:par)
                    if(pa.getAnalysisParameter().getParameter().getParameterName().equals(parameterName) &&
                    pa.getAnalysisParameter().getLabAnalysis().getAnalysisName().equals(analysisName))
                        flag=true;
                Assertions.assertTrue(flag);
            }
        }

    }

    @Test
    public void findPrescriptionsForPatientRestTest(){

        int prescriptionCount=10;
        List<Prescription> prescriptionList=new ArrayList<>();
        for(int i=0;i<prescriptionCount;i++) {
            prescriptionList.add(prescriptionGenerator.getPrescription());
            prescriptionList.get(i).setId((long)i);
        }
        Page<Prescription> page=new PageImpl<>(prescriptionList);

        String lbp="mojLbp";
        String lbz="mojLbz";

        given(prescriptionRepository.findPrescriptionsByLbpAndDoctorLbz(any(),eq(lbp),eq(lbz),eq(PrescriptionStatus.NEREALIZOVAN) ))
                .willReturn(page);

        List<PrescriptionDto> ret=prescriptionRecieveService.findPrescriptionsForPatientRest(lbp,lbz);

        List<Prescription>pom1=new ArrayList<Prescription>(prescriptionList);
        List<PrescriptionDto>pom2=new ArrayList<PrescriptionDto>(ret);
        pom1.sort(Comparator.comparing(Prescription::getId));
        pom2.sort(Comparator.comparing(PrescriptionDto::getId));

        for(int i=0;i<prescriptionCount;i++)
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));

    }


    @Test
    public void findPrescriptionsForPatientNotRealizedTest(){

        int prescriptionCount=10;
        List<Prescription> prescriptionList=new ArrayList<>();
        for(int i=0;i<prescriptionCount;i++) {
            prescriptionList.add(prescriptionGenerator.getPrescription());
            prescriptionList.get(i).setId((long)i);
        }
        Page<Prescription> page=new PageImpl<>(prescriptionList);

        String lbp="mojLbp";

        given(prescriptionRepository.findPrescriptionsByLbpNotRealized(any(),eq(lbp),eq(PrescriptionStatus.NEREALIZOVAN) ))
                .willReturn(page);

        Page<PrescriptionDto> ret=prescriptionRecieveService.findPrescriptionsForPatientNotRealized(lbp,0,1000000000);

        List<Prescription>pom1=new ArrayList<Prescription>(prescriptionList);
        List<PrescriptionDto>pom2=new ArrayList<PrescriptionDto>(ret.getContent());
        pom1.sort(Comparator.comparing(Prescription::getId));
        pom2.sort(Comparator.comparing(PrescriptionDto::getId));

        for(int i=0;i<prescriptionCount;i++)
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));

    }


    @Test
    public void findPatientsTest(){

        int prescriptionCount=10;
        List<Prescription> prescriptionList=new ArrayList<>();
        for(int i=0;i<prescriptionCount;i++) {
            prescriptionList.add(prescriptionGenerator.getPrescription());
            prescriptionList.get(i).setId((long)i);
        }
        Page<Prescription> page=new PageImpl<>(prescriptionList);

        given(prescriptionRepository.findPrescriptionsNotRealized(any(),eq(PrescriptionStatus.NEREALIZOVAN) ))
                .willReturn(page);

        Page<PatientDto> ret=prescriptionRecieveService.findPatients(0,1000000000);

        List<Prescription>pom1=new ArrayList<Prescription>(prescriptionList);
        List<PatientDto>pom2=new ArrayList<PatientDto>(ret.getContent());
        pom1.sort(Comparator.comparing(Prescription::getId));
        pom2.sort(Comparator.comparing(PatientDto::getPrescriptionId));

        Assertions.assertTrue(pom1.size()==pom2.size());
        for(int i=0;i<pom1.size();i++){
            Assertions.assertTrue(pom1.get(i).getId().equals(pom2.get(i).getPrescriptionId()));
            Assertions.assertTrue(pom1.get(i).getLbp().equals(pom2.get(i).getLbp()));
        }

    }

}
