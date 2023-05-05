package raf.bolnica1.infirmary.dataGenerators.classes.dto.dischargeList.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DischargeListFilter {

    private String lbp;
    private Date startDate;
    private Date endDate;
    private Long hospitalizationId;

    public boolean applyFilter(DischargeListDto dischargeListDto, HospitalizationRepository hospitalizationRepository){
        try{

            if (startDate!=null && dischargeListDto.getCreation().getTime()<startDate.getTime()) return false;
            if (endDate!=null && dischargeListDto.getCreation().getTime()>=(endDate.getTime()+24*60*60*1000) ) return false;
            if(lbp!=null && lbp!=hospitalizationRepository.findHospitalizationById(dischargeListDto.getHospitalizationId())
                    .getPrescription().getLbp())return false;
            if(hospitalizationId!=null && dischargeListDto.getHospitalizationId()!=hospitalizationId)return false;

            return true;

        }catch (Exception e){
            return false;
        }
    }

    public List<DischargeListDto> applyFilterToList(List<DischargeListDto> dischargeListDtos, HospitalizationRepository hospitalizationRepository){

        List<DischargeListDto> ret=new ArrayList<>();

        for(DischargeListDto dischargeListDto:dischargeListDtos)
            if(applyFilter(dischargeListDto,hospitalizationRepository))
                ret.add(dischargeListDto);

        return ret;
    }


    @Override
    public String toString(){
        return "lbp: "+lbp+"  | startDate: "+startDate+"  | endDate: "+endDate+" | hosId: "+hospitalizationId;
    }

}
