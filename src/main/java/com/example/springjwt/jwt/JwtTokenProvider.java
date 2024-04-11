package com.example.springjwt.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecrect;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() +jwtExpirationDate);
        String token  = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

                return token;

    }

    public String getUserName(String token){

      try {
          return Jwts.parser()
                  .verifyWith((SecretKey) key())
                  .build()
                  .parseSignedClaims(token)
                  .getPayload()
                  .getSubject();
      }
      catch (Exception e){
          System.out.println(e);
      }
      return null;

    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecrect));
    }

    public static void main(String[] args) {

        System.out.println(Keys.hmacShaKeyFor(Decoders.BASE64.decode("daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb")));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token).getPayload() ;
            return true;
        } catch (MalformedJwtException e) {

            System.out.println("Invalid JWT signature.");
            System.out.println("Invalid JWT token trace: {}"+e);
        } catch (ExpiredJwtException e) {
            System.out.println("Invalid JWT signature.");
            System.out.println("Invalid JWT token trace: {}"+e);
        } catch (UnsupportedJwtException e) {
            System.out.println("Invalid JWT signature.");
            System.out.println("Invalid JWT token trace: {}"+e);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid JWT signature.");
            System.out.println("Invalid JWT token trace: {}"+e);
        }
        return false;
    }

/*
* Sẽ có 4 hàm
* generate Jwt token
*       input ( Authentiation auth)
*       output String token
*       process  + getUsername from authentication
*                + create expireDate  = currentTime  + time in config
           + Generate token from jwts.builder
           * subject
           * issuedAt  currentDate
           * expiration  expireDate
           * signWith Key
           * compact

* *  key  return alogrithm
*
* * get Username
*           purpose :  Decode  token
*           input : String token
*           ouput : String username
*           verify with  secrect key
*           build  passSigndClaim token
*           payload
*           getSubject
*
*
* validateToken
*
* */
}
