package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Operation;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.general.OperationDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperationMapper {

    public OperationDto toDto(Operation entity){
        if(entity==null)return null;

        OperationDto dto=new OperationDto();

        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setOperationDate(entity.getOperationDate());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setHospitalId(entity.getHospitalId());

        return dto;
    }

    public List<OperationDto> toDto(List<Operation> operations){
        if(operations==null)return null;

        List<OperationDto> dto=new ArrayList<>();

        for(Operation operation:operations)
            dto.add(toDto(operation));

        return dto;
    }


    public Operation toEntity(OperationCreateDto dto, MedicalRecord medicalRecord){
        if(dto==null)return null;

        Operation entity=new Operation();

        entity.setDepartmentId(dto.getDepartmentId());
        entity.setDescription(dto.getDescription());
        entity.setOperationDate(dto.getOperationDate());
        entity.setHospitalId(dto.getHospitalId());
        entity.setMedicalRecord(medicalRecord);

        return entity;
    }


}
