package com.example.FinalProject.entity;

import com.example.FinalProject.enums.Role;
import com.example.FinalProject.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^([a-zA-Z0-9\\\\-\\\\.\\\\_]+)'+'(\\\\@)([a-zA-Z0-9\\\\-\\\\.]+)'+'(\\\\.)([a-zA-Z]{2,4})$")
    private String email;
    @Size(min = 3, max = 20)
    private String username;
    @Size(max = 50)
    private String password;
    @Size(max = 50)
    private String bio;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodeConfirm> codeConfirms = new ArrayList<>();
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;
    private Date removeDate;

    public User(String username) {
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
}

