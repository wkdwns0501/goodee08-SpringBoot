package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {
    private Long id;
//    @NotNull
//    @NotEmpty
    @NotBlank(message = "이름을 입력하세요") // null, 빈 문자열, 공백만 있는 문자열 모두 검사
    private String name;
    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식이 잘못 되었습니다") // 간단한 이메일 형식검사
    private String email;
//    @NotBlank(message = "패스워드를 입력하세요")
    @Size(min = 8, message = "패스워드를 8글자 이상 입력하세요")
    private String password;
    private String passwordConfirm;

    // (참고) 정규표현식 사용
    // @Pattern은 문자열 타입만 가능
//    @Pattern(regexp = "^[a-zA-Z0-9]", message = "영문과 숫자만 입력 가능합니다")
//    @Email(regexp = "", message = "")
}
