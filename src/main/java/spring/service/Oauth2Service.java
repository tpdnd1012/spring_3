package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import spring.domain.MemberEntity;
import spring.domain.MemberRepository;
import spring.web.dto.MemberDto;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class Oauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // Oauth2 기본 객체
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        // 요청한 회원의 객체
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 요청 클라이언트 아이디 = 구글, 네이버, 카카오
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // Oauth2 클라이언트 아이디 가져오기

        // 요청 클라이언트로 부터 인증 값 받아오기
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // Oauth2 회원정보를 담을 객체
        MemberDto memberDto = MemberDto.of(
                registrationId,
                userNameAttributeName,
                oAuth2User.getAttributes());

        // 회원을 DB에 저장하기
        MemberEntity entity = saveorupdate(memberDto);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(entity.getkey()))
                ,memberDto.getAttribute()
                ,memberDto.getNameAttributekey());

    }

    // 저장 메소드
    private MemberEntity saveorupdate(MemberDto memberDto) {

        return memberRepository.save(memberDto.toEntity());

    }

}
