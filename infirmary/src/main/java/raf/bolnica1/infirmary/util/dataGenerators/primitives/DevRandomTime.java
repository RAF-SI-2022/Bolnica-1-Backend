package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Random;

@Component
@NoArgsConstructor
public class DevRandomTime {

    private final Random random = new Random();
    private final int millisInDay = 24*60*60*1000;
    private final int secondsInDay = 24*60*60;


    public static DevRandomTime getInstance(){
        return new DevRandomTime();
    }

    public Time getTimeFromRandom(){
        Time time = new Time((long)random. nextInt(secondsInDay)*1000L);
        return time;
    }

}
