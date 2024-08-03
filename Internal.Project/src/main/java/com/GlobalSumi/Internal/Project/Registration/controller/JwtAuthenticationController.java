
package com.GlobalSumi.Internal.Project.Registration.controller;

import com.GlobalSumi.Internal.Project.Registration.config.JwtTokenRequest;
import com.GlobalSumi.Internal.Project.Registration.config.JwtTokenResponse;
import com.GlobalSumi.Internal.Project.Registration.service.JwtTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    private final JwtTokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationController(JwtTokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody JwtTokenRequest jwtTokenRequest) {
        logger.info("Authenticating user with email: {}", jwtTokenRequest.email());

        var authenticationToken = new UsernamePasswordAuthenticationToken(
                jwtTokenRequest.email(),
                jwtTokenRequest.password()
        );

        try {
            var authentication = authenticationManager.authenticate(authenticationToken);
            var token = tokenService.generateToken(authentication);

            logger.info("Authentication successful for user with email: {}", jwtTokenRequest.email());
            logger.info("Generated token: {}", token);

            return ResponseEntity.ok(new JwtTokenResponse(token));
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user with email: {}", jwtTokenRequest.email(), e);
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}
