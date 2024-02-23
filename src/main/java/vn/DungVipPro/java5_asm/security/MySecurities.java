package vn.DungVipPro.java5_asm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class MySecurities {

    @Bean
    @Autowired
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(config ->
                config.requestMatchers("/shopping-cart").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/dashboard").hasRole("ADMIN")
                        .requestMatchers("/dashboard-products").hasRole("ADMIN")
                        .requestMatchers("/**").permitAll())
                .formLogin(login -> {
                    login.loginPage("/showLogin").loginProcessingUrl("/authenticateTheUser").defaultSuccessUrl("/index").permitAll();
                })
                .logout(
                    logout -> logout.permitAll()
                );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
