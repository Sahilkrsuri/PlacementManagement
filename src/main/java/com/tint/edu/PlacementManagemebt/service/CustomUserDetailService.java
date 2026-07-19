package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.entity.User;
import com.tint.edu.PlacementManagemebt.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        return loadUser(user);
    }
    public UserDetails loadUser(User user){
        List<GrantedAuthority> authorities=user.getRoles().stream()
                .map((role)->new SimpleGrantedAuthority(role.getName().startsWith("ROLE_")?role.getName():"ROLE_"+role.getName())).collect(Collectors.toList());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(false)
                .disabled(!user.isEnable())
                .build();
    }
}
