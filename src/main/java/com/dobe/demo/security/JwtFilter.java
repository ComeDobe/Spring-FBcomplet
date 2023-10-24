package com.dobe.demo.security;

import com.dobe.demo.dao.UtilisateurDao;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
   MonUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader( "Authorization" );

        if (jwt != null && jwt.startsWith( "Bearer " )) {

            String token = jwt.substring(7);

            if (jwtUtils.isTokenValide(token)) {

                //System.out.println(token);
                Claims donnes = jwtUtils.getData(token);

                // On recupere le user dans la base de donnée en fonction de l'email du jwt
               UserDetails userDetails =  userDetailsService.loadUserByUsername(donnes.getSubject());

                // On ajoute l'utilisateur au processus d'identification de spring security

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

               // System.out.println(donnes.getSubject());
            }


        }
        filterChain.doFilter(request, response);
    }
}
