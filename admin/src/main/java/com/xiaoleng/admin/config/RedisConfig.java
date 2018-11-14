package com.xiaoleng.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@PropertySource(value = "classpath:redis.properties", ignoreResourceNotFound = false)
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private String port = "6379";

    @Value("${spring.redis.database}")
    private String dataBaseIndex;

    @Value("${spring.redis.password}")
    private String password;


    /**
     * 配置jedisConnectionFactory
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setDatabase(Integer.valueOf(dataBaseIndex));
        configuration.setPassword(RedisPassword.none());
        configuration.setPort(Integer.valueOf(port));
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);
        System.out.println(port);
        return jedisConnectionFactory;
    }

    @Bean(name = "myRedisTemplate")
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setConnectionFactory(getJedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "myCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1)); // 设置缓存有效期一小时
        RedisCacheManager redisCacheManager = RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
        redisCacheManager.afterPropertiesSet();
        System.out.println(redisCacheManager.getCacheNames());
        return redisCacheManager;
    }

}
