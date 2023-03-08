package raf.bolnica1.patient.mapper;

import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.SocialData;
import raf.bolnica1.patient.dto.PatientDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PatientMapper {

    public static Patient patientDtoToPatient(PatientDto dto){
        Patient patient = new Patient();

        patient.setJmbg(dto.getJmbg());
        patient.setName(dto.getName());
        patient.setParentName(dto.getParentName());
        patient.setSurname(dto.getSurname());
        patient.setGender(dto.getGender());
        patient.setDateOfBirth(Date.valueOf(dto.getDateOfBirth()));
        if(!dto.getDateAndTimeOfDeath().equals(""))
            patient.setDateAndTimeOfDeath(Timestamp.valueOf(dto.getDateAndTimeOfDeath()));
        patient.setBirthPlace(dto.getBirthPlace());
        patient.setPlaceOfLiving(dto.getPlaceOfLiving());
        patient.setCitizenship(dto.getCitizenship());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setGuardianJmbg(dto.getGuardianJmbg());
        patient.setGuardianNameAndSurname(dto.getGuardianNameAndSurname());
        patient.setDeleted(false);

        SocialData socialData = new SocialData();
        socialData.setMaritalStatus(dto.getMaritalStatus());
        socialData.setNumOfChildren(dto.getNumOfChildren());
        socialData.setExpertiseDegree(dto.getExpertiseDegree());
        socialData.setProfession(dto.getProfession());
        socialData.setFamilyStatus(dto.getFamilyStatus());

        patient.setSocialData(socialData);

        return patient;
    }

    public static PatientDto patientToPatientDto(Patient patient){
        PatientDto dto = new PatientDto();

        if(patient.getLbp() != null)
            dto.setLbp(patient.getLbp());
        dto.setJmbg(patient.getJmbg());
        dto.setName(patient.getName());
        dto.setParentName(patient.getParentName());
        dto.setSurname(patient.getSurname());
        dto.setGender(patient.getGender());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());
        if(patient.getDateAndTimeOfDeath() != null)
            dto.setDateAndTimeOfDeath(patient.getDateAndTimeOfDeath().toString());
        dto.setBirthPlace(patient.getBirthPlace());
        dto.setPlaceOfLiving(patient.getPlaceOfLiving());
        dto.setCitizenship(patient.getCitizenship());
        dto.setPhone(patient.getPhone());
        dto.setEmail(patient.getEmail());
        dto.setGuardianJmbg(patient.getGuardianJmbg());
        dto.setGuardianNameAndSurname(patient.getGuardianNameAndSurname());

        dto.setMaritalStatus(patient.getSocialData().getMaritalStatus());
        dto.setNumOfChildren(patient.getSocialData().getNumOfChildren());
        dto.setExpertiseDegree(patient.getSocialData().getExpertiseDegree());
        dto.setProfession(patient.getSocialData().getProfession());
        dto.setFamilyStatus(patient.getSocialData().getFamilyStatus());

        return dto;
    }

    public static List<PatientDto> allToDto(List<Patient> entityList){
        List<PatientDto> dtoList = new ArrayList<>();
        for(Patient patient: entityList){
            dtoList.add(patientToPatientDto(patient));
        }
        return dtoList;
    }

    public static List<Patient> allToEntity(List<PatientDto> dtoList){
        List<Patient> entityList = new ArrayList<>();
        for(PatientDto dto: dtoList){
            entityList.add(patientDtoToPatient(dto));
        }
        return entityList;
    }

}
