package raf.bolnica1.patient.services;

import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.dto.*;


import java.sql.Date;
import java.util.List;


public interface PatientService {



    //Registracija pacijenta
    public PatientDto registerPatient(PatientDto dto);

    //Azuriranje podataka pacijenta
    public PatientDto updatePatient(PatientDto dto);

    //Brisanje pacijenta
    public boolean deletePatient(String lbp);

    public List<PatientDto> filterPatients(String lbp, String jmbg, String name, String surname);

    //Pretraga pacijenta
    public Object findPatient(Object object);

    //Pretraga pacijenta preko LBP-a
    public Patient findPatientLBP(String lbp);

    //Dobijanje istorije bolesti pacijenta
    public List<PatientDtoDesease> hisotryOfDeseasePatient(String  lbp, Long mkb10);

    //Svi izvestaji
    //Dohvatanje izvestaja pregleda preko lbp-a pacijenta i preko konkretnog datuma
    public List<PatientDtoReport>  findReportPatientByCurrDate(String lbp, Date currDate);

    //Dohvatanje izvestaja pregleda preko lbp-a pacijenta i preko raspona datuma od-do
    public List<PatientDtoReport> findReportPatientByFromAndToDate(String lbp,Date fromDate,Date toDate);

    //Svi kartoni

    //m22
    public MedicalRecordDto findMedicalRecordByLbp(String lbp);


    //Dohvatanje GeneralMedicalData po LBP(GMD,vaccines,allergies)
    public GeneralMedicalDataDto findGeneralMedicalDataByLbp(String lbp);

    ///Dohvatanje liste operacije po LBP
    public List<OperationDto> findOperationsByLbp(String lbp);

    ///Dohvatanje liste MedicalHistory po LBP
    public List<MedicalHistoryDto> findMedicalHystoryByLbp(String lbp);

}
