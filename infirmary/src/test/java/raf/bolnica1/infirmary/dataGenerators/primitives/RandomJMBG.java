package raf.bolnica1.infirmary.dataGenerators.primitives;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class RandomJMBG {

    private List<String> list;
    private Random random=new Random();

    public RandomJMBG(){

        list=new ArrayList<>();

        list.add("0705003312430");
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
        list.add("2504005303457");

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
