package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.InfoUpdateDto;
import com.project.orderflow.admin.dto.LoginDto;
import com.project.orderflow.admin.dto.SignUpDto;
import com.project.orderflow.admin.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Boolean loginOwner(@RequestBody LoginDto loginDto){
        Owner findOwner = ownerRepository.findByEmail(loginDto.getEmail());

        if(findOwner==null){
            return false;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(loginDto.getPassword(), findOwner.getPasswordHash())) {
            return true;
        }

        return false;
    }

    public void updateOwnerInfo(Long ownerId, InfoUpdateDto infoUpdateDto){
        Owner owner=ownerRepository.findById(ownerId).orElse(null);
        if(infoUpdateDto.getBusinessNumber()!=null){
            owner.setBusinessNumber(infoUpdateDto.getBusinessNumber());
        }

        if(infoUpdateDto.getNewPassword()!=null && infoUpdateDto.getNewPassword().equals(infoUpdateDto.getNewPasswordConfirm())){
            owner.setPasswordHash(infoUpdateDto.getNewPassword());
        }

        ownerRepository.save(owner);
    }
}
