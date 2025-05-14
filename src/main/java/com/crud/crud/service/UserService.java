package com.crud.crud.service;

import com.crud.crud.dto.PaginationResponses;
import com.crud.crud.dto.UserDto;
import com.crud.crud.dto.UserRequest;
import com.crud.crud.exception.BadRequestException;
import com.crud.crud.exception.EntityNotFoundException;
import com.crud.crud.model.User;
import com.crud.crud.repository.UserRepository;
import com.crud.crud.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserDto save(UserRequest request) {
    final var existUser = this.userRepository.findByEmail(request.getEmail());
    if (existUser.isPresent()) {
      throw new BadRequestException("User with this email already exist.");
    }
    final var user = this.modelMapper.map(request, User.class);
    user.setPassword(this.passwordEncoder.encode(request.getPassword()));
    final var savedUser = this.userRepository.save(user);
    return this.modelMapper.map(savedUser, UserDto.class);
  }

  public UserDto getUserById(Long id) {
    return this.modelMapper.map(this.findEntityById(id), UserDto.class);
  }

  public void delete(Long id) {
    final var user = this.getUserById(id);
    this.userRepository.delete(this.modelMapper.map(user, User.class));
  }

  public UserDto update(UserDto userDto) {
    final var foundUser = this.findEntityById(userDto.getId());
    this.modelMapper.map(userDto, foundUser);

    final var updatedUser = this.userRepository.save(foundUser);
    return this.modelMapper.map(updatedUser, UserDto.class);
  }

  public PaginationResponses<UserDto> getAll(Pageable pageable) {
    final var userPage = this.userRepository.findAll(pageable);
    final var userContent =
        userPage.getContent().stream()
            .map((User user) -> this.modelMapper.map(user, UserDto.class))
            .toList();
    return PaginationUtils.buildPaginationResponse(userPage, userContent);
  }

  private User findEntityById(Long id) {
    return this.userRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found."));
  }
}
