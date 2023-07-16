package raf.bolnica1.infirmary.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.dto.externalPatientService.DischargeListCreateDto;
import raf.bolnica1.infirmary.dto.stats.CovidStat;
import raf.bolnica1.infirmary.dto.stats.CovidStatsDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.services.DischargeListService;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class DischargeListServiceImpl implements DischargeListService {

    /// MAPPERS
    private final DischargeListMapper dischargeListMapper;

    /// REPOSITORIES
    private final DischargeListRepository dischargeListRepository;
    private final HospitalizationRepository hospitalizationRepository;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String destination;
    private String destination2;

    public DischargeListServiceImpl(DischargeListMapper dischargeListMapper, DischargeListRepository dischargeListRepository,
                                    HospitalizationRepository hospitalizationRepository,
                                    JmsTemplate jmsTemplate,
                                    MessageHelper messageHelper,
                                    @Value("${destination.send.stats}") String destination,
                                    @Value("${destination.send.discharge}") String destination2){
        this.dischargeListMapper = dischargeListMapper;
        this.dischargeListRepository = dischargeListRepository;
        this.hospitalizationRepository = hospitalizationRepository;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.destination = destination;
        this.destination2 = destination2;
    }


    @Override
    public DischargeListDto createDischargeList(CreateDischargeListDto createDischargeListDto) {
        DischargeList dischargeList= dischargeListMapper.toEntity(createDischargeListDto,hospitalizationRepository);
        dischargeList=dischargeListRepository.save(dischargeList);
        Hospitalization hospitalization=hospitalizationRepository.findHospitalizationById(createDischargeListDto.getHospitalizationId());
        hospitalization.setDischargeDateAndTime(new Timestamp(System.currentTimeMillis()));
        hospitalizationRepository.save(hospitalization);
        if(dischargeList.isDied() && hospitalization.isCovid()){
            CovidStatsDto stats = new CovidStatsDto(CovidStat.DEAD, hospitalization.getPrescription().getLbp());
            jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(stats));
        }
        if(!dischargeList.isDied() && hospitalization.isCovid()){
            CovidStatsDto stats = new CovidStatsDto(CovidStat.HEALED, hospitalization.getPrescription().getLbp());
            jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(stats));
        }
        DischargeListCreateDto dischargeListCreateDto = dischargeListMapper.forPatientToDto(dischargeList);

        jmsTemplate.convertAndSend(destination2, messageHelper.createTextMessage(dischargeListCreateDto));
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
