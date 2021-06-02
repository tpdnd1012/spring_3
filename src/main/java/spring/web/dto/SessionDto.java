package spring.web.dto;

import lombok.Builder;
import lombok.Getter;
import spring.domain.MemberEntity;
import spring.domain.Role;

@Getter
public class SessionDto {

    private String name;
    private String email;
    private Role role;

    @Builder
    public SessionDto(MemberEntity entity) {
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.role = entity.getRole();
    }
}
