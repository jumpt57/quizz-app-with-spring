package com.jumpt57.quizz.domain;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Table(name = "questions")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "questions-uuid")
    @GenericGenerator(name = "questions-uuid", strategy = "uuid2")
    Long id;

    @Column(nullable = false, length = 50)
    String query;

    @Column(nullable = false)
    Boolean mandatory;

    @OneToMany(mappedBy = "question")
    List<Answer> answers;

}
