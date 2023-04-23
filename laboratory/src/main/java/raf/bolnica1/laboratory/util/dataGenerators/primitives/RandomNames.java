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
public class RandomNames {

    @Autowired
    private PatientGetter patientGetter;

    private List<String> list;
    private Random random=new Random(RandomSeed.seed);


    public static RandomNames getInstance(){
        return new RandomNames(PatientGetter.getInstance());
    }


    public RandomNames(PatientGetter patientGetter){
        this.patientGetter=patientGetter;

        list=new ArrayList<>();

        /*list.add("James");
        list.add("Igor");
        list.add("Blake");
        list.add("Uros");
        list.add("Stefan");
        list.add("Marko");
        list.add("Ivan");
        list.add("Kosta");
        list.add("Ivana");
        list.add("Ana");
        list.add("Snezana");
        list.add("Katica");*/

        List<PatientDto>patientDtos=patientGetter.getAllPatients();
        for(PatientDto patientDto:patientDtos)
            list.add(patientDto.getName());

        /*System.out.println("NAMES LISTA");
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
}
