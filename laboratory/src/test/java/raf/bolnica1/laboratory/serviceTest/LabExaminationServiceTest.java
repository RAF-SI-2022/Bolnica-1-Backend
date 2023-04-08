package raf.bolnica1.laboratory.serviceTest;


import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.repository.ScheduledLabExaminationRepository;
import raf.bolnica1.laboratory.services.lab.LabExaminationsService;
import raf.bolnica1.laboratory.services.lab.impl.LabExaminationsServiceImpl;
import raf.bolnica1.laboratory.mappers.ScheduledLabExaminationMapper;
import static org.mockito.ArgumentMatchers.anyLong;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LabExaminationServiceTest {
    @Mock
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;

    @InjectMocks
    private LabExaminationsServiceImpl scheduledLabExaminationService;
    @InjectMocks
    private ScheduledLabExaminationMapper scheduledLabExaminationMapper;
    @Test
    void createExamination() {

    }
    @Test
    public void updateExaminationStatus_shouldUpdateExaminationStatus() {

        ScheduledLabExamination scheduledLabExamination = new ScheduledLabExamination();
        scheduledLabExamination.setId(1L);
        scheduledLabExamination.setDepartmentId(1L);
        scheduledLabExamination.setLbp("testLbp");
        scheduledLabExamination.setScheduledDate(java.sql.Date.valueOf("2020-01-01"));
        scheduledLabExamination.setExaminationStatus(ExaminationStatus.ZAKAZANO);
        scheduledLabExamination.setNote("testNote");
        scheduledLabExamination.setLbz("testLbz");

        when(scheduledLabExaminationRepository.findById(1L))
                .thenReturn(Optional.of(scheduledLabExamination));
        when(scheduledLabExaminationRepository.save(scheduledLabExamination))
                .thenReturn(scheduledLabExamination);

        scheduledLabExaminationService.changeExaminationStatus(1L, ExaminationStatus.OTKAZANO);

        assertEquals(ExaminationStatus.OTKAZANO, scheduledLabExamination.getExaminationStatus());
    }

}
