package raf.bolnica1.infirmary.dataGenerators.primitives;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.util.PatientGetter;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.util.ExtractPageContentFromPageJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomLBP {

    @Autowired
    private PatientGetter patientGetter;


    private List<String> list;
    private Random random=new Random();



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
