package com.crud.crud.service;

import com.crud.crud.config.security.JwtUtil;
import com.crud.crud.dto.AuthRequest;
import com.crud.crud.dto.AuthResponse;
import com.crud.crud.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  public AuthResponse login(AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (Exception e) {
      throw new BadRequestException("Incorrect username or password");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());

    return AuthResponse.builder().accessToken(jwt).build();
  }
}
