package com.talentlink.talentlink.user;

import com.talentlink.talentlink.security.CustomUserDetailsService;
import com.talentlink.talentlink.user.dto.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // ✅ 회원가입 + 자동 로그인 (뷰용)
    public void register(String username, String password, String nickname, HttpServletRequest httpRequest) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                nickname,
                UserRole.USER
        );

        userRepository.save(user);
        autoLogin(httpRequest, username, password);
    }

    // ✅ 자동 로그인
    private void autoLogin(HttpServletRequest request, String username, String rawPassword) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, rawPassword);

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void updateProfile(User user, UserUpdateRequest request) {
        user.updateProfile(request.getNickname());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.getNickname(); // 강제 초기화
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}

