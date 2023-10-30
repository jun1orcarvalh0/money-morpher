package com.ada.moneymorpher.login;

import com.ada.moneymorpher.profile.Profile;
import com.ada.moneymorpher.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthRestController {

    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public String login(@RequestBody AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        this.authenticationManager.authenticate(authentication);
        Profile profile = this.profileRepository.findByUsername(request.username()).orElseThrow();
        return this.jwtService.createToken(profile);
    }

}