package raf.bolnica1.patient.cucumber.dataGenerators.classes.dto.patient;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.cucumber.dataGenerators.primitives.RandomDate;
import raf.bolnica1.patient.cucumber.dataGenerators.primitives.RandomPatientData;
import raf.bolnica1.patient.cucumber.dataGenerators.primitives.RandomString;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.create.PatientCreateDto;

@Component
@AllArgsConstructor
public class PatientCreateDtoGenerator {
    private final RandomString randomString;
    private final RandomPatientData randomNames;
    private final RandomDate randomDate;

    public PatientCreateDto generatePatientCreateDto(){
        PatientCreateDto patientCreateDto = new PatientCreateDto();
        patientCreateDto.setCitizenship(CountryCode.ITA);
        patientCreateDto.setBirthPlace(randomString.getString(10));
        patientCreateDto.setEmail(randomString.getEmail(10));
        patientCreateDto.setGender(Gender.MUSKO);
        patientCreateDto.setDateAndTimeOfDeath(null);
        patientCreateDto.setLbp(randomString.getString(6));
        patientCreateDto.setPhone(randomString.getString(10));
        patientCreateDto.setName(randomNames.getNameFromRandom());
        patientCreateDto.setSurname(randomNames.getSurnameFromRandom());
        patientCreateDto.setJmbg(randomNames.getJmbgFromRandom());
        patientCreateDto.setRegisterDate(randomDate.getFromRandom());
        patientCreateDto.setLbp(randomNames.getLbpFromRandom());

        return patientCreateDto;
    }

}
