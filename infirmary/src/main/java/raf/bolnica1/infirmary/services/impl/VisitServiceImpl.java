package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.mapper.VisitMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.VisitRepository;
import raf.bolnica1.infirmary.services.VisitService;

import java.sql.Date;

@Service
@AllArgsConstructor
public class VisitServiceImpl implements VisitService {

    /// MAPPERS
    private final VisitMapper visitMapper;

    /// REPOSITORIES
    private final VisitRepository visitRepository;
    private final HospitalizationRepository hospitalizationRepository;

    @Override
    public VisitDto createVisit(VisitCreateDto visitCreateDto) {
        Visit visit=visitMapper.toEntity(visitCreateDto,hospitalizationRepository);
        visit=visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    @Override
    public Page<VisitDto> getVisitsWithFilter(Long departmentId, Long hospitalRoomId, Long hospitalizationId, Date startDate, Date endDate,Integer page,Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        if(endDate!=null)endDate=new Date(endDate.getTime()+24*60*60*1000);
        Page<Visit>visits=visitRepository.findVisitsWithFilter(pageable,departmentId,hospitalRoomId,hospitalizationId,startDate,endDate);
        return visits.map(visitMapper::toDto);
    }
}
