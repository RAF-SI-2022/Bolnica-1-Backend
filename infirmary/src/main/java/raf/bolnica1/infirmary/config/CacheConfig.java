package raf.bolnica1.infirmary.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

// Vezbe 8: Potrebno je dodati @EnableCaching anotaciju u jednu od konfiguracionih klasa kako bi Redis kesiranje
// funkcionisalo.
@Configuration
@EnableCaching
//@PropertySource("application.properties")
public class CacheConfig {

    @Bean
    public RedisTemplate<String, Object> deliveryRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
