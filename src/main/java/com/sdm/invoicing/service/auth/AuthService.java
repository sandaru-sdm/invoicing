package com.sdm.invoicing.service.auth;

import com.sdm.invoicing.dto.UserRegisterRequest;
import com.sdm.invoicing.dto.UserDto;
import com.sdm.invoicing.entity.User;
import com.sdm.invoicing.enums.UserRole;
import com.sdm.invoicing.repository.UserRepository;
import com.sdm.invoicing.service.ActivationService;
import com.sdm.invoicing.service.EmailService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final ActivationService activationService;

    private final EmailService emailService;

    public AuthService(UserRepository userRepository, ActivationService activationService, EmailService emailService) {
        this.userRepository = userRepository;
        this.activationService = activationService;
        this.emailService = emailService;
    }

    public UserDto createUser(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setEmail(userRegisterRequest.getEmail());
        user.setName(userRegisterRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()));
        String activationCode = activationService.generateActivationCode();
        user.setActivationCode(activationCode);
        user.setRole(userRegisterRequest.getUserRole());
        User createUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        userDto.setEmail(createUser.getEmail());
        userDto.setName(createUser.getName());
        userDto.setRole(createUser.getRole());
        userDto.setActivationCode(createUser.getActivationCode());

        // Send activation email
        emailService.sendActivationEmail(userDto.getEmail(), activationCode);

        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.SUPER_ADMIN);
        if(adminAccount == null){
            User user = new User();
            user.setEmail("maduhansadilshan@gmail.com");
            user.setName("Sandaru Gunathilake");
            user.setRole(UserRole.SUPER_ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("dilshan2000"));

            String activationCode = activationService.generateActivationCode();
            user.setActivationCode(activationCode);

            emailService.sendActivationEmail("maduhansadilshan@gmail.com", activationCode);
            userRepository.save(user);
        } else {
            System.out.println("Admin Account is exist." + adminAccount);
        }
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(User::getDto).collect(Collectors.toList());
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
        assert user != null;
        return user.getDto();
    }

    public UserDto updateUser(Long id, UserRegisterRequest userRegisterRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No User found with the ID :: " + id));

        user.setEmail(userRegisterRequest.getEmail());
        user.setName(userRegisterRequest.getName());

        if (userRegisterRequest.getPassword() != null && !userRegisterRequest.getPassword().isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()));
        }
        if (userRegisterRequest.getUserRole() != null) {
            user.setRole(userRegisterRequest.getUserRole());
        }

        user.setActivated(true);
        user = userRepository.save(user);

        return user.getDto();
    }

}
