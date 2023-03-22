package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;

public interface PrescriptionRecieveService {

    PrescriptionDto createPrescription(PrescriptionCreateDto dto);
}
