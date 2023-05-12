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



    public static ClassJsonComparator getInstance(){
        return new ClassJsonComparator(new ObjectMapper());
    }

    private void convertNestedObjectsToMaps(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                convertNestedObjectsToMaps(nestedMap);
                entry.setValue(nestedMap);
            }
        }
    }
    private boolean areMapsEqual(Map<String, Object> map1, Map<String, Object> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }

        for (Map.Entry<String, Object> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object value1 = entry.getValue();
            Object value2 = map2.get(key);

            if (value1 == null && value2 == null) {
                continue;
            }
            if (value1 == null || value2 == null) {
                return false;
            }

            if (value1 instanceof Map && value2 instanceof Map) {
                if (!areMapsEqual((Map<String, Object>) value1, (Map<String, Object>) value2)) {
                    return false;
                }
            } else if (value1 instanceof List && value2 instanceof List) {
                if (!areListsEqual((List<Object>) value1, (List<Object>) value2)) {
                    return false;
                }
            } else {
                ///System.out.println(value1.toString());
                if (!value1.equals(value2)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean areListsEqual(List<Object> list1, List<Object> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            Object value1 = list1.get(i);
            Object value2 = list2.get(i);

            if (value1 == null && value2 == null) {
                continue;
            }
            if (value1 == null || value2 == null) {
                return false;
            }

            if (value1 instanceof Map && value2 instanceof Map) {
                if (!areMapsEqual((Map<String, Object>) value1, (Map<String, Object>) value2)) {
                    return false;
                }
            } else if (value1 instanceof List && value2 instanceof List) {
                if (!areListsEqual((List<Object>) value1, (List<Object>) value2)) {
                    return false;
                }
            } else {
                ///System.out.println(value1.toString());
                if (!value1.equals(value2)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean compareAllFields(Object a,Object b){


        String jsonA;
        String jsonB;
        try {
            jsonA=objectMapper.writeValueAsString(a);
            jsonB=objectMapper.writeValueAsString(b);
        } catch (JsonProcessingException e) {
            System.out.println("nije mogao pretvoriti u JSON");
            return false;
        }

        ///System.out.println(jsonA);
        ///System.out.println(jsonB);

        Map<String,Object> mapA,mapB;
        try {
            mapA = objectMapper.readValue(jsonA, Map.class);
            mapB = objectMapper.readValue(jsonB, Map.class);

            convertNestedObjectsToMaps(mapA);
            convertNestedObjectsToMaps(mapB);

        } catch (JsonProcessingException e) {
            System.out.println("nije mogao pretvoriti u mapu iz JSON-a");
            return false;
        }


        System.out.println(mapA);
        System.out.println(mapB);

        return areMapsEqual(mapA,mapB);
    }

    public boolean compareCommonFields(Object a,Object b){


        String jsonA;
        String jsonB;
        try {
            jsonA=objectMapper.writeValueAsString(a);
            jsonB=objectMapper.writeValueAsString(b);
        } catch (JsonProcessingException e) {
            System.out.println("nije mogao pretvoriti u JSON");
            return false;
        }

        Map<String,Object> mapA,mapB;
        try {
            mapA = objectMapper.readValue(jsonA, new TypeReference<Map<String, Object>>() {});
            mapB = objectMapper.readValue(jsonB, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            System.out.println("nije mogao pretvoriti u mapu iz JSON-a");
            return false;
        }


        /*System.out.println(mapA);
        System.out.println(mapB);*/

        for(String key:mapA.keySet()){
            if(!mapB.containsKey(key))continue;
            if(mapB.get(key)==null && mapA.get(key)==null)continue;
            if(mapB.get(key)==null || mapA.get(key)==null){
                System.out.println("Null vrednosti za kljuc: " + key);
                return false;
            }
            if(!mapA.get(key).equals(mapB.get(key))){
                System.out.println("Razlicite vrednosti za kljuc: " + key + ", A: " + mapA.get(key) +", B: " + mapB.get(key));
                return false;
            }
        }

        return true;
    }

    public <T,U> boolean compareListCommonFields(List<T> a, List<U> b){
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
