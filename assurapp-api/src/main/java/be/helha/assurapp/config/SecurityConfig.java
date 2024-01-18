package be.helha.assurapp.config;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.services.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    @Profile("!test")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                    authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/register/**").permitAll()
                        // insurance package
                        .requestMatchers(HttpMethod.GET, "/api/v1/insurances/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/insurances/**").hasRole(RoleList.INSURER.toString())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/insurances/**").hasRole(RoleList.INSURER.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/insurances/**").hasRole(RoleList.INSURER.toString())

                        .requestMatchers(HttpMethod.GET, "api/v1/subscriptions/**").hasAnyRole(RoleList.INSURER.toString(), RoleList.CLIENT.toString() )
                        .requestMatchers(HttpMethod.POST, "api/v1/subscriptions/**").hasAnyRole(RoleList.INSURER.toString(), RoleList.CLIENT.toString() )
                        .requestMatchers(HttpMethod.PUT, "api/v1/subscriptions/**").hasAnyRole(RoleList.INSURER.toString(), RoleList.CLIENT.toString() )
                        .requestMatchers(HttpMethod.DELETE, "api/v1/subscriptions/**").hasRole(RoleList.INSURER.toString())

                        .requestMatchers(HttpMethod.GET, "api/v1/payments/**").hasAnyRole(RoleList.INSURER.toString(), RoleList.CLIENT.toString() )
                        .requestMatchers(HttpMethod.POST, "api/v1/payments/**").hasAnyRole(RoleList.INSURER.toString(), RoleList.CLIENT.toString() )
                        .requestMatchers(HttpMethod.PUT, "api/v1/payments/**").hasAnyRole(RoleList.INSURER.toString(), RoleList.CLIENT.toString() )
                        .requestMatchers(HttpMethod.DELETE, "api/v1/payments/**").hasRole(RoleList.INSURER.toString())

                        .anyRequest().authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}
