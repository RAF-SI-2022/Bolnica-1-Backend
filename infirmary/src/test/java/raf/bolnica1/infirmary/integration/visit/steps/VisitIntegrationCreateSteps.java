package raf.bolnica1.infirmary.integration.visit.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.integration.visit.VisitIntegrationTestConfig;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.VisitCreateDtoGenerator;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.VisitService;

import java.sql.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class VisitIntegrationCreateSteps extends VisitIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private VisitCreateDtoGenerator visitCreateDtoGenerator;
    @Autowired
    private HospitalizationGenerator hospitalizationGenerator;


    /// SERVICES
    @Autowired
    private VisitService visitService;


    /// REPOSITORIES
    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;
    @Autowired
    private HospitalizationRepository hospitalizationRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;


    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private TokenSetter tokenSetter;


    /// CLASS DATA
    private VisitDto tempVisitDto;



    @When("kada zabelezimo informacije o novoj poseti")
    public void kada_zabelezimo_informacije_o_novoj_poseti() {

        String token=jwtTokenGetter.getDefaultToken();
        String lbz=tokenSetter.setToken(token);

        Hospitalization hospitalization= hospitalizationGenerator.getHospitalizationWithDBSave(hospitalizationRepository,
                hospitalRoomRepository,prescriptionRepository,null,null);
        VisitCreateDto visitCreateDto=visitCreateDtoGenerator.getVisitCreateDto(hospitalization.getId());

        VisitDto visitDto=visitService.createVisit(visitCreateDto);
        tempVisitDto=visitDto;

        assertNotNull(visitDto);
        assertTrue(classJsonComparator.compareCommonFields(visitDto,visitCreateDto));
        assertTrue(lbz.equals(visitDto.getLbzRegister()));
    }
    @Then("te informacije su sacuvane u bazi")
    public void te_informacije_su_sacuvane_u_bazi() {

        Page<VisitDto> visitsPage=visitService.getVisitsWithFilter(null,null, tempVisitDto.getHospitalizationId(),
                new Date(tempVisitDto.getVisitTime().getTime()),new Date(tempVisitDto.getVisitTime().getTime()),0,100000);

        assertNotNull(visitsPage);
        assertTrue(visitsPage.hasContent());

        List<VisitDto> visits=visitsPage.getContent();

        boolean flag=false;
        for(VisitDto visitDto:visits)
            if(classJsonComparator.compareCommonFields(tempVisitDto,visitDto))
                flag=true;

        assertTrue(flag);
    }

}
