package com.example.FinalProject.entity;

import com.example.FinalProject.enums.Role;
import com.example.FinalProject.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    private String bio;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodeConfirm> codeConfirms = new ArrayList<>();
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;
    private LocalDateTime removeDate;

    @Column(name = "is_blocked")
    private Boolean isBlocked = false;

    @Column(name = "is_un_blocked")
    private Boolean isUnBlocked = false;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;

    @Column(name = "un_blocked_at")
    private LocalDateTime unBlockedAt;

    private int bonus;

    public User(String username, String mail, int i) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUnBlockedAt(LocalDateTime unBlockedAt) {
        this.unBlockedAt = unBlockedAt;
    }

    public void setUnBlocked(Boolean unBlocked) {
        isUnBlocked = unBlocked;
    }

    public void setUserStatus(UserStatus userStatus, String s) {
    }
}

