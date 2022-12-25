package com.example.apiCS.Security.Service;

import com.example.apiCS.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails  {
    private Long id;
    private Map<String, Object> attributes;
    private String phone;

    private String email;

    @JsonIgnore
    private String password;
    GrantedAuthority authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List< GrantedAuthority> collectionAuthorities =  new ArrayList<>();
        collectionAuthorities.add(authorities);
        return collectionAuthorities;
    }
    public UserDetailsImpl(Long id, String phone, String email, String password,
                           GrantedAuthority authority) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.authorities = authority;
    }
    public static UserDetailsImpl build(User user) {
        GrantedAuthority authority =new SimpleGrantedAuthority(user.getRole().getName().name());
        return new UserDetailsImpl(
                user.getId(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                authority);
    }
    public static UserDetailsImpl create(User user, Map<String, Object> attributes) {
        UserDetailsImpl userPrincipal = UserDetailsImpl.build(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
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
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }




}
