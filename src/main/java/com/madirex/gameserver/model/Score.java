package com.madirex.gameserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "score")
@ToString
public class Score {
    private String id;

    @Column(nullable = false)
    private User user;

    @Column(nullable = false)
    private int level;

    private int amount;

    private LocalDateTime date;

    public Score(User user, int level, int amount, LocalDateTime date) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.level = level;
        this.amount = amount;
        this.date = date;
    }

    @Id
    @NotBlank(message = "El id no puede estar vacío")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @NotBlank(message = "La cantidad de score no puede estar vacío")
    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NotNull(message = "La fecha no puede ser nula")
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}