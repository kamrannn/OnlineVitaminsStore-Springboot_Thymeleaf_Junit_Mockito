package com.app.onlinevitaminstore;

import com.app.onlinevitaminstore.model.Role;
import com.app.onlinevitaminstore.model.User;
import com.app.onlinevitaminstore.repository.UserRepository;
import com.app.onlinevitaminstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void saveUserTest() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        User user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@gmail.com")
                .username("test_user")
                .address("Street 54, house #3, Islamabad, Pakistan")
                .password(bCryptPasswordEncoder.encode("password"))
                .role(role)
                .build();
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.save(user));
    }

    @Test
    public void getUserByUsernameTest() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        User user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@gmail.com")
                .username("test_user")
                .address("Street 54, house #3, Islamabad, Pakistan")
                .password(bCryptPasswordEncoder.encode("password"))
                .role(role)
                .build();
        when(userRepository.findUserByUsername("test_user")).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUserByUsername("test_user").get());
    }
}
