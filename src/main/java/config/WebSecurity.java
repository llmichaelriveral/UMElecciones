package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	/*LOGEO DEL SUPER ADMINISTRADOR*/
    	http.authorizeRequests()
    	.antMatchers("/admin/**").access("hasRole('ROLE_SUPER')")
    	.antMatchers("/jurado/**").access("hasRole('ROLE_ADMIN')")
    	.antMatchers("/user/**").access("hasRole('ROLE_USER')")
    	.and()
    		.formLogin().loginPage("/home")
    	.and()
    		.exceptionHandling().accessDeniedPage("/404")
    	.and()
    	.csrf().disable();
    }
}