package raf.bolnica1.employees.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.dto.department.HospitalDto;

@AllArgsConstructor
@Component
public class HospitalMapper {

    public HospitalDto toDto(Hospital entity){

        if(entity==null)return null;

        HospitalDto dto=new HospitalDto();

        dto.setId(entity.getId());
        dto.setName(entity.getFullName());

        return dto;
    }


}
