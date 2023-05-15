package raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledAppointmentFilter {

    private String lbp;
    private Long departmentId;
    private AdmissionStatus admissionStatus;
    private Date startDate;
    private Date endDate;


    public boolean applyFilter(ScheduledAppointmentDto scheduledAppointmentDto, PrescriptionRepository prescriptionRepository){

        try {
            if (startDate!=null && scheduledAppointmentDto.getPatientAdmission().getTime()<startDate.getTime()) return false;
            if (endDate!=null && scheduledAppointmentDto.getPatientAdmission().getTime()>=endDate.getTime()+24*60*60*1000)return false;
            Prescription prescription= prescriptionRepository.findPrescriptionById(scheduledAppointmentDto.getPrescriptionId());
            if(lbp!=null && !prescription.getLbp().contains(lbp))return false;
            if(departmentId!=null && !prescription.getDepartmentToId().equals(departmentId))return false;
            if(admissionStatus!=null && !admissionStatus.equals(scheduledAppointmentDto.getAdmissionStatus()))return false;

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<ScheduledAppointmentDto> applyFilterToList(List<ScheduledAppointmentDto> scheduledAppointmentDtos,
                                                           PrescriptionRepository prescriptionRepository){

        List<ScheduledAppointmentDto> ret=new ArrayList<>();

        for(ScheduledAppointmentDto scheduledAppointmentDto:scheduledAppointmentDtos)
            if(applyFilter(scheduledAppointmentDto,prescriptionRepository))
                ret.add(scheduledAppointmentDto);

        return ret;
    }


    @Override
    public String toString(){
        return "status: "+admissionStatus+" lbp: "+lbp+" depId: "+departmentId+"  | startDate: "+startDate+"  | endDate: "+endDate;
    }

}
