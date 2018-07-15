package com.jumpt57.quizz.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "users-uuid")
    @GenericGenerator(name = "users-uuid", strategy = "uuid2")
    UUID id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = false)
    Boolean enabled;

    @Column(name = "avatar_url")
    String avatarUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Answer> answers;

}
