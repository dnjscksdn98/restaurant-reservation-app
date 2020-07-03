package com.alexcode.eatgo;

import com.alexcode.eatgo.security.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.alexcode.eatgo.security.ApplicationUserRole.ADMIN;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

  private final ApplicationUserService applicationUserService;

  @Autowired
  public SecurityJavaConfig(ApplicationUserService applicationUserService) {
    this.applicationUserService = applicationUserService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .cors().disable()
            .csrf().disable()
            .formLogin().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/admin/api/**").hasRole(ADMIN.name())
            .and()
            .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(applicationUserService);

    return provider;
  }

}
