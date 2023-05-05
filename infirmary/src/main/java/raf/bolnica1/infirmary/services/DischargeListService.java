package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;

import java.sql.Date;

public interface DischargeListService {

    DischargeListDto createDischargeList(CreateDischargeListDto createDischargeListDto);

    Page<DischargeListDto> getDischargeListWithFilter(Long hospitalizationId, Date startDate,
                                                               Date endDate, String lbp, Integer page, Integer size);

}
