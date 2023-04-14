package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.DiagnosisCodeDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public Prescription getPrescription(){

        Prescription ret=new Prescription();

        ret.setStatus(PrescriptionStatus.values()[randomLong.getLong(new Long(PrescriptionStatus.values().length) ).intValue()]);
        ret.setType(PrescriptionType.values()[randomLong.getLong(new Long(PrescriptionType.values().length) ).intValue()]);
        ret.setLbp(randomLBP.getFromRandom());
        ret.setCreationDateTime(randomTimestamp.getFromRandom());
        ret.setDepartmentToId(randomLong.getLong(4L));
        ret.setReferralDiagnosis(randomString.getString(10));
        ret.setDoctorLbz(randomString.getString(10));
        ret.setReferralReason(randomString.getString(10));
        ret.setDepartmentFromId(randomLong.getLong(4L));

        return ret;
    }


    public Prescription getPrescriptionWithDBSave(PrescriptionRepository prescriptionRepository){
        return prescriptionRepository.save(getPrescription());
    }


}
