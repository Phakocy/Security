package com.phakocy.jwtsendotp.auth;

import com.phakocy.jwtsendotp.auth.dto.AuthDto;
import com.phakocy.jwtsendotp.auth.dto.RegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationDto registrationDto){

        return ResponseEntity.ok(authenticationService.register(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthDto authDto){

        return ResponseEntity.ok(authenticationService.authenticate(authDto));
    }

}
