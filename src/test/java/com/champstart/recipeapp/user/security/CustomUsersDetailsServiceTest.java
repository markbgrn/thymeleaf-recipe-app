package com.champstart.recipeapp.user.security;
import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.model.Role;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Collections;

public class CustomUsersDetailsServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private HttpSession session;

    @Mock
    private UserDto userDto;

    @InjectMocks
    private CustomUsersDetailsService userDetailsService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userDto = UserDto.builder()
                .email("emailtest@gmail.com")
                .password("password")
                .confirmPassword("password")
                .firstName("Jane")
                .lastName("Doe")
                .verificationId("")
                .build();
    }

    @Test
    public void loadUserByUsernameTest(){

        UserModel user = new UserModel();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setIsVerified(true);
        Role role = new Role();
        role.setName("Client");
        user.setRoles(Collections.singletonList(role));

        when(userService.findByEmail(userDto.getEmail())).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());

        assertNotNull(userDetails);
        assertEquals(userDto.getEmail(), userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("Client", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(userService, times(1)).findByEmail(userDto.getEmail());
    }

    @Test
    public void loadUserByUsernameNullEmailTest(){
        String email = "emailtest@gmail.com";

        when(userService.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));

        verify(userService, times(1)).findByEmail(email);
    }
}
