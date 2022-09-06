//package com.example.apiCS.Security.Service;
//
//import com.example.apiCS.Entity.User;
//import com.example.apiCS.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//        User user = userRepository.findByUsernameOrEmail(account).orElseThrow(
//                () -> new UsernameNotFoundException("User Not Found with username: " + account)
//        );
//        return UserDetailsImpl.build(user);
//    }
//}
