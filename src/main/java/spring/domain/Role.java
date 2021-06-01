package spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 열거형 : 한 변수에 => 두개 이상 변수를 묶음
        // 순서가 있는 상수

    ADMIN("ROLE_ADMIN", "관리자"),
    MEMBER("ROLE_MEMBER", "일반회원");

    private final String key;
    private final String title;

}
