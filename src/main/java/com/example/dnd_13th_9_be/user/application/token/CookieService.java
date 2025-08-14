package com.example.dnd_13th_9_be.user.application.token;


import com.example.dnd_13th_9_be.common.utils.CookieUtil;
import com.example.dnd_13th_9_be.global.error.InvalidTokenException;
import com.example.dnd_13th_9_be.user.application.dto.TokenRefreshResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CookieService {

    private final CookieUtil cookieUtil;

    public String extractRefreshToken(HttpServletRequest request){
        Optional<Cookie> refreshCookie = cookieUtil.find(request.getCookies(), "Refresh");
        return refreshCookie
                .map(Cookie::getValue)
                .orElseThrow(() -> new InvalidTokenException("리프레시 토큰이 없습니다"));
    }

    public void setTokenCookies(HttpServletResponse response, TokenRefreshResponseDto tokenResponse) {
        response.addCookie(cookieUtil.create("Access", tokenResponse.getAccessToken(), tokenResponse.getAccessTokenExpiresIn()));
        response.addCookie(cookieUtil.create("Refresh", tokenResponse.getRefreshToken(), tokenResponse.getRefreshTokenExpiresIn()));
    }



}
