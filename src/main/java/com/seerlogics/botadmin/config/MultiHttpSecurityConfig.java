package com.seerlogics.botadmin.config;

import com.lingoace.common.CustomHttpServletRequestWrapperFilter;
import com.lingoace.spring.authentication.JWTAuthenticationFilter;
import com.lingoace.spring.authentication.RestAuthenticationEntryPoint;
import com.seerlogics.botadmin.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Created by bkane on 11/4/18.
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(1)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Resource(name = "accountService")
        private UserDetailsService userDetailsService;

        @Autowired
        private AppProperties appProperties;

        @Bean
        public BCryptPasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder(11);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth)
                throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        JWTAuthenticationFilter authenticationTokenFilterBean() {
            JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
            jwtAuthenticationFilter.setUserDetailsService(this.userDetailsService);
            jwtAuthenticationFilter.setSecretKey(appProperties.getJwtSecretKey());
            return jwtAuthenticationFilter;
        }

        CustomHttpServletRequestWrapperFilter customHttpServletRequestWrapperFilter() {
            return new CustomHttpServletRequestWrapperFilter();
        }

        RestAuthenticationEntryPoint unauthorizedHandler() {
            return new RestAuthenticationEntryPoint();
        }

        /**
         * ******* The below is VERY important. The below means that we are setting "security='none'" for the below
         * URL patterns. The Spring Security interceptor will basically just ignore the below URLs.
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            // "/**" will allow ALL requests.
            web.ignoring().antMatchers("/api/**/generate-token", "/error", "/metadata/validation/**",
                    "/api/v1/account/signup", "/metadata/messages",
                    "/api/v1/account/init/**", "/api/cms/all-content");
        }

        /**
         * https://www.devglan.com/spring-security/jwt-role-based-authorization
         * <p>
         * For ALL other URLs which are not ignored, the below will authenticate access!
         *
         * @param http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers().frameOptions().sameOrigin();
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                            /**
                             * Here, we have configured that no authentication is required to access the url /token, /signup
                             * and rest of the urls are secured. Here prePostEnabled = true enables support for method
                             * level security and enables use of @PreAuthorize
                             */
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler()).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(customHttpServletRequestWrapperFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterAt(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        }
    }
}