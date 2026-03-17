package com.example.demo.controller;

import com.example.demo.dto.MemberForm;
import com.example.demo.dto.PasswordForm;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    // 애플리케이션 주소(http://localhost:8080)만으로 접속했을 때
    // 게시글 목록으로 이동하도록 리다이렉트
    @GetMapping("/")
    public String getHome() {
        return "redirect:/article/list";
    }

    // 단순히 커스텀 로그인/로그아웃 뷰 이름을 반환
    // POST /login, POST /logout은 스프링 시큐리티가 처리하므로 우리가 구현할 필요X
    @GetMapping("/login")
    public String getLogin() {
        return "/auth/login";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "/auth/logout";
    }

    // POST /signup에서 폼을 검증하고 입력된 값을 다시 보여주기 위해
    // member라는 이름의 모델 속성을 추가
    @GetMapping("/signup")
    public String getMemberAdd(@ModelAttribute("member") MemberForm memberForm) {
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String postMemberAdd(@Valid @ModelAttribute("member") MemberForm memberForm,
                                BindingResult bindingResult) {
        // 이름과 이메일 검증을 폼 검증 어노테이션을 사용해 검증한 후에 그 결과를 BindingResult로 전달받음

        // 어노테이션 외에 추가 검증이 필요한 경우
        // 1. 필드 간 검증
        // 2. DB 조회가 필요한 검증

        // 입력한 2개의 패스워드가 동일한지 직접 비교하고,
        // 다르다면 BindingResult에 그 오류 내용을 입력해 다시 입력받을 수 있도록 함
        if (!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "MissMatch", "입력하신 패스워드가 다릅니다");
        }

        // 기존 가입자의 이메일과 중복되면 안됨
        // 입력된 이메일로 회원을 조회하고, 이미 사용중인 이메일이면
        // BindingResult를 통해 오류를 웹 화면으로 전달
        if (memberService.findByEmail(memberForm.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "AlreadyExist", "사용중인 이메일입니다.");
        }

        // 오류가 있다면 다시 입력받을 수 있도록 함
        if (bindingResult.hasErrors()) {
            return "/member/signup";
        }

        memberService.create(memberForm);

        return "redirect:/";
    }

    @GetMapping("/password")
    public String getPassword(@ModelAttribute("password") PasswordForm passwordForm) {
        return "/member/password";
    }

    @PostMapping("/password")
    public String postPassword(@Valid @ModelAttribute("password") PasswordForm passwordForm,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal MemberUserDetails userDetails) {
        // 로그인한 사용자의 패스워드와 비교
        if (!memberService.checkPassword(userDetails.getMemberId(), passwordForm.getOld())) {
            bindingResult.rejectValue("old", "MissMatch", "비밀번호가 잘못 되었습니다.");
        }

        // 변경할 패스워드와 확인용 패스워드가 일치하는지 비교
        if (!passwordForm.getPassword().equals(passwordForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "MissMatch", "비밀번호가 잘못 되었습니다.");
        }

        // 오류가 있다면 다시 입력받을 수 있도록 함
        if (bindingResult.hasErrors()) {
            return "/member/password";
        }

        memberService.updatePassword(userDetails.getMemberId(), passwordForm.getPassword());

        return "redirect:/";
    }

}
