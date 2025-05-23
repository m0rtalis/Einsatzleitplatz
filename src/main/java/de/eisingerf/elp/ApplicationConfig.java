package de.eisingerf.elp;

import de.eisingerf.elp.common.api.rest.list.OffsetPaginationHandlerMethodArgumentResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity // Enable SecurityFilterChain
@EnableMethodSecurity // Enable Method Annotation
@SecurityScheme(name = "Basic", scheme = "basic", type = SecuritySchemeType.HTTP)
@OpenAPIDefinition(
        info = @Info(title = "Einsatzleitplatz", version = "0.0.1"),
        security = {@SecurityRequirement(name = "Basic")})
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .enableSessionUrlRewriting(false))
                .securityContext(securityContext ->
                        securityContext.securityContextRepository(new HttpSessionSecurityContextRepository()))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/users/login", "/swagger-ui*/**", "/v3/**", "/error", "/operations/names")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // Disabled for now because inconvenient. This opens CSRF attack vectors. Enable later
                // https://medium.com/@thecodinganalyst/configure-spring-security-csrf-for-testing-on-swagger-e9e6461ee0c1
                // https://stackoverflow.com/questions/74447118/csrf-protection-not-working-with-spring-security-6
                // https://www.linkedin.com/pulse/solving-invalid-csrf-token-found-error-spring-security-oyeleye/
                .csrf(CsrfConfigurer::disable);

        return http.build();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {
        var jdbcBuilder = builder.jdbcAuthentication().dataSource(dataSource);
        if (dataSource.getConnection().getMetaData().getDatabaseProductName().equals("H2")) {
            jdbcBuilder.withDefaultSchema();
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource, PasswordEncoder passwordEncoder) {
        // This causes "No authentication manager set. Reauthentication of users when changing passwords will not be
        // performed."
        // Maybe it will be fixed when we implement actual user management
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (!users.userExists(user.getUsername())) {
            users.createUser(user);
        }
        if (!users.userExists(admin.getUsername())) {
            users.createUser(admin);
        }
        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new OffsetPaginationHandlerMethodArgumentResolver());
    }
}
