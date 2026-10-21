package br.com.melqui.livraria.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // roda em toda requisição, antes do controller (slides 21 e 22)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = extrairToken(request);
        if (jwt != null) {
            autenticar(jwt);
        }
        filterChain.doFilter(request, response);
    }

    // 1. extrai: o token vem no cabeçalho no formato "Bearer eyJhbGci..."
    private String extrairToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // 2. valida e 3. autentica: se o token for válido, marca o usuário como logado nesta requisição
    private void autenticar(String jwt) {
        try {
            String email = jwtService.extrairEmail(jwt);
            UsernamePasswordAuthenticationToken autenticacao =
                    new UsernamePasswordAuthenticationToken(email, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        } catch (JwtException | IllegalArgumentException e) {
            // token adulterado, vencido ou malformado: segue sem usuário, e o Security devolve 401
            SecurityContextHolder.clearContext();
        }
    }
}
