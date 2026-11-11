package com.adrian.tablaTiempos.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adrian.tablaTiempos.Servicio.JwtService;

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

	@Autowired
	private JwtService jwtService;

 
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
		.antMatchers("/addPiloto").hasAuthority("ROLE_ADMIN")
		.antMatchers("/addCircuito", "/editarCircuito", "/borrarCircuito").hasAuthority("ROLE_ADMIN")
		.antMatchers("/listarPilotos", "/editarPiloto", "/borrarPiloto").hasAuthority("ROLE_ADMIN")
		.antMatchers("/borrarTiempo").hasAuthority("ROLE_ADMIN")
		.antMatchers("/cambiarPass").authenticated()
		.antMatchers("/login", "/setup", "/setup/**").permitAll()
		.antMatchers(HttpMethod.GET, "/settings", "/settings/logo", "/settings/fondo").permitAll()
		.antMatchers(HttpMethod.POST, "/settings").hasAuthority("ROLE_ADMIN")
		.antMatchers("/listarCircuitos").permitAll()
		.anyRequest().permitAll()
		.and().addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService));
		
    }
	
}
