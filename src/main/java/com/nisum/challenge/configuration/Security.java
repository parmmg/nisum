package com.nisum.challenge.configuration;

import com.nisum.challenge.util.DateUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.nisum.challenge.entity.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Security {

    public static String createToken(User user) {
        {
            try{
                HashMap<String, Object> header = new HashMap<>();
                header.put("alg", SignatureAlgorithm.HS512.toString());
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

    public static String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            return password;
        }
    }
}

