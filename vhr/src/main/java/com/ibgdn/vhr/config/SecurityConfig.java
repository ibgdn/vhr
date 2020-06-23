package com.ibgdn.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibgdn.vhr.model.Hr;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.io.PrintWriter;

/**
 * Spring Security 配置类
 */
@Configuration
// 解决 org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'springSecurityFilterChain' defined in class path resource [org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.servlet.Filter]: Factory method 'springSecurityFilterChain' threw exception; nested exception is java.lang.NullPointerException
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HrService hrService;

    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    @Autowired
    CustomizeAccessDecisionManager customizeAccessDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 解决接口被拦截的问题（也可以添加静态资源）
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 处理服务
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 所有请求认证通过之后才可以访问
//                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customizeAccessDecisionManager);
                        object.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                // 表单登录
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录处理的 url
                .loginProcessingUrl("/doLogin")
                // 用户没有登录，会直接跳转登录页面“/login”
                .loginPage("/login")
                // 登录成功的回调
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    Hr hr = (Hr) authentication.getPrincipal();
                    hr.setPassword(null);
                    ResponseBean responseBean = new ResponseBean().ok("登录成功！", hr);
                    String value = new ObjectMapper().writeValueAsString(responseBean);
                    writer.write(value);
                    writer.flush();
                    writer.close();
                })
                // 登录失败的回调
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    ResponseBean responseBean = ResponseBean.error(exception.getMessage());
                    if (exception instanceof LockedException) {
                        responseBean.setMessage("账户被锁定，请联系管理员！");
                    } else if (exception instanceof DisabledException) {
                        responseBean.setMessage("账户被禁用，请联系管理员！");
                    } else if (exception instanceof CredentialsExpiredException) {
                        responseBean.setMessage("密码已过期，请联系管理员！");
                    } else if (exception instanceof AccountExpiredException) {
                        responseBean.setMessage("账户已过期，请联系管理员！");
                    } else if (exception instanceof BadCredentialsException) {
                        responseBean.setMessage("用户名密码错误，请重新输入！");
                    }
                    String value = new ObjectMapper().writeValueAsString(responseBean);
                    writer.write(value);
                    writer.flush();
                    writer.close();
                })
                // 所有相关接口都可以直接返回
                .permitAll()
                .and()
                // 退出登录
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    String value = new ObjectMapper().writeValueAsString(ResponseBean.ok("注销成功！"));
                    writer.write(value);
                    writer.flush();
                    writer.close();
                })
                .permitAll()
                .and()
                .csrf().disable();
    }
}
