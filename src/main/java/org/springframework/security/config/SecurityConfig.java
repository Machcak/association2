package org.springframework.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Machcak
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("spectrum").password("2015//Stowarzyszenie//").roles("ADMIN")
                .and()
        		.withUser("Prezes").password("!Prezes2015Stowarzyszenia*").roles("PREZES");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
		.formLogin().permitAll()
	.and()
		.authorizeRequests()
		.antMatchers("/faces/finances/**", "/faces/members/**", "/faces/association/**").hasRole("PREZES")
		.antMatchers("/faces/association/**", "/faces/definitions/**", "/faces/finances/**", "/faces/members/**").hasRole("ADMIN")
		.anyRequest().authenticated()
	.and()
		.exceptionHandling().accessDeniedPage("/login");
    }
   

}
