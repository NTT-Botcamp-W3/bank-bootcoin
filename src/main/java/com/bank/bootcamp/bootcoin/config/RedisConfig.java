package com.bank.bootcamp.bootcoin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
  
  @Autowired
  private Environment env;

  @Bean
  public JedisConnectionFactory connectionFactory() {
    var configuration = new RedisStandaloneConfiguration();
    configuration.setHostName(env.getProperty("spring.redis.host"));
    configuration.setUsername(env.getProperty("spring.redis.user"));
    configuration.setPassword(env.getProperty("spring.redis.password"));
    configuration.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
    return new JedisConnectionFactory(configuration);
  }
  
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    var template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(connectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new JdkSerializationRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
  }
}
