package raf.bolnica1.patient.messaging.helper;

import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.DischargeList;
import raf.bolnica1.patient.domain.Hospitalization;
import raf.bolnica1.patient.dto.externalInfirmary.DischargeListCreateDto;
import raf.bolnica1.patient.dto.externalInfirmary.HospitalizationCreateDto;
import raf.bolnica1.patient.mapper.HospitalizationMapper;
import raf.bolnica1.patient.repository.DischargeListRepository;
import raf.bolnica1.patient.repository.HospitalizationRepository;
import raf.bolnica1.patient.repository.PatientRepository;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class HospitalizationListener {
    private HospitalizationRepository hospitalizationRepository;
    private DischargeListRepository dischargeListRepository;
    private HospitalizationMapper hospitalizationMapper;
    private PatientRepository patientRepository;
    private MessageHelper messageHelper;

    @JmsListener(destination = "${destination.send.hospitalization}", concurrency = "5-10")
    public void getHospitalization(Message message) throws JMSException {
        HospitalizationCreateDto hospitalizationCreateDto = messageHelper.getMessage(message, HospitalizationCreateDto.class);
        hospitalizationRepository.save(hospitalizationMapper.dtoToEntityHospitalization(hospitalizationCreateDto));
    }

    @JmsListener(destination = "${destination.send.discharge}", concurrency = "5-10")
    public void getDischargeList(Message message) throws JMSException {
        DischargeListCreateDto dischargeListCreateDto = messageHelper.getMessage(message, DischargeListCreateDto.class);
        Hospitalization hospitalization = hospitalizationRepository.findHospitalization(dischargeListCreateDto.getLbp(), dischargeListCreateDto.getHospitalization()).orElse(null);
        if(hospitalization != null) {
            DischargeList dischargeList = hospitalizationMapper.dtoToEntityDischargeList(dischargeListCreateDto);
            dischargeList = dischargeListRepository.save(dischargeList);
            hospitalization.setDischargeList(dischargeList);
            hospitalization.setActive(false);
            hospitalizationRepository.save(hospitalization);
        }
    }


}
