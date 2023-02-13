package com.ezen.gomgome.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.UserCategory;
import com.ezen.gomgome.entity.UserCategoryId;

@Transactional
public interface UserCategoryRepository extends JpaRepository<UserCategory, UserCategoryId>{
	@Query(value="SELECT A.*\r\n"
			+ "     , C.USER_ID\r\n"
			+ "	FROM T_GOMG_MAINCATEGORY A\r\n"
			+ "    LEFT OUTER JOIN (\r\n"
			+ "						SELECT B.*\r\n"
			+ "							FROM T_GOMG_USER_CATEGORY B\r\n"
			+ "                            WHERE USER_ID = :userId\r\n"
			+ "					) C\r\n"
			+ "	  ON A.MAINCATEGORY_NO = C.MAINCATEGORY_NO", nativeQuery = true)
	List<CamelHashMap> getCateInfoList(@Param("userId") String userId);

	void deleteByUserId(String userId);

}
