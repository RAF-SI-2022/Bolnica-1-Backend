package raf.bolnica1.infirmary.services;

import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;

public interface DischargeListService {

    DischargeListDto createDischargeList(DischargeListDto dischargeListDto);

    DischargeListDto getDischargeListByHospitalizationId(Long hospitalizationId);

}
