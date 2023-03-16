package raf.bolnica1.laboratory.services;

public interface LaboratoryWorkOrdersService {

    public Object createWorkOrder(Object object);

    public Object workOrderVerification(Object object);

    public Object updateAnalysisParameters(Object object);

    public Object workOrdersHistory(Object object);

    public Object findWorkOrdersByLab(Object object);

    public Object findAnalysisParametersResults(Object object);

}
