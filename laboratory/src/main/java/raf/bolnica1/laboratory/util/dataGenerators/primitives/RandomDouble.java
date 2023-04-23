package raf.bolnica1.laboratory.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.RandomSeed;

import java.util.Random;

@Component
@NoArgsConstructor
public class RandomDouble {

    private Random random=new Random(RandomSeed.seed);


    public static RandomDouble getInstance(){
        return new RandomDouble();
    }


    public Double getDouble(Double max){
        return Math.abs(random.nextDouble())%max;
    }

}