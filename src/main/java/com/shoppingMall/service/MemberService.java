package com.shoppingMall.service;

import com.shoppingMall.entity.Member;
import com.shoppingMall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service  // 나 서비스다
@Transactional  // 트랜잭선 설정 : 성공하면 그대로 적용, 실패하면 롤백
@RequiredArgsConstructor  // final 또는 @NonNull 명령어가 붙으면 객체를 자동으로 붙임
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //Controller -> Service
    public Member saveMember(Member member){
        validateDuplicateMember(member);  // 같은 이메일 있는지 확인
        return memberRepository.save(member);  // 데이터베이스에 저장하라는 명령
    }
    private void validateDuplicateMember(Member member){
        Member findMember=memberRepository.findByEmail(member.getEmail());
        if(findMember!=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Member member=memberRepository.findByEmail(email);

        if(member==null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder().username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
