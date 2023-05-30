package tko.refresh.repository.refreshtoken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import tko.refresh.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccountEmail(String email);
}
