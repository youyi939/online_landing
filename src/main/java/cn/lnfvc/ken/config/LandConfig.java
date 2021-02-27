package cn.lnfvc.ken.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class LandConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置拦截器保护请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //访问无权限接口时返回
        http.exceptionHandling().accessDeniedPage("/unauth");

        http.logout()
                .logoutUrl("/logout")      //指定退出页面
                .logoutSuccessUrl("/index").permitAll();      //退出之后返回哪个页面

        http.formLogin()
//                .loginPage("/login")               //自定义登录页面,当没有权限时会返回它,在controller中另写一个接口会替代security默认当登陆页面
                .loginProcessingUrl("/index")            //自定义登录路径
                .defaultSuccessUrl("/suc").permitAll();       //登录成功后返回的路径


        //配置接口访问权限
        http.authorizeRequests()
                .antMatchers("/","/index","/user/login","/hello").permitAll()    //不需要任何权限都可以访问
                .antMatchers("/vip").hasAuthority("vip1")   //当前登录用户只有具有vip1的权限时才能访问路径
                .antMatchers("/vip2").hasRole("vip2")
                .anyRequest().authenticated();

        //配置跨域
        http.csrf().disable().cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 根据自动匹配密码编码器
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
