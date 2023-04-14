package com.nisum.challenge.infraestructure;

import com.nisum.challenge.utils.DateUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.nisum.challenge.entity.User;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Security {

    public static String createToken(User user) {
        {
            try{
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
                HashMap<String, Object> header = new HashMap<>();
                header.put("alg", signatureAlgorithm.toString());
                header.put("typ","JWT");
                LocalDateTime expired = LocalDateTime.now().plusHours(1);
                JwtBuilder tokenJWT = Jwts
                        .builder()
                        .setHeader(header)
                        .setId(user.getId().toString())
                        .claim("name", user.getName())
                        .claim("email", user.getEmail())
                        .setIssuedAt(DateUtils.instance().asDate(LocalDate.now()))
                        .setExpiration(DateUtils.instance().asDate(expired));
                return tokenJWT.compact();
            } catch (Exception e) {
                return "Error creating the token JWT";
            }
        }
    }
}

