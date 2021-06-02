package spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // Jpa 메소드 만들기
    // 반환타입 : Optional
    // 메소드 이름 : findByemail
    // 저장되는 엔티티 : MemberEntity
    // 찾는 값 : String email
    Optional<MemberEntity> findByemail(String email);

}
