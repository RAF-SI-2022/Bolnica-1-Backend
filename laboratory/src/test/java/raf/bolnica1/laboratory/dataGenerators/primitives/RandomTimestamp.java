package raf.bolnica1.laboratory.dataGenerators.primitives;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class RandomTimestamp {

    private List<Timestamp> list;
    /*private Timestamp upperLimit=Timestamp.valueOf("2023-01-01 00:00:00");
    private Timestamp lowerLimit=Timestamp.valueOf("1990-01-01 00:00:00");*/
    private Timestamp upperLimit=Timestamp.valueOf("1990-01-05 00:00:00");
    private Timestamp lowerLimit=Timestamp.valueOf("1990-01-01 00:00:00");
    private Random random=new Random();
    private int numberOfStamps=20;


    public static RandomTimestamp getInstance(){
        return new RandomTimestamp();
    }

    public RandomTimestamp(){

        list=new ArrayList<>();

        long maxStamp=upperLimit.getTime();
        long minStamp=lowerLimit.getTime();
        long len=maxStamp-minStamp;
        for(int i=0;i<numberOfStamps;i++) {
            Timestamp timestamp=new Timestamp(minStamp + Math.abs(random.nextLong()) % len);
            list.add(timestamp);
        }

    }

    public int getSize(){
        return list.size();
    }

    public Timestamp getFromPos(int pos){
        return new Timestamp(list.get(pos).getTime());
    }

    public Timestamp getFromRandom(){
        int pom=random.nextInt();
        return new Timestamp(list.get(Math.abs(pom)%getSize()).getTime());
    }
}
