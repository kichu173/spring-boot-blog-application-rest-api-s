package com.sprinboot.blog.config;

import com.sprinboot.blog.security.CustomUserDetailsService;
import com.sprinboot.blog.security.JwtAuthenticationEntryPoint;
import com.sprinboot.blog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter JwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll() //provides bigger json for swagger documentation of spring boot application(http://localhost:8080/v2/api-docs)
                .antMatchers("/swagger-ui/**").permitAll()// http://localhost:8080/swagger-ui/
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .httpBasic();// commenting it out as using JWT Authentication
        http.addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override// Database role based authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PasswordEncoder());
    }

    //    @Override
//    @Bean
//    // Inmemory role based authentication In controllers @PreAuthorize for access permission. 1.postman->authorization->basic auth->Headers basic 64encoder
//    protected UserDetailsService userDetailsService() {
//        UserDetails kiran = User.builder().username("kiran").password(PasswordEncoder()
//                .encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(PasswordEncoder()
//                .encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(kiran, admin);
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
