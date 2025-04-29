package com.security.authentication.resource;

import com.security.authentication.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtAuthResource {

    private final JwtService jwtService;

    public JwtAuthResource(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username) {
        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken) {
        if (jwtService.validateToken(refreshToken)) {
            String username = jwtService.extractUsername(refreshToken);
            String newAccessToken = jwtService.generateAccessToken(username);

            Map<String, String> token = new HashMap<>();
            token.put("accessToken", newAccessToken);

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok("Token válido! Usuário autenticado: " + username);
        } else {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }
    }
}
