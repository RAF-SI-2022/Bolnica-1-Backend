package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.SocialData;
import raf.bolnica1.patient.dto.create.PatientGeneralDto;
import raf.bolnica1.patient.dto.general.PatientDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientMapper {

    public Patient patientDtoToPatientGeneralData(PatientGeneralDto dto){
        Patient patient = new Patient();
        patient.setLbp(dto.getLbp());
        patient.setDeleted(false);
        setPatientGeneralData(dto, patient);

        return patient;
    }

    public Patient setPatientGeneralData(PatientGeneralDto dto, Patient patient){
        patient.setJmbg(dto.getJmbg());
        patient.setName(dto.getName());
        patient.setParentName(dto.getParentName());
        patient.setSurname(dto.getSurname());
        patient.setGender(dto.getGender());
        patient.setDateOfBirth(dto.getDateOfBirth());

        patient.setDateAndTimeOfDeath(dto.getDateAndTimeOfDeath());
        patient.setBirthPlace(dto.getBirthPlace());
        patient.setPlaceOfLiving(dto.getPlaceOfLiving());
        patient.setCitizenship(dto.getCitizenship());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setGuardianJmbg(dto.getGuardianJmbg());
        patient.setGuardianNameAndSurname(dto.getGuardianNameAndSurname());

        return patient;
    }

    public SocialData patientDtoToPatientSocialData(PatientGeneralDto dto) {
        SocialData socialData = new SocialData();
        socialData.setMaritalStatus(dto.getMaritalStatus());
        socialData.setNumOfChildren(dto.getNumOfChildren());
        socialData.setExpertiseDegree(dto.getExpertiseDegree());
        socialData.setProfession(dto.getProfession());
        socialData.setFamilyStatus(dto.getFamilyStatus());

        return socialData;
    }

    public PatientDto patientToPatientDto(Patient patient){
        PatientDto dto = new PatientDto();


        dto.setId(patient.getId());
        dto.setLbp(patient.getLbp());
        dto.setJmbg(patient.getJmbg());
        dto.setName(patient.getName());
        dto.setParentName(patient.getParentName());
        dto.setSurname(patient.getSurname());
        dto.setGender(patient.getGender());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setDateAndTimeOfDeath(patient.getDateAndTimeOfDeath());
        dto.setBirthPlace(patient.getBirthPlace());
        dto.setPlaceOfLiving(patient.getPlaceOfLiving());
        dto.setCitizenship(patient.getCitizenship());
        dto.setPhone(patient.getPhone());
        dto.setEmail(patient.getEmail());
        dto.setDeleted(patient.isDeleted());
        dto.setGuardianJmbg(patient.getGuardianJmbg());
        dto.setGuardianNameAndSurname(patient.getGuardianNameAndSurname());

        dto.setMaritalStatus(patient.getSocialData().getMaritalStatus());
        dto.setNumOfChildren(patient.getSocialData().getNumOfChildren());
        dto.setExpertiseDegree(patient.getSocialData().getExpertiseDegree());
        dto.setProfession(patient.getSocialData().getProfession());
        dto.setFamilyStatus(patient.getSocialData().getFamilyStatus());

        return dto;
    }

}
