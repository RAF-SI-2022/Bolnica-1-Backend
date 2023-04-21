package raf.bolnica1.patient.cucumber.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Random;

@Component
@NoArgsConstructor
public class RandomTime {

    private final Random random = new Random();
    private final int millisInDay = 24*60*60*1000;
    private final int secondsInDay = 24*60*60;

    public Time getTimeFromRandom(){
        Time time = new Time((long)random. nextInt(secondsInDay)*1000L);
        return time;
    }

}
