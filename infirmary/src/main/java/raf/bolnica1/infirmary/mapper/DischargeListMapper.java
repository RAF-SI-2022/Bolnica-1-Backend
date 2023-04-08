package raf.bolnica1.infirmary.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.dto.DtoDischargeList;
@Component
public class DischargeListMapper {

    public DtoDischargeList toDto(DischargeList dischargeList){


       DtoDischargeList dto = new DtoDischargeList();
        dto.setId(dischargeList.getId());
        dto.setFollowingDiagnosis(dischargeList.getFollowingDiagnosis());
        dto.setAnalysis(dischargeList.getAnalysis());
        dto.setAnamnesis(dischargeList.getAnamnesis());
        dto.setCourseOfDisease(dischargeList.getCourseOfDisease());
        dto.setSummary(dischargeList.getSummary());
        dto.setTherapy(dischargeList.getTherapy());
        dto.setLbzDepartment(dischargeList.getLbzDepartment());
        dto.setLbzPrescribing(dischargeList.getLbzPrescribing());
        dto.setCreation(dischargeList.getCreation());
        dto.setHospitalizationId(dischargeList.getHospitalization().getId());


        return dto;
    }
}
