package com.shoppingMall.controller;

import com.shoppingMall.dto.MemberFormDto;
import com.shoppingMall.entity.Member;
import com.shoppingMall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value="/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value="/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                             Model model){ // 에러 발생 시 model에 담아서 올려줌
       if(bindingResult.hasErrors()){  // valid 체크에서 이상 발생 시 실행
           return "member/memberForm";
       }
       try{
           Member member=Member.createMember(memberFormDto,passwordEncoder);
           memberService.saveMember(member);
       } catch(IllegalStateException e){  // 중복된 이메일이 있을 때 실행
           model.addAttribute("errorMessage", e.getMessage());
           return "member/memberForm";
        }
        // 객체 생성
        //Member member=Member.createMember(memberFormDto,passwordEncoder);
        //memberService.saveMember(member);

        // 최종 문제가 없어야 실행
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember(){return "/member/memberLoginForm";}

    @GetMapping(value="login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

}
