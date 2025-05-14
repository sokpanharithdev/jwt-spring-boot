package com.crud.crud.config;

import com.crud.crud.dto.UserRequest;
import com.crud.crud.repository.UserRepository;
import com.crud.crud.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserInit {
  private final UserService userService;
  private final UserRepository userRepository;

  public UserInit(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  @PostConstruct
  public void initSuperAdmin() {
    final var user = this.userRepository.findByEmail("admin@gmail.com");
    if (user.isEmpty()) {
      this.userService.save(
          UserRequest.builder().name("admin").email("admin@gmail.com").password("123").build());
    }
  }
}
