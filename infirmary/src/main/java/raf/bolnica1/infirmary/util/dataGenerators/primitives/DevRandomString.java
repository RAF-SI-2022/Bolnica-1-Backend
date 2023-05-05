package raf.bolnica1.infirmary.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@NoArgsConstructor
@Component
public class DevRandomString {

    private Random random=new Random();


    public static DevRandomString getInstance(){
        return new DevRandomString();
    }

    public String getString(int len){
        return UUID.randomUUID().toString().substring(0, len);
    }

}
