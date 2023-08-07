package com.example.meusgastos.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.meusgastos.domain.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
// pode ser importada em qualquer lugar
@Component
public class JwtUtil {
    @Value("${auth.jwt.secret}")
    private String jwtSecret;
    @Value("${auth.jwt-experiation-milliseg}")
    private Long jwtExpirationMilliseg;

    public String gerarToken(Authentication authentication){
        // data atual + 1 dia em milisegundos
        Date dataExpiracao = new Date(new Date().getTime() + jwtExpirationMilliseg);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        try{
            Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
            return Jwts.builder()
            .setSubject(usuario.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(dataExpiracao)
            .signWith(secretKey)
            .compact();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    private Claims getClaims(String token){
        try{
            Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
            Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token).getBody();
            return claims;
        }catch(Exception e){
            //igual ao sysout
           e.printStackTrace();
           return null;
        }
    }

    public String getUserName(String token){
        Claims claims = getClaims(token);
        if(claims == null){
            return null;
        }
        //foi o que a gente salvou
        return claims.getSubject();
    }

    public boolean isValidToken(String token){
        Claims claims = getClaims(token);
        if(claims == null){
            return false;
        }
        String email = claims.getSubject();
        Date dataExpiracao = claims.getExpiration();
        Date agora = new Date(System.currentTimeMillis());
        if(email != null && agora.before(dataExpiracao)){
            return true;
        }
        return false;
    }

}