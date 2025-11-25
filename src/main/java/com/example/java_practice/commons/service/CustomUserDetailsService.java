package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = authService.getUserInfoById(username);

        if(user == null) throw new UsernameNotFoundException("해당하는 회원이 없습니다");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getF_id())
                .password(user.getF_id())
                .roles("USER")
                .build();
    }
}
