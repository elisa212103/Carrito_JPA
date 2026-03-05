package es.uni.pat.carrito.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("!test") //los tests no fallaran por configuracion seguridad
public class ConfiguracionSeguridad {

    @Bean
    public SecurityFilterChain configuracion(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(authorize -> authorize

                        // endpoints públicos
                        .requestMatchers("/auth/login",
                                "/auth/register",
                                "/auth/me").permitAll()

                        // solo ADMIN puede gestionar artículos
                        .requestMatchers("/articulos/**").hasRole("ADMIN")

                        // usuarios autenticados pueden usar carrito
                        .requestMatchers("/carrito/**").hasRole("USER")

                        .anyRequest().authenticated()
                )

                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public UserDetailsService usuarios() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("usuario")
                .password("clave")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("clave")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}

//luego en el controller ponemos @PreAuthorize("hasRole('ADMIN')")
