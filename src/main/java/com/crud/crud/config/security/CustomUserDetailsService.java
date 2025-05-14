package com.crud.crud.config.security;

import com.crud.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final var user =
        userRepository
            .findByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

    return User.builder().username(user.getEmail()).password(user.getPassword()).build();
  }
}
