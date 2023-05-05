package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.config.client.RestServiceClientConfig;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class HospitalizationMapper {

    private final AuthenticationUtils authenticationUtils;
    @Qualifier("patientRestTemplate")
    private final RestTemplate patientRestTemplate;


    public static HospitalizationMapper getInstance(){
        return new HospitalizationMapper(AuthenticationUtils.getInstance(), RestServiceClientConfig.patientRestTemplate());
    }


    /// kreiranje
    public Hospitalization toEntity(HospitalizationCreateDto dto, HospitalRoomRepository hospitalRoomRepository, PrescriptionRepository prescriptionRepository,String authorization){
        if(dto==null)return null;

        Hospitalization entity=new Hospitalization();

        entity.setHospitalRoom(hospitalRoomRepository.findHospitalRoomById(dto.getHospitalRoomId()));
        entity.setNote(dto.getNote());
        entity.setPrescription(prescriptionRepository.findPrescriptionById(dto.getPrescriptionId()));
        entity.setLbzDoctor(dto.getLbzDoctor());
        entity.setLbzRegister(authenticationUtils.getLbzFromAuthentication());
        entity.setPatientAdmission(dto.getPatientAdmission());
        entity.setDischargeDateAndTime(dto.getDischargeDateAndTime());

        String lbp=entity.getPrescription().getLbp();
        String token = authorization.split(" ")[1];

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        String uri=new String("/find_patient/"+lbp);
        ResponseEntity<PatientDto> patient = patientRestTemplate.exchange(uri, HttpMethod.GET, httpEntity, PatientDto.class);
        if(patient.getBody()==null)
            return null;
        entity.setName(patient.getBody().getName());
        entity.setSurname(patient.getBody().getSurname());
        entity.setJmbg(patient.getBody().getJmbg());


        return entity;
    }


    public HospitalizationDto toDto(Hospitalization entity){

        if(entity==null)return null;

        HospitalizationDto dto=new HospitalizationDto();

        dto.setId(entity.getId());
        dto.setLbp(entity.getPrescription().getLbp());
        dto.setNote(entity.getNote());
        dto.setHospitalRoomId(entity.getHospitalRoom().getId());
        dto.setLbzRegister(entity.getLbzRegister());
        dto.setLbzDoctor(entity.getLbzDoctor());
        dto.setPatientAdmission(entity.getPatientAdmission());
        dto.setPrescriptionId(entity.getPrescription().getId());
        dto.setName(entity.getName());
        dto.setJmbg(entity.getJmbg());
        dto.setSurname(entity.getSurname());
        dto.setDischargeDateAndTime(entity.getDischargeDateAndTime());

        return dto;
    }

    public List<HospitalizationDto> toDto(List<Hospitalization> entity){
        if(entity==null)return null;

        List<HospitalizationDto> dto=new ArrayList<>();

        for(Hospitalization p:entity)
            dto.add(toDto(p));

        return dto;
    }

}
