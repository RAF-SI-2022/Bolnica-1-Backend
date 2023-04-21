package raf.bolnica1.patient.cucumber.dataGenerators.classes.domain;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Allergy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class AllergyGenerator {
    private List<Allergy> allergyList = new ArrayList<>();
    private Random random = new Random();

    public void generate() {
        Allergy a1 = new Allergy();
        a1.setName("Mleko");

        Allergy a2 = new Allergy();
        a2.setName("Jaja");

        Allergy a3 = new Allergy();
        a3.setName("Orašasti plodovi");

        allergyList.add(a1);
        allergyList.add(a2);
        allergyList.add(a3);
    }

    public List<Allergy> getAllergyList() {
        return allergyList;
    }

    public Allergy getRandomAllergy(){
        return allergyList.get(Math.abs(random.nextInt())%allergyList.size());
    }
}
