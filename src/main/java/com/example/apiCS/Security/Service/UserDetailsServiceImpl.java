package com.example.apiCS.Security.Service;

import com.example.apiCS.Entity.User;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(account).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND,"User Not Found with username: " + account)
        );
        return UserDetailsImpl.build(user);
    }
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND,"User Not Found with id: " + id)
        );
        return UserDetailsImpl.build(user);
    }
}
