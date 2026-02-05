package poly.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import poly.edu.models.services.CustomerDetailsService;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); // ⚠️ plain text
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login", "/register", "/products/**", "/about", "/contact", "/home", "/",
								"/cart/**", "/images/**", "/css/**", "/js/**")
						.permitAll().requestMatchers("/checkout/**", "/payment/**").authenticated()
						.requestMatchers("/dashboard/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login").loginProcessingUrl("/login")
						.defaultSuccessUrl("/home", true).failureUrl("/login?error").permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/login?logout") // send param for alert
				);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, CustomerDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) throws Exception {

		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder).and().build();
	}
}
