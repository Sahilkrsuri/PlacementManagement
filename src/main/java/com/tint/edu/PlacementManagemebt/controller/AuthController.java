package com.tint.edu.PlacementManagemebt.controller;

import com.tint.edu.PlacementManagemebt.dto.LoginRequst;
import com.tint.edu.PlacementManagemebt.dto.RegisterRequst;
import com.tint.edu.PlacementManagemebt.entity.Role;
import com.tint.edu.PlacementManagemebt.entity.User;
import com.tint.edu.PlacementManagemebt.service.JwtService;
import com.tint.edu.PlacementManagemebt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private  final UserService userService;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@Valid @RequestBody RegisterRequst requst){
        User user =userService.registerUser(requst);
        Map<String,Object> response=new HashMap<>();
        response.put("sucess",true);
        response.put("message","user registered sucessfully");
        response.put("username",user.getUsername());
        response.put("email",user.getEmail());
        response.put("roles",user.getRoles().stream().map((Role::getName)).collect(Collectors.toList()));
        return ResponseEntity.ok(response);

    }
    @PostMapping("/register-admin")
    public ResponseEntity<Map<String,Object>> registerAdmin(@Valid @RequestBody RegisterRequst requst){

        User user =userService.registerAdmin(requst);
        Map<String,Object> response=new HashMap<>();
        response.put("sucess",true);
        response.put("message","admin  registered sucessfully");
        response.put("username",user.getUsername());
        response.put("email",user.getEmail());
        response.put("roles",user.getRoles().stream().map((Role::getName)).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> createToken(@RequestBody LoginRequst requst){
        try{
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requst.getUserName(),requst.getPassword())
            );
            UserDetails user=(UserDetails) authentication.getPrincipal();
            String token= jwtService.generateToken(user.getUsername());
            Map<String,Object> response=new HashMap<>();
            response.put("token",token);
            response.put("type","Bearer");
            response.put("roles",user.getAuthorities().stream()
                    .map(a->a.getAuthority()).toList());
            response.put("expieTime",jwtService.getJwtExpirationTime());
            System.out.println("User logged in :"+user.getUsername());
            return  ResponseEntity.ok(response);
        }
        catch (Exception e){
            Map<String,Object> response =new HashMap<>() ;
            response.put("error","invalid credential");
            response.put("message",e.getMessage());
//            return ResponseEntity.badRequest().body(response);
            return  ResponseEntity.status(401).body(response);
        }
    }

}
