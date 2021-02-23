package com.example.payments.components;

import com.example.payments.model.Token;
import com.example.payments.service.TokenService;
import com.example.payments.util.Tokens;
import com.sun.istack.Nullable;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("$(jwt.secret)")
    private String jwtSecret;

    private final TokenService tokenService;

    public JwtProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String generateToken(String login, @Nullable String deviceId) {
        Date date = Date.from(LocalDate.now()
                .plusDays(15)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        String stringToken = Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .setId(deviceId == null ? UUID.randomUUID().toString() : deviceId)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        Token curToken = tokenService.findByToken(stringToken);
        if (curToken != null) {
            curToken.setActive(true);
            tokenService.save(curToken);
        } else {
            tokenService.save(new Token(null, stringToken, true));
        }
        return stringToken;
    }

    public boolean validateToken(String token) {
        Token tokenModel = tokenService.findByToken(token);
        if (tokenModel == null) {
            log.error("unknown token");
            return false;
        }
        if (!tokenModel.isActive()) {
            log.error("token disabled");
            return false;
        }
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public void disableToken(HttpServletRequest request) {
        String token = Tokens.getTokenFromRequest(request);
        if (token == null) {
            return;
        }
        tokenService.disableToken(token);
    }
}
