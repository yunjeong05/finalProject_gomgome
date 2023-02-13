package com.ezen.gomgome.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.ezen.gomgome.entity.Question;

public interface QuestionRepository  extends JpaRepository<Question, Integer> {

	
	@Query(value="SELECT * FROM t_gomg_question", nativeQuery=true)
	List<Question> getInquiryList();


	
	
}
