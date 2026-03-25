package com.nwm.api.config;

import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

@Configuration
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
public class RedisConfig {

    @Bean(destroyMethod = "shutdown")
    public RedisClusterClient redisClusterClient() {
        final String REDIS_HOST = Lib.getReourcePropValue(Constants.appConfigFileName, "redis.host");
        final int REDIS_PORT = 6379;
        final String REDIS_PASSWORD = Lib.getReourcePropValue(Constants.appConfigFileName, "redis.password");
        RedisURI redisURI = RedisURI.builder()
                .withHost(REDIS_HOST)
                .withPort(REDIS_PORT)
                .withSsl(true)
                .withTimeout(java.time.Duration.ofSeconds(5))
                .withPassword(REDIS_PASSWORD.toCharArray())
                .build();

        return RedisClusterClient.create(redisURI);
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisClusterConnection<String, String> redisConnection(
            RedisClusterClient redisClusterClient) {

        return redisClusterClient.connect();
    }

    @Bean
    public RedisAdvancedClusterCommands<String, String> redisCommands(
            StatefulRedisClusterConnection<String, String> connection) {

        return connection.sync();
    }
}
