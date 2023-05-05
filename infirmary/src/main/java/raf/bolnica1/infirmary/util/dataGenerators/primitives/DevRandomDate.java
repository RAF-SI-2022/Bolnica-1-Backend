package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DevRandomDate {

    private List<Date> list;
    /*private Timestamp upperLimit=Timestamp.valueOf("2023-01-01 00:00:00");
    private Timestamp lowerLimit=Timestamp.valueOf("1990-01-01 00:00:00");*/
    private Timestamp upperLimit=Timestamp.valueOf("1990-01-05 00:00:00");
    private Timestamp lowerLimit=Timestamp.valueOf("1990-01-01 00:00:00");
    private Random random=new Random();
    private int numberOfStamps=20;
    private long millisInDay=24*60*60*1000;


    public static DevRandomDate getInstance(){
        return new DevRandomDate();
    }

    public DevRandomDate(){

        list=new ArrayList<>();

        long maxStamp=upperLimit.getTime();
        long minStamp=lowerLimit.getTime();
        long len=maxStamp-minStamp;
        for(int i=0;i<numberOfStamps;i++) {
            long pom= Math.abs(random.nextLong()) % len;
            pom/=millisInDay;
            pom*=millisInDay;
            pom+=minStamp;
            list.add(new Date(pom));
        }

    }

    public int getSize(){
        return list.size();
    }

    public Date getFromPos(int pos){
        return new Date(list.get(pos).getTime());
    }

    public Date getFromRandom(){
        return new Date(list.get(Math.abs(random.nextInt())%getSize()).getTime());
    }

}
