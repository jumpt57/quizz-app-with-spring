package com.jumpt57.quizz.repositories;

import com.jumpt57.quizz.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
