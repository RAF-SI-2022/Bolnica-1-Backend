package raf.bolnica1.laboratory.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.RandomSeed;

import java.util.Random;

@Component
@NoArgsConstructor
public class RandomLong {

    private Random random=new Random(RandomSeed.seed);


    public static RandomLong getInstance(){
        return new RandomLong();
    }

    public Long getLong(Long max){
        return Math.abs(random.nextLong())%max;
    }

}
