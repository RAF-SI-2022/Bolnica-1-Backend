package raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PatientStateFilter {
    private Long hospitalizationId;
    private Date startDate;
    private Date endDate;


    public boolean applyFilter(PatientStateDto patientStateDto){

        try {
            if (startDate!=null && patientStateDto.getDateExamState().getTime()<startDate.getTime()) return false;
            if (endDate!=null && patientStateDto.getDateExamState().getTime()>endDate.getTime())return false;
            if(hospitalizationId!=null && !patientStateDto.getHospitalizationId().equals(hospitalizationId))return false;

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<PatientStateDto> applyFilterToList(List<PatientStateDto> patientStateDtoList){

        List<PatientStateDto> ret=new ArrayList<>();

        for(PatientStateDto patientStateDto:patientStateDtoList)
            if(applyFilter(patientStateDto))
                ret.add(patientStateDto);

        return ret;
    }


    @Override
    public String toString(){
        return "hospId: "+hospitalizationId+"  | startDate: "+startDate+"  | endDate: "+endDate;
    }

}
