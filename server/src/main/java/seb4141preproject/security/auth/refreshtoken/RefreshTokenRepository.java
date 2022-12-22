package seb4141preproject.security.auth.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByKey(String key);
    Optional<RefreshToken> findByValue(String value);
}
