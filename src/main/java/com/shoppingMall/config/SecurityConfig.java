package com.shoppingMall.config;

import com.shoppingMall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //설정
@EnableWebSecurity
//WebSecurityConfigurerAdapter 상속 받는 클래스에 @EnableWebSecurity 선언을 하면
//SpringSecurityFilterChain이 자동 포함 메소드를 오버라이딩 할 수 있다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등 설정을 작성
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests()
                .mvcMatchers("/","/members/**","/item/**","/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }
    @Bean // 원두 -> 객체 빈객체 -> SpringContainer  들어감. 이 객체 하나로 돌려씀 (싱글톤)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();  // 비밀번호를 암호화하는 해시함수
    }
    // 디코더 없음

    //AuthenticationManagerBuilder->AuthenticotionManager->UserDetailsService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");  //ignoring 예외처리 한다는 의미 (보안에서 제외한다는 의미)
    }

}
