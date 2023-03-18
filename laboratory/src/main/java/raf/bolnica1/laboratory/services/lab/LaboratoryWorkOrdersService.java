package raf.bolnica1.laboratory.services.lab;

public interface LaboratoryWorkOrdersService {

    Object createWorkOrder(Object object);

    Object workOrderVerification(Object object);

    Object updateAnalysisParameters(Object object);

    Object workOrdersHistory(Object object);

    Object findWorkOrdersByLab(Object object);

    Object findAnalysisParametersResults(Object object);

}
