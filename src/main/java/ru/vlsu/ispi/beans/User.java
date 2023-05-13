package ru.vlsu.ispi.beans;

import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vlsu.ispi.enums.Gender;
import ru.vlsu.ispi.enums.RoleType;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name="Users")
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String contactnumber;

    /*
    private Date registerDate;
    private Date birthdayDate;
    private float rating;
    private String resume;
    private float balance;
    private float bonus;
    */

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}