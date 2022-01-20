package com.ss.utopia.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ss.utopia.dao.UserRepository;
import com.ss.utopia.entity.User;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	RestTemplate rt = new RestTemplate();
	@Value("${user.url}")
	String usersUrl;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
//		String jwtToken = requestTokenHeader.substring(7);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", requestTokenHeader);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try{
			logger.info("Trying authorization");
			ResponseEntity<List> verification = rt.exchange(usersUrl + "/verify", HttpMethod.GET, entity, List.class);
			Integer role = (Integer)verification.getBody().get(0);
			Integer id = (Integer)verification.getBody().get(1);
			if(role != 1 && !request.getRequestURI().equals("/booking") && !request.getMethod().equals("POST")) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				return;
			}
			request.setAttribute("role", role);
			request.setAttribute("id", id);
		}catch(HttpClientErrorException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			logger.error("Unauthorized attempt");
			return;
		}catch(Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			logger.error("Something went wrong with verification");
			return;
		}
		chain.doFilter(request, response);
	}
}