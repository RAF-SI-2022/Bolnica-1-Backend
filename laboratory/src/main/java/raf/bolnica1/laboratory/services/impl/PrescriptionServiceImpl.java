package raf.bolnica1.laboratory.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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
    @Caching(evict = {
            @CacheEvict(value = "pres", key = "#id"),
            @CacheEvict(value = "presForRest", allEntries = true),
            @CacheEvict(value = "presForNotRealized", allEntries = true),
            @CacheEvict(value = "patPres", allEntries = true)
    })
    public void updatePrescriptionStatus(Long id, PrescriptionStatus status) {
        Prescription p = prescriptionRepository.findById(id).orElseThrow(() ->
                new PrescriptionNotFoundException("Could not find prescription with id %s")
        );
        p.setStatus(status);
        prescriptionRepository.save(p);
    }
}
