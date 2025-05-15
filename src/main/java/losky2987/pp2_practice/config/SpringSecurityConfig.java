package losky2987.pp2_practice.config;

import jakarta.validation.groups.Default;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error",
                                "/css/**",
                                "/js/**",
                                "/gate/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(Customizer.withDefaults());
        return chainBuilder.build();
    }
}
