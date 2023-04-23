package raf.bolnica1.laboratory.util.dataGenerators.classes.domain.labWorkOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class LabWorkOrderFilter {

    private String lbp;
    private Date fromDate;
    private Date toDate;
    private List<OrderStatus> status;


    public boolean applyFilter(LabWorkOrder labWorkOrder){

        try{

            if(lbp!=null && !labWorkOrder.getLbp().equals(lbp))return false;
            if(fromDate!=null && labWorkOrder.getCreationDateTime().getTime()<fromDate.getTime() )return false;
            if(toDate!=null && labWorkOrder.getCreationDateTime().getTime()>=toDate.getTime()+24*60*60*1000 )return false;
            if(status!=null && !status.contains(labWorkOrder.getStatus()))return false;

            return true;
        }catch (Exception e){
            return false;
        }

    }

    public List<LabWorkOrder> applyFilterToList(List<LabWorkOrder> list){
        List<LabWorkOrder> ret=new ArrayList<>();

        for(LabWorkOrder p:list)
            if(applyFilter(p))
                ret.add(p);

        return ret;
    }

}
