package com.nutribite.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutribite.ai.dto.OnboardingRequest;

@RestController
@RequestMapping("/api/onboarding")
@CrossOrigin(origins = "http://localhost:5173")
public class OnboardingController {

    @PostMapping
    public ResponseEntity<?> saveOnboarding(
            @RequestBody OnboardingRequest request) {

        System.out.println("Activity: " + request.getActivity());
        System.out.println("Goals: " + request.getGoals());
        System.out.println("Age: " + request.getAge());

        return ResponseEntity.ok(
                java.util.Map.of(
                        "message", "Onboarding received successfully"
                )
        );
    }
}