
package com.rodriguez.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.rodriguez.security.JwtTokenUtil;
import com.rodriguez.security.JwtUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (Exception e) {
				//JWT Token expiro o es invalido
			}
		} else {
			// no empieza con el prefijo "Bearer "
			// o es null
		}

		//proceso para validar el token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			// si el token es valido
			// se configura la authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
				= new UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					userDetails.getAuthorities()
				);
				
				usernamePasswordAuthenticationToken
					.setDetails(
						new WebAuthenticationDetailsSource()
						.buildDetails(request)
					);
				
				
				// se actualiza el contexto de seguridad
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		// si el userName es null
		// o la autenticacion es distinta de null
		// se pasa la solicitud para ser procesada
		// por el siguiente filtro en la cadena de filtros
		chain.doFilter(request, response);
	}

}
