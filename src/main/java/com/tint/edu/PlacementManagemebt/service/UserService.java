package com.tint.edu.PlacementManagemebt.service;


import com.tint.edu.PlacementManagemebt.dto.EmailReq;
import com.tint.edu.PlacementManagemebt.dto.RegisterRequst;
import com.tint.edu.PlacementManagemebt.entity.Role;
import com.tint.edu.PlacementManagemebt.entity.User;
import com.tint.edu.PlacementManagemebt.repository.RoleRepository;
import com.tint.edu.PlacementManagemebt.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMailService studentMailService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,StudentMailService studentMailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentMailService=studentMailService;
    }
    @Transactional
    public User registerUser(RegisterRequst registerRequst){
        String username = normalizeEmail(registerRequst.getUsername());
        String email = normalizeEmail(registerRequst.getEmail());

        if(!isTintEmail(username) || !isTintEmail(email)){
            throw new RuntimeException("Use a valid @tint.edu.in email to register");
        }
        if(!studentMailService.emailExists(new EmailReq(email))){
            throw new RuntimeException("This student email is not added by admin yet");
        }
        if(userRepository.existsByUsername(username)){
            throw  new RuntimeException("username already register"+username);
        }
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("email already exists "+email);
        }
        Role userRole =roleRepository.findByName("USER")
                .orElseGet(()->{
                    return roleRepository.save(Role.builder()
                            .name("USER")
                            .description("Standerd User")
                            .build());
                });
        User user=User.builder()
                .userName(username)
                .email(email)
                .password(passwordEncoder.encode(registerRequst.getPassword()))
                .fullName(registerRequst.getFullName())
                .enabled(true)
                .accountNonLocked(true)
                .build();
        user.addRoles(userRole);

        return  userRepository.save(user);
    }
    @Transactional

    public User registerAdmin(RegisterRequst registerRequst){
        String username = normalizeEmail(registerRequst.getUsername());
        String email = normalizeEmail(registerRequst.getEmail());

        if(!isCollegeStaffEmail(username) || !isCollegeStaffEmail(email)){
            throw new RuntimeException("Use a valid @tint.edu.in or @tict.edu.in email to register as admin");
        }
        if(userRepository.existsByUsername(username)){
            throw  new RuntimeException("username already register"+username);
        }
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("email already exists "+email);
        }
        Role userRole =roleRepository.findByName("USER")
                .orElseGet(()->{
                    return roleRepository.save(Role.builder()
                            .name("USER")
                            .description("Standerd User")
                            .build());
                });
        Role adminRole=roleRepository.findByName("ADMIN")
                .orElseGet(()->{
                    return roleRepository.save(Role.builder()
                            .name("ADMIN")
                            .description("Admin role")
                            .build());
                });
        User admin=User.builder()
                .userName(username)
                .email(email)
                .password(passwordEncoder.encode(registerRequst.getPassword()))
                .fullName(registerRequst.getFullName())
                .enabled(true)
                .accountNonLocked(true)
                .build();
        admin.addRoles(userRole);
        admin.addRoles(adminRole);
        return  userRepository.save(admin);
    }
    public User findByUserName(String username){
        return  userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.strip().toLowerCase();
    }

    private boolean isTintEmail(String email) {
        return email.endsWith("@tint.edu.in");
    }

    private boolean isCollegeStaffEmail(String email) {
        return isTintEmail(email) || email.endsWith("@tict.edu.in");
    }
}
