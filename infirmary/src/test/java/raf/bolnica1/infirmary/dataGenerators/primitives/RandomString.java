package raf.bolnica1.infirmary.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@NoArgsConstructor
@Component
public class RandomString {

    private Random random=new Random();

    public String getString(int len){
        return UUID.randomUUID().toString().substring(0, len);
    }

}
