package com.adrian.tablaTiempos.controller;

import static com.adrian.tablaTiempos.utils.Constans.TIEMPO_VIDA;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.tablaTiempos.Servicio.JwtService;
import com.adrian.tablaTiempos.modelo.Piloto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthController {

	private final AuthenticationManager authManager;

	private final JwtService jwtService;

	public AuthController(AuthenticationManager authManager, JwtService jwtService) {
		this.authManager = authManager;
		this.jwtService = jwtService;
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> login(@RequestBody Piloto usuario) {
		if (usuario == null || usuario.getNombre() == null || usuario.getPassword() == null) {
			return ResponseEntity.badRequest().build();
		}

		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuario.getNombre(), usuario.getPassword()));

			String roles = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));

			return ResponseEntity.ok(new LoginResponse(authentication.getName(), getToken(authentication), roles));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	public record LoginResponse(String nombre, String pass, String rol) {
	}

	private String getToken(Authentication authentication) {
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setSubject(authentication.getName())
				.claim("authorities", authentication.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.setExpiration(new Date(System.currentTimeMillis() + TIEMPO_VIDA))
				.signWith(SignatureAlgorithm.HS512,
						jwtService.obtenerClave().getBytes(StandardCharsets.UTF_8))
				.compact();
	}
}
