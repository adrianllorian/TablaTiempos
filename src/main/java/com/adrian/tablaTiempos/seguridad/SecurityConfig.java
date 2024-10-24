package com.adrian.tablaTiempos.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}





	@Autowired
    private Autenticacion autenticacion;

 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(autenticacion);
    	auth.authenticationProvider(provider);
    }   



    
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable()
    	
		.authorizeRequests()
		.antMatchers("/addTiempo").authenticated()
		.antMatchers("/listarTiempos").authenticated()
		.antMatchers("/listarPorIdTiempoYPiloto").authenticated()
		.antMatchers("/listarTodosTiemposPorCircuito").authenticated()
		.antMatchers("/listarTodosTiemposPorPiloto").authenticated()
		.antMatchers("/listarTodosTiemposPorCoche").authenticated()
		.antMatchers("/listarTodosTiemposPorCircuitoYPiloto").authenticated()
		.antMatchers("/listarTodosTiemposPorCircuitoYCoche").authenticated()
		.antMatchers("/listarTodosTiemposPorPilotoYCoche").authenticated()
		.antMatchers("/listarTodosTiemposPorCircuitoYPilotoYCoche").authenticated()
		.antMatchers("/listarTiempos").authenticated()
		.antMatchers("/addPiloto").authenticated()
		.antMatchers("/cambiarPass").authenticated()
		.antMatchers("/login").permitAll()
		.antMatchers("/listarCircuitos").permitAll()
		.anyRequest().permitAll()
		.and().addFilter(new JWTAuthenticationFilter(authenticationManager()));
		
    }
	
}
