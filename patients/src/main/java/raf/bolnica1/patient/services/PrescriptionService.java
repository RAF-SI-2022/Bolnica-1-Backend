package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionSendDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionUpdateDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabUpdateDto;

public interface PrescriptionService {

    MessageDto sendPersctiption(PrescriptionSendDto prescriptionSendDto);

    Page<PrescriptionDto> getPrescriptionsForPatient(Long doctorId, String lbp, String token, int page, int size);

    MessageDto updatePrescription(PrescriptionUpdateDto prescriptionLabUpdateDto);

    MessageDto deletePresscription(Long prescriptionId);
}
