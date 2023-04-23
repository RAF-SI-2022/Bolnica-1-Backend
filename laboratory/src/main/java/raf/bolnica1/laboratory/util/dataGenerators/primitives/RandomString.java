package raf.bolnica1.laboratory.util.dataGenerators.primitives;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.RandomSeed;

import java.util.Random;
import java.util.UUID;

@NoArgsConstructor
@Component
public class RandomString {

    private Random random=new Random(RandomSeed.seed);
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public static RandomString getInstance(){
        return new RandomString();
    }

    public String getString(int length){
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(randomIndex);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString;
    }

}
