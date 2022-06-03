package com.madirex.gameserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@ToString
public class User implements UserDetails {
    @Column(unique = true)
    private String id;
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private Integer money;

    @ToString.Exclude
    private Set<Login> logins;

    private Set<UserRole> roles;

    private Set<Item> inventory;

    private Set<Score> scores;

    public User(String username, String password, Integer money, String email, Set<Login> logins, Set<UserRole> roles,
                Set<Item> inventory, Set<Score> scores) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.money = Objects.requireNonNullElse(money, 0);
        this.email = email;
        this.logins = logins;
        this.roles = roles;
        this.inventory = Objects.requireNonNullElse(inventory, new LinkedHashSet<>());
        this.scores = scores;
    }

    @Id
    @NotBlank(message = "El id no puede estar vacío")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(unique = true)
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "La password no puede estar vacía")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "El dinero no puede estar vacío")
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Column(unique = true)
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser un email válido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    public Set<Login> getLogins() {
        return logins;
    }

    public void setLogins(Set<Login> logins) {
        this.logins = logins;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REFRESH)
    public Set<Item> getInventory() {
        return inventory;
    }

    public void setInventory(Set<Item> inventory) {
        this.inventory = inventory;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.name())).collect(Collectors.toList());
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}