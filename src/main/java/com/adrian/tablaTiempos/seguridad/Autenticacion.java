package com.adrian.tablaTiempos.seguridad;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adrian.tablaTiempos.JPA.PilotoJPA;
import com.adrian.tablaTiempos.modelo.Piloto;

@Service
public class Autenticacion implements UserDetailsService{
	
	@Autowired
	private PilotoJPA pilotoJpa;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <Piloto> user = pilotoJpa.findById(username);
		
		if(user.isPresent()) {
			return user.get(); 
		}
		else {
			throw new  UsernameNotFoundException("" + username);
			
			
		}
		
	}

	
}
