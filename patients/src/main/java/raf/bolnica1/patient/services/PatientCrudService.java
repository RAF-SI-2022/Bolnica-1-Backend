package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientGeneralDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.PatientDto;

public interface PatientCrudService {
    //Registracija pacijenta
    PatientDto registerPatient(PatientCreateDto patientCreateDto);

    //Azuriranje podataka pacijenta
    PatientDto updatePatient(PatientUpdateDto dto);

    //Brisanje pacijenta
    MessageDto deletePatient(String lbp);

    Page<PatientDto> filterPatients(String lbp, String jmbg, String name, String surname, int page, int size);

    //Pretraga pacijenta
    PatientDto findPatient(String lbp);
}
