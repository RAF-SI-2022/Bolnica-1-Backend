package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;

import java.sql.Date;

public interface VisitService {

    VisitDto createVisit(VisitCreateDto visitCreateDto);

    Page<VisitDto> getVisitsWithFilter(Long departmentId, Long hospitalRoomId, Long hospitalizationId, Date startDate, Date endDate,Integer page,Integer size);

}
