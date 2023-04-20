package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;

public interface PrescriptionService {

    PrescriptionDto createPrescription(Object dto);

    PrescriptionDto updatePrescription(Object dto);

    Object deletePrescription(Long id);

    PrescriptionDto getPrescription(Long id);

    void updatePrescriptionStatus(Long id, PrescriptionStatus status);


}
