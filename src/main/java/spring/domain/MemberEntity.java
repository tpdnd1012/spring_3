package spring.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor // 빈 생성자
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 들어오는 값이 NULL 이면 AUTO값 부여
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING) // Enum 값을 String 값으로 저장하기
    @Column
    Role role;

    // 생성자
    @Builder
    public MemberEntity(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Role 키 반환 메소드
    public String getkey() {

        return this.role.getKey();

    }

    // 멤버 이름 업데이트 메소드
    public MemberEntity update(String name) {

        this.name = name;

        return this;

    }
    
}
