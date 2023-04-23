package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.mappers.ScheduledLabExaminationMapper;
import raf.bolnica1.laboratory.repository.ScheduledLabExaminationRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.impl.LabExaminationsServiceImpl;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.createScheduledLabExamination;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.createScheduledLabExaminationDto;

@ExtendWith(MockitoExtension.class)
public class LabExaminationsServiceTest {

    @Mock
    private AuthenticationUtils authenticationUtils;
    @Mock
    private ScheduledLabExaminationMapper scheduledLabExaminationMapper;
    @Mock
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;
    @Mock
    @Qualifier("employeeRestTemplate")
    private RestTemplate employeeRestTemplate;
    @InjectMocks
    private LabExaminationsServiceImpl labExaminationsService;

    @Test
    public void testCreateScheduledExamination() {
        String lbp = "LBP001";
        Date scheduledDate = Date.valueOf("2023-01-01");
        String note = "Test note";
        String token = "Bearer test-token";
        String lbz = "LBZ001";
        Long departmentId = 1L;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<Long> responseEntity = new ResponseEntity<>(departmentId, HttpStatus.OK);

        ScheduledLabExamination entity = createScheduledLabExamination();
        ScheduledLabExamination savedEntity = createScheduledLabExamination();
        ScheduledLabExaminationDto expectedDto = createScheduledLabExaminationDto();

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn(lbz);
        when(employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class)).thenReturn(responseEntity);
        when(scheduledLabExaminationMapper.toEntity(departmentId, lbp, scheduledDate, note, lbz)).thenReturn(entity);
        when(scheduledLabExaminationRepository.save(entity)).thenReturn(savedEntity);
        when(scheduledLabExaminationMapper.toDto(savedEntity)).thenReturn(expectedDto);

        ScheduledLabExaminationDto result = labExaminationsService.createScheduledExamination(lbp, scheduledDate, note, token);

        assertEquals(expectedDto, result);
    }

    @Test
    public void testChangeExaminationStatus() {
        Long id = 1L;
        ExaminationStatus newStatus = ExaminationStatus.ZAVRSENO;

        ScheduledLabExamination entity = createScheduledLabExamination();
        ScheduledLabExamination updatedEntity = createScheduledLabExamination();
        ScheduledLabExaminationDto expectedDto = createScheduledLabExaminationDto();

        when(scheduledLabExaminationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(scheduledLabExaminationRepository.save(entity)).thenReturn(updatedEntity);
        when(scheduledLabExaminationMapper.toDto(updatedEntity)).thenReturn(expectedDto);

        ScheduledLabExaminationDto result = labExaminationsService.changeExaminationStatus(id, newStatus);

        assertEquals(expectedDto, result);
    }

    @Test
    public void testListScheduledExaminationsByDay() {
        Date date = Date.valueOf("2023-01-01");
        String token = "Bearer test-token";
        String lbz = "LB001";
        Long departmentId = 1L;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<Long> responseEntity = new ResponseEntity<>(departmentId, HttpStatus.OK);

        List<ScheduledLabExamination> entities = Arrays.asList(createScheduledLabExamination(), createScheduledLabExamination());
        List<ScheduledLabExaminationDto> expectedDtos = Arrays.asList(createScheduledLabExaminationDto(), createScheduledLabExaminationDto());

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn(lbz);
        when(employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class)).thenReturn(responseEntity);
        when(scheduledLabExaminationRepository.findScheduledLabExaminationsByDateAndDepartmentId(date, departmentId)).thenReturn(entities);
        when(scheduledLabExaminationMapper.toDto(entities)).thenReturn(expectedDtos);

        List<ScheduledLabExaminationDto> result = labExaminationsService.listScheduledExaminationsByDay(date, token);

        assertEquals(expectedDtos, result);
    }

    @Test
    public void testListScheduledExaminations() {
        String token = "Bearer test-token";
        String lbz = "LBZ001";
        Long departmentId = 1L;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<Long> responseEntity = new ResponseEntity<>(departmentId, HttpStatus.OK);

        List<ScheduledLabExamination> entities = Arrays.asList(createScheduledLabExamination(), createScheduledLabExamination());
        List<ScheduledLabExaminationDto> expectedDtos = Arrays.asList(createScheduledLabExaminationDto(), createScheduledLabExaminationDto());

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn(lbz);
        when(employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class)).thenReturn(responseEntity);
        when(scheduledLabExaminationRepository.findScheduledLabExaminationsByDepartmentId(departmentId)).thenReturn(entities);
        when(scheduledLabExaminationMapper.toDto(entities)).thenReturn(expectedDtos);

        List<ScheduledLabExaminationDto> result = labExaminationsService.listScheduledExaminations(token);

        assertEquals(expectedDtos, result);
    }

    @Test
    public void testListScheduledExaminationsByLbpAndDate() {
        String lbp = "LBP001";
        Date startDate = Date.valueOf("2023-01-01");
        Date endDate = Date.valueOf("2023-01-01");
        String token = "Bearer test-token";
        int page = 0;
        int size = 10;
        String lbz = "LBZ001";
        Long departmentId = 1L;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.split(" ")[1]);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<Long> responseEntity = new ResponseEntity<>(departmentId, HttpStatus.OK);

        Pageable pageable = PageRequest.of(page, size);

        ScheduledLabExamination examination1 = createScheduledLabExamination();
        ScheduledLabExamination examination2 = createScheduledLabExamination();

        Page<ScheduledLabExamination> entityPage = new PageImpl<>(Arrays.asList(examination1, examination2), pageable, 2);

        ScheduledLabExaminationDto dto1 = createScheduledLabExaminationDto();
        ScheduledLabExaminationDto dto2 = createScheduledLabExaminationDto();

        Page<ScheduledLabExaminationDto> expectedDtoPage = new PageImpl<>(Arrays.asList(dto1, dto2), pageable, 2);

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn(lbz);
        when(employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class)).thenReturn(responseEntity);
        when(scheduledLabExaminationRepository.findScheduledLabExaminationByLbpAndDateAndDepartmentId(pageable, lbp, startDate, endDate, departmentId)).thenReturn(entityPage);
        when(scheduledLabExaminationMapper.toDto(examination1)).thenReturn(dto1);
        when(scheduledLabExaminationMapper.toDto(examination2)).thenReturn(dto2);

        Page<ScheduledLabExaminationDto> result = labExaminationsService.listScheduledExaminationsByLbpAndDate(lbp, startDate, endDate, token, page, size);

        assertEquals(expectedDtoPage, result);
    }

}
