package com.lxy.shiro;


import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration //@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
@Slf4j
public class ShiroConfig {

    private static String host;

    private static String port;

    private static String redisPassword;

    @Value("${spring.redis.port}")
    public static void setPort(String port) {
        ShiroConfig.port = port;
    }

    @Value("${spring.redis.host}")
    public static void setHost(String host) {
        ShiroConfig.host = host;
    }

    @Value("${spring.redis.password}")
    public static void setRedisPassword(String redisPassword) {
        ShiroConfig.redisPassword = redisPassword;
    }
}
