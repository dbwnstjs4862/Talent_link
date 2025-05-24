package com.talentlink.talentlink.user;

import com.talentlink.talentlink.security.CustomUserDetailsService;
import com.talentlink.talentlink.user.dto.UserRequest;
import com.talentlink.talentlink.user.dto.UserResponse;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       CustomUserDetailsService customUserDetailsService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    // ✅ 회원가입 + 자동 로그인
    public UserResponse register(UserRequest request, HttpServletRequest httpRequest) {
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

        // ✅ 자동 로그인 + 세션 반영
        autoLogin(httpRequest, user.getUsername(), request.getPassword());
        HttpSession session = httpRequest.getSession(true);
        System.out.println("✅ 회원가입 후 세션 ID: " + session.getId());

        return new UserResponse(user);
    }

    // ✅ SecurityContext + 세션에 SPRING_SECURITY_CONTEXT 수동 저장
    private void autoLogin(HttpServletRequest request, String username, String rawPassword) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, rawPassword);

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ 세션에 SecurityContext 저장
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        System.out.println("✅ 인증 객체: " + authentication);
        System.out.println("✅ 인증 여부: " + authentication.isAuthenticated());
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

    public UserResponse registerWithoutAutoLogin(UserRequest request) {
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

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
