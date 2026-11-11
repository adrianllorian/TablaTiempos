package com.adrian.tablaTiempos.seguridad;

import static com.adrian.tablaTiempos.utils.Constans.ENCABEZADO;
import static com.adrian.tablaTiempos.utils.Constans.PREFIJO_TOKEN;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

import com.adrian.tablaTiempos.Servicio.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

	private final JwtService jwtService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(ENCABEZADO);

		// Sin cabecera no hay nada que autenticar
		if (header == null || header.trim().isEmpty()) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String header) {
		// El frontend manda el token tal cual; se admite tambien con "Bearer "
		String token = header.startsWith(PREFIJO_TOKEN)
				? header.substring(PREFIJO_TOKEN.length())
				: header;

		try {
			Claims claims = Jwts.parser()
					.setSigningKey(jwtService.obtenerClave().getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(token)
					.getBody();

			String user = claims.getSubject();
			List<?> authorities = claims.get("authorities", List.class);

			if (user != null && authorities != null) {
				return new UsernamePasswordAuthenticationToken(user, null, authorities.stream()
						.map(a -> new SimpleGrantedAuthority(String.valueOf(a)))
						.collect(Collectors.toList()));
			}
		} catch (Exception e) {
			// Token invalido o caducado: la peticion sigue sin autenticar
			SecurityContextHolder.clearContext();
		}
		return null;
	}
}
