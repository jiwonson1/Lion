package com.code.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @Column(nullabe =true, unique = true, length = 30)
    private String id;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = true, unique = false)
    private String state; // Y : 정상 회원 , L : 잠긴 계정, P : 패스워드 만료, A : 계정 만료

    // security 기본 회원 정보 UserDetails 클래스 implement 하기 위한 함수

    // 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    // 계정 만료
    @Override
    public boolean isAccountNonExpired() {
        if(StringUtils.equalsIgnoreCase(state, "A")){
            return false;
        }
        return true;
    }

    // 잠긴 계정
    @Override
    public boolean isAccountNonLocked() {
        if(StringUtils.equalsIgnoreCase(state, "L")){
            return false;
        }
        return true;
    }

    // 패스워드 만료
    @Override
    public boolean isCredentialsNonExpired() {
        if(StringUtils.equalsIgnoreCase(state, "P")){
            return false;
        }
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(isCredentialsNonExpired() && isAccountNonExpired() && isAccountNonLocked()){
            return true;
        }
        return false;
    }
}

