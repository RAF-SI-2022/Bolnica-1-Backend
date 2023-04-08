package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.VisitDto;
import raf.bolnica1.infirmary.repository.*;
import raf.bolnica1.infirmary.security.utils.AuthenticationUtils;
import raf.bolnica1.infirmary.security.utils.JwtUtils;
import raf.bolnica1.infirmary.services.VisitService;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VisitServiceImpl implements VisitService {
    private VisitRepository visitRepository;
    private HospitalizationRepository hospitalizationRepository;
    private JwtUtils jwtUtils;
    private AuthenticationUtils authenticationUtils;

    @Override
    public String registerPatientVisit(String authorization, VisitDto visitDto) {

//        String token = authorization.split(" ")[1];
//        String lbz = jwtUtils.getUsernameFromToken(token);

        String lbz = authenticationUtils.getLbzFromAuthentication();

        Visit visit = new Visit();

        visit.setVisitTime(new Timestamp(System.currentTimeMillis()));
        visit.setNote(visitDto.getNote());
        visit.setLbzRegister(lbz);
        visit.setVisitorName(visitDto.getVisitorName());
        visit.setVisitorSurname(visitDto.getVisitorSurname());
        visit.setVisitorJmbg(visitDto.getVisitorJmbg());

        Optional<Hospitalization> hospitalization = hospitalizationRepository.findHospitalizationByLbp(visitDto.getLbp());

        if (!hospitalization.isPresent()) {
            throw new RuntimeException("No prescription for patient with lbp: " + visitDto.getLbp());
        }

        visit.setHospitalization(hospitalization.get());

        visitRepository.save(visit);

        return "Patient visit register successfully!";
    }

}
