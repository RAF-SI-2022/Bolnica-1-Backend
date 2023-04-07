package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;

@Component
@AllArgsConstructor
public class HospitalRoomMapper {


    public HospitalRoomDto toDto(HospitalRoom entity){
        if(entity==null)return null;

        HospitalRoomDto dto=new HospitalRoomDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCapacity(entity.getCapacity());
        dto.setDescription(entity.getDescription());
        dto.setOccupancy(entity.getOccupancy());
        dto.setRoomNumber(entity.getRoomNumber());
        dto.setIdDepartment(entity.getIdDepartment());

        return dto;
    }


    /// za kreiranje
    public HospitalRoom toEntity(HospitalRoomCreateDto dto){

        if(dto==null)return null;

        HospitalRoom entity=new HospitalRoom();

        entity.setOccupancy(0);
        entity.setCapacity(dto.getCapacity());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setRoomNumber(dto.getRoomNumber());
        entity.setIdDepartment(dto.getIdDepartment());

        return entity;
    }

}
