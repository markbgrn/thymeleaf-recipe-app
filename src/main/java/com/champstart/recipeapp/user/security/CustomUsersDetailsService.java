package com.champstart.recipeapp.user.security;

import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
    private UserService userService;

    private HttpSession session;
    @Autowired
    public CustomUsersDetailsService(UserService userService){
        this.userService = userService;
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        UserModel user = userService.findByEmail(email);

        if (user != null){
            User authUser = new User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getIsVerified(),
                     true, true, true,
                    user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList())

            );

            return authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }
}
