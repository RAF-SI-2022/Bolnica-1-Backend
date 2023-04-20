package raf.bolnica1.infirmary.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class RandomLong {

    private Random random=new Random();


    public static RandomLong getInstance(){
        return new RandomLong();
    }

    public Long getLong(Long max){
        return Math.abs(random.nextLong())%max;
    }

}
