package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.dto.create.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.general.*;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionNewDto;

import java.sql.Date;
import java.util.ArrayList;

public interface PrescriptionService {

    MessageDto sendPersctiption(PrescriptionSendDto prescriptionSendDto);

    Page<PrescriptionNewDto> getPrescriptionsForPatient(String lbz, String lbp, String token, int page, int size);

    MessageDto updatePrescription(PrescriptionUpdateDto prescriptionLabUpdateDto);

    MessageDto deletePresscription(Long prescriptionId);

    Page<PrescriptionDoneDto> getAllDonePrescriptionsForPatient(String lbp, Date dateFrom, Date dateTo, int page, int size);

    PrescriptionDoneDto getPrescription(Long prescriptionId, String authorization);

    void createPrescription(PrescriptionCreateDto prescriptionCreateDto);
}
