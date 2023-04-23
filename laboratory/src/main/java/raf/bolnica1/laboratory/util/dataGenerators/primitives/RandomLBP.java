package raf.bolnica1.laboratory.util.dataGenerators.primitives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.RandomSeed;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.util.PatientGetter;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.util.patient.PatientDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomLBP {

    @Autowired
    private PatientGetter patientGetter;


    private List<String> list;
    private Random random=new Random(RandomSeed.seed);



    public static RandomLBP getInstance(){
        return new RandomLBP(PatientGetter.getInstance());
    }


    public RandomLBP(PatientGetter patientGetter){

        this.patientGetter=patientGetter;


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

        List<PatientDto> patients=patientGetter.getAllPatients();

        List<String>ret=new ArrayList<>();
        for(PatientDto patientDto:patients)
            ret.add(patientDto.getLbp());

        return ret;
    }

}
