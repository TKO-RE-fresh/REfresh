package tko.refresh;// RedisProperties.java

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix="spring.redis")
@Component
public class RedisProperties {
    private String host;
    private int port;

    private String password;
}