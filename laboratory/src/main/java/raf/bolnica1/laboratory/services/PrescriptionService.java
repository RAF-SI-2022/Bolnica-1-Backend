package raf.bolnica1.laboratory.services;

import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;

public interface PrescriptionService {

    void updatePrescriptionStatus(Long id, PrescriptionStatus status);

}
