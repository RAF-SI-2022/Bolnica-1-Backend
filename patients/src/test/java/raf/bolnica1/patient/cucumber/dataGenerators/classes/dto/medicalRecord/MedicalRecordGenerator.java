package raf.bolnica1.patient.cucumber.dataGenerators.classes.dto.medicalRecord;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.cucumber.dataGenerators.primitives.RandomDate;
import raf.bolnica1.patient.cucumber.dataGenerators.primitives.RandomLong;
import raf.bolnica1.patient.cucumber.dataGenerators.primitives.RandomString;
import raf.bolnica1.patient.domain.AllergyData;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.MedicalRecordCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.VaccinationDataDto;
import raf.bolnica1.patient.dto.general.AllergyDto;

@Component
@AllArgsConstructor
public class MedicalRecordGenerator {

    private final RandomLong randomLong;
    private final RandomString randomString;
    private final RandomDate randomDate;

    public MedicalRecordCreateDto generateMedicalRecordCreateDto(String lbp) {
        MedicalRecordCreateDto medicalRecordCreateDto = new MedicalRecordCreateDto();
        medicalRecordCreateDto.setLbp(lbp);
        medicalRecordCreateDto.setRegistrationDate(randomDate.getFromRandom());

        return medicalRecordCreateDto;
    }

    public GeneralMedicalDataCreateDto generateGeneralMedicalDataCreateDto(){
        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setBloodType("A");
        generalMedicalDataCreateDto.setRH("+");
        generalMedicalDataCreateDto.setAllergyDtos(null);
        generalMedicalDataCreateDto.setVaccinationDtos(null);
        return generalMedicalDataCreateDto;
    }

    public OperationCreateDto generateOperationCreateDto(){
        OperationCreateDto operationCreateDto = new OperationCreateDto();
        operationCreateDto.setOperationDate(randomDate.getFromRandom());
        operationCreateDto.setDescription(randomString.getString(30));
        operationCreateDto.setDepartmentId(randomLong.getLong(10L));
        operationCreateDto.setHospitalId(randomLong.getLong(10L));

        return operationCreateDto;
    }

    public VaccinationDataDto generateVaccinationCreateDto(String name){
        VaccinationDataDto vaccinationDataDto = new VaccinationDataDto();
        vaccinationDataDto.setVaccinationDate(randomDate.getFromRandom());
        vaccinationDataDto.setVaccinationName(name);
        return vaccinationDataDto;
    }


}
