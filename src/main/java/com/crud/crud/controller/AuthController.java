package com.crud.crud.controller;

import com.crud.crud.dto.AuthRequest;
import com.crud.crud.dto.AuthResponse;
import com.crud.crud.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    return new ResponseEntity<>(this.authService.login(request), HttpStatus.OK);
  }
}
