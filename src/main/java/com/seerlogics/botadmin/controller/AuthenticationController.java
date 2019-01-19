package com.seerlogics.botadmin.controller;

import com.seerlogics.botadmin.config.AppProperties;
import com.lingoace.model.AuthToken;
import com.lingoace.model.Login;
import com.lingoace.spring.authentication.JWTTokenProvider;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by bkane on 11/4/18.
 */
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppProperties appProperties;

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin() {
        return "{success: 'true'}";
    }

    @PostMapping(value = "/generate-token")
    public ResponseEntity register(@RequestBody Login loginUser) throws AuthenticationException {

        /**
         * Authenticate the user using the username and password
         */
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUserName(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = JWTTokenProvider.generateToken(authentication,
                SignatureAlgorithm.forName(appProperties.getJwtSignatureAlgo()),
                appProperties.getJwtSecretKey(), appProperties.getJwtTtl());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        AuthToken authToken = new AuthToken(token);
        for (GrantedAuthority authority : authorities) {
            authToken.getRoles().add(authority.getAuthority().replace("ROLE_", "").trim());
        }
        return ResponseEntity.ok(authToken);
    }
}