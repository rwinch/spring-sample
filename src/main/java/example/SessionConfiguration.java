package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;

@Configuration(proxyBeanMethods = false)
@EnableSpringHttpSession
public class SessionConfiguration {

	@Bean
	RedisSessionRepository redisSessionRepository(RedisTemplate<String, Object> springSessionRedisTemplate) {
		return new RedisSessionRepository(springSessionRedisTemplate);
	}

	@Bean
	RedisTemplate<String, Object> springSessionRedisTempate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
}
