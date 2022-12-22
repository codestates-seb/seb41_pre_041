package seb4141preproject.security.auth;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
    private int ATExpiration; // TODO : 로컬이 아닌, 실제 서버에서 값을 가져오는 것이 바람직

    @Getter
    @Value("${jwt.RTExpiration}")
    private int RTExpiration; // TODO : 로컬이 아닌, 실제 서버에서 값을 가져오는 것이 바람직

    public JwtTokenizer(@Value("${jwt.key}") String secretKey) { // TODO : 로컬이 아닌, 실제 서버에서 값을 가져오는 것이 바람직
        this.key = getKeyFromEncodedSecretKey(secretKey);
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
                .setClaims(claims)  // JWT에 담는 body
                .setSubject(authentication.getName())    // JWT 제목 payload "sub": "email"
                .setIssuedAt(Calendar.getInstance().getTime())  // JWT 발행일자 payload "iat": "발행일자"
                .setExpiration(accessTokenExpiresIn)  // 만료일자 payload "exp": "발행시간 + 설정 시간(ATExpiration)"
                .signWith(key)  // signature header "alg": "HS512"
                .compact(); // 생성
    }

    public String generateRefreshToken(Authentication authentication) {

        long now = (new Date()).getTime();

        Date refreshTokenExpiresIn = new Date(now + RTExpiration * 1000 * 60);
        return Jwts.builder()
                .setSubject(authentication.getName())    // JWT 제목 payload "sub": "email"
                .setIssuedAt(Calendar.getInstance().getTime())  // JWT 발행일자 payload "iat": "발행일자"
                .setExpiration(refreshTokenExpiresIn)  // 만료일자 payload "exp": "발행시간 + 설정 시간(RTExpiration)"
                .signWith(key)  // signature header "alg": "HS512"
                .compact(); // 생성
    }

    public void verifySignature(String jws, String encodedSecretKey) {
        Key secretKey = getKeyFromEncodedSecretKey(encodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jws);
    }

    // 엑세스 토큰에서 인증정보 가져오기
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 만료된 토큰이어도 정보를 꺼내는 로직
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
