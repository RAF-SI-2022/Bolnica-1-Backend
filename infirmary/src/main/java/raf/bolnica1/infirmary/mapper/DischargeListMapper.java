package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.dto.externalPatientService.DischargeListCreateDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class DischargeListMapper {


    public static DischargeListMapper getInstance(){
        return new DischargeListMapper();
    }


    public CreateDischargeListDto toDto(DischargeListDto dto){

        if(dto==null)return null;

        CreateDischargeListDto ret=new CreateDischargeListDto();

        ret.setAnalysis(dto.getAnalysis());
        ret.setAnamnesis(dto.getAnamnesis());
        ret.setCreation(dto.getCreation());
        ret.setFollowingDiagnosis(dto.getFollowingDiagnosis());
        ret.setLbzDepartment(dto.getLbzDepartment());
        ret.setTherapy(dto.getTherapy());
        ret.setSummary(dto.getSummary());
        ret.setLbzPrescribing(dto.getLbzPrescribing());
        ret.setCourseOfDisease(dto.getCourseOfDisease());
        ret.setHospitalizationId(dto.getHospitalizationId());
        ret.setDied(dto.isDied());

        return ret;
    }

    public DischargeListDto toDto(DischargeList entity){
        if(entity==null)return null;

        DischargeListDto dto=new DischargeListDto();

        dto.setId(entity.getId());
        dto.setAnamnesis(entity.getAnamnesis());
        dto.setAnalysis(entity.getAnalysis());
        dto.setCreation(entity.getCreation());
        dto.setSummary(entity.getSummary());
        dto.setTherapy(entity.getTherapy());
        dto.setFollowingDiagnosis(entity.getFollowingDiagnosis());
        dto.setLbzDepartment(entity.getLbzDepartment());
        dto.setLbzPrescribing(entity.getLbzPrescribing());
        dto.setHospitalizationId(entity.getHospitalization().getId());
        dto.setCourseOfDisease(entity.getCourseOfDisease());
        dto.setDied(entity.isDied());

        return dto;
    }

    public List<DischargeListDto> toDto(List<DischargeList> entity){

        if(entity==null)return null;

        List<DischargeListDto> dto=new ArrayList<>();

        for(DischargeList dischargeList:entity)
            dto.add(toDto(dischargeList));

        return dto;
    }


    /// kreiranje
    public DischargeList toEntity(CreateDischargeListDto dto, HospitalizationRepository hospitalizationRepository){

        if(dto==null)return null;

        DischargeList entity=new DischargeList();

        entity.setAnamnesis(dto.getAnamnesis());
        entity.setAnalysis(dto.getAnalysis());
        entity.setCreation(dto.getCreation());
        entity.setSummary(dto.getSummary());
        entity.setTherapy(dto.getTherapy());
        entity.setFollowingDiagnosis(dto.getFollowingDiagnosis());
        entity.setLbzDepartment(dto.getLbzDepartment());
        entity.setLbzPrescribing(dto.getLbzPrescribing());
        entity.setHospitalization(hospitalizationRepository.findHospitalizationById(dto.getHospitalizationId()));
        entity.setCourseOfDisease(dto.getCourseOfDisease());
        entity.setDied(dto.isDied());

        return entity;
    }

    public DischargeList toEntity(DischargeListDto dto, HospitalizationRepository hospitalizationRepository){

        if(dto==null)return null;

        DischargeList entity=new DischargeList();

        entity.setAnamnesis(dto.getAnamnesis());
        entity.setAnalysis(dto.getAnalysis());
        entity.setCreation(dto.getCreation());
        entity.setSummary(dto.getSummary());
        entity.setTherapy(dto.getTherapy());
        entity.setFollowingDiagnosis(dto.getFollowingDiagnosis());
        entity.setLbzDepartment(dto.getLbzDepartment());
        entity.setLbzPrescribing(dto.getLbzPrescribing());
        entity.setHospitalization(hospitalizationRepository.findHospitalizationById(dto.getHospitalizationId()));
        entity.setCourseOfDisease(dto.getCourseOfDisease());
        entity.setDied(dto.isDied());

        return entity;
    }

    public DischargeListCreateDto forPatientToDto(DischargeList dischargeList){
        DischargeListCreateDto dischargeListCreateDto = new DischargeListCreateDto();
        dischargeListCreateDto.setCreation(dischargeList.getCreation());
        dischargeListCreateDto.setDied(dischargeList.isDied());
        dischargeListCreateDto.setSummary(dischargeList.getSummary());
        dischargeListCreateDto.setCourseOfDisease(dischargeList.getCourseOfDisease());
        dischargeListCreateDto.setHospitalization(dischargeList.getHospitalization().getPatientAdmission());
        dischargeListCreateDto.setAnalysis(dischargeList.getAnalysis());
        dischargeListCreateDto.setAnamnesis(dischargeList.getAnamnesis());
        dischargeListCreateDto.setLbp(dischargeList.getHospitalization().getPrescription().getLbp());
        dischargeListCreateDto.setTherapy(dischargeList.getTherapy());
        dischargeListCreateDto.setFollowingDiagnosis(dischargeList.getFollowingDiagnosis());

        return dischargeListCreateDto;
    }

}
