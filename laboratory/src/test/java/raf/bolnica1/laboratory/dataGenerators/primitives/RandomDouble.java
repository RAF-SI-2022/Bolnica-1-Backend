package raf.bolnica1.laboratory.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class RandomDouble {

    private Random random=new Random();


    public static RandomDouble getInstance(){
        return new RandomDouble();
    }


    public Double getDouble(Double max){
        return Math.abs(random.nextDouble())%max;
    }

}