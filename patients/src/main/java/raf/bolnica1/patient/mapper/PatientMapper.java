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

    public Patient patientDtoToPatient(PatientDto dto){
        Patient patient = new Patient();

        if(dto.getId() != null)
            patient.setId(dto.getId());
        if(dto.getLbp() != null && !dto.getLbp().equals(""))
            patient.setLbp(dto.getLbp());
        patient.setJmbg(dto.getJmbg());
        patient.setName(dto.getName());
        patient.setParentName(dto.getParentName());
        patient.setSurname(dto.getSurname());
        patient.setGender(dto.getGender());
        patient.setDateOfBirth(dto.getDateOfBirth());
        if(dto.getDateAndTimeOfDeath() != null && !dto.getDateAndTimeOfDeath().equals(""))
            patient.setDateAndTimeOfDeath(dto.getDateAndTimeOfDeath());
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

    public Patient patientDtoToPatientGeneralDataUpdate(Patient patient, PatientGeneralDto dto){

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

    public List<PatientDto> allToDto(List<Patient> entityList){
        List<PatientDto> dtoList = new ArrayList<>();
        for(Patient patient: entityList){
            dtoList.add(patientToPatientDto(patient));
        }
        return dtoList;
    }

    public List<Patient> allToEntity(List<PatientDto> dtoList){
        List<Patient> entityList = new ArrayList<>();
        for(PatientDto dto: dtoList){
            entityList.add(patientDtoToPatient(dto));
        }
        return entityList;
    }


    public void compareAndSet(PatientDto dto, Patient patient){
        if(dto.getJmbg() != null && !dto.getJmbg().equals(""))
            patient.setJmbg(dto.getJmbg());
        if(dto.getName() != null && !dto.getName().equals(""))
            patient.setName(dto.getName());
        if(dto.getParentName() != null && !dto.getParentName().equals(""))
            patient.setParentName(dto.getParentName());
        if(dto.getSurname() != null && !dto.getSurname().equals(""))
            patient.setSurname(dto.getSurname());
        if(dto.getGender() != null)
            patient.setGender(dto.getGender());
        if(dto.getDateOfBirth() != null && !dto.getDateOfBirth().equals(""))
            patient.setDateOfBirth(dto.getDateOfBirth());
        if(dto.getDateAndTimeOfDeath() != null && !dto.getDateAndTimeOfDeath().equals(""))
            patient.setDateAndTimeOfDeath(dto.getDateAndTimeOfDeath());
        if(dto.getBirthPlace() != null && !dto.getBirthPlace().equals(""))
            patient.setBirthPlace(dto.getBirthPlace());
        if(dto.getPlaceOfLiving() != null && !dto.getPlaceOfLiving().equals(""))
            patient.setPlaceOfLiving(dto.getPlaceOfLiving());
        if(dto.getCitizenship() != null && !dto.getCitizenship().equals(""))
            patient.setCitizenship(dto.getCitizenship());
        if(dto.getPhone() != null && !dto.getPhone().equals(""))
            patient.setPhone(dto.getPhone());
        if(dto.getEmail() != null && !dto.getEmail().equals(""))
            patient.setEmail(dto.getEmail());
        if(dto.getGuardianJmbg() != null && !dto.getGuardianJmbg().equals(""))
            patient.setGuardianJmbg(dto.getGuardianJmbg());
        if(dto.getGuardianNameAndSurname() != null && !dto.getGuardianNameAndSurname().equals(""))
            patient.setGuardianNameAndSurname(dto.getGuardianNameAndSurname());

        if(dto.getMaritalStatus() != null)
            patient.getSocialData().setMaritalStatus(dto.getMaritalStatus());
        if(dto.getNumOfChildren() >= 0)
            patient.getSocialData().setNumOfChildren(dto.getNumOfChildren());
        if(dto.getExpertiseDegree() != null)
            patient.getSocialData().setExpertiseDegree(dto.getExpertiseDegree());
        if(dto.getProfession() != null && !dto.getProfession().equals(""))
            patient.getSocialData().setProfession(dto.getProfession());
        if(dto.getFamilyStatus() != null)
            patient.getSocialData().setFamilyStatus(dto.getFamilyStatus());

    }
}
