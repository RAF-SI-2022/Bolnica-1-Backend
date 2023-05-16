package raf.bolnica1.employees.appConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${spring.data.redis.host}")
    private String abc;

    @Bean
    public void test(){
        System.out.println("app prop: " + abc);
    }

}
