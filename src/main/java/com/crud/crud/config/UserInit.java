package com.crud.crud.config;

import com.crud.crud.dto.UserDto;
import com.crud.crud.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserInit {
  private final UserService userService;

  public UserInit(UserService userService) {
    this.userService = userService;
  }

  @Transactional(rollbackFor = Exception.class)
  @PostConstruct
  public void initSuperAdmin() {
    this.userService.save(
        UserDto.builder().name("admin").email("admin@gmail.com").password("123").build());
  }
}
