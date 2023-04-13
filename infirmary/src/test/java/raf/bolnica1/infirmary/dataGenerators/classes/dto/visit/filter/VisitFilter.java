package raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.bs.A;
import io.cucumber.java.mk_latn.No;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VisitFilter {

    private Long departmentId;
    private Long hospitalRoomId;
    private Long hospitalizationId;
    private Date startDate;
    private Date endDate;


    public boolean applyFilter(VisitDto visitDto, HospitalizationRepository hospitalizationRepository,ObjectMapper objectMapper){

        try {
            if (startDate!=null && visitDto.getVisitTime().getTime()<startDate.getTime()) return false;
            if (endDate!=null && visitDto.getVisitTime().getTime()>=(endDate.getTime()+24*60*60*1000) ) return false;
            if(hospitalizationId!=null && !visitDto.getHospitalizationId().equals(hospitalizationId))return false;
            Hospitalization hospitalization = hospitalizationRepository.findHospitalizationById(visitDto.getHospitalizationId());
            if(hospitalRoomId!=null && !hospitalRoomId.equals(hospitalization.getHospitalRoom().getId()))return false;
            if(departmentId!=null && !departmentId.equals(hospitalization.getHospitalRoom().getIdDepartment()))return false;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<VisitDto> applyFilterToList(List<VisitDto> visitDtoList,HospitalizationRepository hospitalizationRepository,ObjectMapper objectMapper){

        List<VisitDto> ret=new ArrayList<>();

        for(VisitDto visitDto:visitDtoList)
            if(applyFilter(visitDto,hospitalizationRepository,objectMapper))
                ret.add(visitDto);

        return ret;
    }


    @Override
    public String toString(){
        return "depId: "+departmentId+"  | roomId: "+hospitalRoomId+"  | hospId: "+hospitalizationId+"  | startDate: "+startDate+"  | endDate: "+endDate;
    }

}
