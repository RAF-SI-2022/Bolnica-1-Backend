package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.util.DevPatientGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DevRandomNames {

    @Autowired
    private DevPatientGetter devPatientGetter;

    private List<String> list;
    private Random random=new Random();


    public static DevRandomNames getInstance(){
        return new DevRandomNames(DevPatientGetter.getInstance());
    }


    public DevRandomNames(DevPatientGetter devPatientGetter){
        this.devPatientGetter = devPatientGetter;

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

        List<PatientDto>patientDtos= devPatientGetter.getAllPatients();
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
