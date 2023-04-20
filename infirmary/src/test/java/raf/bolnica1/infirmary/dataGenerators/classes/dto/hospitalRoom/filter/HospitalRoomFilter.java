package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HospitalRoomFilter {

    private Long departmentId;

    public boolean applyFilter(HospitalRoomDto hospitalRoomDto){

        try {
            if(departmentId!=null && !departmentId.equals(hospitalRoomDto.getIdDepartment()))return false;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<HospitalRoomDto> applyFilterToList(List<HospitalRoomDto> hospitalRoomDtoList){

        List<HospitalRoomDto> ret=new ArrayList<>();

        for(HospitalRoomDto hospitalRoomDto:hospitalRoomDtoList)
            if(applyFilter(hospitalRoomDto))
                ret.add(hospitalRoomDto);

        return ret;
    }

}
