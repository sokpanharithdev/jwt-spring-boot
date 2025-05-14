package com.crud.crud.controller;

import com.crud.crud.dto.PaginationResponses;
import com.crud.crud.dto.UserDto;
import com.crud.crud.dto.UserRequest;
import com.crud.crud.service.UserService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> save(@RequestBody UserRequest request) {
    return ResponseEntity.ok(this.userService.save(request));
  }

  @PutMapping
  public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(this.userService.update(userDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(this.userService.getUserById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<PaginationResponses<UserDto>> findAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "desc") String sortDirection,
      @RequestParam(value = "storeId", required = false) Long storeId) {
    Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase(Locale.ENGLISH));
    return new ResponseEntity<>(
        userService.getAll(PageRequest.of(page, size, Sort.by(direction, sortBy))), HttpStatus.OK);
  }
}
