package raf.bolnica1.employees.dataGenerators.primitives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomNames {

    private List<String> listDepNames;
    private List<String> listHospitalNames;
    private List<String> listEmployeeNames;
    private RandomLong randomLong;


    public String randomNameDep(){
        return listDepNames.get(randomLong.getLong(Long.valueOf(listDepNames.size())).intValue());
    }

    public String randomNameHospital(){
        return listHospitalNames.get(randomLong.getLong(Long.valueOf(listHospitalNames.size())).intValue());
    }

    public String randomNameEmployee(){
        return listEmployeeNames.get(randomLong.getLong(Long.valueOf(listEmployeeNames.size())).intValue());
    }

    public static RandomNames getInstance(){
        return new RandomNames(RandomLong.getInstance());
    }

    @Autowired
    public RandomNames(RandomLong randomLong){

        this.randomLong = randomLong;

        listDepNames=new ArrayList<>();
        listHospitalNames = new ArrayList<>();
        listEmployeeNames = new ArrayList<>();

        for(int i = 0; i<10; i++)
            listDepNames.add("Odeljenje" + i);

        for(int i = 0; i<5; i++)
            listHospitalNames.add("Ustanova" + i);

        for(int i = 0; i<5; i++)
            listEmployeeNames.add("Zaposljeni" + i);

    }

}
