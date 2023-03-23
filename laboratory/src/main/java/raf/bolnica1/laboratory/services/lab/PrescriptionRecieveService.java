package raf.bolnica1.laboratory.services.lab;

import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionDto;

public interface PrescriptionRecieveService {

    void createPrescription(PrescriptionCreateDto dto);

    void deletePrescription(Long id);

    Page<PrescriptionDto> findPrescriptionsForPatient(String lbp, Long doctorId, int page, int size);


}
