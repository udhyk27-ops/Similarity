package com.example.java_practice.commons.security;

import com.example.java_practice.commons.dto.UserWithAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserWithAuth userWithAuth;

    public CustomUserDetails(UserWithAuth userWithAuth) {
        this.userWithAuth = userWithAuth;
    }

    public int getUserNo() { return userWithAuth.getF_user_no(); }

    public String getName() {
        return userWithAuth.getF_name();
    }

    public String getSort(){ return userWithAuth.getF_sort(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("Y".equals((userWithAuth.getF_auth_award()))) authorities.add(new SimpleGrantedAuthority("ROLE_AWARD"));
        if("Y".equals((userWithAuth.getF_auth_invit()))) authorities.add(new SimpleGrantedAuthority("ROLE_INVIT"));
        if("Y".equals((userWithAuth.getF_auth_reg()))) authorities.add(new SimpleGrantedAuthority("ROLE_REG"));
        if("Y".equals((userWithAuth.getF_auth_sim()))) authorities.add(new SimpleGrantedAuthority("ROLE_SIM"));
        if("Y".equals((userWithAuth.getF_auth_user()))) authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if("Y".equals((userWithAuth.getF_auth_admin()))) authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return "{noop}" + userWithAuth.getF_id(); // 실습용. 실제 서비스는 암호화 필요
    }

    @Override
    public String getUsername() {
        return userWithAuth.getF_id();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
