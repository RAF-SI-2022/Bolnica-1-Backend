package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class DevRandomLong {

    private Random random=new Random();


    public static DevRandomLong getInstance(){
        return new DevRandomLong();
    }

    public Long getLong(Long max){
        return Math.abs(random.nextLong())%max;
    }

}
