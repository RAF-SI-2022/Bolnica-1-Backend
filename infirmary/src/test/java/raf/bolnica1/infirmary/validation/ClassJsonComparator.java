package raf.bolnica1.infirmary.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ClassJsonComparator {

    private final ObjectMapper objectMapper;

    public boolean compareCommonFields(Object a,Object b){


        String jsonA;
        String jsonB;
        try {
            jsonA=objectMapper.writeValueAsString(a);
            jsonB=objectMapper.writeValueAsString(b);
        } catch (JsonProcessingException e) {
            return false;
        }

        Map<String,String> mapA,mapB;
        try {
            mapA = objectMapper.readValue(jsonA, new TypeReference<Map<String, String>>() {});
            mapB = objectMapper.readValue(jsonB, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            return false;
        }


        /*System.out.println(mapA);
        System.out.println(mapB);*/

        for(String key:mapA.keySet()){
            if(!mapB.containsKey(key))continue;
            if(!mapA.get(key).equals(mapB.get(key)))return false;
        }

        return true;
    }

    public <T> boolean compareListCommonFields(List<T> a, List<T> b){
        if(a==null && b==null)return true;
        if(a==null || b==null){
            return false;
        }
        if(a.size()!=b.size()){
            /*System.out.println("drugi false "+a.size()+" "+b.size());
            try {
                System.out.println(objectMapper.writeValueAsString(a));
                System.out.println(objectMapper.writeValueAsString(b));
                //System.out.println(((VisitDto)a.get(0)).getVisitTime().getTime()+" "+((VisitDto)a.get(0)).getVisitTime().getTime());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println("druga lista");
            System.out.println(b);*/
            return false;
        }

        for(int i=0;i<a.size();i++)
            if(!compareCommonFields(a.get(i),b.get(i))) return false;

        return true;
    }

}
