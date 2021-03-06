package spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import spring.service.Oauth2Service;

@Configuration // 외부 라이브러리 설정 값 변경시
@EnableWebSecurity // 시큐리티 정의시 사용, 기본적인 Web 보안을 활성화 하겠다는 어노테이션 ( extends WebSecurityConfigurerAdapter )
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Oauth2Service oauth2Service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/member/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll() // 모든 접근 허용

        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
                .logoutSuccessUrl("/") // 로그아웃 성공시
                .invalidateHttpSession(true) // 세션 초기화

        .and() // 연결 메소드
                .csrf() // 사이트 간 요청 위조
                .ignoringAntMatchers("/h2-console/**") // 사이트 간 요청 위조 방지를 제거해서 console 사용

        .and()
                .logout() // 로그아웃 관련 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL 설정
                .logoutSuccessUrl("/") // 로그아웃 성공시 URL
                .invalidateHttpSession(true) // 세션 초기화

        .and()
                .oauth2Login() // Oauth2 로그인 설정
                .userInfoEndpoint()
                .userService(oauth2Service);

        http.headers().frameOptions().disable(); // h2 접근 하기 위해 frame 제거

    }

}
