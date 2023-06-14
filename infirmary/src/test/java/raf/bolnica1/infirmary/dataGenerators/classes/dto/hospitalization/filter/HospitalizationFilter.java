package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class HospitalizationFilter {

    private String name;
    private String surname;
    private String jmbg;
    private Long departmentId;
    private Long hospitalRoomId;
    private String lbp;
    private Date startDate;
    private Date endDate;

    public boolean applyFilter(HospitalizationDto hospitalizationDto,HospitalizationRepository hospitalizationRepository){

        try {
            if(hospitalizationDto.getDischargeDateAndTime()!=null)return false;
            if (startDate!=null && hospitalizationDto.getPatientAdmission().getTime()<startDate.getTime()) return false;
            if (endDate!=null && hospitalizationDto.getPatientAdmission().getTime()>=(endDate.getTime()+24*60*60*1000) ) return false;
            Hospitalization hospitalization = hospitalizationRepository.findHospitalizationById(hospitalizationDto.getId());
            if(hospitalRoomId!=null && !hospitalRoomId.equals(hospitalization.getHospitalRoom().getId()))return false;
            if(departmentId!=null && !departmentId.equals(hospitalization.getHospitalRoom().getIdDepartment()))return false;
            if(name!=null && !hospitalizationDto.getName().contains(name))return false;
            if(surname!=null && !hospitalizationDto.getSurname().contains(surname))return false;
            if(jmbg!=null && !hospitalizationDto.getJmbg().contains(jmbg))return false;
            if(lbp!=null && !hospitalization.getPrescription().getLbp().contains(lbp))return false;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<HospitalizationDto> applyFilterToList(List<HospitalizationDto> hospitalizationDtoList,HospitalizationRepository hospitalizationRepository){

        List<HospitalizationDto> ret=new ArrayList<>();

        for(HospitalizationDto hospitalizationDto:hospitalizationDtoList)
            if(applyFilter(hospitalizationDto,hospitalizationRepository))
                ret.add(hospitalizationDto);

        return ret;
    }


    @Override
    public String toString(){
        return "depId: "+departmentId+"  | roomId: "+hospitalRoomId+"  | name: "+name+" |  surname: "+surname+" |  lbp: "+lbp+" | jmbg: "+jmbg+"  | startDate: "+startDate+"  | endDate: "+endDate;
    }


}
