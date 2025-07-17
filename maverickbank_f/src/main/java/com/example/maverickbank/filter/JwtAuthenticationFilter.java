package com.example.maverickbank.filter;

import com.example.maverickbank.service.CustomUserDetailsService;
import com.example.maverickbank.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;

    private static final String AUTH = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final List<String> PUBLIC = List.of(
            "/auth", "/auth/", "/auth/login", "/auth/register",
            "/createAccount", "/api/admins/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = req.getServletPath();
        if (PUBLIC.stream().anyMatch(path::startsWith)) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader(AUTH);
        String token = null;
        String username = null;

        if (header != null && header.startsWith(BEARER)) {
            token = header.substring(BEARER.length());
            try {
                username = jwtUtil.extractUsername(token);
            } catch (ExpiredJwtException ex) {
                // ==> tell the client “please login again”
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setHeader("X-Token-Expired", "true");
                return;   // stop filter-chain
            } catch (Exception ex) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                auth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, res);
    }
}
