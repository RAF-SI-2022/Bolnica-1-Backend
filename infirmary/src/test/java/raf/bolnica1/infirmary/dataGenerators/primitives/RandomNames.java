package raf.bolnica1.infirmary.dataGenerators.primitives;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomNames {

    private List<String> list;
    private Random random=new Random();

    public RandomNames(){

        list=new ArrayList<>();

        list.add("James");
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
        list.add("Katica");

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
