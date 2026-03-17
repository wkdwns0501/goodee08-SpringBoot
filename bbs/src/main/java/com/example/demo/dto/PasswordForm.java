package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordForm {
    // 기존 패스워드, 새로 변경할 패스워드, 확인을 위한 패스워드 총 3가지가 필요
    @NotBlank(message = "기존 패스워드를 입력해 주세요")
    private String old;
    @Size(min = 8, message = "8글자 이상 입력해 주세요")
//    @NotBlank(message = "새로운 패스워드를 입력해 주세요")
    private String password;
    @Size(min = 8, message = "8글자 이상 입력해 주세요")
//    @NotBlank(message = "새로운 패스워드를 확인해 주세요")
    private String passwordConfirm;
}
