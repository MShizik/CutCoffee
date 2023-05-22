package com.cutcoffee.CutCoffee.config;

import com.cutcoffee.CutCoffee.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonDetailsService personDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String contactEmail = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = personDetailsService.loadUserByUsername(contactEmail);

        if (user == null) {
            throw new BadCredentialsException("Username not found");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

}
