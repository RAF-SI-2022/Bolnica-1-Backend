package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledLabExaminationDtoFilter {

    private String lbp;
    private Date startDate;
    private Date endDate;
    private Long departmentId;

    public boolean applyFilter(ScheduledLabExaminationDto o){

        try {

            if(lbp!=null && !lbp.equals(o.getLbp()))return false;
            if(startDate!=null && startDate.getTime()>o.getScheduledDate().getTime())return false;
            if(endDate!=null && endDate.getTime()<o.getScheduledDate().getTime())return false;
            if(departmentId!=null && !departmentId.equals(o.getDepartmentId()))return false;

            return true;

        }catch (Exception e){
            return false;
        }

    }

    public List<ScheduledLabExaminationDto> applyFilterToList(List<ScheduledLabExaminationDto> list){

        List<ScheduledLabExaminationDto> ret=new ArrayList<>();

        for(ScheduledLabExaminationDto o:list)
            if(applyFilter(o))
                ret.add(o);

        return ret;
    }


    @Override
    public String toString(){
        return "lbp: "+lbp+" | startDate: "+startDate+" | endDate: "+endDate+" | depId: "+departmentId;
    }

}
