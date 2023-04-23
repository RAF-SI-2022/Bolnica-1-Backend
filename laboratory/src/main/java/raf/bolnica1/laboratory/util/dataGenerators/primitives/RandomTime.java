package raf.bolnica1.laboratory.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.RandomSeed;

import java.sql.Time;
import java.util.Random;

@Component
@NoArgsConstructor
public class RandomTime {

    private final Random random = new Random(RandomSeed.seed);
    private final int millisInDay = 24*60*60*1000;
    private final int secondsInDay = 24*60*60;


    public static RandomTime getInstance(){
        return new RandomTime();
    }

    public Time getTimeFromRandom(){
        Time time = new Time((long)random. nextInt(secondsInDay)*1000L);
        return time;
    }

}
