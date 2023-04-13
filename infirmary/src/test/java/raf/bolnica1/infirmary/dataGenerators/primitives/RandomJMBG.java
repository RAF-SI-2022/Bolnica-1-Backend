package raf.bolnica1.infirmary.dataGenerators.primitives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.util.PatientGetter;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class RandomJMBG {

    @Autowired
    private PatientGetter patientGetter;

    private List<String> list;
    private Random random=new Random();

    public RandomJMBG(PatientGetter patientGetter){
        this.patientGetter=patientGetter;

        list=new ArrayList<>();

        /*list.add("0705003312430");
        list.add("0211974369304");
        list.add("0708982315363");
        list.add("0501000394873");
        list.add("0703991385976");
        list.add("1701973385013");
        list.add("2509973307476");
        list.add("1904982388910");
        list.add("1904995381770");
        list.add("2210989351313");
        list.add("2210992325650");
        list.add("2504005303457");*/

        List<PatientDto>patientDtos=patientGetter.getAllPatients();
        for(PatientDto patientDto:patientDtos)
            list.add(patientDto.getJmbg());

        /*System.out.println("JMBG LISTA");
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
