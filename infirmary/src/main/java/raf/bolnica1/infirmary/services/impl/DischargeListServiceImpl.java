package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.services.DischargeListService;

@AllArgsConstructor
@Service
public class DischargeListServiceImpl implements DischargeListService {

    /// MAPPERS
    private final DischargeListMapper dischargeListMapper;

    /// REPOSITORIES
    private final DischargeListRepository dischargeListRepository;
    private final HospitalizationRepository hospitalizationRepository;


    @Override
    public DischargeListDto createDischargeList(DischargeListDto dischargeListDto) {
        DischargeList dischargeList= dischargeListMapper.toEntity(dischargeListDto,hospitalizationRepository);
        dischargeList=dischargeListRepository.save(dischargeList);
        return dischargeListMapper.toDto(dischargeList);
    }

    @Override
    public DischargeListDto getDischargeListByHospitalizationId(Long hospitalizationId) {
        DischargeList dischargeList=dischargeListRepository.findDischargeListByHospitalizationId(hospitalizationId);
        return dischargeListMapper.toDto(dischargeList);
    }


}
