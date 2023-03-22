package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.lab.AnalysisParameter;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.mappers.PrescriptionMapper;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;

@Service
@AllArgsConstructor
public class PrescriptionRecieveServiceImpl implements PrescriptionRecieveService {

    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AnalysisParameterRepository analysisParameterRepository;
    private final PrescriptionMapper prescriptionMapper;

    private final LabWorkOrdersService labWorkOrdersService;

    @Override
    public PrescriptionDto createPrescription(PrescriptionCreateDto dto) {
        Prescription prescription = prescriptionMapper.toEntity(dto);
        prescription = prescriptionRepository.save(prescription);

        LabWorkOrder labWorkOrder=labWorkOrdersService.createWorkOrder(prescription.getId());

        for(PrescriptionAnalysisDto prescriptionAnalysisDto : dto.getPrescriptionAnalysisDtos()){
            ///System.out.println(prescriptionAnalysisDto.getAnalysisId());
            Long analysisId=prescriptionAnalysisDto.getAnalysisId();
            for(Long parameterId:prescriptionAnalysisDto.getParametersIds()){
                AnalysisParameter analysisParameter=analysisParameterRepository.findAnalysisParameterByAnalysisIdAndParameterId(analysisId,parameterId);

                ParameterAnalysisResult parameterAnalysisResult=new ParameterAnalysisResult();
                parameterAnalysisResult.setAnalysisParameter(analysisParameter);
                parameterAnalysisResult.setLabWorkOrder(labWorkOrder);
                parameterAnalysisResult=parameterAnalysisResultRepository.save(parameterAnalysisResult);
            }
        }
        return prescriptionMapper.toDto(prescription);
    }
}
