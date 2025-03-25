package io.muzoo.ssc.project.backend.config;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.util.AjaxUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Adapted: Define the AuthenticationManager bean using AuthenticationConfiguration
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF for API (or handle it properly in production)
				.csrf(AbstractHttpConfigurer::disable)
				// Configure CORS with custom settings
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					// Allow only your domain – adjust as needed
					config.setAllowedOrigins(List.of("https://sscrewear.store", "http://localhost:3000"));
					config.setAllowedMethods(List.of("*"));
					config.setAllowedHeaders(List.of("*"));
					config.setAllowCredentials(true);
					return config;
				}))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						// Public endpoints
						.requestMatchers("/", "/api/login", "/api/logout", "/api/whoami", "/api/register", "/api/products").permitAll()
						// Admin endpoints
						.requestMatchers("/api/admin/**").hasRole("admin")
						// Require authentication for all other requests
						.anyRequest().authenticated()
				)
				// Return JSON error response on unauthorized access
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(new JsonHttp403ForbiddenEntryPoint())
				);
		return http.build();
	}

	static class JsonHttp403ForbiddenEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request,
							 HttpServletResponse response,
							 AuthenticationException authException) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			response.getWriter().write("{\"success\": false, \"message\": \"Forbidden\"}");
		}
	}

}