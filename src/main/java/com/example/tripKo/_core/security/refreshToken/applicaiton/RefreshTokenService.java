package com.example.tripKo._core.security.refreshToken.applicaiton;

import com.example.tripKo._core.security.refreshToken.dao.RefreshTokenRepository;
import com.example.tripKo._core.security.refreshToken.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(String memberId, String refreshToken, String accessToken) {
        refreshTokenRepository.save(new RefreshToken(memberId, refreshToken, accessToken));
    }

    @Transactional
    public void removeRefreshToken(String accessToken) {
        refreshTokenRepository.findByAccessToken(accessToken)
                .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
    }
}

