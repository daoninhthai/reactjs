package com.nt.rookie.post.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Configure AuthenticationManager so that it knows from where to load user for
		// matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

				httpSecurity.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll()
						.antMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
						.antMatchers("/api/v1/search").permitAll().antMatchers("/api/v1/getall").permitAll().antMatchers("/api/v1/id/**").permitAll()
						.antMatchers("/api/v1/author/**").hasAnyRole("AUTHOR").antMatchers("/api/v1/create").hasAnyRole("AUTHOR")
						.anyRequest().authenticated().and().exceptionHandling()

						.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin().loginPage("/login").defaultSuccessUrl("/home");

		httpSecurity.cors();
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}