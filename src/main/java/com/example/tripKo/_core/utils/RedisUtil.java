package com.example.tripKo._core.utils;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static com.example.tripKo._core.security.data.JwtType.REFRESH_TOKEN;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import com.example.tripKo._core.security.data.RefreshToken;

@Repository
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    public RedisUtil(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(final RefreshToken refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getId(), refreshToken.getRefreshToken());
        redisTemplate.expire(refreshToken.getId(), REFRESH_TOKEN.getExpiredMillis(), TimeUnit.SECONDS);
    }

    public Optional<String> getData(String key) {
        ValueOperations<String , String> valueOperations = redisTemplate.opsForValue();
        String refreshToken = valueOperations.get(key);
        if (Objects.isNull(refreshToken)) {
            return Optional.empty();
        }

        return Optional.of(refreshToken);
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofMillis(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
