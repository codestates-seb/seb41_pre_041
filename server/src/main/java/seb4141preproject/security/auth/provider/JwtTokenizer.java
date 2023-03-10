package seb4141preproject.security.auth.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenizer {
    private final Key key;

    @Getter
    @Value("${jwt.ATExpiration}")
    private int ATExpiration;

    @Getter
    @Value("${jwt.RTExpiration}")
    private int RTExpiration;

    private final UserDetailsService userDetailsService;

    public JwtTokenizer(@Value("${jwt.key}") String secretKey,
                        UserDetailsService userDetailsService) {
        this.key = getKeyFromEncodedSecretKey(encodeSecretKey(secretKey));
        this.userDetailsService = userDetailsService;
    }

    public String encodeSecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getKeyFromEncodedSecretKey(String encodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(encodedSecretKey);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        return secretKey;
    }

    public String generateAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("auth", authorities);

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + ATExpiration * 1000 * 60);
        return Jwts.builder()
                .setClaims(claims)  // JWT??? ?????? body
                .setSubject(authentication.getName())    // JWT ?????? payload "sub": "email"
                .setIssuedAt(Calendar.getInstance().getTime())  // JWT ???????????? payload "iat": "????????????"
                .setExpiration(accessTokenExpiresIn)  // ???????????? payload "exp": "???????????? + ?????? ??????(ATExpiration)"
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("auth", authorities);

        long now = (new Date()).getTime();

        Date refreshTokenExpiresIn = new Date(now + RTExpiration * 1000 * 60);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())    // JWT ?????? payload "sub": "email"
                .setIssuedAt(Calendar.getInstance().getTime())  // JWT ???????????? payload "iat": "????????????"
                .setExpiration(refreshTokenExpiresIn)  // ???????????? payload "exp": "???????????? + ?????? ??????(RTExpiration)"
                .signWith(key)  // signature header "alg": "HS512"
                .compact(); // ??????
    }

    // ????????? ???????????? ???????????? ????????????
    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken); // ????????? ?????? ??????

        if (claims.get("auth") == null) {
            throw new RuntimeException("?????? ????????? ?????? ???????????????.");
        }

        Collection<? extends GrantedAuthority> authorities = // ?????? ?????? ????????????
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // ?????? ????????? ??????
    public boolean validateToken(String token) {
       Jwts
               .parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token);

       return true;
    }

    public Claims parseClaims(String accessToken) { // ?????? ?????? ?????? (?????? ????????? ?????? ??????)
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
