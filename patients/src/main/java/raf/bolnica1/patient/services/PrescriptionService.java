package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.PrescriptionDto;

public interface PrescriptionService {

    MessageDto sendPersctiption(PrescriptionCreateDto perscriptionCreateDto);

    Page<PrescriptionDto> getPrescriptionsForPatient(Long doctorId, String lbp, String token, int page, int size);

    PrescriptionCreateDto updatePrescription();

    MessageDto deletePresscription(Long prescriptionId, String token);
}
