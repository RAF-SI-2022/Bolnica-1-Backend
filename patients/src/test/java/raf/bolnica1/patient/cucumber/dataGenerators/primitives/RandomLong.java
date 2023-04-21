package raf.bolnica1.patient.cucumber.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class RandomLong {

    private Random random=new Random();

    public Long getLong(Long max){
        return Math.abs(random.nextLong())%max;
    }

}
