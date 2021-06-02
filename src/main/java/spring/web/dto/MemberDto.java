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
    private String platform;

    @Builder
    public MemberDto(Map<String, Object> attribute, String nameAttributekey, String name, String email, String platform) {
        this.attribute = attribute;
        this.nameAttributekey = nameAttributekey;
        this.name = name;
        this.email = email;
        this.platform = platform;
    }

    // SNS 구분 메소드
    public static MemberDto of(String registrationId, String userNameAttributeName, Map<String, Object> attribute) {

        if(registrationId.equals("naver")) {

            return ofNaver(userNameAttributeName, attribute, registrationId);

        } else if(registrationId.equals("kakao")) {

            return ofKakao(userNameAttributeName, attribute, registrationId);

        } else {

            return ofGoogle(userNameAttributeName, attribute, registrationId);

        }

    }

    // 구글 회원 가져오기 메소드
    public static MemberDto ofGoogle(String userNameAttributeName, Map<String, Object> attribute, String registrationId) {

        return MemberDto.builder()
                .name((String) attribute.get("name"))
                .email((String) attribute.get("email"))
                .attribute(attribute)
                .platform(registrationId)
                .nameAttributekey(userNameAttributeName).build();

    }

    // 카카오 회원 가져오기 메소드
    public static MemberDto ofKakao(String userNameAttributeName, Map<String, Object> attribute, String registrationId) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account"); // JSON 이라서 Map으로 형변환
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return MemberDto.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attribute(attribute)
                .platform(registrationId)
                .nameAttributekey(userNameAttributeName).build();

    }

    // 네이버 회원 가져오기 메소드
    public static MemberDto ofNaver(String userNameAttributeName, Map<String, Object> attribute, String registrationId) {

        // 네이버의 attribute 이름 : response
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");

        return MemberDto.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attribute(attribute)
                .platform(registrationId)
                .nameAttributekey(userNameAttributeName).build();

    }

    // DTO --> Entity
    public MemberEntity toEntity() {

        return MemberEntity.builder()
                .name(name)
                .email(email)
                .role(Role.MEMBER)
                .platform(platform).build();

    }
    
}
