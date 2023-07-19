package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.services.HospitalizationService;

import java.sql.Date;


@Component
@AllArgsConstructor
public class HospitalizationServiceImpl implements HospitalizationService {

    /// MAPPERS
    private final HospitalizationMapper hospitalizationMapper;

    /// REPOSITORIES
    private final HospitalizationRepository hospitalizationRepository;


    @Override
    //@Cacheable(value = "hospDep", key = "{#departmentId, #page, #size}")
    public Page<HospitalizationDto> getHospitalizationsByDepartmentId(Long departmentId, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Hospitalization>hospitalizations=hospitalizationRepository.findHospitalizationsByDepartmentId(pageable,departmentId);
        return hospitalizations.map(hospitalizationMapper::toDto);
    }

    @Override
    //@Cacheable(value = "hostRoom", key = "{#hospitalRoomId, #page, #size}")
    public Page<HospitalizationDto> getHospitalizationsByHospitalRoomId(Long hospitalRoomId, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Hospitalization>hospitalizations=hospitalizationRepository.findHospitalizationsByHospitalRoomId(pageable,hospitalRoomId);
        return hospitalizations.map(hospitalizationMapper::toDto);
    }

    @Override
    public Page<HospitalizationDto> getHospitalizationsWithFilter(String name, String surname, String jmbg, Long departmentId, Long hospitalRoomId, String lbp, Date startDate, Date endDate, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        if(endDate!=null)endDate=new Date(endDate.getTime()+24*60*60*1000);
        Page<Hospitalization>hospitalizations=hospitalizationRepository.findHospitalizationsWithFilter(pageable,name,surname,jmbg,departmentId,hospitalRoomId,lbp,startDate,endDate);
        return hospitalizations.map(hospitalizationMapper::toDto);
    }


}
