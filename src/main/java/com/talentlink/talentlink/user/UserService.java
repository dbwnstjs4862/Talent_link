package com.talentlink.talentlink.user;

import com.talentlink.talentlink.user.dto.UserRequest;
import com.talentlink.talentlink.user.dto.UserResponse;
import com.talentlink.talentlink.user.dto.UserUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    // 회원가입
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                UserRole.USER
        );

        userRepository.save(user);
        return new UserResponse(user);
    }

    // 로그인용 사용자 조회
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void updateProfile(User user, UserUpdateRequest request) {
        user.updateProfile(request.getNickname());
        userRepository.save(user);
    }
}
