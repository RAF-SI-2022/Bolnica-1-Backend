package raf.bolnica1.laboratory.services.lab;

import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.dto.prescription.*;

import java.util.ArrayList;

public interface PrescriptionRecieveService {

    void createPrescription(PrescriptionCreateDto dto);

    void updatePrescription(PrescriptionUpdateDto dto);

    void deletePrescription(Long id, String lbz);

    Page<PrescriptionDto> findPrescriptionsForPatient(String lbp, String doctorLbz, int page, int size);


    PrescriptionDoneDto findPrescription(Long id);

    ArrayList<PrescriptionDto> findPrescriptionsForPatientRest(String lbp, String doctorLbz);
}
