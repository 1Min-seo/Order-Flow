package com.project.orderflow.admin.service;

import com.project.orderflow.Jwt.JwtUtil;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.*;
import com.project.orderflow.admin.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public Owner findOwnerById(Long ownerId){
        Optional<Owner> owner= ownerRepository.findById(ownerId);
        if(owner.isPresent()){
            return owner.get();
        }else{
            throw new IllegalStateException("Owner 찾을 수 없음");
        }
    }

    public Owner registerOwner (@RequestBody SignUpDto signUpDto){
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        Owner owner= Owner.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .passwordHash(encodedPassword)
                .businessNumber(signUpDto.getBusinessNumber())
                .build();

        return ownerRepository.save(owner);
    }


    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        Optional<Owner> optionalUser = ownerRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isPresent()) {
            Owner user = optionalUser.get();
            // 해시된 비밀번호 비교
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                return new LoginResponseDTO("Success", "User logged in successfully", generateJwtToken(user));
            } else {
                return new LoginResponseDTO("Failure", "Invalid password", null);
            }
        } else {
            return new LoginResponseDTO("Failure", "User not found", null);
        }
    }

    private String generateJwtToken(Owner user) {
        // 이메일과 ID를 함께 사용하여 토큰 생성
        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }

    public Owner findOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("해당 이메일로 등록된 Owner를 찾을 수 없습니다: " + email));
    }

    public void updateOwnerInfo(Long ownerId, InfoUpdateDto infoUpdateDto){
        Owner owner=ownerRepository.findById(ownerId).orElse(null);
        if(infoUpdateDto.getBusinessNumber()!=null){
            owner.setBusinessNumber(infoUpdateDto.getBusinessNumber());
        }

        String encodedPassword = passwordEncoder.encode(infoUpdateDto.getNewPassword());

        if(infoUpdateDto.getNewPassword().equals(infoUpdateDto.getNewPasswordConfirm())){
            owner.setPasswordHash(encodedPassword);
        }

        ownerRepository.save(owner);
    }
}
