package raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PrescriptionFilter {

    private String lbp;
    private Long departmentId;
    private PrescriptionStatus prescriptionStatus;

    public boolean applyFilter(PrescriptionDto prescriptionDto){

        try {
            if(lbp!=null && !lbp.equals(prescriptionDto.getLbp()))return false;
            if(departmentId!=null && !departmentId.equals(prescriptionDto.getDepartmentToId()))return false;
            if(prescriptionStatus!=null && !prescriptionStatus.equals(prescriptionDto.getStatus()))return false;

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<PrescriptionDto> applyFilterToList(List<PrescriptionDto> prescriptionDtos){

        List<PrescriptionDto> ret=new ArrayList<>();

        for(PrescriptionDto prescriptionDto:prescriptionDtos)
            if(applyFilter(prescriptionDto))
                ret.add(prescriptionDto);

        return ret;
    }


    @Override
    public String toString(){
        return "depId: "+departmentId+"  | lbp: "+lbp+"  | status: "+prescriptionStatus;
    }


}
