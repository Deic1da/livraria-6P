package br.com.melqui.livraria.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private final Key secret;
    private final long expiracao;

    // @Value lê o valor do application.yaml; a secret vira a chave que assina os tokens (slide 11)
    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiracao) {
        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiracao = expiracao;
    }

    // monta o crachá: dono (sub), quando foi criado (iat), quando vence (exp) e a assinatura
    public String gerarToken(String email) {
        Date agora = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(agora)
                .setExpiration(new Date(agora.getTime() + expiracao))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    // confere a assinatura e a validade; se o token foi adulterado ou venceu, lança exceção
    public String extrairEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

