package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.util.DevPatientGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DevRandomLBP {

    @Autowired
    private DevPatientGetter devPatientGetter;


    private List<String> list;
    private Random random=new Random();



    public static DevRandomLBP getInstance(){
        return new DevRandomLBP(DevPatientGetter.getInstance());
    }


    public DevRandomLBP(DevPatientGetter devPatientGetter){

        this.devPatientGetter = devPatientGetter;


        list=getLbpList();

        /*System.out.println("LBP LISTA");
        System.out.println(list);*/

    }


    public int getSize(){
        return list.size();
    }

    public String getFromPos(int pos){
        return new String(list.get(pos));
    }

    public String getFromRandom(){
        return new String(list.get(Math.abs(random.nextInt())%getSize()));
    }




    private List<String> getLbpList(){

        List<PatientDto> patients= devPatientGetter.getAllPatients();

        List<String>ret=new ArrayList<>();
        for(PatientDto patientDto:patients)
            ret.add(patientDto.getLbp());

        return ret;
    }

}
