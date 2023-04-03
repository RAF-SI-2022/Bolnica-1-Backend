package raf.bolnica1.infirmary.services;

import raf.bolnica1.infirmary.dto.VisitDto;

public interface VisitService {

    String registerPatientVisit(String authorization, VisitDto visitDto);

}
