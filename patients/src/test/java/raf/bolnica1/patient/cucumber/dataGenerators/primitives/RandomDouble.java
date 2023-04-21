package raf.bolnica1.patient.cucumber.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class RandomDouble {

    private Random random=new Random();

    public Double getDouble(Double max){
        return Math.abs(random.nextDouble())%max;
    }

}