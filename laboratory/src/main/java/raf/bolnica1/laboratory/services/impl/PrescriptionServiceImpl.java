package raf.bolnica1.laboratory.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.exceptions.prescription.PrescriptionNotFoundException;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.PrescriptionService;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;


    @Override
    public void updatePrescriptionStatus(Long id, PrescriptionStatus status) {
        Prescription p = prescriptionRepository.findById(id).orElseThrow(() ->
                new PrescriptionNotFoundException("Could not find prescription with id %s")
        );
        p.setStatus(status);
        prescriptionRepository.save(p);
    }
}
