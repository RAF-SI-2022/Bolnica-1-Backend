package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;

import java.sql.Date;

public interface HospitalizationService {

    Page<HospitalizationDto> getHospitalizationsByDepartmentId(Long departmentId,Integer page,Integer size);

    Page<HospitalizationDto> getHospitalizationsByHospitalRoomId(Long hospitalRoomId,Integer page,Integer size);

    Page<HospitalizationDto> getHospitalizationsWithFilter(String name, String surname, String jmbg,
                                                           Long departmentId, Long hospitalRoomId, String lbp,
                                                           Date startDate, Date endDate, Integer page, Integer size);

    MessageDto addOnVentilator(Long id);

    MessageDto removeFromVentilator(Long id);

    Boolean getVentilator(Long id);

}
