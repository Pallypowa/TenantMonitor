package com.bigfoot.tenantmonitor.jwt;

import com.bigfoot.tenantmonitor.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


/*
 * JwtAuthFilter intercepts each incoming HTTP requests and
 * verifies the JWT token by getting the username and validating it.
 * */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer") || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        String jwt = header.substring(7);
        String userName;
        try {
            userName = jwtService.getUserName(jwt);
            if(userName == null){
                filterChain.doFilter(request,response);
                return;
            }
        } catch (Exception e){
            resolver.resolveException(request, response, null, new InvalidTokenException("Invalid JWT!"));
            return;
        }



        if(!jwtService.isValidToken(jwt)){
            resolver.resolveException(request, response, null, new InvalidTokenException("Invalid JWT!"));
            return;
        }
        //Get current user based on JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        UserContext.setUserName(userName);

        //Set user to SecurityContextHolder authentication to set the current user as "Logged in"
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}