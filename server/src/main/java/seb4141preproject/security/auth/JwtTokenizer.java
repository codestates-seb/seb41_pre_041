package seb4141preproject.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenizer {
    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    @Getter
    @Value("${jwt.ATExpiration}")
    private int ATExpiration;

    @Getter
    @Value("${jwt.RTExpiration}")
    private int RTExpiration;

    public String encodeSecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String encodedSecretKey) {
        Key secretKey = getKeyFromEncodedSecretKey(encodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String subject, Date expiration, String encodedSecretKey) {
        Key secretKey = getKeyFromEncodedSecretKey(encodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> getClaims(String jws, String encodedSecretKey) {
        Key secretKey = getKeyFromEncodedSecretKey(encodedSecretKey);

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jws);
    }

    public void verifySignature(String jws, String encodedSecretKey) {
        Key secretKey = getKeyFromEncodedSecretKey(encodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jws);
    }

    public Date getTokenExpiration(int expiration) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiration);
        Date date = calendar.getTime();

        return date;
    }

    private Key getKeyFromEncodedSecretKey(String encodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(encodedSecretKey);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        return secretKey;
    }


}
