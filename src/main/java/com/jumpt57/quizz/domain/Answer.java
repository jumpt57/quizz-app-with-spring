package com.jumpt57.quizz.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Table(name = "answers")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "answers-uuid")
    @GenericGenerator(name = "answers-uuid", strategy = "uuid2")
    Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    Question question;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    User user;

    @Column(nullable = false)
    Integer score;

    @Column(nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    LocalDateTime date;
}
