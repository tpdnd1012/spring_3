package spring.web.dto;

import lombok.Builder;
import lombok.Getter;
import spring.domain.MemberEntity;
import spring.domain.Role;

import java.util.Map;

@Getter
public class MemberDto {
    
    // 회원정보 반환 => JSON 타입 => Map 컬렉션
        // JSON : 키, 값 => 한쌍

    private Map<String, Object> attribute; // 회원정보
    private String nameAttributekey;
    private String name;
    private String email;

    @Builder
    public MemberDto(Map<String, Object> attribute, String nameAttributekey, String name, String email) {
        this.attribute = attribute;
        this.nameAttributekey = nameAttributekey;
        this.name = name;
        this.email = email;
    }

    // SNS 구분 메소드
    public static MemberDto of(String registrationId, String userNameAttributeName, Map<String, Object> attribute) {

        return ofGoogle(userNameAttributeName, attribute);

    }

    // 구글 회원 가져오기 메소드
    public static MemberDto ofGoogle(String userNameAttributeName, Map<String, Object> attribute) {

        return MemberDto.builder()
                .name((String) attribute.get("name"))
                .email((String) attribute.get("email"))
                .attribute(attribute)
                .nameAttributekey(userNameAttributeName).build();

    }

    // 카카오 회원 가져오기 메소드

    // 네이버 회원 가져오기 메소드

    // DTO --> Entity
    public MemberEntity toEntity() {

        return MemberEntity.builder()
                .name(name)
                .email(email)
                .role(Role.MEMBER).build();

    }
    
}
