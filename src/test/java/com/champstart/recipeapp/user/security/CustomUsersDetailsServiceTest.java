package com.champstart.recipeapp.user.security;
import com.champstart.recipeapp.user.model.Role;
import com.champstart.recipeapp.user.model.UserModel;
import com.champstart.recipeapp.user.service.UserService;
import com.champstart.recipeapp.user.service.impl.UserDetailsServiceImpl;
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
    @Test
    public void sampleTest(){
        boolean boolValue = true;
        assertTrue(boolValue);
    }

    @Mock
    private UserService userService;
    @Mock
    private HttpSession session;

    @InjectMocks
    private CustomUsersDetailsService userDetailsService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_ValidEmail_ReturnsUserDetails(){
        String email = "emailtest@gmail.com";
        String password = "password123";
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(password);
        user.setIsVerified(true);
        Role role = new Role();
        role.setName("Client");
        user.setRoles(Collections.singletonList(role));

        when(userService.findByEmail(email)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("Client", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(userService, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_InvalidEmail_ThrowsException(){
        String email = "emailtest@gmail.com";

        when(userService.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));

        verify(userService, times(1)).findByEmail(email);
    }
}
