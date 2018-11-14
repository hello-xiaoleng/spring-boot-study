package com.xiaoleng.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class redisTest {

    @Resource(name = "myRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "myCacheManager")
    private CacheManager cacheManager;

    @Test
    public void testRedisTemplate() {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set("name", "chenjia");
        System.out.println(stringStringValueOperations.get("name"));
    }

    @Test
    public void testCacheManagerPut() {
        org.springframework.cache.Cache cache = cacheManager.getCache("name");
        cache.put("chenjia", "chenjia");
    }

    @Test
    public void testCacheManagerGet() {
        org.springframework.cache.Cache cache = cacheManager.getCache("name");
    }

}
