package com.champstart.recipeapp.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{
    private CustomUsersDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUsersDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    class LoginPageFilter extends GenericFilterBean {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
            if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && ((HttpServletRequest)request).getRequestURI().equals("/login")) {
                ((HttpServletResponse)response).sendRedirect("/home");
            }
            filterChain.doFilter(request, response);
        }
    }


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);

        http.httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and().formLogin().permitAll()
                .and().authorizeRequests().antMatchers("/login").not().authenticated()
                .antMatchers("/login", "/register", "/reset-password").anonymous()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/home", "/recipes/**", "/comments/**", "/search/**", "/view-profile/**").authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()

                );
        return http.build();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
