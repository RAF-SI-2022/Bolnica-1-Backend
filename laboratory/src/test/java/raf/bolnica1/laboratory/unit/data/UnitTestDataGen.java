package raf.bolnica1.laboratory.unit.data;

import raf.bolnica1.laboratory.domain.constants.*;
import raf.bolnica1.laboratory.domain.lab.*;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ParameterAnalysisResultDto;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;

public class UnitTestDataGen {

    public static AnalysisParameterDto createAnalysisParameterDto() {
        AnalysisParameterDto analysisParameterDto = new AnalysisParameterDto();
        analysisParameterDto.setId(1L);
        LabAnalysisDto labAnalysisDto = new LabAnalysisDto();
        labAnalysisDto.setId(1L);
        labAnalysisDto.setAnalysisName("Default Analysis");
        labAnalysisDto.setAbbreviation("DA");
        analysisParameterDto.setLabAnalysis(labAnalysisDto);
        analysisParameterDto.setParameter(createParameterDto());
        return analysisParameterDto;
    }

    public static ParameterDto createParameterDto() {
        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setId(1L);
        parameterDto.setParameterName("Default Parameter");
        parameterDto.setType(ParameterValueType.NUMERICKI);
        parameterDto.setUnitOfMeasure("Units");
        parameterDto.setLowerLimit(0.0);
        parameterDto.setUpperLimit(100.0);
        return parameterDto;
    }

    public static AnalysisParameter createAnalysisParameter() {
        AnalysisParameter analysisParameter = new AnalysisParameter();
        LabAnalysis labAnalysis = new LabAnalysis();
        labAnalysis.setId(1L);
        labAnalysis.setAnalysisName("Default Analysis");
        labAnalysis.setAbbreviation("DA");
        analysisParameter.setLabAnalysis(labAnalysis);
        analysisParameter.setParameter(createParameter());
        return analysisParameter;
    }

    public static Parameter createParameter() {
        Parameter parameter = new Parameter();
        parameter.setId(1L);
        parameter.setParameterName("Default Parameter");
        parameter.setType(ParameterValueType.NUMERICKI);
        parameter.setUnitOfMeasure("Units");
        parameter.setLowerLimit(0.0);
        parameter.setUpperLimit(100.0);
        return parameter;
    }

    public static LabAnalysis createLabAnalysisEntity() {
        LabAnalysis labAnalysis = new LabAnalysis();
        labAnalysis.setId(1L);
        labAnalysis.setAnalysisName("Default Analysis");
        labAnalysis.setAbbreviation("DA");
        return labAnalysis;
    }

    public static LabAnalysisDto createLabAnalysisDto() {
        LabAnalysisDto labAnalysisDto = new LabAnalysisDto();
        labAnalysisDto.setId(1L);
        labAnalysisDto.setAnalysisName("Default Analysis");
        labAnalysisDto.setAbbreviation("DA");
        return labAnalysisDto;
    }

    public static ScheduledLabExamination createScheduledLabExamination() {
        ScheduledLabExamination scheduledLabExamination = new ScheduledLabExamination();
        scheduledLabExamination.setId(1L);
        scheduledLabExamination.setDepartmentId(1L);
        scheduledLabExamination.setLbp("LBP001");
        scheduledLabExamination.setScheduledDate(Date.valueOf("2023-01-01"));
        scheduledLabExamination.setExaminationStatus(ExaminationStatus.ZAKAZANO);
        scheduledLabExamination.setNote("Sample note");
        scheduledLabExamination.setLbz("LBZ001");
        return scheduledLabExamination;
    }

    public static ScheduledLabExaminationDto createScheduledLabExaminationDto() {
        ScheduledLabExaminationDto scheduledLabExaminationDto = new ScheduledLabExaminationDto();
        scheduledLabExaminationDto.setId(1L);
        scheduledLabExaminationDto.setDepartmentId(1L);
        scheduledLabExaminationDto.setLbp("LBP001");
        scheduledLabExaminationDto.setScheduledDate(Date.valueOf("2023-01-01"));
        scheduledLabExaminationDto.setExaminationStatus(ExaminationStatus.ZAKAZANO);
        scheduledLabExaminationDto.setNote("Sample note");
        scheduledLabExaminationDto.setLbz("LBZ001");
        return scheduledLabExaminationDto;
    }

    public static ResultUpdateDto createResultUpdateDto() {
        ResultUpdateDto dto = new ResultUpdateDto();
        dto.setLabWorkOrderId(1L);
        dto.setAnalysisParameterId(1L);
        dto.setResult("Test result");
        dto.setDateTime(createDefaultTimestamp());
        dto.setBiochemistLbz("BiochemistLBZ");
        return dto;
    }

    public static ParameterAnalysisResult createParameterAnalysisResult() {
        ParameterAnalysisResult parameterAnalysisResult = new ParameterAnalysisResult();
        parameterAnalysisResult.setId(1L);
        parameterAnalysisResult.setLabWorkOrder(createLabWorkOrder());
        parameterAnalysisResult.setAnalysisParameter(createAnalysisParameter());
        parameterAnalysisResult.setResult(null);
        parameterAnalysisResult.setDateTime(null);
        parameterAnalysisResult.setBiochemistLbz(null);
        return parameterAnalysisResult;
    }

    public static LabWorkOrder createLabWorkOrder() {
        LabWorkOrder labWorkOrder = new LabWorkOrder();
        labWorkOrder.setId(1L);
        labWorkOrder.setPrescription(createPrescription());
        labWorkOrder.setLbp("LBP");
        labWorkOrder.setCreationDateTime(createDefaultTimestamp());
        labWorkOrder.setStatus(OrderStatus.NEOBRADJEN);
        labWorkOrder.setTechnicianLbz("TechnicianLBZ");
        labWorkOrder.setBiochemistLbz(null);
        return labWorkOrder;
    }

    public static Prescription createPrescription() {
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setType(PrescriptionType.LABORATORIJA);
        prescription.setDoctorLbz("DoctorLBZ");
        prescription.setDepartmentFromId(1L);
        prescription.setDepartmentToId(2L);
        prescription.setLbp("LBP");
        prescription.setCreationDateTime(createDefaultTimestamp());
        prescription.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescription.setComment("Test comment");
        return prescription;
    }

    public static PrescriptionDto createPrescriptionDto() {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setType(PrescriptionType.LABORATORIJA);
        prescriptionDto.setDoctorLbz("LBZ001");
        prescriptionDto.setDepartmentFromId(1L);
        prescriptionDto.setDepartmentToId(2L);
        prescriptionDto.setLbp("LBP001");
        prescriptionDto.setCreationDateTime(createDefaultTimestamp());
        prescriptionDto.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescriptionDto.setRequestedTests("Blood Test, Urine Test");
        prescriptionDto.setComment("Some comment");
        prescriptionDto.setReferralDiagnosis("Some diagnosis");
        prescriptionDto.setReferralReason("Some reason");
        return prescriptionDto;
    }

    public static Timestamp createDefaultTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static LabWorkOrderDto getDefaultLabWorkOrderDto() {
        PrescriptionDto prescriptionDto = createPrescriptionDto();

        AnalysisParameterDto analysisParameterDto = new AnalysisParameterDto();
        analysisParameterDto.setId(1L);

        LabAnalysisDto labAnalysisDto = new LabAnalysisDto();
        labAnalysisDto.setId(1L);
        labAnalysisDto.setAnalysisName("Blood Test");
        labAnalysisDto.setAbbreviation("BT");

        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setId(1L);
        parameterDto.setParameterName("Blood Glucose");
        parameterDto.setType(ParameterValueType.NUMERICKI);
        parameterDto.setUnitOfMeasure("mg/dL");
        parameterDto.setLowerLimit(70.0);
        parameterDto.setUpperLimit(100.0);

        analysisParameterDto.setParameter(parameterDto);
        analysisParameterDto.setLabAnalysis(labAnalysisDto);

        ParameterAnalysisResultDto parameterAnalysisResultDto = new ParameterAnalysisResultDto();
        parameterAnalysisResultDto.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
        parameterAnalysisResultDto.setResult("100");
        parameterAnalysisResultDto.setBiochemistLbz("LBZ001");

        parameterAnalysisResultDto.setAnalysesParameters(Collections.singletonList(analysisParameterDto));

        LabWorkOrderDto labWorkOrderDto = new LabWorkOrderDto();
        labWorkOrderDto.setPrescription(prescriptionDto);
        labWorkOrderDto.setLbp("LBP001");
        labWorkOrderDto.setCreationDateTime(Timestamp.valueOf(LocalDateTime.now()));
        labWorkOrderDto.setStatus(OrderStatus.U_OBRADI);
        labWorkOrderDto.setTechnicianLbz("LBZ001");
        labWorkOrderDto.setBiochemistLbz("LBZ001");
        labWorkOrderDto.setParameterAnalysisResults(Collections.singletonList(parameterAnalysisResultDto));

        return labWorkOrderDto;
    }

}
