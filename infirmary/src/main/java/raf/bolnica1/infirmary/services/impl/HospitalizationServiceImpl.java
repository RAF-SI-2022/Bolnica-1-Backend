package raf.bolnica1.infirmary.services.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.dto.stats.CovidStat;
import raf.bolnica1.infirmary.dto.stats.CovidStatsDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.services.HospitalizationService;

import java.sql.Date;


@Component
public class HospitalizationServiceImpl implements HospitalizationService {

    /// MAPPERS
    private HospitalizationMapper hospitalizationMapper;
    private MessageHelper messageHelper;

    /// REPOSITORIES
    private HospitalizationRepository hospitalizationRepository;

    private JmsTemplate jmsTemplate;
    private String destination;

    public HospitalizationServiceImpl(HospitalizationMapper hospitalizationMapper,
                                      MessageHelper messageHelper,
                                      HospitalizationRepository hospitalizationRepository,
                                      JmsTemplate jmsTemplate,
                                      @Value("${destination.send.stats}") String destination){
        this.hospitalizationMapper = hospitalizationMapper;
        this.messageHelper = messageHelper;
        this.hospitalizationRepository = hospitalizationRepository;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }


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

    @Override
    public MessageDto addOnVentilator(Long id) {
        Hospitalization hospitalization = hospitalizationRepository.findHospitalizationById(id);
        hospitalization.setVentiltor(true);
        hospitalizationRepository.save(hospitalization);
        if(hospitalization.isCovid())
            jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(new CovidStatsDto(CovidStat.VENTILATOR_ADD, hospitalization.getPrescription().getLbp(), new Date(System.currentTimeMillis()))));
        return new MessageDto("Pacijent stavljen na respirator");
    }

    @Override
    public MessageDto removeFromVentilator(Long id) {
        Hospitalization hospitalization = hospitalizationRepository.findHospitalizationById(id);
        if(!hospitalization.isVentiltor())
            return new MessageDto("Pacijent nije bio na respiratoru");

        hospitalization.setVentiltor(false);
        hospitalizationRepository.save(hospitalization);
        if(hospitalization.isCovid())
            jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(new CovidStatsDto(CovidStat.VENTILATOR_REMOVE, hospitalization.getPrescription().getLbp(), new Date(System.currentTimeMillis()))));

        return new MessageDto("Pacijent skinut sa respiratora");
    }


}
