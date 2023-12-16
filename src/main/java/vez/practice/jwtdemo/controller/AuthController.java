package vez.practice.jwtdemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vez.practice.jwtdemo.service.TokenService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public String token(Authentication authentication) {
        log.info("Token requested for user: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        log.debug("token granted: {}", token);
        return token;
    }
}
