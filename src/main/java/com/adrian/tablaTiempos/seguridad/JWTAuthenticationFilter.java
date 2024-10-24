package com.adrian.tablaTiempos.seguridad;

import static com.adrian.tablaTiempos.utils.Constans.CLAVE;
import static com.adrian.tablaTiempos.utils.Constans.ENCABEZADO;
import static com.adrian.tablaTiempos.utils.Constans.PREFIJO_TOKEN;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTAuthenticationFilter extends BasicAuthenticationFilter{

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(ENCABEZADO);
		if(header == null || header.startsWith(PREFIJO_TOKEN)) {
			chain.doFilter(req, res);
			return;
		}
		//Obtenemos los datos del usuario a partir del token
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// La informacion del ususario se guarda en el contexto deseguridad
		// para que pueda ser usada por Spring Security durante el proceso de autorizacion
				
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication( HttpServletRequest request) throws ServletException {
		//El token viene en la cabezera de la peticion
		String token = request.getHeader(ENCABEZADO);
		if(token != null) {
			//Se procesa el token y se recupera el usuario y los roles
			 try {
			Claims claims = Jwts.parser()
					.setSigningKey(CLAVE)
					.parseClaimsJws(token.replace(PREFIJO_TOKEN, ""))
					.getBody();
			 
			String user= claims.getSubject();
			List<String> authorities = (List<String>) claims.get("authorities");
			if(user != null) {
				//Creamos el objeto con la informacion del usuario
				return new UsernamePasswordAuthenticationToken(user, null, authorities.stream()
												.map(SimpleGrantedAuthority::new)
												.collect(Collectors.toList()));
			}
			return null;
			 } catch ( Exception e) {
			        throw new ServletException("Invalid token.");
			    }
			 
		}
			 
			 
		return null;
	}
}
