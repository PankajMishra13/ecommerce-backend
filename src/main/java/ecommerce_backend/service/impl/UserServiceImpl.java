package ecommerce_backend.service.impl;

import ecommerce_backend.dto.UserRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.entity.User;
import ecommerce_backend.exception.MobileNumberAlreadyExistsException;
import ecommerce_backend.mapper.UserMapper;
import ecommerce_backend.repository.RoleRepository;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ecommerce_backend.entity.Role;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserResponseDto createUser(UserRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new MobileNumberAlreadyExistsException( "Mobile number already registered"
            );

        }

        User user = UserMapper.toEntity(request);

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        user.setRole(role);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto  getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null){
            return null;
        }

        return UserMapper.toResponseDto(user);
    }
}
