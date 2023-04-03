package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.VisitDto;
import raf.bolnica1.infirmary.repository.*;
import raf.bolnica1.infirmary.security.utils.JwtUtils;
import raf.bolnica1.infirmary.services.VisitService;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class VisitorServiceImpl implements VisitService {
    private VisitRepository visitRepository;
    private JwtUtils jwtUtils;

    @Override
    public String registerPatientVisit(String authorization, VisitDto visitDto) {

        String token = authorization.split(" ")[1];
        String lbz = jwtUtils.getUsernameFromToken(token);

        Visit visit = new Visit();

        visit.setVisitTime(new Timestamp(System.currentTimeMillis()));
        visit.setNote(visitDto.getNote());
        visit.setLbzRegister(visitDto.getLbzRegister());
        visit.setVisitorName(visitDto.getVisitorName());
        visit.setVisitorSurname(visitDto.getVisitorSurname());
        visit.setVisitorJmbg(visitDto.getVisitorJmbg());
        // TODO: Set lbp?

        visitRepository.save(visit);

        return "Patient visit register successfully!";
    }

}
