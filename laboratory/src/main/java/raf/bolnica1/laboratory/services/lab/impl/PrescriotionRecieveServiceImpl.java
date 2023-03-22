package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.mappers.PrescriptionMapper;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.PrescriptionRecieveService;

@Service
@AllArgsConstructor
public class PrescriotionRecieveServiceImpl implements PrescriptionRecieveService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public PrescriptionDto createPrescription(PrescriptionCreateDto dto) {
        Prescription prescription = prescriptionMapper.toEntity(dto);
        prescription = prescriptionRepository.save(prescription);
        for(PrescriptionAnalysisDto prescriptionAnalysisDto : dto.getPrescriptionAnalysisDtos()){
            System.out.println(prescriptionAnalysisDto.getAnalysisId());
        }
        return prescriptionMapper.toDto(prescription);
    }
}
