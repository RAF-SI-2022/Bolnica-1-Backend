package raf.bolnica1.laboratory.runner;


import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.ParameterValueType;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.lab.*;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.repository.*;
import java.sql.Timestamp;
import java.util.Arrays;

import raf.bolnica1.laboratory.services.lab.LabExaminationsService;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.ScheduledLabExaminationCreate;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.ScheduledLabExaminationCreateGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.TokenSetter;

@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private final LabAnalysisRepository labAnalysisRepository;
    private final ParameterRepository parameterRepository;
    private final AnalysisParameterRepository analysisParameterRepository;

    private final PrescriptionRepository prescriptionRepository;
    private final LabWorkOrderRepository labWorkOrderRepository;

    private final PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;
    private final PrescriptionRecieveService prescriptionRecieveService;
    private final LabExaminationsService labExaminationsService;
    private final ScheduledLabExaminationCreateGenerator scheduledLabExaminationCreateGenerator;
    private final JwtTokenGetter jwtTokenGetter;
    private final TokenSetter tokenSetter;

    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    @Override
    public void run(String... args) throws Exception {
        defaultAnalysisParameter();
        generatedData();
    }

    private void generatedData(){

        int prescriptionCount=10;

        for(int i=0;i<prescriptionCount;i++){
            PrescriptionCreateDto p=prescriptionCreateDtoGenerator.getPrescriptionCreateDto(analysisParameterRepository);
            prescriptionRecieveService.createPrescription(p);
        }

        int scheduledExaminationCount=10;
        tokenSetter.setToken(jwtTokenGetter.getDrMedSpec());
        for(int i=0;i<scheduledExaminationCount;i++){
            ScheduledLabExaminationCreate s=scheduledLabExaminationCreateGenerator.getScheduledLabExamination();
            labExaminationsService.createScheduledExamination(s.getLbp(),s.getScheduledDate(),s.getNote(),
                    "Bearer "+jwtTokenGetter.getDrMedSpec());
        }

    }

    private void defaultAnalysisParameter() {

        LabAnalysis la1 = new LabAnalysis();
        la1.setId(1L);
        la1.setAnalysisName("Glukoza");
        la1.setAbbreviation("GLU");

        LabAnalysis la2 = new LabAnalysis();
        la2.setId(2L);
        la2.setAnalysisName("Holesterol");
        la2.setAbbreviation("HOL");

        LabAnalysis la3 = new LabAnalysis();
        la3.setId(3L);
        la3.setAnalysisName("Trigliceridi");
        la3.setAbbreviation("TRIG");

        LabAnalysis la4 = new LabAnalysis();
        la4.setId(4L);
        la4.setAnalysisName("Urea");
        la4.setAbbreviation("URE");

        LabAnalysis la5 = new LabAnalysis();
        la5.setId(5L);
        la5.setAnalysisName("Kreatinin");
        la5.setAbbreviation("KREAT");

        LabAnalysis la6 = new LabAnalysis();
        la6.setId(6L);
        la6.setAnalysisName("Mokraćna kiselina");
        la6.setAbbreviation("MK");

        LabAnalysis la7 = new LabAnalysis();
        la7.setId(7L);
        la7.setAnalysisName("Bilirubin");
        la7.setAbbreviation("BILIR-uk");

        LabAnalysis la8 = new LabAnalysis();
        la8.setId(8L);
        la8.setAnalysisName("Alanin aminotransferaza");
        la8.setAbbreviation("ALT");

        LabAnalysis la9 = new LabAnalysis();
        la9.setId(9L);
        la9.setAnalysisName("Aspartat aminotransferaza");
        la9.setAbbreviation("AST");

        LabAnalysis la10 = new LabAnalysis();
        la10.setId(10L);
        la10.setAnalysisName("Kreatin kinaza");
        la10.setAbbreviation("CK");

        LabAnalysis la11 = new LabAnalysis();
        la11.setId(11L);
        la11.setAnalysisName("Tireostimulirajući hormon");
        la11.setAbbreviation("TSH");

        LabAnalysis la12 = new LabAnalysis();
        la12.setId(12L);
        la12.setAnalysisName("Slobodni T4");
        la12.setAbbreviation("FT4");

        LabAnalysis la13 = new LabAnalysis();
        la13.setId(13L);
        la13.setAnalysisName("C-reaktivni protein");
        la13.setAbbreviation("CRP");

        LabAnalysis la14 = new LabAnalysis();
        la14.setId(14L);
        la14.setAnalysisName("Leukociti");
        la14.setAbbreviation("WBC");

        LabAnalysis la15 = new LabAnalysis();
        la15.setId(15L);
        la15.setAnalysisName("Eritrociti");
        la15.setAbbreviation("RBC");

        LabAnalysis la16 = new LabAnalysis();
        la16.setId(16L);
        la16.setAnalysisName("Trombociti");
        la16.setAbbreviation("PLT");

        LabAnalysis la17 = new LabAnalysis();
        la17.setId(17L);
        la17.setAnalysisName("Hemoglobin");
        la17.setAbbreviation("Hb");

        LabAnalysis la18 = new LabAnalysis();
        la18.setId(18L);
        la18.setAnalysisName("Kompletna krvna slika");
        la18.setAbbreviation("KKS");

        LabAnalysis la19 = new LabAnalysis();
        la19.setId(19L);
        la19.setAnalysisName("Sedimentacija");
        la19.setAbbreviation("SE");

        LabAnalysis la20 = new LabAnalysis();
        la20.setId(20L);
        la20.setAnalysisName("SARS CoV-2 antigen");
        la20.setAbbreviation("SARS CoV-2 antigen");

        LabAnalysis la21 = new LabAnalysis();
        la21.setId(21L);
        la21.setAnalysisName("Urin");
        la21.setAbbreviation("URIN");

        labAnalysisRepository.saveAll(Arrays.asList(
                la1, la2, la3, la4, la5, la6, la7, la8, la9, la10, la11,
                la12, la13, la14, la15, la16, la17, la18, la19, la20, la21)
        );


        Parameter p1 = new Parameter();
        p1.setId(1L);
        p1.setParameterName("Glukoza");
        p1.setUnitOfMeasure("mmol/L");
        p1.setType(ParameterValueType.NUMERICKI);
        p1.setLowerLimit(3.9);
        p1.setUpperLimit(6.1);

        Parameter p2 = new Parameter();
        p2.setId(2L);
        p2.setParameterName("Holesterol");
        p2.setUnitOfMeasure("mmol/L");
        p2.setType(ParameterValueType.NUMERICKI);
        p2.setUpperLimit(5.2);

        Parameter p3 = new Parameter();
        p3.setId(3L);
        p3.setParameterName("Trigliceridi");
        p3.setUnitOfMeasure("mmol/L");
        p3.setType(ParameterValueType.NUMERICKI);
        p3.setUpperLimit(1.7);

        Parameter p4 = new Parameter();
        p4.setId(4L);
        p4.setParameterName("Urea");
        p4.setUnitOfMeasure("mmol/L");
        p4.setType(ParameterValueType.NUMERICKI);
        p4.setLowerLimit(2.1);
        p4.setUpperLimit(7.1);

        Parameter p5 = new Parameter();
        p5.setId(5L);
        p5.setParameterName("Kreatinin");
        p5.setUnitOfMeasure("umol/L");
        p5.setType(ParameterValueType.NUMERICKI);
        p5.setLowerLimit(53D);
        p5.setUpperLimit(97D);

        Parameter p6 = new Parameter();
        p6.setId(6L);
        p6.setParameterName("Mokraćna kiselina");
        p6.setUnitOfMeasure("umol/L");
        p6.setType(ParameterValueType.NUMERICKI);
        p6.setLowerLimit(150D);
        p6.setUpperLimit(400D);

        Parameter p7 = new Parameter();
        p7.setId(7L);
        p7.setParameterName("Bilirubin");
        p7.setUnitOfMeasure("umol/L");
        p7.setType(ParameterValueType.NUMERICKI);
        p7.setLowerLimit(5.1);
        p7.setUpperLimit(20.5);

        Parameter p8 = new Parameter();
        p8.setId(8L);
        p8.setParameterName("Alanin aminotransferaza");
        p8.setUnitOfMeasure("U/L");
        p8.setType(ParameterValueType.NUMERICKI);
        p8.setLowerLimit(10D);
        p8.setUpperLimit(40D);

        Parameter p9 = new Parameter();
        p9.setId(9L);
        p9.setParameterName("Aspartat aminotransferaza");
        p9.setUnitOfMeasure("U/L");
        p9.setType(ParameterValueType.NUMERICKI);
        p9.setLowerLimit(10D);
        p9.setUpperLimit(40D);

        Parameter p10 = new Parameter();
        p10.setId(10L);
        p10.setParameterName("Kreatin kinaza");
        p10.setUnitOfMeasure("U/L");
        p10.setType(ParameterValueType.NUMERICKI);
        p10.setLowerLimit(24D);
        p10.setUpperLimit(170D);

        Parameter p11 = new Parameter();
        p11.setId(11L);
        p11.setParameterName("Tireostimulirajući hormon");
        p11.setUnitOfMeasure("mIU/L");
        p11.setType(ParameterValueType.NUMERICKI);
        p11.setLowerLimit(0.4);
        p11.setUpperLimit(4.0);

        Parameter p12 = new Parameter();
        p12.setId(12L);
        p12.setParameterName("Slobodni T4");
        p12.setUnitOfMeasure("pmol/L");
        p12.setType(ParameterValueType.NUMERICKI);
        p12.setLowerLimit(9.9);
        p12.setUpperLimit(22.7);

        Parameter p13 = new Parameter();
        p13.setId(13L);
        p13.setParameterName("C-reaktivni protein");
        p13.setUnitOfMeasure("mg/L");
        p13.setType(ParameterValueType.NUMERICKI);
        p13.setUpperLimit(5.0);

        Parameter p14 = new Parameter();
        p14.setId(14L);
        p14.setParameterName("Leukociti");
        p14.setUnitOfMeasure("10*9/L");
        p14.setType(ParameterValueType.NUMERICKI);
        p14.setLowerLimit(4.0);
        p14.setUpperLimit(10.0);

        Parameter p15 = new Parameter();
        p15.setId(15L);
        p15.setParameterName("Eritrociti");
        p15.setUnitOfMeasure("10*12/L");
        p15.setType(ParameterValueType.NUMERICKI);
        p15.setLowerLimit(3.80);
        p15.setUpperLimit(5.60);

        Parameter p16 = new Parameter();
        p16.setId(16L);
        p16.setParameterName("Trombociti");
        p16.setUnitOfMeasure("10*9/L");
        p16.setType(ParameterValueType.NUMERICKI);
        p16.setLowerLimit(140.0);
        p16.setUpperLimit(440.0);

        Parameter p17 = new Parameter();
        p17.setId(17L);
        p17.setParameterName("Hemoglobin");
        p17.setUnitOfMeasure("g/L");
        p17.setType(ParameterValueType.NUMERICKI);
        p17.setLowerLimit(117.0);
        p17.setUpperLimit(155.0);

        Parameter p18 = new Parameter();
        p18.setId(18L);
        p18.setParameterName("Limfociti");
        p18.setUnitOfMeasure("10*9/L");
        p18.setType(ParameterValueType.NUMERICKI);
        p18.setLowerLimit(1.0);
        p18.setUpperLimit(4.0);

        Parameter p19 = new Parameter();
        p19.setId(19L);
        p19.setParameterName("Monociti");
        p19.setUnitOfMeasure("10*9/L");
        p19.setType(ParameterValueType.NUMERICKI);
        p19.setLowerLimit(0.2);
        p19.setUpperLimit(1.0);

        Parameter p20 = new Parameter();
        p20.setId(20L);
        p20.setParameterName("Granulociti");
        p20.setUnitOfMeasure("10*9/L");
        p20.setType(ParameterValueType.NUMERICKI);
        p20.setLowerLimit(2.0);
        p20.setUpperLimit(7.0);

        Parameter p21 = new Parameter();
        p21.setId(21L);
        p21.setParameterName("Limfociti");
        p21.setUnitOfMeasure("%");
        p21.setType(ParameterValueType.NUMERICKI);
        p21.setLowerLimit(20.0);
        p21.setUpperLimit(45.0);

        Parameter p22 = new Parameter();
        p22.setId(22L);
        p22.setParameterName("Monociti");
        p22.setUnitOfMeasure("%");
        p22.setType(ParameterValueType.NUMERICKI);
        p22.setLowerLimit(4.0);
        p22.setUpperLimit(10.0);

        Parameter p23 = new Parameter();
        p23.setId(23L);
        p23.setParameterName("Granulociti");
        p23.setUnitOfMeasure("%");
        p23.setType(ParameterValueType.NUMERICKI);
        p23.setLowerLimit(45.0);
        p23.setUpperLimit(70.0);

        Parameter p24 = new Parameter();
        p24.setId(24L);
        p24.setParameterName("Hematokrit");
        p24.setUnitOfMeasure("L/L");
        p24.setType(ParameterValueType.NUMERICKI);
        p24.setLowerLimit(0.340);
        p24.setUpperLimit(0.500);

        Parameter p25 = new Parameter();
        p25.setId(25L);
        p25.setParameterName("MCV");
        p25.setUnitOfMeasure("fL");
        p25.setType(ParameterValueType.NUMERICKI);
        p25.setLowerLimit(81.0);
        p25.setUpperLimit(100.0);

        Parameter p26 = new Parameter();
        p26.setId(26L);
        p26.setParameterName("MCH");
        p26.setUnitOfMeasure("pg");
        p26.setType(ParameterValueType.NUMERICKI);
        p26.setLowerLimit(27.0);
        p26.setUpperLimit(34.0);

        Parameter p27 = new Parameter();
        p27.setId(27L);
        p27.setParameterName("MCHC");
        p27.setUnitOfMeasure("g/L");
        p27.setType(ParameterValueType.NUMERICKI);
        p27.setLowerLimit(315D);
        p27.setUpperLimit(360D);

        Parameter p28 = new Parameter();
        p28.setId(28L);
        p28.setParameterName("RDW");
        p28.setUnitOfMeasure("%");
        p28.setType(ParameterValueType.NUMERICKI);
        p28.setLowerLimit(10.0);
        p28.setUpperLimit(16.0);

        Parameter p29 = new Parameter();
        p29.setId(29L);
        p29.setParameterName("MPV");
        p29.setUnitOfMeasure("fL");
        p29.setType(ParameterValueType.NUMERICKI);
        p29.setLowerLimit(6.5);
        p29.setUpperLimit(11.0);

        Parameter p30 = new Parameter();
        p30.setId(30L);
        p30.setParameterName("PDW");
        p30.setUnitOfMeasure("%");
        p30.setType(ParameterValueType.NUMERICKI);
        p30.setLowerLimit(10.0);
        p30.setUpperLimit(18.0);

        Parameter p31 = new Parameter();
        p31.setId(31L);
        p31.setParameterName("PC");
        p31.setUnitOfMeasure("cL/L");
        p31.setType(ParameterValueType.NUMERICKI);
        p31.setLowerLimit(0.125);
        p31.setUpperLimit(0.350);

        Parameter p32 = new Parameter();
        p32.setId(32L);
        p32.setParameterName("Sedimentacija");
        p32.setUnitOfMeasure("mm/1.h");
        p32.setType(ParameterValueType.NUMERICKI);
        p32.setLowerLimit(20D);

        Parameter p33 = new Parameter();
        p33.setId(33L);
        p33.setParameterName("SARS CoV-2 antigen");
        p33.setType(ParameterValueType.TEKSTUALNI);

        Parameter p34 = new Parameter();
        p34.setId(34L);
        p34.setParameterName("Izgled");
        p34.setType(ParameterValueType.TEKSTUALNI);

        Parameter p35 = new Parameter();
        p35.setId(35L);
        p35.setParameterName("Boja");
        p35.setType(ParameterValueType.TEKSTUALNI);

        Parameter p36 = new Parameter();
        p36.setId(36L);
        p36.setParameterName("Reakcija, pH");
        p36.setType(ParameterValueType.NUMERICKI);
        p36.setLowerLimit(4.7);
        p36.setUpperLimit(7.8);

        Parameter p37 = new Parameter();
        p37.setId(37L);
        p37.setParameterName("Specifična težina");
        p37.setType(ParameterValueType.NUMERICKI);
        p37.setLowerLimit(1.003);
        p37.setUpperLimit(1.035);

        Parameter p38 = new Parameter();
        p38.setId(38L);
        p38.setParameterName("Proteini");
        p38.setType(ParameterValueType.TEKSTUALNI);

        Parameter p39 = new Parameter();
        p39.setId(39L);
        p39.setParameterName("Glukoza");
        p39.setType(ParameterValueType.TEKSTUALNI);

        Parameter p40 = new Parameter();
        p40.setId(40L);
        p40.setParameterName("Ketoni");
        p40.setType(ParameterValueType.TEKSTUALNI);

        Parameter p41 = new Parameter();
        p41.setId(41L);
        p41.setParameterName("Bilirubin");
        p41.setType(ParameterValueType.TEKSTUALNI);

        Parameter p42 = new Parameter();
        p42.setId(42L);
        p42.setParameterName("Urobilinogen");
        p42.setType(ParameterValueType.TEKSTUALNI);

        Parameter p43 = new Parameter();
        p43.setId(43L);
        p43.setParameterName("Hemoglobin");
        p43.setType(ParameterValueType.TEKSTUALNI);

        Parameter p44 = new Parameter();
        p44.setId(44L);
        p44.setParameterName("Nitriti");
        p44.setType(ParameterValueType.TEKSTUALNI);

        Parameter p45 = new Parameter();
        p45.setId(45L);
        p45.setParameterName("Askorbinska kiselina");
        p45.setType(ParameterValueType.TEKSTUALNI);

        Parameter p46 = new Parameter();
        p46.setId(46L);
        p46.setParameterName("Eritrociti sveža");
        p46.setUnitOfMeasure("sveža/hpf");
        p46.setType(ParameterValueType.NUMERICKI);
        p46.setUpperLimit(2D);

        Parameter p47 = new Parameter();
        p47.setId(47L);
        p47.setParameterName("Leukociti");
        p47.setUnitOfMeasure("/hpf");
        p47.setType(ParameterValueType.NUMERICKI);
        p47.setUpperLimit(5D);

        Parameter p48 = new Parameter();
        p48.setId(48L);
        p48.setParameterName("Pločaste epitelne ćelije");
        p48.setUnitOfMeasure("/hpf");
        p48.setType(ParameterValueType.NUMERICKI);
        p48.setUpperLimit(4D);

        Parameter p49 = new Parameter();
        p49.setId(49L);
        p49.setParameterName("Male epitelne ćelije");
        p49.setType(ParameterValueType.TEKSTUALNI);

        Parameter p50 = new Parameter();
        p50.setId(50L);
        p50.setParameterName("Cilindri");
        p50.setType(ParameterValueType.TEKSTUALNI);

        Parameter p51 = new Parameter();
        p51.setId(51L);
        p51.setParameterName("Kristali");
        p51.setType(ParameterValueType.TEKSTUALNI);

        Parameter p52 = new Parameter();
        p52.setId(52L);
        p52.setParameterName("Bakterije");
        p52.setType(ParameterValueType.TEKSTUALNI);

        parameterRepository.saveAll(Arrays.asList(
                p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12,
                p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25,
                p26, p27, p28, p29, p30, p31, p32, p33, p34, p35, p36, p37, p38,
                p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50, p51, p52)
        );

        // analysisParameterRepository

        AnalysisParameter ap1 = new AnalysisParameter();
        ap1.setLabAnalysis(la1);
        ap1.setParameter(p1);

        AnalysisParameter ap2 = new AnalysisParameter();
        ap2.setLabAnalysis(la2);
        ap2.setParameter(p2);

        AnalysisParameter ap3 = new AnalysisParameter();
        ap3.setLabAnalysis(la3);
        ap3.setParameter(p3);

        AnalysisParameter ap4 = new AnalysisParameter();
        ap4.setLabAnalysis(la4);
        ap4.setParameter(p4);

        AnalysisParameter ap5 = new AnalysisParameter();
        ap5.setLabAnalysis(la5);
        ap5.setParameter(p5);

        AnalysisParameter ap6 = new AnalysisParameter();
        ap6.setLabAnalysis(la6);
        ap6.setParameter(p6);

        AnalysisParameter ap7 = new AnalysisParameter();
        ap7.setLabAnalysis(la7);
        ap7.setParameter(p7);

        AnalysisParameter ap8 = new AnalysisParameter();
        ap8.setLabAnalysis(la8);
        ap8.setParameter(p8);

        AnalysisParameter ap9 = new AnalysisParameter();
        ap9.setLabAnalysis(la9);
        ap9.setParameter(p9);

        AnalysisParameter ap10 = new AnalysisParameter();
        ap10.setLabAnalysis(la10);
        ap10.setParameter(p10);

        AnalysisParameter ap11 = new AnalysisParameter();
        ap11.setLabAnalysis(la11);
        ap11.setParameter(p11);

        AnalysisParameter ap12 = new AnalysisParameter();
        ap12.setLabAnalysis(la12);
        ap12.setParameter(p12);

        AnalysisParameter ap13 = new AnalysisParameter();
        ap13.setLabAnalysis(la13);
        ap13.setParameter(p13);

        AnalysisParameter ap14 = new AnalysisParameter();
        ap14.setLabAnalysis(la14);
        ap14.setParameter(p14);

        AnalysisParameter ap15 = new AnalysisParameter();
        ap15.setLabAnalysis(la15);
        ap15.setParameter(p15);

        AnalysisParameter ap16 = new AnalysisParameter();
        ap16.setLabAnalysis(la16);
        ap16.setParameter(p16);

        AnalysisParameter ap17 = new AnalysisParameter();
        ap17.setLabAnalysis(la17);
        ap17.setParameter(p17);

        for (int i = 14; i <= 31; i++) {
            makeThemRelatable(la18, i);
        }

        AnalysisParameter ap19 = new AnalysisParameter();
        ap19.setLabAnalysis(la19);
        ap19.setParameter(p32);

        AnalysisParameter ap20 = new AnalysisParameter();
        ap20.setLabAnalysis(la20);
        ap20.setParameter(p33);

        for (int i = 34; i <= 52; i++) {
            makeThemRelatable(la21, i);
        }

        // 18 i 21 su setovani u makeThemRelatable()
        analysisParameterRepository.saveAll(Arrays.asList(
                ap1, ap2, ap3, ap4, ap5, ap6, ap7, ap8, ap9, ap10, ap11, ap12,
                ap13, ap14, ap15, ap16, ap17, ap19, ap20)
        );

        // prescriptions
        Prescription prescription1 = new Prescription();
        prescription1.setId(1L);
        prescription1.setType(PrescriptionType.LABORATORIJA); // Replace SOME_TYPE with the appropriate PrescriptionType
        prescription1.setDoctorLbz("E0003");
        prescription1.setDepartmentFromId(201L);
        prescription1.setDepartmentToId(202L);
        prescription1.setLbp("10001L");
        prescription1.setCreationDateTime(Timestamp.valueOf("2023-03-18 10:00:00"));
        prescription1.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescription1.setComment("Urgent");

        Prescription prescription2 = new Prescription();
        prescription2.setId(2L);
        prescription2.setType(PrescriptionType.DIJAGNOSTIKA); // Replace ANOTHER_TYPE with the appropriate PrescriptionType
        prescription2.setDoctorLbz("E0003");
        prescription2.setDepartmentFromId(203L);
        prescription2.setDepartmentToId(204L);
        prescription2.setLbp("10002L");
        prescription2.setCreationDateTime(Timestamp.valueOf("2023-03-18 11:00:00"));
        prescription2.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescription2.setComment("Regular");

        prescriptionRepository.saveAll(Arrays.asList(prescription1, prescription2));

        // work orders
        LabWorkOrder labWorkOrder1 = new LabWorkOrder();
        labWorkOrder1.setId(1L);
        labWorkOrder1.setPrescription(prescription1);
        labWorkOrder1.setLbp("20001L");
        labWorkOrder1.setCreationDateTime(Timestamp.valueOf("2023-03-18 12:00:00"));
        labWorkOrder1.setStatus(OrderStatus.NEOBRADJEN);
        labWorkOrder1.setTechnicianLbz("ABC123");
        //labWorkOrder1.setBiochemistLbz("Biochemist 1");

        LabWorkOrder labWorkOrder2 = new LabWorkOrder();
        labWorkOrder2.setId(2L);
        labWorkOrder2.setPrescription(prescription2);
        labWorkOrder2.setLbp("20002L");
        labWorkOrder2.setCreationDateTime(Timestamp.valueOf("2023-03-18 13:00:00"));
        labWorkOrder2.setStatus(OrderStatus.U_OBRADI);
        labWorkOrder2.setTechnicianLbz("ABC1233");
        //labWorkOrder2.setBiochemistLbz("Biochemist 2");

        /*LabWorkOrder labWorkOrder3 = new LabWorkOrder();
        labWorkOrder3.setId(3L);
        labWorkOrder3.setPrescription(prescription2);
        labWorkOrder3.setLbp("20001L");
        labWorkOrder3.setCreationDateTime(Timestamp.valueOf("2023-03-19 13:00:00"));
        labWorkOrder3.setStatus(OrderStatus.U_OBRADI);
        labWorkOrder3.setTechnicianLbz("ABC1233");*/
        //labWorkOrder3.setBiochemistLbz("Biochemist 3");

        labWorkOrderRepository.saveAll(Arrays.asList(labWorkOrder1, labWorkOrder2));

        // parameter analysis result
        ParameterAnalysisResult parameterAnalysisResult1 = new ParameterAnalysisResult();
        parameterAnalysisResult1.setId(1L);
        parameterAnalysisResult1.setLabWorkOrder(labWorkOrder1); // Assuming labWorkOrder1 is defined
        parameterAnalysisResult1.setAnalysisParameter(ap1);
        parameterAnalysisResult1.setResult("15.5");
        parameterAnalysisResult1.setDateTime(Timestamp.valueOf("2023-03-18 14:00:00"));
        parameterAnalysisResult1.setBiochemistLbz("Biochemist 1");

        ParameterAnalysisResult parameterAnalysisResult2 = new ParameterAnalysisResult();
        parameterAnalysisResult2.setId(2L);
        parameterAnalysisResult2.setLabWorkOrder(labWorkOrder1); // Assuming labWorkOrder2 is defined
        parameterAnalysisResult2.setAnalysisParameter(ap2);
        parameterAnalysisResult2.setResult("5.0");
        parameterAnalysisResult2.setDateTime(Timestamp.valueOf("2023-03-18 15:00:00"));
        parameterAnalysisResult2.setBiochemistLbz("Biochemist 2");

        parameterAnalysisResultRepository.saveAll(Arrays.asList(parameterAnalysisResult1, parameterAnalysisResult2));

    }

    private void makeThemRelatable(LabAnalysis la, int i) {
        AnalysisParameter ap = new AnalysisParameter();
        ap.setLabAnalysis(la);
        Parameter p = parameterRepository.findById((long) i).isPresent() ? parameterRepository.findById((long) i).get() : null;
        if (p == null) {
            throw new RuntimeException("Parameter p" + i + "=null");
        }
        ap.setParameter(p);
        analysisParameterRepository.save(ap);
    }

}
