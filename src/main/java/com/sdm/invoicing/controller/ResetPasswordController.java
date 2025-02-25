package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.ResetPasswordRequest;
import com.sdm.invoicing.service.ForgotPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ResetPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    public ResetPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordRequest request) {

        if (forgotPasswordService.validateResetToken(token)) {
            if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password and Confirm Password Not Match.");
            } else {
                forgotPasswordService.updatePassword(token, request.getNewPassword());
                return ResponseEntity.ok("Password has been successfully reset.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
    }
}
