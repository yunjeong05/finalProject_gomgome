package com.ezen.gomgome.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {
	@Query(value="SELECT * FROM T_GOMG_NOTE WHERE USER_ID = :userId ORDER BY NOTE_REGDATE DESC", nativeQuery=true)
	List<Note> findAllByUser(@Param("userId") String userId);
}
