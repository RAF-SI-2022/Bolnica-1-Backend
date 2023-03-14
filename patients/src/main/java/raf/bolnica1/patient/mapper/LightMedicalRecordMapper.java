package raf.bolnica1.patient.mapper;

import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.dto.general.LightMedicalRecordDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LightMedicalRecordMapper {

    public static LightMedicalRecordDto toDto(MedicalRecord medicalRecord){

        LightMedicalRecordDto dto = new LightMedicalRecordDto();

        dto.setId(medicalRecord.getId());
        dto.setRegistrationDate(medicalRecord.getRegistrationDate());
        dto.setGeneralMedicalData(medicalRecord.getGeneralMedicalData());
        dto.setDeleted(medicalRecord.isDeleted());

        return dto;
    }

    public static List<LightMedicalRecordDto> allToDto(List<MedicalRecord> list){

        List<LightMedicalRecordDto> listDto = new ArrayList<>();

        for(MedicalRecord medicalRecord: list){
            listDto.add(toDto(medicalRecord));
        }

        return listDto;
    }

    public static MedicalRecord toEntity (LightMedicalRecordDto medicalRecordDto){

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setId(medicalRecordDto.getId());
        medicalRecord.setRegistrationDate((Date) medicalRecordDto.getRegistrationDate());
        medicalRecord.setGeneralMedicalData(medicalRecordDto.getGeneralMedicalData());
        medicalRecord.setDeleted(medicalRecordDto.isDeleted());

        return medicalRecord;
    }

    public static List<MedicalRecord> allToEntity(List<LightMedicalRecordDto> listDto){

        List<MedicalRecord> list = new ArrayList<>();

        for(LightMedicalRecordDto medicalRecordDto: listDto){
            list.add(toEntity(medicalRecordDto));
        }

        return list;
    }
}
