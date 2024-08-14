package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
    User user = new User();
    user.setUsername("testuser");
    user.setPassword("password");
    user.setEmail("testuser@example.com");

    // 模擬 userRepository.save() 方法的行為
    doNothing().when(userRepository).save(any(User.class));

    userService.saveUser(user);

    // 驗證 userRepository 的 save 方法是否被調用了一次
    verify(userRepository, times(1)).save(any(User.class));
}


    @Test
    void testExistsByUsername() {
        String username = "testuser";
        
        // 模擬userRepository.existsByUsername()方法的行為
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean exists = userService.existsByUsername(username);

        // 驗證返回結果以及userRepository.existsByUsername()是否被調用一次
        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        // 模擬userRepository.findById()方法的行為
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.getUserById(1L);

        // 驗證返回結果以及userRepository.findById()是否被調用一次
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("olduser");

        User updatedUser = new User();
        updatedUser.setUsername("newuser");

        // 模擬userRepository.existsById()和findById()方法的行為
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUser));

        boolean updated = userService.updateUser(1L, updatedUser);

        // 驗證結果以及userRepository方法的調用次數
        assertTrue(updated);
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // 模擬userRepository.existsById()方法的行為
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean deleted = userService.deleteUser(1L);

        // 驗證結果以及userRepository方法的調用次數
        assertTrue(deleted);
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
