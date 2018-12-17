package com.lxy.shiro;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

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

    // 下面两个方法对 注解权限起作用有很大的关系，请把这两个方法，放在配置的最上面,

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)
     * 和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro的Web过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        //用户访问未对其授权的资源时,所显示的连接
        shiroFilterFactoryBean.setUnauthorizedUrl("/notlogin");

        //设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //游客 开放权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //用户,需要角色权限"user"
        filterChainDefinitionMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("===============Shiro拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    /**
     * 注入SecurityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        log.info("===============注入Shiro的Web过滤器securityManager");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(realm());

        //自定义缓存实现,使用redis
        securityManager.setCacheManager(cacheManagerShiro());

        return securityManager;
    }

    /**
     * 自定义身份认证 realm
     * 需要将自定义的密码匹配器注入到Realm中
     * 必须写这个类,并加上@Bean注解,目的是注入CustomRealm
     * 否则会影响CustomRealm类中其他类的依赖注入
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    @ConditionalOnMissingBean
    public Realm realm(){
        AuthRealm authRealm = new AuthRealm();
        //根据情况使用缓存器
        authRealm.setCacheManager(cacheManagerShiro());
        return authRealm;
    }

    /**
     * shiro缓存管理器
     * 需要添加securityManager中
     * @return
     */
    public RedisCacheManager cacheManagerShiro(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        
    }

}
