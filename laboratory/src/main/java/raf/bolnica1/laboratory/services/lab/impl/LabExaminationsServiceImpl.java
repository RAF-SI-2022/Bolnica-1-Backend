package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.exceptions.workOrder.LabWorkOrderNotFoundException;
import raf.bolnica1.laboratory.exceptions.workOrder.NotAuthenticatedException;
import raf.bolnica1.laboratory.mappers.ScheduledLabExaminationMapper;
import raf.bolnica1.laboratory.repository.ScheduledLabExaminationRepository;
import raf.bolnica1.laboratory.services.lab.LabExaminationsService;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class LabExaminationsServiceImpl implements LabExaminationsService {

    private ScheduledLabExaminationMapper scheduledLabExaminationMapper;
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;

    @Qualifier("employeeRestTemplate")
    private RestTemplate employeeRestTemplate;

    @Override
    public MessageDto createScheduledExamination(String lbp, Date scheduledDate,String note,String token) {

        String lbz=getLbzFromAuthentication();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity httpEntity=new HttpEntity<>(null,httpHeaders);
        ResponseEntity<Long> departmentId=employeeRestTemplate.exchange("/department/employee/"+lbz, HttpMethod.GET,httpEntity, Long.class);

        ScheduledLabExamination scheduledLabExamination=scheduledLabExaminationMapper.toEntity(departmentId.getBody(),lbp,scheduledDate,note,lbz);
        scheduledLabExaminationRepository.save(scheduledLabExamination);

        return new MessageDto(String.format("Uspesno kreiran zakazani laboratorijski pregled za pacijenta %s\n",lbp) );
    }

    @Override
    public Object changeExaminationStatus(Long id, ExaminationStatus newStatus)
    {
        ScheduledLabExamination scheduledLabExamination= scheduledLabExaminationRepository.findById(id).orElseThrow(() ->
                new LabWorkOrderNotFoundException(String.format("No examination with id %s", id))
        );
        scheduledLabExamination.setExaminationStatus(newStatus);
        scheduledLabExaminationRepository.save(scheduledLabExamination);
        return new MessageDto(String.format("Uspesno promenjen status pregleda") );
    }

    @Override
    public List<ScheduledLabExaminationDto> listScheduledExaminationsByDay(Date date,String token) {
        String lbz=getLbzFromAuthentication();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity httpEntity=new HttpEntity<>(null,httpHeaders);
        ResponseEntity<Long> departmentId=employeeRestTemplate.exchange("/department/employee/"+lbz, HttpMethod.GET,httpEntity, Long.class);

        Date sqlDate=date;
        return scheduledLabExaminationMapper.toDto(scheduledLabExaminationRepository.findScheduledLabExaminationsByDateAndDepartmentId(sqlDate,departmentId.getBody()));
    }

    @Override
    public List<ScheduledLabExaminationDto> listScheduledExaminations(String token) {
        String lbz=getLbzFromAuthentication();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity httpEntity=new HttpEntity<>(null,httpHeaders);
        ResponseEntity<Long> departmentId=employeeRestTemplate.exchange("/department/employee/"+lbz, HttpMethod.GET,httpEntity, Long.class);

        return scheduledLabExaminationMapper.toDto(scheduledLabExaminationRepository.findScheduledLabExaminationsByDepartmentId(departmentId.getBody()));
    }

    private String getLbzFromAuthentication(){
        String lbz = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            lbz = (String) authentication.getPrincipal();
        }
        // temp linija, treba malo refaktorisati
        if(lbz == null) throw new NotAuthenticatedException("Something went wrong.");
        return lbz;
    }
}
