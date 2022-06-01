package com.shoppingMall.repository;

import com.shoppingMall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email); // select * from member where email = email; 넘어온 이메일이랑 같은지 체크

}
