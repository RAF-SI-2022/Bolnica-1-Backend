package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.Parameter;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;

@Component
@AllArgsConstructor
public class ParameterMapper {

    public ParameterDto toDto(Parameter entity) {
        ParameterDto dto = new ParameterDto();
        dto.setId(entity.getId());
        dto.setParameterName(entity.getParameterName());
        dto.setType(entity.getType());
        dto.setUnitOfMeasure(entity.getUnitOfMeasure());
        dto.setLowerLimit(entity.getLowerLimit());
        dto.setUpperLimit(entity.getUpperLimit());
        return dto;
    }

    public Parameter toEntity(ParameterDto dto){

        Parameter entity=new Parameter();

        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setParameterName(dto.getParameterName());
        entity.setUpperLimit(dto.getUpperLimit());
        entity.setLowerLimit(dto.getLowerLimit());
        entity.setUnitOfMeasure(dto.getUnitOfMeasure());

        return entity;
    }

}
