package com.example.springjwt.config;

import com.example.springjwt.jwt.JwtAuthenticationEntryPoint;
import com.example.springjwt.jwt.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration


@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

@Autowired
    private UserDetailsService userDetailsService;
@Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;


    public static final String[] PUBLIC_PATHS = {"/api/auth/**",
            "/v3/api-docs.yaml",
            "/v3/**",
            "/swagger-ui/**",
            "/swagger-ui.html"};

@Autowired
    private JwtAuthenticationFilter authenticationFilter ;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {

//                    authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
                    // authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN", "USER");
                    // authorize.requestMatchers(HttpMethod.GET, "/api1/**").permitAll();



                            authorize
                                    .requestMatchers(PUBLIC_PATHS).permitAll()
                            .requestMatchers("/swagger/**").permitAll().
                             requestMatchers("/api/admin/**").hasRole("ADMIN").

//                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    requestMatchers("/api/auth/login").permitAll()
                    .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
//    http.csrf(AbstractHttpConfigurer::disable).
//            authorizeHttpRequests((authorize) ->
//
//                    authorize.
//                            requestMatchers("/api/auth/**").permitAll()
//                            .requestMatchers("/swagger-ui/**").permitAll()
//                            .requestMatchers("/swagger/**").permitAll()
//                            .requestMatchers("/v3/**").permitAll()
//                            .anyRequest().authenticated()
//            );
//    return http.build();
//}
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                    .permitAll())
//            .csrf(AbstractHttpConfigurer::disable);
//    return http.build();
//}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/api/users/**").permitAll()
//                .requestMatchers("/swagger-ui/**").permitAll()
//                            .requestMatchers("/swagger/**").permitAll()
//                            .requestMatchers("/v3/**").permitAll()
//                .anyRequest().authenticated()
////                .and().httpBasic()
////                .and().formLogin()
////                .and().logout()
//                .and().csrf().disable();
//
//        return http.build();
//    }

//        @Bean
//        SecurityFilterChain securityFilterChain (HttpSecurity http ) throws Exception {
//          http.csrf( csrf ->csrf.disable())
//                  .authorizeHttpRequests((authorize) ->{
//                      authorize.requestMatchers(HttpMethod.GET,"/api/auth/**").permitAll();
//                      authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
//                      authorize.anyRequest().authenticated();
//                  })
//                  .csrf(AbstractHttpConfigurer::disable)
//                  .httpBasic(Customizer.withDefaults()) ;
//
//          http.exceptionHandling( exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
//
//          http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//            return http.build();
//        }

//        tạo bean Authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
    }
}
