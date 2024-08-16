package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.SignUpDto;
import com.project.orderflow.admin.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public Owner registerOwner (SignUpDto signUpDto){
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        Owner owner= Owner.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .passwordHash(encodedPassword)
                .businessNumber(signUpDto.getBusinessNumber())
                .build();

        return ownerRepository.save(owner);
    }

}
