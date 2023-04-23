package raf.bolnica1.laboratory.integration.labWorkOrders.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.labWorkOrder.LabWorkOrderFilter;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.labWorkOrder.LabWorkOrderFilterGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomLong;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderWithAnalysisDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.ParameterAnalysisResultWithDetailsDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.integration.labWorkOrders.LabWorkOrdersIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.*;

public class LabWorkOrdersSteps extends LabWorkOrdersIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;
    @Autowired
    private LabWorkOrderFilterGenerator labWorkOrderFilterGenerator;
    @Autowired
    private RandomString randomString;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private LabWorkOrderRepository labWorkOrderRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    /// SERVICES
    @Autowired
    private LabWorkOrdersService labWorkOrdersService;
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private TokenSetter tokenSetter;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private List<PrescriptionCreateDto> prescriptionCreateDtos;
    private String lbp,lbp2;
    private List<LabWorkOrderFilter> filters;




    private List<Long> getAnalysisParameterIdsFrom(List<PrescriptionAnalysisDto> prescriptionAnalysisDtos){
        List<Long> analysisParameterIds=new ArrayList<>();
        for(PrescriptionAnalysisDto pa:prescriptionAnalysisDtos){
            for(Long id2:pa.getParametersIds()){
                analysisParameterIds.add(analysisParameterRepository.findAnalysisParameterByAnalysisIdAndParameterId(
                        pa.getAnalysisId(),id2).getId());
            }
        }

        ///System.out.println(analysisParameterIds+"  EVO IH");
        return analysisParameterIds;
    }



    @Before
    public void prepare(){
        tokenSetter.setToken(jwtTokenGetter.getDrMedSpec());
        lbp2= randomString.getString(10);
    }


    @Given("napravljeno {int} uputa za lbp {string}")
    public void napravljeno_uputa_za_lbp(Integer prescriptionCount, String lbp) {
        try{
            this.lbp=lbp;
            prescriptionCreateDtos=new ArrayList<>();
            for(int i=0;i<prescriptionCount;i++) {
                String c=lbp;
                if(i>0){
                    if(randomLong.getLong(2L)==0){
                        c=lbp2;
                    }
                }

                prescriptionCreateDtos.add(prescriptionCreateDtoGenerator.getPrescriptionCreateDtoWithLBP(c,
                        analysisParameterRepository));
                prescriptionRecieveService.createPrescription(prescriptionCreateDtos.get(i));
            }

            List<Prescription> p=prescriptionRepository.findAll();
            for(PrescriptionCreateDto q:prescriptionCreateDtos){
                boolean flag=false;
                for(Prescription pom:p)
                    if(classJsonComparator.compareCommonFields(pom,q)){
                        flag=true;
                        q.setPid(pom.getId());
                        break;
                    }
                Assertions.assertTrue(flag);
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @When("napravljen LabWorkOrder za te upute")
    public void napravljen_lab_work_order_za_te_upute() {
        try{
            for(PrescriptionCreateDto p:prescriptionCreateDtos)
                Assertions.assertTrue(labWorkOrderRepository.findByPrescription(p.getPid()).isPresent());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("taj LabWorkOrder je sacuvan u bazi")
    public void taj_lab_work_order_je_sacuvan_u_bazi() {
        try{
            for(PrescriptionCreateDto p:prescriptionCreateDtos)
                Assertions.assertTrue(labWorkOrderRepository.findByPrescription(p.getPid()).isPresent());
        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("registrovanje pacijenta")
    public void registrovanje_pacijenta() {
        try{

            labWorkOrdersService.registerPatient(lbp);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("LabWorkOrder-i su azurirani da su u obradi")
    public void lab_work_order_i_su_azurirani_da_su_u_obradi() {
        try{

            List<LabWorkOrder> pom=labWorkOrderRepository.findAll();

            for(LabWorkOrder labWorkOrder:pom)
                if(labWorkOrder.getPrescription().getLbp().equals(lbp)){
                    Assertions.assertTrue(labWorkOrder.getStatus().equals(OrderStatus.U_OBRADI));
                }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }




    private boolean exceptionThrown=false;
    @When("verifikujemo rezultate LabWorkOrder-a")
    public void verifikujemo_rezultate_lab_work_order_a() {
        try{

            labWorkOrdersService.verifyWorkOrder(labWorkOrderRepository.findByPrescription(prescriptionCreateDtos.get(0)
                    .getPid()).get().getId());

            exceptionThrown=false;
            System.out.println("nije pukao exception");
        }catch (Exception e){
            exceptionThrown=true;
            System.out.println("pukao exception");
        }
    }
    @Then("operacija javlja da nisu gotovi rezultati")
    public void operacija_javlja_da_nisu_gotovi_rezultati() {
        try{
            Assertions.assertTrue(exceptionThrown);
        }catch (Exception e){
            Assertions.fail(e);
        }
    }




    @When("uradimo analize i postavimo rezultate")
    public void uradimo_analize_i_postavimo_rezultate() {
        try{

            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(prescriptionCreateDtos.get(0)
                    .getPid()).get().getId();
            List<Long> analysisParameterIds=getAnalysisParameterIdsFrom(prescriptionCreateDtos.get(0)
                    .getPrescriptionAnalysisDtos());

            for(Long api:analysisParameterIds)
                labWorkOrdersService.updateAnalysisParameters(labWorkOrderId,api,randomString.getString(10));


        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("operacija javlja da su gotovi rezultati")
    public void operacija_javlja_da_su_gotovi_rezultati() {
        try{
            Assertions.assertTrue(!exceptionThrown);
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("LabWorkOrder je obelezen sa obradjen")
    public void lab_work_order_je_obelezen_sa_obradjen() {
        try{
            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(prescriptionCreateDtos.get(0)
                    .getPid()).get().getId();
            Assertions.assertTrue(labWorkOrderRepository.findLabWorkOrderById(labWorkOrderId).getStatus()
                    .equals(OrderStatus.OBRADJEN));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("uput je obelezen sa realizovan")
    public void uput_je_obelezen_sa_realizovan() {
        try{
            Assertions.assertTrue(prescriptionRepository.findPrescriptionById(prescriptionCreateDtos.get(0).getPid())
                            .getStatus()
                    .equals(PrescriptionStatus.REALIZOVAN));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    private String result;
    @When("postavimo rezultat")
    public void postavimo_rezultat() {
        try{
            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(prescriptionCreateDtos.get(0)
                    .getPid()).get().getId();
            Long analysisParameterIds=getAnalysisParameterIdsFrom(prescriptionCreateDtos.get(0)
                    .getPrescriptionAnalysisDtos()).get(0);

            result= randomString.getString(10);
            labWorkOrdersService.updateAnalysisParameters(labWorkOrderId,analysisParameterIds,result);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("taj rezultat je uspesno postavljen")
    public void taj_rezultat_je_uspesno_postavljen() {
        try{

            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(prescriptionCreateDtos.get(0)
                    .getPid()).get().getId();
            Long analysisParameterIds=getAnalysisParameterIdsFrom(prescriptionCreateDtos.get(0)
                    .getPrescriptionAnalysisDtos()).get(0);

            ParameterAnalysisResult par=parameterAnalysisResultRepository.findParameterAnalysisResultByLabWorkOrderIdAndAnalysisParameterId(
                    labWorkOrderId,analysisParameterIds);

            Assertions.assertTrue(par.getResult().equals(result));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Given("napravljeno {int} filtera za LabWorkOrder")
    public void napravljeno_filtera_za_lab_work_order(Integer filterCount) {
        try{

            filters=new ArrayList<>();
            for(int i=0;i<filterCount;i++){
                filters.add(labWorkOrderFilterGenerator.getRandomFilter());
                if(filters.get(i).getLbp()!=null){
                    if(randomLong.getLong(2L)==0)filters.get(i).setLbp(lbp);
                    else filters.get(i).setLbp(lbp2);
                }
                filters.get(i).setStatus(null);
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    private List<List<LabWorkOrder>> results,queried;
    @When("dobavljeni rezultati filtriranja po datumu i LBP")
    public void dobavljeni_rezultati_filtriranja_po_datumu_i_lbp() {
        try{
            for(int i=0;i<filters.size();i++) {
                List<OrderStatus>pom=new ArrayList<>();
                pom.add(OrderStatus.OBRADJEN);
                pom.add(OrderStatus.U_OBRADI);
                filters.get(i).setStatus(pom);
            }

            List<LabWorkOrder> list=labWorkOrderRepository.findAll();

            results=new ArrayList<>();
            queried=new ArrayList<>();
            for(int i=0;i<filters.size();i++){
                LabWorkOrderFilter f=filters.get(i);
                results.add(f.applyFilterToList(list));
                queried.add(labWorkOrdersService.workOrdersHistory(f.getLbp(),f.getFromDate(),f.getToDate(),0,1000000000)
                        .getContent());
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("filtriranje po tim filterima daje zeljene rezultate")
    public void filtriranje_po_tim_filterima_daje_zeljene_rezultate() {
        try{


            for(int i=0;i< filters.size();i++){

                List<LabWorkOrder>pom1=new ArrayList<>(results.get(i));
                List<LabWorkOrder>pom2=new ArrayList<>(queried.get(i));

                pom1.sort(Comparator.comparing(LabWorkOrder::getId));
                pom2.sort(Comparator.comparing(LabWorkOrder::getId));

                System.out.println("FILTER:  "+filters.get(i).getLbp()+" "+pom1.size());
                Assertions.assertTrue(classJsonComparator.compareListCommonFields(pom1,pom2));

            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }




    @When("dobavljeni rezultati filtriranja po datumu, LBP i statusu")
    public void dobavljeni_rezultati_filtriranja_po_datumu_lbp_i_statusu() {
        try{

            List<LabWorkOrder> list=labWorkOrderRepository.findAll();

            results=new ArrayList<>();
            queried=new ArrayList<>();
            for(int i=0;i<filters.size();i++){
                LabWorkOrderFilter f=filters.get(i);
                results.add(f.applyFilterToList(list));
                List<OrderStatus> sendStatus=f.getStatus();
                if(sendStatus==null){
                    sendStatus=new ArrayList<>();
                    sendStatus.add(null);
                }
                queried.add(labWorkOrdersService.findWorkOrdersByLab(f.getLbp(),f.getFromDate(),
                                f.getToDate(),sendStatus.get(0),0,1000000000)
                        .getContent());
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }




    @Given("prijavljeni smo kao ROLE_MED_BIOHEMICAR")
    public void prijavljeni_smo_kao_role_med_biohemicar() {
        try{
            tokenSetter.setToken(jwtTokenGetter.getMedBiohem());
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @When("dobavimo rezultate oni su tacni")
    public void dobavimo_rezultate_oni_su_tacni() {
        try{

            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(prescriptionCreateDtos.get(0).getPid()).get().getId();

            LabWorkOrderWithAnalysisDto lwowad=labWorkOrdersService.findParameterAnalysisResultsForWorkOrder(
                    labWorkOrderId);

            LabWorkOrder lwo=labWorkOrderRepository.findLabWorkOrderById(labWorkOrderId);

            Assertions.assertTrue(classJsonComparator.compareCommonFields(lwo,lwowad));
            Assertions.assertEquals(lwo.getPrescription().getId(), lwowad.getPrescriptionId());

            List<ParameterAnalysisResult> allPar=parameterAnalysisResultRepository.findAll();
            List<ParameterAnalysisResult> myPar=new ArrayList<>();
            for(ParameterAnalysisResult par:allPar)
                if(par.getLabWorkOrder().getId().equals(labWorkOrderId))
                    myPar.add(par);

            List<ParameterAnalysisResult>pom1=new ArrayList<>(myPar);
            List<ParameterAnalysisResultWithDetailsDto>pom2=new ArrayList<>(lwowad.getParameterAnalysisResults());
            pom1.sort(Comparator.comparing(ParameterAnalysisResult::getId));
            pom2.sort(Comparator.comparing(ParameterAnalysisResultWithDetailsDto::getId));

            Assertions.assertTrue(pom1.size()==pom2.size());
            for(int i=0;i<pom1.size();i++){

                Assertions.assertTrue(classJsonComparator.compareCommonFields(pom1.get(i),pom2.get(i)));

                Assertions.assertTrue(classJsonComparator.compareCommonFields(
                        pom1.get(i).getAnalysisParameter().getParameter(),
                        pom2.get(i).getParameter()));

                Assertions.assertTrue(classJsonComparator.compareCommonFields(
                        pom1.get(i).getAnalysisParameter().getLabAnalysis(),
                        pom2.get(i).getLabAnalysis()
                ));

            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("obrisemo LabWorkOrder")
    public void obrisemo_lab_work_order() {
        try{

            labWorkOrdersService.deleteWorkOrder(labWorkOrderRepository.findByPrescription(
                    prescriptionCreateDtos.get(0).getPid()).get());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("on i njegove analize se vise ne nalaze u bazi")
    public void on_i_njegove_analize_se_vise_ne_nalaze_u_bazi() {
        try{

            Optional<LabWorkOrder> pom=labWorkOrderRepository.findByPrescription(
                    prescriptionCreateDtos.get(0).getPid());

            Assertions.assertTrue(!pom.isPresent());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("azuriramo status LabWorkOrdera")
    public void azuriramo_status_lab_work_ordera() {
        try{

            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(
                    prescriptionCreateDtos.get(0).getPid()).get().getId();

            labWorkOrdersService.updateLabWorkOrderStatus(labWorkOrderId,OrderStatus.OBRADJEN);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("je on azuriran")
    public void je_on_azuriran() {
        try{

            Long labWorkOrderId=labWorkOrderRepository.findByPrescription(
                    prescriptionCreateDtos.get(0).getPid()).get().getId();

            LabWorkOrder pom=labWorkOrderRepository.findLabWorkOrderById(labWorkOrderId);

            Assertions.assertTrue(pom.getStatus()==OrderStatus.OBRADJEN);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
