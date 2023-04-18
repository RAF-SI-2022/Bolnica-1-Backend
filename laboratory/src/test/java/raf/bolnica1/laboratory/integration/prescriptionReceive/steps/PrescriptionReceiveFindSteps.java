package raf.bolnica1.laboratory.integration.prescriptionReceive.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionUpdateDtoGenerator;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomLBP;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomLong;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.PatientDto;
import raf.bolnica1.laboratory.dto.prescription.*;
import raf.bolnica1.laboratory.integration.prescriptionReceive.PrescriptionReceiveIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PrescriptionReceiveFindSteps extends PrescriptionReceiveIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;
    @Autowired
    private RandomString randomString;
    @Autowired
    private RandomLBP randomLBP;
    @Autowired
    private RandomLong randomLong;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @Autowired
    private LabWorkOrderRepository labWorkOrderRepository;

    /// SERVICES
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private String lbz1,lbz2;
    private String lbp;


    @Given("dodali smo {int} uputa")
    public void dodali_smo_uputa(Integer prescriptionCount) {
        try{

            lbp=randomLBP.getFromRandom();
            lbz1= randomString.getString(10);
            lbz2= randomString.getString(10);

            for(int i=0;i<prescriptionCount;i++){
                PrescriptionCreateDto p=prescriptionCreateDtoGenerator.getPrescriptionCreateDto(analysisParameterRepository);
                p.setLbp(lbp);
                if(randomLong.getLong(2L)==0)p.setDoctorLbz(lbz1);
                else p.setDoctorLbz(lbz2);
                prescriptionRecieveService.createPrescription(p);
            }


        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("pretrazili smo po lbp i lbz\\(paginacija) uspesno")
    public void pretrazili_smo_po_lbp_i_lbz_paginacija_uspesno() {
        try{

            List<Prescription> prescriptionList=prescriptionRepository.findAll();
            List<Prescription> result=new ArrayList<>();
            for(Prescription p:prescriptionList)
                if(p.getLbp().equals(lbp) && p.getDoctorLbz().equals(lbz1) && p.getStatus()==PrescriptionStatus.NEREALIZOVAN)
                    result.add(p);

            Page<PrescriptionDto> queried=prescriptionRecieveService.findPrescriptionsForPatient(lbp,lbz1,0,1000000000);
            List<PrescriptionDto> q=queried.getContent();

            List<PrescriptionDto> pom2=new ArrayList<>(q);
            List<Prescription> pom1=new ArrayList<>(result);
            pom1.sort(Comparator.comparing(Prescription::getId));
            pom2.sort(Comparator.comparing(PrescriptionDto::getId));

            Assertions.assertTrue(q.size()==result.size());
            for(int i=0;i<q.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));


        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("dobavili smo rezultate korektno za uput sa ID {int}")
    public void dobavili_smo_rezultate_korektno_za_uput_sa_id(Integer prescriptionId) {
        try{

            PrescriptionDoneDto prescriptionDoneDto=prescriptionRecieveService.findPrescription((long)prescriptionId);
            Prescription prescription=prescriptionRepository.findPrescriptionById((long)prescriptionId);

            System.out.println(objectMapper.writeValueAsString(prescription));
            System.out.println(objectMapper.writeValueAsString(prescriptionDoneDto));
            Assertions.assertTrue(classJsonComparator.compareCommonFields(prescription,prescriptionDoneDto));

            Optional<LabWorkOrder> pomLabWorkOrder=labWorkOrderRepository.findByPrescription((long)prescriptionId);
            if(!pomLabWorkOrder.isPresent()){
                Assertions.assertEquals(prescriptionDoneDto.getParameters().size(),0);
                return;
            }

            LabWorkOrder labWorkOrder= pomLabWorkOrder.get();

            List<ParameterAnalysisResult>par=parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(
                    labWorkOrder.getId());

            int resultCount=par.size();
            for(PrescriptionAnalysisNameDto prescriptionAnalysisNameDto:prescriptionDoneDto.getParameters()){
                for(ParameterDto parameterDto:prescriptionAnalysisNameDto.getParameters()){

                    boolean flag=false;
                    for(ParameterAnalysisResult p:par){
                        if(p.getAnalysisParameter().getLabAnalysis().getAnalysisName().equals(prescriptionAnalysisNameDto.getAnalysisName())){

                            if(!classJsonComparator.compareCommonFields(parameterDto,
                                    p.getAnalysisParameter().getParameter()))continue;

                            if(p.getResult()==null && parameterDto.getResult()==null){
                                flag=true;
                                continue;
                            }
                            else if(p.getResult()==null || parameterDto.getResult()==null)continue;

                            if(p.getResult().equals(parameterDto.getResult())){
                                flag=true;
                            }

                        }

                    }

                    Assertions.assertTrue(flag);
                    resultCount--;
                }
            }

            Assertions.assertTrue(resultCount==0);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }




    @Then("pretrazili smo po lbp i lbz uspesno")
    public void pretrazili_smo_po_lbp_i_lbz_uspesno() {
        try{

            List<Prescription> prescriptionList=prescriptionRepository.findAll();
            List<Prescription> result=new ArrayList<>();
            for(Prescription p:prescriptionList)
                if(p.getLbp().equals(lbp) && p.getDoctorLbz().equals(lbz1) && p.getStatus()==PrescriptionStatus.NEREALIZOVAN)
                    result.add(p);

            List<PrescriptionDto> queried=prescriptionRecieveService.findPrescriptionsForPatientRest(lbp,lbz1);
            List<PrescriptionDto> q=queried;

            List<PrescriptionDto> pom2=new ArrayList<>(q);
            List<Prescription> pom1=new ArrayList<>(result);
            pom1.sort(Comparator.comparing(Prescription::getId));
            pom2.sort(Comparator.comparing(PrescriptionDto::getId));

            Assertions.assertTrue(q.size()==result.size());
            for(int i=0;i<q.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));


        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @Then("pretrazili smo po lbp uspesno")
    public void pretrazili_smo_po_lbp_uspesno() {
        try{

            List<Prescription> prescriptionList=prescriptionRepository.findAll();
            List<Prescription> result=new ArrayList<>();
            for(Prescription p:prescriptionList)
                if(p.getLbp().equals(lbp) && p.getStatus()==PrescriptionStatus.NEREALIZOVAN)
                    result.add(p);

            Page<PrescriptionDto> queried=prescriptionRecieveService.findPrescriptionsForPatientNotRealized(lbp,0,1000000000);
            List<PrescriptionDto> q=queried.getContent();

            List<PrescriptionDto> pom2=new ArrayList<>(q);
            List<Prescription> pom1=new ArrayList<>(result);
            pom1.sort(Comparator.comparing(Prescription::getId));
            pom2.sort(Comparator.comparing(PrescriptionDto::getId));

            Assertions.assertTrue(q.size()==result.size());
            for(int i=0;i<q.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));


        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("pronasli smo sve trazene rezultate")
    public void pronasli_smo_sve_trazene_rezultate() {
        try{

            List<Prescription> prescriptionList=prescriptionRepository.findAll();
            List<PatientDto> result=new ArrayList<>();
            for(Prescription p:prescriptionList)
                if(p.getStatus()==PrescriptionStatus.NEREALIZOVAN)
                    result.add(new PatientDto(p.getLbp(),p.getId()));

            Page<PatientDto> queried=prescriptionRecieveService.findPatients(0,1000000000);
            List<PatientDto> q=queried.getContent();

            List<PatientDto> pom2=new ArrayList<>(q);
            List<PatientDto> pom1=new ArrayList<>(result);
            pom1.sort(Comparator.comparing(PatientDto::getPrescriptionId));
            pom2.sort(Comparator.comparing(PatientDto::getPrescriptionId));

            Assertions.assertTrue(q.size()==result.size());
            for(int i=0;i<q.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));


        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
