package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Allergy;
import raf.bolnica1.patient.dto.general.AllergyDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllergyMapper {

    public AllergyDto toDto(Allergy allergy){
        if(allergy==null)return null;

        AllergyDto dto=new AllergyDto();

        dto.setName(allergy.getName());

        return dto;
    }

    public List<AllergyDto> toDto(List<Allergy> allergies){
        if(allergies==null)return null;

        List<AllergyDto> dto=new ArrayList<>();
        for(Allergy allergy:allergies)
            dto.add(toDto(allergy));

        return dto;
    }

    public Allergy toEntity(AllergyDto allergyDto){
        if(allergyDto==null)return null;

        Allergy entity=new Allergy();

        entity.setName(allergyDto.getName());

        return entity;
    }

}
