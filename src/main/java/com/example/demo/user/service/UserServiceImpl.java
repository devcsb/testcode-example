package com.example.demo.user.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.controller.port.*;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserCreateService, UserReadService, UserUpdateService, AuthenticationService {

    private final UserRepository userRepository;
    private final CertificationService certificationServiceImpl; // 하위레이어에서 상위 레이어를 의존하게 되었음. 추후 수정해야 함.
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    @Transactional
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, uuidHolder);
        user = userRepository.save(user);

        certificationServiceImpl.send(userCreate.getEmail(), user.getId(), user.getCertificationCode());
        return user;
    }

    @Override
    @Transactional
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        user = user.update(userUpdate);
        user = userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void login(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
//        user.setLastLoginAt(Clock.systemUTC().millis()); // 원래는 Jpa의 dirty checking으로 DB 데이터가 수정되었지만, 영속성 객체와 의존을 끊어냈기 때문에, 따로 save()로 저장 처리한다.
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.certificate(certificationCode);
        userRepository.save(user);
    }
}