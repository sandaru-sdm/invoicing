package com.sdm.invoicing.controller;

import com.sdm.invoicing.entity.User;
import com.sdm.invoicing.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class ActivationController {
    private final UserRepository userRepository;

    public ActivationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam("code") String code) {
        User user = userRepository.findByActivationCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid activation code!"));

        user.setActivated(true);
        user.setActivationCode(null);
        userRepository.save(user);

        return ResponseEntity.ok("Account activated successfully! You can now log in.");
    }
}
