package com.jumpt57.quizz.repositories;

import com.jumpt57.quizz.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
