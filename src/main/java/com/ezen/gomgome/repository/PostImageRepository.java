package com.ezen.gomgome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.entity.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
	@Query(value="SELECT IFNULL(MAX(A.POST_IMAGE_NO), 0) + 1"
			+ "		FROM T_GOMG_POST_IMAGE A"
			+ "		WHERE A.POST_NO = :postNo", nativeQuery=true)
	int getNextPostImgNo(@Param("postNo") int postNo);

	List<PostImage> findByPostNo(@Param("postNo") int postNo);

}
