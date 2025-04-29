package com.security.authentication.resource;

import com.security.authentication.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
public class JwtAuthResource {

    private final JwtService jwtService;

    public JwtAuthResource(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(token);
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
