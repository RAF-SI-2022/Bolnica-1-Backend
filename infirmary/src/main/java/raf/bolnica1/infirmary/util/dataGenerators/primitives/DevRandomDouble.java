package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class DevRandomDouble {

    private Random random=new Random();


    public static DevRandomDouble getInstance(){
        return new DevRandomDouble();
    }


    public Double getDouble(Double max){
        return Math.abs(random.nextDouble())%max;
    }

}