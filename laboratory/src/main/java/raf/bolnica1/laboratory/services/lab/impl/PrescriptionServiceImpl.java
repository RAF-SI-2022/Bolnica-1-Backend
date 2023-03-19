package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.exceptions.prescription.PrescriptionNotFoundException;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Override
    public PrescriptionDto createPrescription(Object dto) {
        return null;
    }

    @Override
    public PrescriptionDto updatePrescription(Object dto) {
        return null;
    }

    @Override
    public Object deletePrescription(Long id) {
        return null;
    }

    @Override
    public PrescriptionDto getPrescription(Long id) {
        return null;
    }

    @Override
    public void updatePrescriptionStatus(Long id, PrescriptionStatus status) {
        Prescription p = prescriptionRepository.findById(id).orElseThrow(() ->
                new PrescriptionNotFoundException("Could not find prescription with id %s")
        );
        p.setStatus(status);
        prescriptionRepository.save(p);
    }
}
