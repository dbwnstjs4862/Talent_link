package com.talentlink.talentlink.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public Long register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        return userRepository.save(user).getId();
    }

    // 로그인용 사용자 조회
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
