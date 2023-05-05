package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.services.DischargeListService;

import java.sql.Date;

@AllArgsConstructor
@Service
public class DischargeListServiceImpl implements DischargeListService {

    /// MAPPERS
    private final DischargeListMapper dischargeListMapper;

    /// REPOSITORIES
    private final DischargeListRepository dischargeListRepository;
    private final HospitalizationRepository hospitalizationRepository;


    @Override
    public DischargeListDto createDischargeList(CreateDischargeListDto createDischargeListDto) {
        DischargeList dischargeList= dischargeListMapper.toEntity(createDischargeListDto,hospitalizationRepository);
        dischargeList=dischargeListRepository.save(dischargeList);
        return dischargeListMapper.toDto(dischargeList);
    }

    @Override
    public Page<DischargeListDto> getDischargeListWithFilter(Long hospitalizationId, Date startDate,
                                                             Date endDate, String lbp, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Date endDate2=null;
        if(endDate!=null)endDate2=new Date(endDate.getTime()+24*60*60*1000);
        Page<DischargeList> dischargeList=dischargeListRepository.findDischargeListWithFilter(pageable,
                hospitalizationId,startDate,endDate2,lbp);
        return dischargeList.map(dischargeListMapper::toDto);
    }


}
