package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.util.DevPatientGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class DevRandomSurnames {

    @Autowired
    private DevPatientGetter devPatientGetter;

    private List<String> list;
    private Random random=new Random();


    public static DevRandomSurnames getInstance(){
        return new DevRandomSurnames(DevPatientGetter.getInstance());
    }


    public DevRandomSurnames(DevPatientGetter devPatientGetter){
        this.devPatientGetter = devPatientGetter;

        list=new ArrayList<>();

        /*list.add("Smith");
        list.add("Johnson");
        list.add("West");
        list.add("Lucic");
        list.add("Jovanovic");
        list.add("Kerestes");
        list.add("Kostic");
        list.add("Grahovac");
        list.add("Siflis");
        list.add("Najdic");
        list.add("Markovic");*/


        List<PatientDto>patientDtos= devPatientGetter.getAllPatients();
        for(PatientDto patientDto:patientDtos)
            list.add(patientDto.getSurname());


        /*System.out.println("SURNAMES LISTA");
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
