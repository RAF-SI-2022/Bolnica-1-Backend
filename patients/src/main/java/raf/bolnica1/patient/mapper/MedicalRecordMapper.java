package raf.bolnica1.patient.mapper;

import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.dto.MedicalRecordDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordMapper {

    public static MedicalRecordDto toDto(MedicalRecord medicalRecord){

        MedicalRecordDto dto = new MedicalRecordDto();

        dto.setId(medicalRecord.getId());
        dto.setRegistrationDate(medicalRecord.getRegistrationDate());
        dto.setGeneralMedicalData(medicalRecord.getGeneralMedicalData());
        dto.setDeleted(medicalRecord.isDeleted());

        return dto;
    }

    public static List<MedicalRecordDto> allToDto(List<MedicalRecord> list){

        List<MedicalRecordDto> listDto = new ArrayList<>();

        for(MedicalRecord medicalRecord: list){
            listDto.add(toDto(medicalRecord));
        }

        return listDto;
    }

    public static MedicalRecord toEntity (MedicalRecordDto medicalRecordDto){

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setId(medicalRecordDto.getId());
        medicalRecord.setRegistrationDate((Date) medicalRecordDto.getRegistrationDate());
        medicalRecord.setGeneralMedicalData(medicalRecordDto.getGeneralMedicalData());
        medicalRecord.setDeleted(medicalRecordDto.isDeleted());

        return medicalRecord;
    }

    public static List<MedicalRecord> allToEntity(List<MedicalRecordDto> listDto){

        List<MedicalRecord> list = new ArrayList<>();

        for(MedicalRecordDto medicalRecordDto: listDto){
            list.add(toEntity(medicalRecordDto));
        }

        return list;
    }
}
