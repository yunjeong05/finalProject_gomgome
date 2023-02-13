package com.ezen.gomgome.repository;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import com.ezen.gomgome.dto.UserDTO;
import com.ezen.gomgome.entity.User;

@Transactional
public interface UserRepository extends JpaRepository <User, String> {
	@Modifying
	@Query(value="UPDATE T_GOMG_USER SET USER_PLANNER_TOTAL = USER_PLANNER_TOTAL + 1 WHERE USER_ID = :userId", nativeQuery=true)
	void addNumberOfPlan(@Param("userId") String userId);
	
	@Modifying
	@Query(value="UPDATE T_GOMG_USER SET USER_PLANNER_TOTAL = USER_PLANNER_TOTAL - 1 WHERE USER_ID = :userId", nativeQuery=true)
	void subNumberOfPlan(@Param("userId") String userId);
	
	Optional<User> findByUserNickname(String userNickname);
	
	Optional<User> findByUserNameAndUserEmail(@Param("userName") String userName, @Param("userEmail") String userEmail);
	
	@Modifying
	@Query(value="UPDATE T_GOMG_USER"
			+ "		SET USER_PW = :userPw "
			+ "		WHERE USER_ID = :userId", nativeQuery=true)
	void saveTempPw(@Param("userId") String userId, @Param("userPw") String userPw);

	@Modifying
	@Query (value = "UPDATE T_GOMG_USER"
			+ "			SET USER_PW = :userPw"
			+ "         WHERE USER_ID = :userId", nativeQuery=true)
	int pwChange(@Param("userId") String userId, @Param("userPw") String userPw);
/*
	@Query (value="SELECT * FROM T_GOMG_USER"
			+ "			WHERE USER_ID = :userId"
			+ "			AND USER_DELETE = 'N'",
			nativeQuery=true)
	Optional<User> findByIdAndUserDelete(@Param("userId") String userId, @Param("userDelete") String userDelete);
*/
//	@Transactional
	@Modifying
	@Query (value = "UPDATE T_GOMG_USER"
			+ "			SET USER_DELETE = 'Y'"
			+ "         WHERE USER_ID = :userId" , nativeQuery=true)
	int deleteInfo(@Param("userId") String userId);



}

