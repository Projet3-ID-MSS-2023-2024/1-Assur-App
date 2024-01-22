package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.models.User;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Service
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;

    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        boolean isTokenExpired = true;

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")){
            token = authorization.substring(7);
            username = jwtService.extractUsername(token);
            isTokenExpired = jwtService.isTokenExpired(token);
        }

        if(SecurityContextHolder.getContext().getAuthentication()==null && username!=null && !isTokenExpired){
            UserDetails user = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
